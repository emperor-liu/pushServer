/**
 * Project Name pushServer
 * File Name SocketConfig
 * Package Name com.huxiaosu.tools.pushserver.config
 * Create Time 2019/12/5
 * Create by name：liujie
 */
package com.huxiaosu.tools.pushserver.config;

import lombok.Data;

/**
 * Description
 *
 * @author liujie
 * @ClassName SocketConfig
 * @date 2019/12/5 15:28
 */
@Data
public class SocketConfig {
    private String ip;
    private Integer port;
    private Integer heartBeat;
}
