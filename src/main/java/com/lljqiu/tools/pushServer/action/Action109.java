/**
 * Project Name pushServer
 * File Name package-info.java
 * Package Name com.lljqiu.tools.pushServer.action
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: jie_liu1@asdc.com.cn
 * Copyright © 2006, 2017, ASDC DAI. All rights reserved.
 */
package com.lljqiu.tools.pushServer.action;

import java.io.UnsupportedEncodingException;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.lljqiu.tools.pushServer.stack.BaseMessage;
import com.lljqiu.tools.pushServer.stack.SessionUsers;
import com.lljqiu.tools.pushServer.utils.Constants;

/** 
 * ClassName: Action109.java <br>
 * Description: 平台设备导入<br>
 * Create by: name：liujie <br>email: jie_liu1@asdc.com.cn <br>
 * Create Time: 2017年6月23日<br>
 */
public class Action109 extends ActionFactoy{

    private static Logger log = LoggerFactory.getLogger(Action109.class);
    
    /* (non-Javadoc)
     * @see com.asdc.messagepush.action.ActionFactoy#exec()
     */
    @Override
    protected void exec() throws Exception {
        log.info("<==== push platform importDevice ====>");
        String jsonMessage = new String(message.getBody(),"UTF-8");
        JSONObject requestJson = JSONObject.parseObject(jsonMessage);
        String userSessionId = requestJson.getString("sessionId");
        IoSession userSession =  (IoSession) SessionUsers.getInstance().getSession(userSessionId);
        if (userSession == null) {
            log.info("<[{}]当前连接已关闭...>", userSessionId);
            SessionUsers.getInstance().clearSession(userSessionId);
            return;
        }
        boolean sessionStatus = userSession.isConnected();
        if (!sessionStatus) {
            log.info("<[{}]当前连接已关闭...>", userSessionId);
            SessionUsers.getInstance().clearSession(userSessionId);
            return;
        }
        userSession.write(message.toByte());
        
        BaseMessage responseMessage = new BaseMessage();
        JSONObject json = new JSONObject();
        json.put("status", Constants.SUCCESS);
        json.put("result", "push platform importDevice success");
        try {
            
            responseMessage.setType(Constants.T109);
            responseMessage.setBody(json.toString().getBytes(Constants.CHARTSET));
            responseMessage.setBodyLength(json.toString().getBytes(Constants.CHARTSET).length);
        } catch (UnsupportedEncodingException e) {
            log.debug("响应push platform importDevice请求异常" + e);
        }
        session.write(responseMessage.toByte());
    }

}
