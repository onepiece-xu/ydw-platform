package com.ydw.message.redis;

import com.alibaba.fastjson.JSON;
import com.ydw.message.model.vo.CgpMessage;
import com.ydw.message.service.IWSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * redis消息处理接口，redis只做消息转发，不做其他处理
 * @author xulh
 *
 */
@Component
public class RedisMessageSub {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IWSService wsService;

    /**
     * 经过redis广播后接收消息
     * @param msgStr
     */
    public void receiveMessage(String msgStr) {
        logger.info("redis客户端收到消息消息：{}",msgStr);
        CgpMessage msg = JSON.parseObject(msgStr, CgpMessage.class);
        wsService.sendClient(msg);
    }
}
