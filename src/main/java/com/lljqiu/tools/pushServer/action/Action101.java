/**
 * Project Name pushServer
 * File Name package-info.java
 * Package Name com.lljqiu.tools.pushServer.action
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: jie_liu1@asdc.com.cn
 * Copyright © 2006, 2017, ASDC DAI. All rights reserved.
 */
package com.lljqiu.tools.pushServer.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** 
 * ClassName: Action101.java <br>
 * Description: <br>
 * Create by: name：liujie <br>email: jie_liu1@asdc.com.cn <br>
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
