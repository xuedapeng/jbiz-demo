### 摘要

本篇介绍如何通过jbiz-core框架（v1.1）集成的websocket-api方便地构建websocket服务。


### 前提


由于tomcat7的版本不完全支持JSR-356（Java API for WebSocket规范），遵循甩掉包袱轻装上阵的原则，jbiz-core-1.1的websocket-api 要求tomcat的最低版本为8.0。

目前只支持文本格式消息。


### jbiz websocket api 概要
jbiz 采用 path-handler映射模型，jbiz解析websocket请求的path地址，并将消息的处理委托给相应的handler，jbiz负责连接的管理工作。

① 客户端（html5、android等）发起websocket连接

   		ws://xxx.com/zws

↓

② 服务端响应连接成功

↓

③ 客户端发送一条登录消息（json格式），包含path，以及身份信息

   		{"msgType":"sign_in", "path":"gps", "userId":"xxx", "apiKey":"yyy"}

↓

④ 服务端生成path对应的handler，并依次调用handler的如下方法：

			auth(ZWsHandlerParam handlerParam, IResponseObject response);
			onSignIn(ZWsHandlerParam handlerParam, IResponseObject response);

↓

⑤ 客户端收到登录成功消息，开始处理业务消息

		{"longitude":"123.456", "latitude":"78.90"}

↓

⑥ handler的onMessage方法处理业务消息

		validate(ZWsHandlerParam handlerParam, IResponseObject response);
		onMessage(ZWsHandlerParam handlerParam, IResponseObject response);

⑦　服务端主动向客户端发送消息

		ZWsHandlerManager.broadcast(IResponseObject response);
		ZWsHandlerManager.send(IResponseObject response, String sessionId);
		

### 编程示例

本例演示客户端通过websocket将gps信息发送给服务端。

1. 配置handler类所在包名

	jbiz_config.properties
	
		# websocket config
		websocket_handler_package=org.jbiz.wshandler
		

1. 在handler包下创建ZWsHandler和ZWsHandlerParam的子类。

	GpsHandler.java
	
		@WsHandler(path="gps")
		public class GpsHandler extends ZWsHandler {
			@Override
			public boolean auth(ZWsHandlerParam handlerParam, IResponseObject response) {
				// 登录时验证身份，并保存该连接的身份信息
				return true;
			}
			
			@Override
			public boolean validate(ZWsHandlerParam handlerParam, IResponseObject response) {

				// 校验传入的参数，onMessage之前被调用
				return true;
			}
			
			
			@Override
			public void onMessage(ZWsHandlerParam handlerParam, IResponseObject response) {
			
				// 收到消息时被调用
				// 可以在这里将接收到的gps信息保存到数据库
			｝
			
			@Override
			public void onSignIn(ZWsHandlerParam handlerParam, IResponseObject response) {
				// 登录时被调用
			}
			
			
			@Override
			public void onSignOut(ZWsHandlerParam handlerParam, IResponseObject response) {
				// 退出时被调用
			}
			
			@Override
			public ZWsHandlerParam getHandlerParam() {
				// 返回当前handler所使用的param类
				return new GpsHandlerParam();
			}
	
		｝
		
		
	GpsHandlerParam.java
		
		参考 jbiz-demo 源代码 org.jbiz.wshandler.param.GpsHandlerParam
		
		
1. 客户端（javascript）

	testWs.html
	
		参考 jbiz-demo 源代码 WebRoot/testWs.html
		
		
	1. 将 jbiz-demo发布到tomcat8，在浏览器地址栏输入
	
			http://localhost:8080/jbizdemo/testWs.html	
	2. 连接到
	
			ws://localhost:8080/jbizdemo/zws
			
	3. 发送登录消息
	
			{"msgType":"sign_in", "path":"gps", "userId":"xxx", "apiKey":"yyy"}
			
	4. 发送gps信息
	
			{"longitude":"123.456", "latitude":"78.90"}
			
	5. 观察浏览器控制台的输出信息。
