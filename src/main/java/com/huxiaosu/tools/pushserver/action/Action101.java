/**
 * Project Name pushServer
 * File Name package-info.java
 * Package Name com.huxiaosu.tools.pushserver.action
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: liujie@huxiaosu.com
 *
 */
package com.huxiaosu.tools.pushserver.action;

import lombok.extern.slf4j.Slf4j;

/** 
 * ClassName: Action101.java <br>
 * Description: <br>
 * @author liujie <br>email: liujie@huxiaosu.com <br>
 * Create Time: 2017年6月5日<br>
 */
@Slf4j
public class Action101 extends ActionFactoy {

    @Override
    protected void exec() throws Exception {
        log.info("此请求为心跳=====");
        
    }

}
