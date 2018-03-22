/**
 * Project Name pushServer
 * File Name package-info.java
 * Package Name com.lljqiu.tools.pushServer.codec
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: jie_liu1@asdc.com.cn
 * Copyright © 2006, 2017, ASDC DAI. All rights reserved.
 */
package com.lljqiu.tools.pushServer.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/** 
 * ClassName: MPCodecFactory.java <br>
 * Description: <br>
 * Create by: name：liujie <br>email: jie_liu1@asdc.com.cn <br>
 * Create Time: 2017年6月6日<br>
 */
public class MPCodecFactory implements ProtocolCodecFactory {

    /**
     * 编码器
     */
    private MPMessageEncoder encoder;
    /**
     * 解码器
     */
    private MPMessageDecoder decoder;

    public MPCodecFactory(String charset) {
        encoder = new MPMessageEncoder(charset);
        decoder = new MPMessageDecoder(charset);
    }

    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return encoder;
    }

    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return decoder;
    }

}
