package com.ydw.game.websocket.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ydw.game.model.enums.ConnectStatusEnum;
import com.ydw.game.model.vo.ConnectMsg;
import com.ydw.game.model.vo.WSMsg;
import com.ydw.game.service.IConnectService;

/**
 * 连接消息处理
 * @author xulh
 *
 */
@Service
public class ConnectMessageService extends MessageService{
	
	private Logger logger  = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IConnectService connectServiceImpl;
	
	@Autowired
    private SimpMessagingTemplate messagingTemplate;
	
	public void send(String msg) {
		WSMsg wsmsg = JSON.parseObject(msg, WSMsg.class);
		if(wsmsg.getType() == 0){
			messagingTemplate.convertAndSendToUser(wsmsg.getReceiver(), wsmsg.getDestination(), wsmsg.getData());
		}else{
			messagingTemplate.convertAndSend(wsmsg.getDestination(), wsmsg.getData());
		}
	}
	
	public void receive(String wsmsg){
//		redisUtil.sendMsg(MessageTopicEnum.CONNECT.getTopic(), wsmsg);
	}

	public void handlerMsg(ConnectMsg connectMsg) {
		Integer status = connectMsg.getConnectSatus();
		if(ConnectStatusEnum.UNCONNECT.getStatus() == status){
			
		}else if(ConnectStatusEnum.APPLYCONNECT.getStatus() == status){
			
		}else if(ConnectStatusEnum.CONNECTING.getStatus() == status){
			
		}else if(ConnectStatusEnum.CONNECTOUT.getStatus() == status){
			
		}else if(ConnectStatusEnum.ABNORMALCONNECT.getStatus() == status){
			connectServiceImpl.abnormalNotice(connectMsg.getConnectId());
		}
	}
}
