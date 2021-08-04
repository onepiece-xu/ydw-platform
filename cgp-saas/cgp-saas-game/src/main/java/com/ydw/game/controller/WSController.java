package com.ydw.game.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WSController {
	
	@Autowired
    private SimpMessagingTemplate messagingTemplate;

	@MessageMapping("/connect")
    public Object sendMsg1(Principal principal, String msg) {
		messagingTemplate.convertAndSend("/game/connect", msg);
		return null;
    }
	
	@MessageMapping("/connect1")
    public Object sendMsg2(Principal principal, String msg) {
		messagingTemplate.convertAndSendToUser("2", "/game/connect", msg);
		return null;
    }
}
