/**
 * Project Name pushServer
 * File Name package-info.java
 * Package Name com.lljqiu.tools.pushServer.codec
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: jie_liu1@asdc.com.cn
 * Copyright © 2006, 2017, ASDC DAI. All rights reserved.
 */
package com.lljqiu.tools.pushServer.codec;

import java.nio.ByteOrder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.StringTokenizer;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.util.Base64;

import com.lljqiu.tools.pushServer.exception.PushServerException;
import com.lljqiu.tools.pushServer.stack.BaseMessage;
import com.lljqiu.tools.pushServer.stack.FrameType;
import com.lljqiu.tools.pushServer.stack.RequestDataContext;
import com.lljqiu.tools.pushServer.utils.Constants;
import com.lljqiu.tools.pushServer.utils.LogUtils;
import com.lljqiu.tools.pushServer.utils.WSToolKit;
import com.lljqiu.tools.pushServer.utils.WSToolKit.WSSessionState;

/** 
 * ClassName: MPMessageDecoder.java <br>
 * Description: <br>
 * Create by: name：liujie <br>email: jie_liu1@asdc.com.cn <br>
 * Create Time: 2017年6月6日<br>
 */
public class MPMessageDecoder extends CumulativeProtocolDecoder {

    private String              charset;
    private final static String END_TAG   = "\r\n";
    private ByteOrder           byteOrder = ByteOrder.BIG_ENDIAN;
    private final static String REQUEST_CONTEXT_KEY = "__REQUEST_DATA_CONTEXT";  


    public MPMessageDecoder() {
        this.setCharset("UTF-8");
    }

    public MPMessageDecoder(String charset) {
        this.setCharset(charset);
    }

    @Override
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
            throws Exception {

        BaseMessage message = new BaseMessage();
        if (in.remaining() < 4) {
            return false;
        }
        in.mark();
        LogUtils.debug("<session={}>", session);
        WSSessionState sessionState = WSToolKit.getSessionState(session);
        LogUtils.debug("<getSessionState={}>", sessionState);
        if (sessionState != null && sessionState.equals(WSSessionState.Connected)) {
            if (in.remaining() < 2)
                return false;

            in.mark().order(this.byteOrder);

            byte fstByte = in.get();

            int opCode = fstByte & 0xf;
            LogUtils.debug("<opCode={}>", opCode);
            switch (opCode) {
                case 0x0:
                    LogUtils.debug("<case 0x0 "); break;
                case 0x1:
                    // 次请求为客户端发送 string 数据
                    LogUtils.debug("<case 0x0 > "); break;
                case 0x2:
                    // websocket 发送 buffer 数据
                    LogUtils.debug("< case 0x2 opCode={}>", opCode);
                    boolean isFinalFrame2 = fstByte < 0;
                    boolean isRsvColZero2 = (fstByte & 0x70) == 0;
                    if (!isRsvColZero2) {
                        closeConnection(session, in);
                        break;
                    }

                    byte secByte2 = in.get();
                    boolean isMasking2 = secByte2 < 0;

                    int dataLength2 = 0;
                    byte payload2 = (byte) (secByte2 & 0x7f);
                    if (payload2 == 126)
                        dataLength2 = in.getUnsignedShort();
                    else if (payload2 == 127)
                        dataLength2 = (int) in.getLong();
                    else
                        dataLength2 = payload2;

                    if (in.remaining() < (isMasking2 ? dataLength2 + 4 : dataLength2)) {
                        in.reset();
                        return false;
                    }

                    byte[] mask2 = new byte[4];
                    byte[] data2 = new byte[dataLength2];

                    if (isMasking2)
                        in.get(mask2);

                    in.get(data2);

                    // 用掩码处理数据。  
                    for (int i = 0, maskLength2 = mask2.length, looplimit = data2.length; i < looplimit; i++)
                        data2[i] = (byte) (data2[i] ^ mask2[i % maskLength2]);

                    // 创建一个对象保存“数据帧的数据类型”。协议规定——对于分片的数据只有第一帧会携带数据类型信息，所以要新建对象保存数据类型，以应对分片。  
                    RequestDataContext context2 = (RequestDataContext) session
                            .getAttribute(REQUEST_CONTEXT_KEY);
                    if (context2 == null) {
                        context2 = new RequestDataContext(Constants.CHAR_SETDE_DCODER.charset().name());
                        context2.setFrameType((opCode == 0x1) ? FrameType.Text : FrameType.Binary);
                        session.setAttribute(REQUEST_CONTEXT_KEY, context2);
                    }

                    context2.append(data2);
                    if (isFinalFrame2) {
                        context2 = (RequestDataContext) session.removeAttribute(REQUEST_CONTEXT_KEY);

                        if (context2.getFrameType() == FrameType.Text){
                            
                            out.write(context2.getDataAsString());
                        } else{
                            byte[] dataAsArray = context2.getDataAsArray();
                            BaseMessage writeMessage = new BaseMessage();
                            writeMessage.setType(dataAsArray[0]);
                            LogUtils.debug("<case 0x2 writeMessage type={}>", dataAsArray[0]);
                            byte[] messageLengthByte = new byte[4];
                            System.arraycopy(dataAsArray, 1, messageLengthByte, 0, 4);
                            int messageLength = WSToolKit.bytes2int(messageLengthByte);
                            LogUtils.debug("<case 0x2 writeMessage messageLength={}>", messageLength);
                            writeMessage.setBodyLength(messageLength);
                            byte[] messageBytes = new byte[messageLength];
                            
                            System.arraycopy(dataAsArray, 5, messageBytes, 0, messageLength);
                            String messageStr = new String(messageBytes);
                            LogUtils.debug("<case 0x2 writeMessage messageBytes={}>", Arrays.toString(messageBytes));
                            LogUtils.debug("<case 0x2 writeMessage messageString={}>", messageStr);
                            writeMessage.setBody(messageBytes);
                            out.write(writeMessage);
                        }
                        return true;
                    } else{
                        return false;
                    }

                case 0x3:
                case 0x4:
                case 0x5:
                case 0x6:
                case 0x7:
                    break;
                case 0x8:
                    closeConnection(session, in);
                    break;
                case 0x9:
                case 0xA:
                default:
                    closeConnection(session, in);
                    break;
            }
        }else{
            byte type = in.get();
            LogUtils.debug("<type={}>", type);
            if (isWS(type)) {
                LogUtils.debug("<ws send={}>", "1111111");
                try {
                    WSToolKit.setSessionState(session);
                    String handshakeMessage = in.getString(Constants.CHAR_SETDE_DCODER);
                    handshakeMessage = "G" + handshakeMessage;
                    String[] msgColumns = splitHandshakeMessage(handshakeMessage);
                    String requestURI = msgColumns[0];
                    String httpVersion = requestURI.substring(requestURI.lastIndexOf("/") + 1,
                            requestURI.length());
                    String upgradeCol = WSToolKit.getMessageColumnValue(msgColumns, "Upgrade:");
                    String connectionCol = WSToolKit.getMessageColumnValue(msgColumns, "Connection:");
                    String secWsProtocolCol = WSToolKit.getMessageColumnValue(msgColumns,
                            "Sec-WebSocket-Protocol:");
                    String secWskeyCol = WSToolKit.getMessageColumnValue(msgColumns,
                            "Sec-WebSocket-Key:");
                    String wsVersionCol = WSToolKit.getMessageColumnValue(msgColumns,
                            "Sec-WebSocket-Version:");
                    // 校验重要字段。任何字段不满足条件，都会导致握手失败！  
                    boolean hasWebsocket = contains(upgradeCol, "websocket");
                    boolean hasUpgrade = contains(connectionCol, "upgrade");
                    boolean isGetMethod = "GET"
                            .equalsIgnoreCase(WSToolKit.subString(requestURI, 1, " "));

                    boolean isSecWsKeyNull = secWskeyCol == null || secWskeyCol.isEmpty();
                    boolean isValidVersion = "13".equals(wsVersionCol);
                    boolean isValidHttpVer = Float.parseFloat(httpVersion) >= 1.1F;

                    if (!hasWebsocket || !hasUpgrade || !isGetMethod)
                        throw new PushServerException("Invalid websocket request!");

                    if (isSecWsKeyNull || !isValidVersion || !isValidHttpVer)
                        throw new PushServerException("Invalid websocket request!");

                    String secWsAccept = getSecWebsocketAccept(secWskeyCol);
                    String response = getResponseData(upgradeCol, connectionCol, secWsAccept,
                            secWsProtocolCol);

                    initRequestContext(session, msgColumns);
                    in.mark();
                    session.write(response);
                } catch (Exception e) {
                    LogUtils.error("websocket error{}", e);
                }
            } else {
                // 获得消息类型

                int bodyLength = in.getInt();
                LogUtils.debug("<bodyLength={}>", bodyLength);
                if (bodyLength > in.remaining()) {
                    return false;
                }
                message.setBodyLength(bodyLength);
                byte[] body = new byte[bodyLength];
                in.get(body);
                LogUtils.debug("<body={}>", Arrays.toString(body));
                message.setBody(body);
                message.setType(type);
                out.write(message);
                in.mark();
                if (in.remaining() > 0) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @return the charset
     */
    public String getCharset() {
        return charset;
    }

    /**
     * @param charset the charset to set
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }

    private void initRequestContext(IoSession session, String[] data) {
        session.setAttribute("__SESSION_CONTEXT", data);
    }

    private String[] splitHandshakeMessage(String handshakeMessage) {
        StringTokenizer st = new StringTokenizer(handshakeMessage, END_TAG);
        String[] result = new String[st.countTokens()];

        for (int i = 0; st.hasMoreTokens(); i++)
            result[i] = st.nextToken();

        return result;
    }

    private boolean contains(String src, String target) {
        if (src == null || src.isEmpty())
            return false;
        else
            return src.toLowerCase().contains(target);
    }

    private String getSecWebsocketAccept(String secWebsocketkey) throws NoSuchAlgorithmException {

        StringBuilder srcBuilder = new StringBuilder();
        srcBuilder.append(secWebsocketkey);
        srcBuilder.append("258EAFA5-E914-47DA-95CA-C5AB0DC85B11");

        MessageDigest md = MessageDigest.getInstance("SHA-1");

        md.update(srcBuilder.toString().getBytes(Constants.CHAR_SETDE_DCODER.charset()));
        byte[] ciphertext = md.digest();

        String result = new String(Base64.encodeBase64(ciphertext),
                Constants.CHAR_SETDE_DCODER.charset());
        return result;
    }

    private String getResponseData(String upgrade, String connection, String secWsAccept,
                                   String secWsProtocol) {
        StringBuilder result = new StringBuilder();
        result.append("HTTP/1.1 101 Switching Protocols\r\n");
        result.append("Upgrade:").append(upgrade).append(END_TAG);
        result.append("Connection:").append(connection).append(END_TAG);
        result.append("Sec-WebSocket-Accept:").append(secWsAccept).append(END_TAG);
        if (secWsProtocol != null && !"".equals(secWsProtocol))
            result.append("Sec-WebSocket-Protocol:").append(secWsProtocol).append(END_TAG);
        result.append(END_TAG);
        return result.toString();
    }

    private boolean isWS(byte type) {
        boolean result = true;
        if (type == Constants.T101 || type == Constants.T102 || type == Constants.T103
                || type == Constants.T104 || type == Constants.T105 || type == Constants.T106
                || type == Constants.T107 || type == Constants.T108 || type == Constants.T109) {
            result = false;
        }
        return result;
    }

    private void closeConnection(IoSession session, IoBuffer buffer) {
        buffer.free();
        session.close(true);
    }

    @SuppressWarnings("unused")
    private void closeConnection(IoSession session, String errorMsg) {
        session.write(errorMsg).addListener(new IoFutureListener<WriteFuture>() {
            public void operationComplete(WriteFuture future) {
                future.getSession().close(true);
            }
        });
    }
}
