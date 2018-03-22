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
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.lljqiu.tools.pushServer.utils.LogUtils;

/** 
 * ClassName: MPMessageEncoder.java <br>
 * Description: <br>
 * Create by: name：liujie <br>email: jie_liu1@asdc.com.cn <br>
 * Create Time: 2017年6月6日<br>
 */
public class MPMessageEncoder extends ProtocolEncoderAdapter {
    
    private String charset;

    public MPMessageEncoder() {
        this.setCharset("UTF-8");
    }

    public MPMessageEncoder(String charset) {
        this.setCharset(charset);
    }

    /* (non-Javadoc)
     * @see org.apache.mina.filter.codec.ProtocolEncoder#encode(org.apache.mina.core.session.IoSession, java.lang.Object, org.apache.mina.filter.codec.ProtocolEncoderOutput)
     */
    @Override
    public void encode(IoSession paramIoSession, Object message, ProtocolEncoderOutput out)
            throws Exception {
        byte[] bytes = (byte[])message;
        LogUtils.debug("<encode message "+Arrays.toString(bytes)+">");
        IoBuffer buf = IoBuffer.allocate(bytes.length, false);
        
        buf.put(bytes);

        buf.flip();
        out.write(buf);

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
