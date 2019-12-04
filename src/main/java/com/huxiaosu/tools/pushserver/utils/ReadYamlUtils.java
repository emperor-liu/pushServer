/**
 * Project Name pushServer
 * File Name ReadYamlUtils.java
 * Package Name com.lljqiu.tools.pushServer.utils
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2017, www.lljqiu.com. All rights reserved.
 */
package com.huxiaosu.tools.pushserver.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

/** 
 * ClassName: ReadYamlUtils.java <br>
 * Description: <br>
 * @author name：liujie <br>email: liujie@huxiaosu.com <br>
 * @date: 2018年3月15日<br>
 */
@Component
@SuppressWarnings("rawtypes")
public class ReadYamlUtils {

    static Map configMap   = new HashMap();
    static Map redisConfig = new HashMap();
    static {
        try {
            File dumpFile = new File(ReadYamlUtils.class.getClassLoader().getResource("").getPath()
                    + "application.yaml");
            Yaml yaml = new Yaml();
            Map father = yaml.loadAs(new FileInputStream(dumpFile), HashMap.class);
            String configFileName = "application-dev.yaml";
            for (Object key : father.keySet()) {
                if ("spring".equals(key)) {
                    Map applicationMap = (Map) father.get(key);
                    Map config = (Map) applicationMap.get("profiles");
                    String type = (String) config.get("active");
                    switch (type) {
                        case "dev":
                            configFileName = "application-dev.yaml";
                            break;
                        case "prod":
                            configFileName = "application-prod.yaml";
                            break;
                        case "test":
                            configFileName = "application-test.yaml";
                            break;
                        default:
                            configFileName = "application-dev.yaml";
                            break;
                    }
                }
            }
            File applyFile = new File(ReadYamlUtils.class.getClassLoader().getResource("").getPath()
                    + configFileName);
            Map applyFather = yaml.loadAs(new FileInputStream(applyFile), HashMap.class);
            for (Object key : applyFather.keySet()) {
                if ("socket".equals(key)) {
                    configMap = (Map) applyFather.get(key);
                }
                if ("redis".equals(key)) {
                    redisConfig = (Map) applyFather.get(key);
                }
            }
        } catch (FileNotFoundException e) {
            LoggerFactory.getLogger(ReadYamlUtils.class).error("读取配置文件异常", e);
            System.exit(1);
        }
    }

    /** 
    * Description： 获取监听端口
    * @return String
    * @author name：liujie <br>email: liujie@huxiaosu.com
    **/
    public static Integer getSocketPost() {
        return (Integer) configMap.get("port");
    }
    /** 
     * Description： 获取心跳时间
     * @return Integer
     * @author name：liujie <br>email: liujie@huxiaosu.com
     * @date 2018年3月15日
     **/
    public static Integer getSocketHeartBeat() {
        return (Integer) configMap.get("heartBeat");
    }

    /** 
     * Description：获取 redis 配置信息-ip
     * @return String
     * @author name：liujie <br>email: liujie@huxiaosu.com
     * @date 2018年3月15日
     **/
    public static String getRedisIp() {
        return (String) redisConfig.get("ip");
    }

    /** 
     * Description： 获取 redis 端口
     * @return Integer
     * @author name：liujie <br>email: liujie@huxiaosu.com
     * @date 2018年3月15日
     **/
    public static Integer getRedisPort() {
        return (Integer) redisConfig.get("port");
    }
}
