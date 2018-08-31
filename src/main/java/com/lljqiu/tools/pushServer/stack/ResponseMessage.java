/**
 * Project Name pushServer
 * File Name package-info.java
 * Package Name com.lljqiu.tools.pushServer.stack
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: liujie@lljqiu.com
 * Copyright © 2015, 2017, www.lljqiu.com. All rights reserved.
 */
package com.lljqiu.tools.pushServer.stack;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

import com.lljqiu.tools.pushServer.utils.LogUtils;

/** 
 * ClassName: ResponseMessage.java <br>
 * Description: <br>
 * Create by: name：liujie <br>email: liujie@lljqiu.com <br>
 * Create Time: 2017年6月5日<br>
 */
public class ResponseMessage implements Serializable {

    /**
     * Copyright © 2015, 2017, www.lljqiu.com. All rights reserved.
     */
    private static final long serialVersionUID = 1L;

    /**  发送时间 */
    //private String sendTime;
    /**  响应码 */
    private int               respCode;
    /**  响应消息 */
    private byte              type;

    public ResponseMessage(int respCode, byte type) {
        this.respCode = respCode;
        this.type = type;
    }
    public byte[] toByte(){
        ByteArrayOutputStream bous = new ByteArrayOutputStream();
        DataOutputStream dous = new DataOutputStream(bous);
        try {
            dous.writeByte(type);
            dous.writeInt(respCode);
            dous.close();
        } catch (IOException e) {
            LogUtils.error("封装链接二进制数组失败。"+e);
        }
        return bous.toByteArray();
    }

    /**
     * @return the respCode
     */
    public int getRespCode() {
        return respCode;
    }

    /**
     * @param respCode the respCode to set
     */
    public void setRespCode(int respCode) {
        this.respCode = respCode;
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

}
