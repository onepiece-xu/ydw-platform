package com.ydw.game.websocket.server;

import java.security.Principal;
import java.text.MessageFormat;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.messaging.SubProtocolWebSocketHandler;

import com.ydw.game.config.jwt.JwtUtil;
import com.ydw.game.model.constant.Constant;
import com.ydw.game.redis.RedisUtil;

public class StompServerChannelInterceptor implements ChannelInterceptor {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "subProtocolWebSocketHandler")
    private SubProtocolWebSocketHandler subProtocolWebSocketHandler;

	@Autowired
	private RedisUtil redisUtil;

	private Long timeout = 15L;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor stompAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
		String account = doAuthentication(stompAccessor);
		String sessionId = stompAccessor.getSessionId();
		if(stompAccessor.getMessageType() == SimpMessageType.HEARTBEAT){
			heartbeat(account, sessionId);
		}
		StompCommand command = stompAccessor.getCommand();
		if (command != null) {
			// 连接
			switch (command) {
			case CONNECT:
				connect(account, sessionId);
				break;
			// 断开
			case ABORT:
			case ERROR:
			case DISCONNECT:
				disconnect(account, sessionId);
				break;
			default:
				break;
			}
		}
		return message;
	}

	public void connect(String account , String sessionId) {
		logger.info("connect websocket ...................");
		String userSessionKey = MessageFormat.format(Constant.SET_WS_ONLINE, account);
		redisUtil.sSetAndTime(userSessionKey, timeout, sessionId);
		redisUtil.zRemove(Constant.ZSET_WS_OFFLINE, account);
		logger.info("account: " + account + " connect websocket ...................");
	}

	public void disconnect(String account , String sessionId) {
		logger.info("disconnect websocket ...................");
		String userSessionKey = MessageFormat.format(Constant.SET_WS_ONLINE, account);
		redisUtil.setRemove(userSessionKey, sessionId);
		redisUtil.zSet(Constant.ZSET_WS_OFFLINE, account, new Double(System.currentTimeMillis()));
		logger.info("account: " + account + " disconnect websocket ...................");
	}
	
	public void heartbeat(String account , String sessionId) {
		String userSessionKey = MessageFormat.format(Constant.SET_WS_ONLINE, account);
		redisUtil.expire(userSessionKey, timeout);
	}

	private String doAuthentication(StompHeaderAccessor accessor) {
		try {
			// 取头部信息做鉴权
			String token = accessor.getFirstNativeHeader(Constant.PAAS_AUTHORIZATION_HEADER_NAME);
			if (StringUtils.isNotBlank(token)) {
				// 提交给UserRealm进行认证，如果错误他会抛出异常并被捕获
				String account = JwtUtil.getClaim(token, Constant.ACCOUNT);
				// 开始认证，要AccessToken认证通过，且Redis中存在RefreshToken
				if (JwtUtil.verify(token) && 
						token.equals((String)redisUtil.get(Constant.PREFIX_SHIRO_TOKEN + account))) {
					if (accessor.getUser() == null) {
						accessor.setUser(new Principal() {
							@Override
							public String getName() {
								return account;
							}
						});
					}
					return account;
				}
			} else {
				Principal user = accessor.getUser();
				if (user != null && StringUtils.isNotBlank(user.getName())) {
					return user.getName();
				}
			}
			throw new RuntimeException("webSocket：认证失败！");
		} catch (Exception e) {
			//认证失败，关闭连接
			StompHeaderAccessor headerAccessor = StompHeaderAccessor.create(StompCommand.ERROR);
	        headerAccessor.setSessionId(accessor.getSessionId());
	        Message<byte[]> createMessage = MessageBuilder.createMessage(new byte[0], headerAccessor.getMessageHeaders());
	        subProtocolWebSocketHandler.handleMessage(createMessage);
		}
		throw new RuntimeException("webSocket：认证失败！");
	}

}
