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
 * ClassName: BaseMessage.java <br>
 * Description: <br>
 * @author liujie <br>email: liujie@huxiaosu.com <br>
 * Create Time: 2017年6月1日<br>
 */
@Data
public class BaseMessage implements Serializable {

    /**
     *
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

}
