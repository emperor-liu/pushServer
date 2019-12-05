/**
 * Project Name pushServer
 * File Name package-info.java
 * Package Name com.huxiaosu.tools.pushserver.stack
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: liujie@huxiaosu.com
 *
 */
package com.huxiaosu.tools.pushserver.stack;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

import com.huxiaosu.tools.pushserver.utils.LogUtils;
import lombok.Data;

/** 
 * ClassName: ResponseMessage.java <br>
 * Description: <br>
 * @author liujie <br>email: liujie@huxiaosu.com <br>
 * Create Time: 2017年6月5日<br>
 */
@Data
public class ResponseMessage implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

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
    
}
