/**
 * Project Name pushServer
 * File Name package-info.java
 * Package Name com.lljqiu.tools.pushServer.exception
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: jie_liu1@asdc.com.cn
 * Copyright © 2006, 2017, ASDC DAI. All rights reserved.
 */
package com.lljqiu.tools.pushServer.exception;

/**
 * 
 * ClassName: PushServerException.java <br>
 * Description: <br>
 * Create by: name：liujie <br>email: jie_liu1@asdc.com.cn <br>
 * Create Time: 2018年3月29日<br>
 */
public class PushServerException extends RuntimeException {

    /**
     * Copyright © 2006, 2017, ASDC DAI. All rights reserved.
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
