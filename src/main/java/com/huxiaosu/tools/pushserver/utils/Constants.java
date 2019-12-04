/**
 * Project Name pushServer
 * File Name package-info.java
 * Package Name com.lljqiu.tools.pushServer.utils
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2017, www.lljqiu.com. All rights reserved.
 */
package com.huxiaosu.tools.pushserver.utils;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/** 
 * ClassName: Constants.java <br>
 * Description: <br>
 * @author liujie <br>email: liujie@huxiaosu.com <br>
 * Create Time: 2017年6月5日<br>
 */
public class Constants {

    /** 心跳 */
    public final static byte   T101                   = 101;
    /** 注册 */
    public final static byte   T102                   = 102;
    /** 业务平台上报告警数据 */
    public final static byte   T103                   = 103;
    /** Browser init */
    public final static byte   T104                   = 104;
    /** 推送下载链接 */
    public static final byte   T105                   = 105;
    /** 推送设备状态 */
    public static final byte   T106                   = 106;
    /** 点击地图设备-进入客户端直播页 */
    public static final byte   T107                   = 107;
    public static final byte   T108                   = 108;
    public static final byte   T109                   = 109;
    /** 成功*/
    public final static int    SUCCESS                = 200;
    /** 失败*/
    public final static int    FAILURE                = 500;
    /** 统一编码*/
    public final static String CHARTSET               = "UTF-8";

    public final static String USER_DOWNLOAD_KEY      = "com.lljqiu.tools.pushServer.action.Action102#online_PUSH_USERINFO#";
    public final static String ONLINE_USER_INFO       = "com.lljqiu.tools.pushServer.action.Action102#online_user_sessionList#";
    /** 链接用户 key 前缀 */
    public final static String CONNECTION_SESSION_KEY = "com.lljqiu.tools.pushServer#connectionUserSession#";
    public final static String CONNECTION_USER_KEY    = "com.lljqiu.tools.pushServer#connectionUser#";
    public final static CharsetDecoder     CHAR_SETDE_DCODER         = Charset.forName("utf-8").newDecoder();
    public final static CharsetEncoder     CHAR_SETDE_ECODER         = Charset.forName("utf-8").newEncoder();

}
