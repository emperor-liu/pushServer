/**
 * Project Name pushServer
 * File Name KeepAliveMessageFactoryImpl.java
 * Package Name com.huxiaosu.tools.pushserver.filter
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: liujie@huxiaosu.com
 */
package com.huxiaosu.tools.pushserver.filter;

import java.io.UnsupportedEncodingException;

import com.huxiaosu.tools.pushserver.stack.BaseMessage;
import com.huxiaosu.tools.pushserver.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * ClassName: KeepAliveMessageFactoryImpl.java <br>
 * Description: <br>
 * @author name：liujie <br>email: liujie@huxiaosu.com <br>
 * @date: 2018年3月15日<br>
 */
@Slf4j
public class KeepAliveMessageFactoryImpl implements KeepAliveMessageFactory {

    @Override
    public boolean isRequest(IoSession session, Object message) {

        BaseMessage command = null;
        try {
            command = (BaseMessage) message;
        } catch (Exception e) {
            return false;
        }
        if (command.getType() == Constants.T101) {
            log.info("request keepalive ...");
            return true;
        }
        return false;
    }

    @Override
    public boolean isResponse(IoSession session, Object message) {
        return false;
    }

    @Override
    public Object getRequest(IoSession session) {
        return null;
    }

    @Override
    public Object getResponse(IoSession session, Object request) {
        BaseMessage requestMessage = (BaseMessage) request;

        log.debug("服务端接收心跳请求数据：" + ToStringBuilder.reflectionToString(requestMessage));

        JSONObject json = new JSONObject();
        json.put("status", Constants.SUCCESS);
        json.put("result", "keep live success");
        //ResponseMessage responseMessage = new ResponseMessage(Constants.SUCCESS,Constants.T101);
        try {
            requestMessage.setBody(json.toString().getBytes(Constants.CHARTSET));
            requestMessage.setBodyLength(json.toString().getBytes(Constants.CHARTSET).length);
        } catch (UnsupportedEncodingException e) {
            log.debug("响应心跳请求异常" + e);
        }
        return requestMessage.toByte();
    }

}
