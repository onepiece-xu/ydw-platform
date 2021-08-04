package com.ydw.game.websocket.client;

import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ydw.game.model.vo.ConnectMsg;
import com.ydw.game.websocket.service.ConnectMessageService;

@Component
public class ConnectMessageHandler implements StompFrameHandler{
	
	@Autowired
	private ConnectMessageService connectMessageService;

	@Override
	public Type getPayloadType(StompHeaders headers) {
		return JSONObject.class;
	}

	@Override
	public void handleFrame(StompHeaders headers, Object payload) {
		ConnectMsg connectMsg = JSON.parseObject(JSON.toJSONString(payload), ConnectMsg.class);
		connectMessageService.handlerMsg(connectMsg);
	}

}
