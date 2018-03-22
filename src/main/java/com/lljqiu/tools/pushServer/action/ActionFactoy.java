/**
 * Project Name pushServer
 * File Name package-info.java
 * Package Name com.lljqiu.tools.pushServer.action
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: jie_liu1@asdc.com.cn
 * Copyright © 2006, 2017, ASDC DAI. All rights reserved.
 */
package com.lljqiu.tools.pushServer.action;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.lljqiu.tools.pushServer.stack.BaseMessage;
import com.lljqiu.tools.pushServer.utils.RedisClient;

/** 
 * ClassName: ActionFactoy.java <br>
 * Description: <br>
 * Create by: name：liujie <br>email: jie_liu1@asdc.com.cn <br>
 * Create Time: 2017年6月5日<br>
 */
public abstract class ActionFactoy implements ActionService {
    protected IoSession session;
    protected BaseMessage message;
    protected RedisClient redisClient = new RedisClient();
    
    
    protected abstract void exec() throws Exception;
    
    public void doProcess(IoSession session, BaseMessage message) throws Exception{
        try {
            this.session = session;
            this.message = message;
            exec();
        } catch (Exception e) {
            Logger.getLogger(getClass()).error("ActionFactoy doProcess error{}",e);
        } 
    }
}
