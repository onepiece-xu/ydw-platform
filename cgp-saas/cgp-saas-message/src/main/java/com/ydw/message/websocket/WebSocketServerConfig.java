package com.ydw.message.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * 配置stomp
 * 
 * @author xulh
 *
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketServerConfig implements WebSocketMessageBrokerConfigurer {

	/**
	 * 创建具有一个或多个用于发送和接收消息的目的地的内存中消息代理。
	 * 在上面的示例中，定义了两个目标前缀：topic和queue。
	 * 它们遵循的约定是，要通过pub-sub模型传递给所有订阅的客户端的消息的目的地应加上前缀topic。另一方面，私人消息的目的地通常以前缀queue。
	 * 定义app用于过滤目标的前缀，这些目标由带注释的方法处理@MessageMapping，您将在控制器中实现这些方法。控制器在处理了消息之后，会将其发送给代理。
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
//		config.enableSimpleBroker("/topic/", "/queue/");
		ThreadPoolTaskScheduler te = new ThreadPoolTaskScheduler();
		te.setPoolSize(1);
		te.setThreadNamePrefix("wss-heartbeat-thread-");
		te.initialize();
		config.enableSimpleBroker("").setHeartbeatValue(new long[]{10000 ,10000}).setTaskScheduler(te);
		config.setApplicationDestinationPrefixes("/app");
	}

	/**
	 * 客户端注册节点
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws").setAllowedOrigins("*");
	}

	/**
	 * 配置客户端入站通道拦截器
	 */
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(createMyChannelInterceptor());
	}

	/**
	 * 注入拦截器
	 */
	@Bean
	public StompServerChannelInterceptor createMyChannelInterceptor() {
		return new StompServerChannelInterceptor();
	}
	
}