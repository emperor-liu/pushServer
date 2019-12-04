/**
 * Project Name pushServer
 * File Name package-info.java
 * Package Name com.lljqiu.tools.pushServer.codec
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2017, www.lljqiu.com. All rights reserved.
 */
package com.huxiaosu.tools.pushserver.codec;

import java.nio.charset.CharacterCodingException;
import java.util.Arrays;
import java.util.Random;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.huxiaosu.tools.pushserver.utils.Constants;
import com.huxiaosu.tools.pushserver.utils.LogUtils;
import com.huxiaosu.tools.pushserver.utils.WSToolKit;
import com.huxiaosu.tools.pushserver.utils.WSToolKit.WSSessionState;

/**
 * ClassName: MPMessageEncoder.java <br>
 * Description: <br>
 *
 * @author liujie <br>email: liujie@huxiaosu.com <br>
 * Create Time: 2017年6月6日<br>
 */
public class MPMessageEncoder extends ProtocolEncoderAdapter {

    private String charset;
    private int defaultPageSize = 65536;
    private boolean isMasking = false;

    public void setIsMasking(boolean masking) {
        this.isMasking = masking;
    }

    public MPMessageEncoder() {
        this.setCharset("UTF-8");
    }

    public MPMessageEncoder(String charset) {
        this.setCharset(charset);
    }


    @Override
    public void encode(IoSession paramIoSession, Object message, ProtocolEncoderOutput out)
            throws Exception {

        IoBuffer buff = IoBuffer.allocate(1024).setAutoExpand(true);

        WSSessionState status = WSToolKit.getSessionState(paramIoSession);
        if (status != null && status.equals(WSSessionState.WSHandshake)) {
            // 此处响应 websocket 握手信息
            try {
                if (message instanceof String) {
                    buff.putString((String) message, Constants.CHAR_SETDE_ECODER).flip();
                    paramIoSession.setAttribute(WSSessionState.ATTRIBUTE_KEY, WSSessionState.Connected);
                    out.write(buff);
                } else {
                    byte[] bytes = (byte[]) message;
                    LogUtils.debug("<encode message " + Arrays.toString(bytes) + ">");
                    IoBuffer buf = IoBuffer.allocate(bytes.length, false);
                    buf.put(bytes);

                    buf.flip();
                    out.write(buf);
                }


            } catch (CharacterCodingException e) {
                paramIoSession.close(true);
            }

        } else if (status != null && status.equals(WSSessionState.Connected)) {
            // 暂时没有用到
            if (!paramIoSession.isConnected() || message == null) {
                return;
            }

            byte dataType = 1;

            // 将数据统一转换成byte数组进行处理。  
            byte[] data = null;
            if (message instanceof String) {
                data = ((String) message).getBytes(Constants.CHAR_SETDE_ECODER.charset());
            } else {
                data = (byte[]) message;
                dataType = 2;
            }

            // 生成掩码。  
            byte[] mask = new byte[4];
            Random random = new Random();

            // 用掩码处理数据。  
            for (int i = 0, limit = data.length; i < limit; i++) {
                data[i] = (byte) (data[i] ^ mask[i % 4]);
            }

            /**
             * 以分片的方式向客户端推送数据。 
             */
            int pageSize = this.defaultPageSize;
            int remainLength = data.length;
            int realLength = 0;
            int dataIndex = 0;
            for (boolean isFirstFrame = true, isFinalFrame = false; !isFinalFrame; buff
                    .clear(), isFirstFrame = false) {

                int headerLeng = 2;

                int payload = 0;
                if (remainLength > 0 && remainLength <= 125) {
                    payload = remainLength;
                } else if (remainLength > 125 && remainLength <= 65536) {
                    payload = 126;
                } else {
                    payload = 127;
                }

                switch (payload) {
                    case 126:
                        headerLeng += 2;
                        break;

                    case 127:
                        headerLeng += 8;
                        break;

                    default:
                        headerLeng += 0;
                        break;
                }

                headerLeng += isMasking ? 4 : 0;

                // 计算当前帧中剩余的可用于保存“负载数据”的字节长度。  
                realLength = (pageSize - headerLeng) >= remainLength ? remainLength
                        : (pageSize - headerLeng);

                // 判断当前帧是否为最后一帧。  
                isFinalFrame = (remainLength - (pageSize - headerLeng)) < 0;

                // 生成第一个字节  
                byte fstByte = (byte) (isFinalFrame ? 0x80 : 0x0);
                // 若当前帧为第一帧，则还需保存数据类型信息。  
                fstByte += isFirstFrame ? dataType : 0;
                buff.put(fstByte);

                // 生成第二个字节。判断是否需要掩码，若需要掩码，则置标志位的值为1.  
                byte sndByte = (byte) (isMasking ? 0x80 : 0);
                // 保存payload信息。  
                sndByte += payload;
                buff.put(sndByte);

                switch (payload) {
                    case 126:
                        buff.putUnsignedShort(realLength);
                        break;

                    case 127:
                        buff.putLong(realLength);
                        break;

                    default:
                        break;
                }
                if (isMasking) {
                    random.nextBytes(mask);
                    buff.put(mask);
                }
                for (int loopCount = dataIndex
                        + realLength, i = 0; dataIndex < loopCount; dataIndex++, i++) {
                    buff.put((byte) (data[i] ^ mask[i % 4]));
                }

                buff.flip();
                out.write(buff);

                remainLength -= (pageSize - headerLeng);
            }
        } else {
            byte[] bytes = (byte[]) message;
            LogUtils.debug("<encode message " + Arrays.toString(bytes) + ">");
            IoBuffer buf = IoBuffer.allocate(bytes.length, false);
            buf.put(bytes);

            buf.flip();
            out.write(buf);
        }

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

}
