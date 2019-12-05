package com.huxiaosu.tools.pushserver;

import com.huxiaosu.tools.pushserver.config.PushServerConfig;
import com.huxiaosu.tools.pushserver.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.huxiaosu.tools.pushserver.acceptor.MessageAcceptor;


/** 
 * ClassName: MonitorApplication.java <br>
 * Description: 服务启动入口<br>
 * @author name：liujie <br>email: liujie@huxiaosu.com <br>
 * Create Time: 2017年10月16日<br>
 */
@Slf4j
@SpringBootApplication
public class PushServerApplication {

	public static void main(String[] args){
        SpringApplication.run(PushServerApplication.class, args);
		log.info("start server ...");
		MessageAcceptor acceptor = new MessageAcceptor();
        PushServerConfig pushServerConfig = SpringUtil.getBean(PushServerConfig.class);
		acceptor.start(pushServerConfig.getSocketPost(),pushServerConfig.getSocketHeartBeat());
		
	}
}
