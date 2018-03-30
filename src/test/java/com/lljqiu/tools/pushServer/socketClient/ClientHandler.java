/**
 * Project Name pushServer
 * File Name ClientHandler.java
 * Package Name com.lljqiu.tools.pushServer.socketClient
 * Create Time 2017年6月6日
 * Create by name：liujie -- email: jie_liu1@asdc.com.cn
 * Copyright © 2006, 2017, ASDC DAI. All rights reserved.
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
 * Create by: name：liujie <br>email: jie_liu1@asdc.com.cn <br>
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
