## pushServer 基于 Spring Boot+MINA 开发的一个推送工具
## 采用 byte 作为消息体传输
## 项目编译
	介绍已 Linux 服务形式运行
	使用 maven 命令构建为jar 
	mvn clean install spring-boot:run
	把构建好的 jar 上传到服务器，已 root 用户执行以下命令
	chmod +x pushServer.jar
	ln -s pushServer.jar /etc/init.d/pushServer
	service pushServer [start,stop,restart]

	
