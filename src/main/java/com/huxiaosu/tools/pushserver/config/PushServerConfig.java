/**
 * Project Name pushServer
 * File Name PushServerConfig
 * Package Name com.huxiaosu.tools.pushserver.config
 * Create Time 2019/12/5
 * Create by name：liujie
 */
package com.huxiaosu.tools.pushserver.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Description
 *
 * @author liujie
 * @ClassName PushServerConfig
 * @date 2019/12/5 15:23
 */
@Data
@Configuration
@ConfigurationProperties()
public class PushServerConfig {

    private SocketConfig socket;
    private RedisConfig redis;

    /**
     * Description： 获取监听端口
     * @return String
     * @author name：liujie <br>email: liujie@huxiaosu.com
     **/
    public Integer getSocketPost() {
        return socket.getPort();
    }
    /**
     *
     * Description:
     *  获取 socketIP
     * @return  ip
     * @author liujie
     * @date 2019/12/5 15:31
     */
    public String getSocketIp() {
        return socket.getIp();
    }
    /**
     * Description： 获取心跳时间
     * @return Integer
     * @author name：liujie <br>email: liujie@huxiaosu.com
     * @date 2018年3月15日
     **/
    public Integer getSocketHeartBeat() {
        return socket.getHeartBeat();
    }

    /**
     * Description：获取 redis 配置信息-ip
     * @return String
     * @author name：liujie <br>email: liujie@huxiaosu.com
     * @date 2018年3月15日
     **/
    public String getRedisIp() {
        return redis.getIp();
    }

    /**
     * Description： 获取 redis 端口
     * @return Integer
     * @author name：liujie <br>email: liujie@huxiaosu.com
     * @date 2018年3月15日
     **/
    public Integer getRedisPort() {
        return (Integer) redis.getPort();
    }
}
