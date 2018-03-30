/**
 * Project Name pushServer
 * File Name package-info.java
 * Package Name com.lljqiu.tools.pushServer.action
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: jie_liu1@asdc.com.cn
 * Copyright © 2006, 2017, ASDC DAI. All rights reserved.
 */
package com.lljqiu.tools.pushServer.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * ClassName: Action105.java <br>
 * Description: 推送下载链接<br>
 * Create by: name：liujie <br>email: jie_liu1@asdc.com.cn <br>
 * Create Time: 2017年6月23日<br>
 */
public class Action105 extends ActionFactoy{

    private static Logger log = LoggerFactory.getLogger(Action105.class);
    
    /* (non-Javadoc)
     * @see com.asdc.messagepush.action.ActionFactoy#exec()
     */
     @Override
    protected void exec() throws Exception {
         log.error("<暂时没用>");
//        log.info("<==== push download status ====>");
//        String jsonMessage = new String(message.getBody(),"UTF-8");
//        JSONObject requestJson = JSONObject.parseObject(jsonMessage);
//        String sessionId = requestJson.getString("sessionId");
//        log.debug(sessionId);
//        IoSession userSession = redisClient.get(Constants.CONNECTION_USER_KEY+sessionId);
//        if (userSession == null) {
//            log.info("<[{}]当前连接已关闭...>",sessionId);
//            return;
//        }
//        boolean sessionStatus = userSession.isConnected();
//        if (!sessionStatus) {
//            log.info("<[{}]当前连接已关闭...>",sessionId);
//            return;
//        }
//        userSession.write(message.toByte());
//        log.info("<push warning message success to 【" + sessionId + "】>");
//        
//        BaseMessage responseMessage = new BaseMessage();
//        JSONObject json = new JSONObject();
//        json.put("status", Constants.SUCCESS);
//        json.put("result", "push download status success");
//        try {
//            
//            responseMessage.setType(Constants.T105);
//            responseMessage.setBody(json.toString().getBytes(Constants.CHARTSET));
//            responseMessage.setBodyLength(json.toString().getBytes(Constants.CHARTSET).length);
//        } catch (UnsupportedEncodingException e) {
//            log.debug("响应pushDownload请求异常" + e);
//        }
//        session.write(responseMessage.toByte());
    }

}
