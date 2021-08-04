package com.ydw.game.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ydw.game.websocket.client.StompClient;

@Component
public class ApplicationJob implements CommandLineRunner{
	
	@Autowired
	private StompClient stompClient;

	@Override
	public void run(String... args) throws Exception {
		//开启saas平台对接paas平台的消息任务
		stompClient.run(args);
	}

}
