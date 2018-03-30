/**
 * Project Name pushServer
 * File Name RequestDataContext.java
 * Package Name com.lljqiu.tools.pushServer.stack
 * Create Time 2018年3月30日
 * Create by name：liujie -- email: jie_liu1@asdc.com.cn
 * Copyright © 2006, 2017, ASDC DAI. All rights reserved.
 */
package com.lljqiu.tools.pushServer.stack;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;

import com.lljqiu.tools.pushServer.utils.LogUtils;

/** 
 * ClassName: RequestDataContext.java <br>
 * Description: webSocket 内容解析<br>
 * Create by: name：liujie <br>email: jie_liu1@asdc.com.cn <br>
 * Create Time: 2018年3月30日<br>
 */
public class RequestDataContext {
    private IoBuffer       _tmp;
    private CharsetDecoder _charsetDecoder;
    private FrameType      _frameType;

    public RequestDataContext(String charset) {
        this._tmp = IoBuffer.allocate(512).setAutoExpand(true);

        this._charsetDecoder = Charset.forName("utf-8").newDecoder();
    }

    public FrameType getFrameType() {
        return this._frameType;
    }

    public String getDataAsString() {
        try {
            LogUtils.debug("<case 0x1 _tmp={}> ", _tmp);
            _tmp.flip();
            return _tmp.getString(_charsetDecoder);
        } catch (CharacterCodingException e) {
            return null;
        }
    }

    public byte[] getDataAsArray() {
        _tmp.flip();

        byte[] data = new byte[_tmp.remaining()];
        _tmp.get(data);

        return data;
    }

    public void append(byte[] data) {
        this._tmp.put(data);
    }

    public void setFrameType(FrameType _frameType) {
        this._frameType = _frameType;
    }
}
