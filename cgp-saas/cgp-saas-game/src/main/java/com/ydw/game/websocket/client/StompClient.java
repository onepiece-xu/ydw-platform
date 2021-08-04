package com.ydw.game.websocket.client;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.ydw.game.model.constant.Constant;
import com.ydw.game.service.impl.YdwPaasTokenService;

@Component
public class StompClient {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${url.paasWs}")
	private String paasWsIp;
	
	@Autowired
	private WebSocketStompClient stompClient;
	
	private StompSession stompSession;
	
	@Autowired
	private ConnectStompSessionHandler stompSessionHandler;
	
	@Autowired
	private YdwPaasTokenService ydwPaasApiService;
	
	@Bean
	public WebSocketStompClient createWebSocketClient() {
		WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
		return stompClient;
	}

	public void run(String... args) throws Exception {
		try {
			new Timer().schedule(new TimerTask(){
				@Override
				public void run() {
					connectToPaas();
				}
				
			}, 1000 * 10, 1000 * 10);
		} catch (RuntimeException e) {
			logger.error("服务启动后不能登录访问paas平台！！！");
		}
		
	}
	
	private void connectToPaas(){
		if(stompSession == null || !stompSession.isConnected()){
			try {
				String enterprisePaasToken = ydwPaasApiService.getEnterprisePaasToken();
				StompHeaders sheader = new StompHeaders();
				sheader.add(Constant.PAAS_AUTHORIZATION_HEADER_NAME, enterprisePaasToken);
				stompClient.setMessageConverter(new MappingJackson2MessageConverter());
				stompSession = stompClient.connect(paasWsIp + Constant.Url.URL_PAAS_MESSAGE, new WebSocketHttpHeaders(), sheader ,stompSessionHandler).get(5,TimeUnit.SECONDS);
			} catch (RuntimeException e) {
				logger.error("服务启动后不能登录访问paas平台！！！");
				logger.error(e.getMessage());
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
			} catch (ExecutionException e) {
				logger.error(e.getMessage());
			} catch (TimeoutException e) {
				logger.error(e.getMessage());
			}
		}
	}
	
}
