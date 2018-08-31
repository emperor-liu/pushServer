/**
 * Project Name pushServer
 * File Name ConnectionFilter.java
 * Package Name com.lljqiu.tools.pushServer.filter
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: liujie@lljqiu.com
 * Copyright © 2015, 2017, www.lljqiu.com. All rights reserved.
 */
package com.lljqiu.tools.pushServer.filter;

import java.net.InetSocketAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;

/** 
 * ClassName: ConnectionFilter.java <br>
 * Description: <br>
 * @author name：liujie <br>email: liujie@lljqiu.com <br>
 * @date: 2018年3月15日<br>
 */
public class ConnectionFilter extends IoFilterAdapter{
    
    private static Log log = LogFactory.getLog(ConnectionFilter.class);

    @Override
    public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
        InetSocketAddress socketAddress = (InetSocketAddress) session.getRemoteAddress();
        log.debug("远程服务器地址：" + socketAddress.getAddress().getHostAddress());
        nextFilter.messageReceived(session, message);
    }
}
