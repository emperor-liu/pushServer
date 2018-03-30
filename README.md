## pushServer 基于 Spring Boot+MINA 开发
    采用 byte 作为消息体传输
    多客户端支持，IOS、Android、PC、webSocket
## 配置
application.yaml
```
spring: 
  profiles:
    active: dev # 用于区分环境(开发dev、测试test、生产prod)
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: Asia/Shanghai
logging:
  config: classpath:logback.xml
application:
  name: pushServer
  version: 2.0.0
```
在 application-*.yaml 中修改一下配置
```
#socket config
socket:
  port: 9090
  heartBeat: 600
  
#redis config
redis:
  ip: 127.0.0.1
  port: 6379
```
## 项目部署
	介绍已 Linux 服务形式运行
	使用 maven 命令构建为jar 
	mvn clean package -Dmaven.test.skip=true
	把构建好的 jar 上传到服务器，已 root用户执行以下命令
	chmod +x pushServer.jar
	ln -s pushServer.jar /etc/init.d/pushServer
	service pushServer [start,stop,restart]