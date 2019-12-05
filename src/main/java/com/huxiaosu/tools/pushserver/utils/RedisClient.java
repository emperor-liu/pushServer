/**
 * Project Name pushServer
 * File Name RedisClient.java
 * Package Name com.huxiaosu.tools.pushserver.utils
 * Create Time 2018年3月15日
 * Create by name：liujie -- email: liujie@huxiaosu.com
 *
 */
package com.huxiaosu.tools.pushserver.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.huxiaosu.tools.pushserver.config.PushServerConfig;
import org.apache.commons.lang3.SerializationUtils;

import redis.clients.jedis.Jedis;

/** 
 * ClassName: RedisClient.java <br>
 * Description: 缓存操作<br>
 * @author name：liujie <br>email: liujie@huxiaosu.com <br>
 * @date: 2018年3月15日<br>
 */
public class RedisClient {
    private static Jedis jedis;
    protected String     IP ;
    protected int        PORT;

    public RedisClient() {
        PushServerConfig pushServerConfig = SpringUtil.getBean(PushServerConfig.class);
        this.IP = pushServerConfig.getRedisIp();
        this.PORT = pushServerConfig.getRedisPort();
        jedis = new Jedis(IP, PORT);
    }

    /** 
     * Description：存储数据
     * @param key
     * @param value
     * @return void
     * @author name：liujie <br>email: liujie@huxiaosu.com
     * @date 2017年10月24日
     **/
    public void put(String key, Object value) {
        jedis.set(key.getBytes(), SerializationUtils.serialize((Serializable) value));
    }

    /** 
     * Description：向对列最左侧添加一条数据
     * @param key
     * @param value
     * @return void
     * @author name：liujie <br>email: liujie@huxiaosu.com
     * @date 2017年10月24日
     **/
    public void lpush(String key, Object value) {
        jedis.lpush(key.getBytes(), SerializationUtils.serialize((Serializable) value));
    }

    /** 
     * Description：相对列最右侧添加一条数据
     * @param key
     * @param value
     * @return void
     * @author name：liujie <br>email: liujie@huxiaosu.com
     * @date 2017年10月24日
     **/
    public void rpush(String key, Object value) {
        jedis.rpush(key.getBytes(), SerializationUtils.serialize((Serializable) value));
    }

    /** 
     * Description：从队列最左侧获取一条数据
     * @param key
     * @return
     * @return T
     * @author name：liujie <br>email: liujie@huxiaosu.com
     * @date 2017年10月24日
     **/
    public <T> T lpop(String key) {
        byte[] ret = jedis.lpop(key.getBytes());
        if (ret == null) {
            return null;
        }
        return (T) SerializationUtils.<T> deserialize(ret);
    }

    /** 
     * Description：从队列最右侧获取一条数据
     * @param key
     * @return
     * @return T
     * @author name：liujie <br>email: liujie@huxiaosu.com
     * @date 2017年10月24日
     **/
    public <T> T rpop(String key) {
        byte[] ret = jedis.rpop(key.getBytes());
        if (ret == null) {
            return null;
        }
        return (T) SerializationUtils.<T> deserialize(ret);
    }

    /** 
     * Description：通过制定的 key 获取数据
     * @param key
     * @return
     * @return T
     * @author name：liujie <br>email: liujie@huxiaosu.com
     * @date 2017年10月24日
     **/
    public <T> T get(String key) {
        byte[] ret = jedis.get(key.getBytes());
        if (ret == null) {
            return null;
        }
        return (T) SerializationUtils.<T> deserialize(ret);
    }

    /** 
     * Description：通过key前缀获取 keyList
     * @param key
     * @return
     * @return Set<String>
     * @author name：liujie <br>email: liujie@huxiaosu.com
     * @date 2017年10月24日
     **/
    public Set<String> getKeys(String key) {
        Set<byte[]> keys = jedis.keys(key.getBytes());
        Set<String> results = new HashSet<String>();
        if (keys != null) {
            for (byte[] k : keys) {
                results.add(new String(k));
            }
        }
        return results;
    }

    /** 
     * Description：删除数据
     * @param key
     * @return void
     * @author name：liujie <br>email: liujie@huxiaosu.com
     * @date 2017年10月24日
     **/
    public void del(String key) {
        jedis.del(key);
    }

    /** 
     * Description：获取 list 集合
     * @param key
     * @param start
     * @param end
     * @return
     * @return List<String>
     * @author name：liujie <br>email: liujie@huxiaosu.com
     * @date 2018年3月15日
     **/
    public List<String> getLrange(String key,Integer start, Integer end){
        if(start < 0){
            start = 0;
        }
        if(end == 0){
            start = -1;
        }
        List<byte[]> list = jedis.lrange(key.getBytes(), start, end);
        List<String> results = new ArrayList<String>();
        if (list != null) {
            for (byte[] k : list) {
                results.add((String) SerializationUtils.<String> deserialize(k));
            }
        }
        return results;
    }
    
    public void lremValue(String key , String value){
        jedis.lrem(key.getBytes(), 0, SerializationUtils.serialize((Serializable) value));
    }
    
    public static void main(String[] args) {
//        RedisClient redisClient = new RedisClient();
//        List<String> userSessionList = new ArrayList<String>();
//        userSessionList.add("asd");
//        userSessionList.add("123");
//        redisclient.put("test", userSessionList);
//        redisClient.lpush("test", "1223");
//        redisClient.lpush("test", "123");
//        redisClient.lpush("test", "123");
//        List<String> keys = redisClient.getLrange(Constants.CONNECTION_USER_KEY+"2",0,-1);
//        for(int i = 0; i < keys.size() ; i++ ){
//            IoSession userSession = redisClient.get(keys.get(i));
//            if(userSession == null || userSession.isClosing()){
//                System.out.println("delete info "+ keys.get(i));
//                redisClient.lremValue(Constants.CONNECTION_USER_KEY+"2",keys.get(i));
//                redisClient.del(keys.get(i));
//            }
//        }
//        IoSession userSession = new IoSession();
//        redisClient.put("test", userSession);
    }
}
