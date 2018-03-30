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

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.lljqiu.tools.pushServer.stack.BaseMessage;
import com.lljqiu.tools.pushServer.stack.SessionUsers;
import com.lljqiu.tools.pushServer.utils.Constants;

/** 
 * ClassName: Action103.java <br>
 * Description: 接受业务平台告警信息<br>
 * Create by: name：liujie <br>email: jie_liu1@asdc.com.cn <br>
 * Create Time: 2017年6月5日<br>
 */
public class Action103 extends ActionFactoy{

    private static Logger log = LoggerFactory.getLogger(Action103.class);
    /* (non-Javadoc)
     * @see com.asdc.messagepush.action.ActionFactoy#exec()
     */
    @Override
    protected void exec() throws Exception {
        try {
            log.info("<此请求为业务平台上报告警数据=====>");
            String warningInfo = new String(message.getBody());
            JSONObject warnInfo = JSONObject.parseObject(warningInfo);
            log.debug("<warningInfo="+warnInfo.toJSONString()+">");
            String userId = warnInfo.getString("userId");
            
            List<String> keys = redisClient.getLrange(Constants.CONNECTION_USER_KEY+userId,0,-1);
            
            log.debug("<当前用户连接数：userId={}连接数：{}",userId,keys.size());
            
            for(String key : keys){
                IoSession userSession =  (IoSession) SessionUsers.getInstance().getSession(key);
                if(userSession == null){
                    log.info("<用户[{}]已关闭次连接[{}]>",userId,key);
                    SessionUsers.getInstance().clearSession(key);
                    continue;
                }
                boolean sessionStatus = userSession.isConnected();
                if(!sessionStatus){
                    log.info("<用户[{}]已关闭次连接[{}]>",userId,key);
                    SessionUsers.getInstance().clearSession(key);
                    continue;
                }
                
                userSession.write(message.toByte());
                log.info("<push warning message success to [{}=>>{}]>",userId,key);
                
            }
            
            JSONObject json = new JSONObject();
            json.put("status", Constants.SUCCESS);
            json.put("result", "push warning message success to [{"+userId+"}=>>{"+keys+"}]");
            BaseMessage responseMessage = new BaseMessage();
            try {
                responseMessage.setType(Constants.T103);
                responseMessage.setBody(json.toString().getBytes(Constants.CHARTSET));
                responseMessage.setBodyLength(json.toString().getBytes(Constants.CHARTSET).length);
            } catch (UnsupportedEncodingException e) {
                log.debug("响应告警推送请求异常{}" ,e);
            }
           
            session.write(responseMessage.toByte());
        } catch (Exception e) {
            log.error("发送消息失败{}",e);
        }
    }

}
