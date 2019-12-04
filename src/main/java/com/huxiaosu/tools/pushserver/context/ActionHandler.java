/**
 * Project Name pushServer
 * File Name ActionHandler.java
 * Package Name com.lljqiu.tools.pushServer.context
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2017, www.lljqiu.com. All rights reserved.
 */
package com.huxiaosu.tools.pushserver.context;

import com.huxiaosu.tools.pushserver.action.ActionService;
import com.huxiaosu.tools.pushserver.stack.BaseMessage;
import org.apache.mina.core.session.IoSession;

/** 
 * ClassName: ActionHandler.java <br>
 * Description: 消息分发器<br>
 * @author name：liujie <br>email: liujie@huxiaosu.com <br>
 * @date: 2018年3月15日<br>
 */
public class ActionHandler {
    /** 
     * Description：消息处理-消息分发
     * @param session
     * @param command
     * @throws Exception
     * @return void
     * @author name：liujie <br>email: liujie@huxiaosu.com
     **/
    public static void process(IoSession session, BaseMessage command) throws Exception {

        ActionService service = MessageFactory.createService(command.getType());
        service.doProcess(session, command);

    }
}
