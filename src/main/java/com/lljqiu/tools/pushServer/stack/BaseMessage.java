/**
 * Project Name pushServer
 * File Name package-info.java
 * Package Name com.lljqiu.tools.pushServer.stack
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: jie_liu1@asdc.com.cn
 * Copyright © 2006, 2017, ASDC DAI. All rights reserved.
 */
package com.lljqiu.tools.pushServer.stack;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

import com.lljqiu.tools.pushServer.utils.LogUtils;

/** 
 * ClassName: BaseMessage.java <br>
 * Description: <br>
 * Create by: name：liujie <br>email: jie_liu1@asdc.com.cn <br>
 * Create Time: 2017年6月1日<br>
 */
public class BaseMessage implements Serializable {

    /**
     * Copyright © 2006, 2017, ASDC DAI. All rights reserved.
     */
    private static final long serialVersionUID = 1L;

    private byte              type;
    private Integer           bodyLength;
    private byte[]            body;

    public byte[] toByte(){
        ByteArrayOutputStream bous = new ByteArrayOutputStream();
        DataOutputStream dous = new DataOutputStream(bous);
        try {
            dous.writeByte(type);
            dous.writeInt(body.length);
            dous.write(body);
            dous.close();
        } catch (IOException e) {
            LogUtils.error("封装链接二进制数组失败。"+e);
        }
        return bous.toByteArray();
    }
    
    /**
     * @return the type
     */
    public byte getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(byte type) {
        this.type = type;
    }

    /**
     * @return the bodyLength
     */
    public Integer getBodyLength() {
        return bodyLength;
    }

    /**
     * @param bodyLength the bodyLength to set
     */
    public void setBodyLength(Integer bodyLength) {
        this.bodyLength = bodyLength;
    }

    /**
     * @return the body
     */
    public byte[] getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(byte[] body) {
        this.body = body;
    }

}
