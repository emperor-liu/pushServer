/**
 * Project Name pushServer
 * File Name package-info.java
 * Package Name com.lljqiu.tools.pushServer.action
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2017, www.lljqiu.com. All rights reserved.
 */
package com.huxiaosu.tools.pushserver.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** 
 * ClassName: Action101.java <br>
 * Description: <br>
 * @author liujie <br>email: liujie@huxiaosu.com <br>
 * Create Time: 2017年6月5日<br>
 */
public class Action101 extends ActionFactoy {

    private static Log log = LogFactory.getLog(Action101.class);
    /* (non-Javadoc)
     * @see com.asdc.messagepush.action.ActionFactoy#exec()
     */
    @Override
    protected void exec() throws Exception {
        log.info("此请求为心跳=====");
        
    }

}
