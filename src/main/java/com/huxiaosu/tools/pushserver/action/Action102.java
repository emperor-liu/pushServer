/**
 * Project Name pushServer
 * File Name package-info.java
 * Package Name com.huxiaosu.tools.pushserver.action
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: liujie@huxiaosu.com
 *
 */
package com.huxiaosu.tools.pushserver.action;

import java.util.List;

import com.huxiaosu.tools.pushserver.stack.SessionUsers;
import com.huxiaosu.tools.pushserver.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/** 
 * ClassName: Action102.java <br>
 * Description: 客户端注册用户信息<br>
 * @author liujie <br>email: liujie@huxiaosu.com <br>
 * Create Time: 2017年6月6日<br>
 */
@Slf4j
public class Action102 extends ActionFactoy {


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
        log.info("response info{}",json.toJSONString());
        session.write(message.toByte());
    }

}
