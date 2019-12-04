/**
 * Project Name pushServer
 * File Name package-info.java
 * Package Name com.lljqiu.tools.pushServer.exception
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2017, www.lljqiu.com. All rights reserved.
 */
package com.huxiaosu.tools.pushserver.exception;

/**
 * 
 * ClassName: PushServerException.java <br>
 * Description: <br>
 * @author liujie <br>email: liujie@huxiaosu.com <br>
 * Create Time: 2018年3月29日<br>
 */
public class PushServerException extends RuntimeException {

    /**
     * Copyright © 2015, 2017, www.lljqiu.com. All rights reserved.
     */
    private static final long serialVersionUID = -5755070623546084709L;
    
    public PushServerException(Throwable cause) {
        this(null, cause);
    }
    public PushServerException(String message) {
        this(message, null);
    }
    public PushServerException(String message, Throwable cause) {
        super(message, cause);
    }
}