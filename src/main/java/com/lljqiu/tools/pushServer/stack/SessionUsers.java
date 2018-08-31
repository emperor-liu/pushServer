/**
 * Project Name pushServer
 * File Name SessionUsers.java
 * Package Name com.lljqiu.tools.pushServer.stack
 * Create Time 2018年3月16日
 * Create by name：liujie -- email: liujie@lljqiu.com
 * Copyright © 2015, 2017, www.lljqiu.com. All rights reserved.
 */
package com.lljqiu.tools.pushServer.stack;

import java.util.HashMap;

/** 
 * ClassName: SessionUsers.java <br>
 * Description: <br>
 * @author name：liujie <br>email: liujie@lljqiu.com <br>
 * @date: 2018年3月16日<br>
 */
public class SessionUsers {
    
    private HashMap<Object,Object> userSession = null;
    
    public SessionUsers() {
        userSession = new HashMap<Object,Object>();
    }
    private static SessionUsers       instance;
    
    public synchronized static SessionUsers getInstance() {
        if (instance == null) {
            instance = new SessionUsers();
        }
        return instance;
    }
    
    /** 
     * Description：保存用户session
     * @param key sessionID
     * @param value
     * @return void
     * @author name：liujie <br>email: liujie@lljqiu.com
     * @date 2018年3月16日
     **/
    public void addUserSession(Object key,Object value) {
        if (instance == null) {
            instance = new SessionUsers();
        }
        userSession.put(key, value);
    }
    
    /** 
     * Description：获取session数据
     * @param key
     * @return
     * @return Object
     * @author name：liujie <br>email: liujie@lljqiu.com
     **/
    public Object getSession(Object key){
        if (instance != null) {
            return userSession.get(key);
        }
        return null;
    }
    
    /** 
     * Description：清除用户 session
     * @param key
     * @return void
     * @author name：liujie <br>email: liujie@lljqiu.com
     * @date 2018年3月16日
     **/
    public void clearSession(Object key) {
        if (instance != null && key != null) {
            userSession.remove(key);
        }
    }
}
