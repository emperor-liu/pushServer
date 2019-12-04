package com.huxiaosu.tools.pushserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.huxiaosu.tools.pushserver.acceptor.MessageAcceptor;


/** 
 * ClassName: MonitorApplication.java <br>
 * Description: 服务启动入口<br>
 * @author name：liujie <br>email: liujie@huxiaosu.com <br>
 * Create Time: 2017年10月16日<br>
 */
@SpringBootApplication
public class PushServerApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(PushServerApplication.class);
    
	public static void main(String[] args) {

		SpringApplication.run(PushServerApplication.class, args);
		LOGGER.info("start server ...");
		MessageAcceptor acceptor = new MessageAcceptor();
		acceptor.start();
		
	}
}
