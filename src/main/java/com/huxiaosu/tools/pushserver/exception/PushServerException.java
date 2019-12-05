/**
 * Project Name pushServer
 * File Name package-info.java
 * Package Name com.huxiaosu.tools.pushserver.exception
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: liujie@huxiaosu.com
 *
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
     *
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
