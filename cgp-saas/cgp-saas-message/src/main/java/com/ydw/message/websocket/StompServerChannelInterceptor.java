package com.ydw.message.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ydw.message.model.constants.Constant;
import com.ydw.message.redis.RedisUtil;
import com.ydw.message.service.IYdwAuthenticationService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.messaging.SubProtocolWebSocketHandler;

import javax.annotation.Resource;
import java.security.Principal;
import java.text.MessageFormat;

/**
 * 维护token与sessionId的关系
 * token与sessionId是一对多的关系
 */
public class StompServerChannelInterceptor implements ChannelInterceptor {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "subProtocolWebSocketHandler")
    private SubProtocolWebSocketHandler subProtocolWebSocketHandler;

	@Autowired
	private IYdwAuthenticationService ydwAuthenticationService;

	@Autowired
	private RedisUtil redisUtil;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor stompAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
		StompCommand command = stompAccessor.getCommand();
		if (command != null) {
            String sessionId = stompAccessor.getSessionId();
			String token = doAuthentication(stompAccessor);
			// 连接
			switch (command) {
			case CONNECT:
				connect(token,sessionId);
				break;
			// 断开
			case ABORT:
			case ERROR:
			case DISCONNECT:
				disconnect(token,sessionId);
				break;
			default:
				break;
			}
		}
		return message;
	}

	public void connect(String token, String sessionId) {
		logger.info("connect websocket ...................");
		String key = MessageFormat.format(Constant.SET_TOKEN_SESSIONIDS, token);
        redisUtil.sSet(key, sessionId);
		logger.info("token: " + token + " connect websocket ...................");
	}

	public void disconnect(String token, String sessionId) {
		logger.info("disconnect websocket ...................");
        String key = MessageFormat.format(Constant.SET_TOKEN_SESSIONIDS, token);
        redisUtil.setRemove(key, sessionId);
		logger.info("token: " + token + " disconnect websocket ...................");
	}
	
	public void heartbeat(String account , String sessionId) {
//		String userSessionKey = MessageFormat.format(Constant.SET_ONLINE, account);
//		redisUtil.expire(userSessionKey, timeout);
	}

	private String doAuthentication(StompHeaderAccessor accessor) {
		try {
			// 取头部信息做鉴权
			String token = accessor.getFirstNativeHeader(Constant.AUTHORIZATION_HEADER_NAME);
			if (StringUtils.isNotBlank(token)) {
				// 提交给UserRealm进行认证，如果错误他会抛出异常并被捕获
                String result = ydwAuthenticationService.getUserInfoByToken(token);
                JSONObject jsonObject = JSON.parseObject(result);
                if (jsonObject.getIntValue("code") != 200){
                    throw new RuntimeException("webSocket：认证失败！");
                }
//                String account = jsonObject.getJSONObject("data").getString("id");
				// 开始认证，要AccessToken认证通过，且Redis中存在RefreshToken
                if (accessor.getUser() == null) {
                    accessor.setUser(new Principal() {
                        @Override
                        public String getName() {
                            return token;
                        }
                    });
                }
                return token;
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
