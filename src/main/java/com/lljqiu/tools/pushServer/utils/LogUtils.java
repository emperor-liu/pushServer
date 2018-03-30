/**
 * Project Name pushServer
 * File Name package-info.java
 * Package Name com.lljqiu.tools.pushServer.utils
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: jie_liu1@asdc.com.cn
 * Copyright © 2006, 2017, ASDC DAI. All rights reserved.
 */
package com.lljqiu.tools.pushServer.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * ClassName: VSSLog.java <br>
 * Description: 公共日志类<br>
 * Create by: name：liujie <br>email: jie_liu1@asdc.com.cn <br>
 * Create Time: 2017年3月29日<br>
 */
public class LogUtils {
    private static Logger logger = LoggerFactory.getLogger(LogUtils.class);
    
    /** 日志类型：系统日志 */
    public static String       system   = "System";

    /** 日志类型：发包日志 */
    public static String       send     = "Send";

    /** 日志类型：收包日志 */
    public static String       reciv    = "Reciv";

    /** 日志类型：控制台 */
    public static String       console  = "Console";

    /** 日志类型：数据库 */
    public static String       database = "Database";

    public static void debug(String debug) {
        if (logger.isDebugEnabled()) {
            logger.debug(debug);
        }
    }
    public static void debug(String debug, Object arg1) {
        if (logger.isDebugEnabled()) {
            logger.debug(debug, arg1);
        }
    }
    public static void debug(String debug,Object arg1, Object arg2) {
        if (logger.isDebugEnabled()) {
            logger.debug(debug,arg1,arg2);
        }
    }
    
    public static void info(String info) {
        if (logger.isInfoEnabled()) {
            logger.info(info);
        }
    }
    public static void info(String debug, Object arg1) {
        if (logger.isInfoEnabled()) {
            logger.info(debug, arg1);
        }
    }
    public static void info(String debug,Object arg1, Object arg2) {
        if (logger.isInfoEnabled()) {
            logger.info(debug,arg1,arg2);
        }
    }
    public static void error(String error) {
        if (logger.isErrorEnabled()) {
            logger.error(error);
        }
    }
    public static void error(String error,Exception e) {
        if (logger.isErrorEnabled()) {
            logger.error(error,e);
        }
    }
}
