/**
 * Project Name pushServer
 * File Name ClientHandler.java
 * Package Name com.huxiaosu.tools.pushserver.socketClient
 * Create Time 2017年6月6日
 * Create by name：liujie -- email: liujie@huxiaosu.com
 *
 */
package com.huxiaosu.tools.pushserver.socketClient;

import java.util.Arrays;

import com.huxiaosu.tools.pushserver.stack.BaseMessage;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;


/** 
 * ClassName: ClientHandler.java <br>
 * Description: <br>
 * @author liujie <br>email: liujie@huxiaosu.com <br>
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
