/**
 * Project Name pushServer
 * File Name MessageHandler.java
 * Package Name com.huxiaosu.tools.pushserver.context
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: liujie@huxiaosu.com
 *
 */
package com.huxiaosu.tools.pushserver.context;

import com.huxiaosu.tools.pushserver.stack.BaseMessage;
import com.huxiaosu.tools.pushserver.stack.SessionUsers;
import com.huxiaosu.tools.pushserver.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/** 
 * ClassName: MessageHandler.java <br>
 * Description: 服务端IO处理器<br>
 * @author name：liujie <br>email: liujie@huxiaosu.com <br>
 * @date: 2018年3月15日<br>
 */
@Slf4j
public class MessageHandler extends IoHandlerAdapter {

    protected static final String TSSESSIONKEY = "MPSession_Key";


    @Override
    public void sessionCreated(IoSession session) throws Exception {
        log.debug("sessionCreated()...sessionId=" + session.getId());
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        log.debug("sessionOpened()...sessionId=" + session.getId());
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        log.debug("sessionClosed()...sessionId=" + session.getId());
        SessionUsers.getInstance().clearSession(session.getId());
        session.close(true);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        log.debug("sessionIdle()...sessionId=" + session.getId());
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        log.debug("messageSent()...sessionId=" + session.getId() + ", message="
                + ToStringBuilder.reflectionToString(message));
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        log.debug("exceptionCaught()..." + cause.getMessage());
        cause.printStackTrace();
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        log.debug("messageReceived()...业务消息处理" + ToStringBuilder.reflectionToString(message));
        BaseMessage baseMessage = new BaseMessage();
        try {
            baseMessage = (BaseMessage) message;
        } catch (Exception e) {
            baseMessage.setType(Constants.T101);
        }
        //业务消息处理
        ActionHandler.process(session, baseMessage);

    }
}
