/**
 * Project Name pushServer
 * File Name package-info.java
 * Package Name com.huxiaosu.tools.pushserver.action
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: liujie@huxiaosu.com
 *
 */
package com.huxiaosu.tools.pushserver.action;

import com.alibaba.fastjson.JSONObject;
import com.huxiaosu.tools.pushserver.stack.BaseMessage;
import com.huxiaosu.tools.pushserver.stack.SessionUsers;
import com.huxiaosu.tools.pushserver.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/** 
 * ClassName: Action102.java <br>
 * Description: 客户端注册用户信息<br>
 * @author liujie <br>email: liujie@huxiaosu.com <br>
 * Create Time: 2017年6月6日<br>
 */
@Slf4j
public class Action103 extends ActionFactoy {

    @Override
    protected void exec() throws Exception {
        try {
            log.info("<send reptile-system zhihu info >");
            String info = new String(message.getBody());
            JSONObject messageJson = JSONObject.parseObject(info);
            String userId = messageJson.getString("userId");
            List<String> keys = redisClient.getLrange(Constants.CONNECTION_USER_KEY+userId, 0, -1);
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
            } catch (Exception e) {
                log.error("response zhihu {}" ,e);
            }

            session.write(responseMessage.toByte());

        } catch (Exception e) {
            log.error("send zhihu error {}", e);
        }

    }

}
