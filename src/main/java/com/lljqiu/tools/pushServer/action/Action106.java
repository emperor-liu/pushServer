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
import java.util.List;
import java.util.Set;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.lljqiu.tools.pushServer.stack.BaseMessage;
import com.lljqiu.tools.pushServer.stack.SessionUsers;
import com.lljqiu.tools.pushServer.utils.Constants;

/** 
 * ClassName: Action106.java <br>
 * Description: <br>
 * Create by: name：liujie <br>email: jie_liu1@asdc.com.cn <br>
 * Create Time: 2017年6月23日<br>
 */
public class Action106 extends ActionFactoy{

    private static Logger log = LoggerFactory.getLogger(Action106.class);
    
    /* (non-Javadoc)
     * @see com.asdc.messagepush.action.ActionFactoy#exec()
     */
    @Override
    protected void exec() throws Exception {
        log.info("<==== push deviceStatus status ====>");
//        String jsonMessage = new String(message.getBody(),"UTF-8");
        
//        Map<Object, Object> allMap = SessionUsers.getInstance().getAllMap();
        // 获取所有用户
        Set<String> keys = redisClient.getKeys(Constants.CONNECTION_USER_KEY);
        
        if(keys.size() < 1){
            log.info("<没有在线用户不予发送>");
            return ;
        }
        // 逐个获取用户的 session
        for (String key : keys) {
            
            List<String> userSessionList = redisClient.getLrange(key,0,-1);

            log.debug("<=== userSessionList{} ===>", userSessionList);

            for (String sessionId : userSessionList) {
                IoSession userSession =  (IoSession) SessionUsers.getInstance().getSession(sessionId);
                if (userSession == null) {
                    log.info("<[{}]当前连接已关闭...>",sessionId);
                    SessionUsers.getInstance().clearSession(sessionId);
                    continue;
                }
                boolean sessionStatus = userSession.isConnected();
                if (!sessionStatus) {
                    log.info("<[{}]当前连接已关闭...>",sessionId);
                    SessionUsers.getInstance().clearSession(sessionId);
                    continue;
                }

                userSession.write(message.toByte());
              
            }
        }
        
        BaseMessage responseMessage = new BaseMessage();
        JSONObject json = new JSONObject();
        json.put("status", Constants.SUCCESS);
        json.put("result", "push deviceStatus success");
        try {
            
            responseMessage.setType(Constants.T106);
            responseMessage.setBody(json.toString().getBytes(Constants.CHARTSET));
            responseMessage.setBodyLength(json.toString().getBytes(Constants.CHARTSET).length);
        } catch (UnsupportedEncodingException e) {
            log.debug("响应deviceStatus请求异常{}", e);
        }
        session.write(responseMessage.toByte());
    }

}
