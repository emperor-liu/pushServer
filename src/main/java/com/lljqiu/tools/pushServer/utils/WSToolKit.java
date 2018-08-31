/**
 * Project Name pushServer
 * File Name package-info.java
 * Package Name com.lljqiu.tools.pushServer.utils
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: liujie@lljqiu.com
 * Copyright © 2015, 2017, www.lljqiu.com. All rights reserved.
 */
package com.lljqiu.tools.pushServer.utils;

import org.apache.mina.core.session.IoSession;

/**
 * websocket 工具
 * ClassName: WSToolKit.java <br>
 * Description: <br>
 * Create by: name：liujie <br>email: liujie@lljqiu.com <br>
 * Create Time: 2018年3月29日<br>
 */
public class WSToolKit {

    private WSToolKit() {
    }

    public static enum WSSessionState {
        WSHandshake,
        Connected;
        public final static String ATTRIBUTE_KEY = "__SESSION_STATE";
    }

    static <T> T nvl(T t1, T t2) {
        return t1 == null ? t2 : t1;
    }

    public static void setSessionState(IoSession session) {
        session.setAttribute(WSSessionState.ATTRIBUTE_KEY, WSSessionState.WSHandshake);

    }
    public static WSSessionState getSessionState(IoSession session) {
        WSSessionState state = (WSSessionState) session.getAttribute(WSSessionState.ATTRIBUTE_KEY);

//        if (state == null) {
//            state = WSSessionState.WSHandshake;
//            session.setAttribute(WSSessionState.ATTRIBUTE_KEY, state);
//        }

        return state;
    }

    public static String getMessageColumnValue(String[] headers, String headerTag) {
        for (String header : headers) {
            if (header.startsWith(headerTag))
                return header.substring(headerTag.length(), header.length()).trim();
        }

        return null;
    }

    public static String subString(String src, int order, String flag) {
        for (int i = 1, j = 0, k = 0;; i++) {
            j = src.indexOf(flag, k);
            if (i < order) {
                if (j == -1)
                    return "";
                else
                    k = j + 1;
            } else {
                if (j == -1)
                    return src.substring(k, src.length());
                else
                    return src.substring(k, j);
            }
        }
    }
    
    public static int bytes2int(byte[] bytes){  
        int result = 0;  
        if(bytes.length == 4){  
            int a = (bytes[0] & 0xff) << 24;  
            int b = (bytes[1] & 0xff) << 16;  
            int c = (bytes[2] & 0xff) << 8;  
            int d = (bytes[3] & 0xff);  
            result = a | b | c | d;  
        }  
        return result;  
    }  
}
