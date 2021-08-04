package com.ydw.game.websocket.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.ydw.game.model.vo.WSMsg;
import com.ydw.game.redis.RedisUtil;

public abstract class MessageService {

	@Autowired
	private RedisUtil redisUtil;
	
	public abstract void send(String msg);
	
	public void send(WSMsg wsmsg){
		redisUtil.sendMsg(wsmsg.getDestination(), wsmsg);
	}
	
	public abstract void receive(String wsmsg);
}
