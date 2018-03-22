/**
 * Project Name pushServer
 * File Name package-info.java
 * Package Name com.lljqiu.tools.pushServer.codec
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: jie_liu1@asdc.com.cn
 * Copyright © 2006, 2017, ASDC DAI. All rights reserved.
 */
package com.lljqiu.tools.pushServer.codec;

import java.util.Arrays;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.lljqiu.tools.pushServer.stack.BaseMessage;
import com.lljqiu.tools.pushServer.utils.LogUtils;

/** 
 * ClassName: MPMessageDecoder.java <br>
 * Description: <br>
 * Create by: name：liujie <br>email: jie_liu1@asdc.com.cn <br>
 * Create Time: 2017年6月6日<br>
 */
public class MPMessageDecoder extends CumulativeProtocolDecoder {

    private String charset;

    public MPMessageDecoder() {
        this.setCharset("UTF-8");
    }

    public MPMessageDecoder(String charset) {
        this.setCharset(charset);
    }

    @Override
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        
        BaseMessage message = new BaseMessage();
        if (in.remaining() < 4) {
            return false;
        }
        in.mark();
        // 获得消息类型
        byte type = in.get();
        LogUtils.debug("type="+type);
        
        int bodyLength = in.getInt();
        LogUtils.debug("bodyLength="+bodyLength);
        if(bodyLength > in.remaining()){
            return false;
        }
        message.setBodyLength(bodyLength);
        byte[] body = new byte[bodyLength];
        in.get(body);
        LogUtils.debug("body="+Arrays.toString(body));
        message.setBody(body);
        message.setType(type);
        out.write(message);
        in.mark();
        if(in.remaining() > 0){
            return true;
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

}
