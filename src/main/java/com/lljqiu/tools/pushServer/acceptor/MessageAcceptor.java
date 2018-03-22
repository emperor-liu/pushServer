/**
 * Project Name pushServer
 * File Name MessageAcceptor.java
 * Package Name com.lljqiu.tools.pushServer.acceptor
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: jie_liu1@asdc.com.cn
 * Copyright © 2006, 2017, ASDC DAI. All rights reserved.
 */
package com.lljqiu.tools.pushServer.acceptor;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lljqiu.tools.pushServer.codec.MPCodecFactory;
import com.lljqiu.tools.pushServer.context.MessageHandler;
import com.lljqiu.tools.pushServer.filter.ConnectionFilter;
import com.lljqiu.tools.pushServer.filter.KeepAliveMessageFactoryImpl;
import com.lljqiu.tools.pushServer.utils.Constants;
import com.lljqiu.tools.pushServer.utils.ReadYamlUtils;

/** 
 * ClassName: MessageAcceptor.java <br>
 * Description: <br>
 * @author name：liujie <br>email: jie_liu1@asdc.com.cn <br>
 * @date: 2018年3月15日<br>
 */
public class MessageAcceptor {
    private static Logger LOGGER = LoggerFactory.getLogger(MessageAcceptor.class);
    
    public void start(){
        try {
            IoAcceptor acceptor = new NioSocketAcceptor();
            acceptor.getFilterChain().addLast("logger", new LoggingFilter());
            acceptor.getFilterChain().addLast("myfliter", new ConnectionFilter());
            acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MPCodecFactory(Constants.CHARTSET)));// 指定编码过滤器
//            acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));// 指定编码过滤器
            acceptor.getFilterChain().addLast("threadPool", new ExecutorFilter(Executors.newCachedThreadPool()));
            acceptor.getSessionConfig().setReadBufferSize(2048*5000);//发送缓冲区10M
//            acceptor.getSessionConfig().set
            KeepAliveMessageFactoryImpl kamfi = new KeepAliveMessageFactoryImpl();
            KeepAliveFilter kaf = new KeepAliveFilter(kamfi, IdleStatus.BOTH_IDLE);
            /** 是否回发 */
            kaf.setForwardEvent(true);
            acceptor.getFilterChain().addLast("heart", kaf);
            
            acceptor.setHandler(new MessageHandler());// 指定业务逻辑处理器
            acceptor.setDefaultLocalAddress(new InetSocketAddress(ReadYamlUtils.getSocketPost()));// 设置端口号

            acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, ReadYamlUtils.getSocketHeartBeat());
           
            acceptor.bind();// 启动监听

            LOGGER.info("server start success, port is " + ReadYamlUtils.getSocketPost());

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

}
