/**
 * Project Name pushServer
 * File Name ClientHandler.java
 * Package Name com.lljqiu.tools.pushServer.socketClient
 * Create Time 2017年6月6日
 * Create by name：liujie -- email: liujie@lljqiu.com
 * Copyright © 2015, 2017, www.lljqiu.com. All rights reserved.
 */
package com.lljqiu.tools.pushServer.socketClient;

import java.util.Arrays;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.lljqiu.tools.pushServer.stack.BaseMessage;


/** 
 * ClassName: ClientHandler.java <br>
 * Description: <br>
 * Create by: name：liujie <br>email: liujie@lljqiu.com <br>
 * Create Time: 2017年6月6日<br>
 */
public class ClientHandler extends IoHandlerAdapter {
    
    public void messageReceived(IoSession session, Object message) throws Exception {
        BaseMessage baseMessage = (BaseMessage)message;
        System.out.println("client receive a message is : " + ToStringBuilder.reflectionToString(baseMessage));
        System.out.println("client receive a message is : " + new String(baseMessage.getBody(),"UTF-8"));
    }

    public void messageSent(IoSession session, Object message) throws Exception {
        System.out.println("messageSent -> ：" + Arrays.toString((byte[])message));
    }
}
