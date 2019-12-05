/**
 * Project Name pushServer
 * File Name package-info.java
 * Package Name com.huxiaosu.tools.pushserver.action
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: liujie@huxiaosu.com
 *
 */
package com.huxiaosu.tools.pushserver.action;

import com.huxiaosu.tools.pushserver.utils.RedisClient;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.huxiaosu.tools.pushserver.stack.BaseMessage;

/** 
 * ClassName: ActionFactoy.java <br>
 * Description: <br>
 * @author liujie <br>email: liujie@huxiaosu.com <br>
 * Create Time: 2017年6月5日<br>
 */
public abstract class ActionFactoy implements ActionService {
    protected IoSession session;
    protected BaseMessage message;
    protected RedisClient redisClient = new RedisClient();
    
    
    protected abstract void exec() throws Exception;

    @Override
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
