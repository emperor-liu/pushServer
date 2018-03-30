/**
 * Project Name pushServer
 * File Name package-info.java
 * Package Name com.lljqiu.tools.pushServer.action
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: jie_liu1@asdc.com.cn
 * Copyright © 2006, 2017, ASDC DAI. All rights reserved.
 */
package com.lljqiu.tools.pushServer.action;

import java.util.List;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.lljqiu.tools.pushServer.stack.SessionUsers;
import com.lljqiu.tools.pushServer.utils.Constants;

/** 
 * ClassName: Action102.java <br>
 * Description: 客户端注册用户信息<br>
 * Create by: name：liujie <br>email: jie_liu1@asdc.com.cn <br>
 * Create Time: 2017年6月6日<br>
 */
public class Action102 extends ActionFactoy {

    private static Logger log = LoggerFactory.getLogger(Action102.class);

    /* (non-Javadoc)
     * @see com.asdc.messagepush.action.ActionFactoy#exec()
     */
    @Override
    protected void exec() throws Exception {
        JSONObject json = new JSONObject();
        try {
            log.info("<client register userinfo...>");
            String userInfo = new String(message.getBody());
            JSONObject userJson = JSONObject.parseObject(userInfo);
            String userId = userJson.getString("userId");
            String sessionid = userJson.getString("sessionid");
            log.info("<userId = " + userId + " & sessionid=" + sessionid + ">");
    
            //获取当前用户所有连接 用户sessionid
            
            List<String> keys = redisClient.getLrange(Constants.CONNECTION_USER_KEY+userId, 0, -1);
            log.debug("keys{}",keys);
            for(String key : keys){
                IoSession userSession = (IoSession) SessionUsers.getInstance().getSession(key);
                if(userSession == null || userSession.isClosing()){
                    // 清楚所有已断开的链接
                    redisClient.lremValue(Constants.CONNECTION_USER_KEY+userId,key);
                    SessionUsers.getInstance().clearSession(key);
                }
            }
            redisClient.lpush(Constants.CONNECTION_USER_KEY+userId, sessionid);
            SessionUsers.getInstance().addUserSession(sessionid, session);
            
            json.put("status", Constants.SUCCESS);
            json.put("result", "register user success");
            
            message.setBody(json.toString().getBytes(Constants.CHARTSET));
            message.setBodyLength(json.toString().getBytes(Constants.CHARTSET).length);
        } catch (Exception e) {
            log.error("register error {}",e);
            json.put("status", Constants.FAILURE);
            json.put("result", "register user error");
            
            message.setBody(json.toString().getBytes(Constants.CHARTSET));
            message.setBodyLength(json.toString().getBytes(Constants.CHARTSET).length);
        }
        log.debug("response info{}",json.toJSONString());
        session.write(message.toByte());
    }

}
