/**
 * Project Name pushServer
 * File Name package-info.java
 * Package Name com.huxiaosu.tools.pushserver.action
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: liujie@huxiaosu.com
 *
 */
package com.huxiaosu.tools.pushserver.action;

import org.apache.mina.core.session.IoSession;

import com.huxiaosu.tools.pushserver.stack.BaseMessage;

/** 
 * ClassName: ActionService.java <br>
 * Description: <br>
 * @author liujie <br>email: liujie@huxiaosu.com <br>
 * Create Time: 2017年6月5日<br>
 */
public interface ActionService {

    public void doProcess(IoSession session, BaseMessage command) throws Exception;
}
