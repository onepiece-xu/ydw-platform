package com.ydw.game.redis;

import org.springframework.beans.factory.annotation.Autowired;

import com.ydw.game.websocket.service.ConnectMessageService;

/**
 * redis接收通知消息
 * @author xulh
 *
 */
public class ConnectMessageSub implements IRedisMessageSub{
	
	@Autowired
	private ConnectMessageService connectMessageService;
	
	@Override
	public void receiveMessage(String msg) {
		connectMessageService.send(msg);
	}
}
