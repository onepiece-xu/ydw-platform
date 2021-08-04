package com.ydw.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ydw.message.model.vo.CgpMessage;
import com.ydw.message.redis.RedisUtil;
import com.ydw.message.service.IWSService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/11/1310:23
 */
@Service
public class WSServiceImpl implements IWSService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void send(CgpMessage msg) {
        //websocket消息先广播出去，在真正连接方推送消息
        redisUtil.sendMsg(msg.getType(),msg);
    }

    @Override
    public void sendClient(CgpMessage msg) {
        //web端消息
        if(msg.getCommunicationType() == 0 || msg.getCommunicationType() == 1){
            logger.info("websocket发送单/多消息：{}", JSON.toJSONString(msg));
            String[] receivers = msg.getReceivers();
            for (String receiver : receivers){
                JSONObject jsonObject = JSON.parseObject(msg.getData());
                if (StringUtils.isBlank(jsonObject.getString("token"))){
                    jsonObject.put("token","FFFFFF");
                }
                messagingTemplate.convertAndSendToUser(receiver, msg.getType(), jsonObject.toJSONString());
            }
        }else{
            logger.info("websocket发送广播消息：{}",JSON.toJSONString(msg));
            messagingTemplate.convertAndSend(msg.getType(), msg.getData());
        }
    }
}
