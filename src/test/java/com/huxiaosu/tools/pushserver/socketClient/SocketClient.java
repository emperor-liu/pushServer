/**
 * Project Name messagePush
 * File Name SocketClient.java
 * Package Name com.asdc.messagepush.test
 * Create Time 2017年6月5日
 * Create by name：liujie -- email: liujie@huxiaosu.com
 *
 */
package com.huxiaosu.tools.pushserver.socketClient;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import com.huxiaosu.tools.pushserver.codec.MPCodecFactory;
import com.huxiaosu.tools.pushserver.stack.BaseMessage;
import com.huxiaosu.tools.pushserver.stack.WarningInfo;
import com.huxiaosu.tools.pushserver.utils.Constants;
import com.huxiaosu.tools.pushserver.utils.GsonUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;

/** 
 * ClassName: SocketClient.java <br>
 * Description: <br>
 * @author liujie <br>email: liujie@huxiaosu.com <br>
 * Create Time: 2017年6月5日<br>
 */
public class SocketClient {

    public static void main(String[] args) throws Exception {


        // 创建客户端连接器.
        NioSocketConnector connector = new NioSocketConnector();
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("codec",new ProtocolCodecFilter(new MPCodecFactory(Constants.CHARTSET)));

        // 设置连接超时检查时间
        connector.setConnectTimeoutCheckInterval(30);
        connector.setHandler(new ClientHandler());

        // 建立连接
        ConnectFuture cf = connector.connect(new InetSocketAddress("127.0.0.1", 9090));
//        ConnectFuture cf = connector.connect(new InetSocketAddress("218.206.176.197", 8080));
        // 等待连接创建完成
        cf.awaitUninterruptibly();
//        for (int i = 0; i < 1; i++) {
//            cf.getSession().write(reqyestMessage());
//        }
        reqyestMessage();
        
        // 等待连接断开
//        cf.getSession().getCloseFuture().awaitUninterruptibly();
        // 释放连接
//        cf.getSession().close(true);
//        connector.dispose(true);
        System.out.println(cf.isConnected());

    
//        reqyestMessage103();
//        reqyestMessage104();
//        reqyestMessage106();
        
        
    }
    
    public static byte[] reqyestMessage() throws Exception{
        JsonObject json = new JsonObject();
        json.addProperty("userId", "2");
//        json.addProperty("sessionid", UUID.randomUUID().toString().replaceAll("-", ""));
        json.addProperty("sessionid", "46018f5503364cdc8a309edbae4c98d3");
        BaseMessage message = new BaseMessage();
        message.setType(Constants.T102);//发送注册
        byte[] body = json.toString().getBytes(Constants.CHARTSET);
        message.setBody(body);
        message.setBodyLength(body.length);
        System.out.println(ToStringBuilder.reflectionToString(message));
        System.out.println(Arrays.toString(message.toByte()));
        return message.toByte();
    }
    
    
    public static byte[] reqyestMessage103() throws Exception{
        WarningInfo warn = new WarningInfo();
        warn.setDeviceId("34020000001320000003");
        warn.setUserId("SknYlFSTOObxM0ww");
        warn.setDeviceName("test");
        warn.setPicUrl("http://pic.58pic.com/58pic/13/60/91/42P58PIChDU_1024.jpg");
        warn.setDevicePosition("五层云台而");
        warn.setWarnType("红色告警");
        warn.setWarnTime(new Date());
        warn.setWarnId(UUID.randomUUID().toString().replaceAll("-", ""));
        System.out.println(GsonUtils.toJson(warn));
        BaseMessage message = new BaseMessage();
        message.setType(Constants.T103);//发送心跳测试
        byte[] body = GsonUtils.toJson(warn).getBytes(Constants.CHARTSET);
        message.setBody(body);
        message.setBodyLength(body.length);
        return message.toByte();
    }
    public static byte[] reqyestMessage104() throws Exception{
        JSONObject json = new JSONObject();
        json.put("sessionId", "4ec43cbae42d416ba3801a3f0ba5d405");
        json.put("code", 0);
        json.put("desc", "ok");
        json.put("download", "http://192.168.127.13/mp4/65010000001327512417_1503023820_1503024000.mp4");
        json.put("md5", "");
        BaseMessage message = new BaseMessage();
        message.setType(Constants.T104);//发送心跳测试
        System.out.println(json.toJSONString());
        byte[] body = json.toString().getBytes(Constants.CHARTSET);
        message.setBody(body);
        message.setBodyLength(body.length);
        return message.toByte();
    }
    public static byte[] reqyestMessage106() throws Exception{
        JSONObject json = new JSONObject();
        json.put("deviceStatus", 0);
        json.put("deviceId", "65010000001327178711");
        json.put("updateTime", "1501667129000");
        BaseMessage message = new BaseMessage();
        message.setType(Constants.T106);//发送心跳测试
        System.out.println(json.toJSONString());
        byte[] body = json.toString().getBytes(Constants.CHARTSET);
        message.setBody(body);
        message.setBodyLength(body.length);
        return message.toByte();
    }
    public static byte[] sendDownload104() throws Exception{
        JSONObject json = new JSONObject();
        json.put("sessionId", "eb427f87ab8749ffa285d98522d63c67");
        json.put("code", 0);
        json.put("desc", "desc");
        json.put("download", "http://192.168.127.13/mp4/65010000001327178711_1501210110_1501210230.mp4");
        json.put("md5", "");
        BaseMessage message = new BaseMessage();
        message.setType(Constants.T104);//发送心跳测试
        byte[] body = json.toString().getBytes(Constants.CHARTSET);
        message.setBody(body);
        message.setBodyLength(body.length);
        return message.toByte();
    }
}
