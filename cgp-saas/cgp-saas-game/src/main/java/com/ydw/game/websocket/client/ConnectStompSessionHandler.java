package com.ydw.game.websocket.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;

@Component
public class ConnectStompSessionHandler extends StompSessionHandlerAdapter {
	
	@Autowired
	private ConnectMessageHandler connectMessageHandler;
	
	@Override
	public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
	    session.subscribe("/user/game/connect", connectMessageHandler);
	}
}