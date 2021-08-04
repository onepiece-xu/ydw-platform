package com.ydw.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.ydw.message.model.constants.Constant;
import com.ydw.message.model.vo.CgpMessage;
import com.ydw.message.model.vo.ResultInfo;
import com.ydw.message.redis.RedisUtil;
import com.ydw.message.service.IJPushService;
import com.ydw.message.service.IMessageService;
import com.ydw.message.service.IWSService;
import com.ydw.message.service.IYdwAuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/10/2216:52
 */
@Service
public class MessageServiceImpl implements IMessageService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IWSService wsService;

    @Autowired
    private IJPushService jPushService;
    
    @Autowired
    private IYdwAuthenticationService ydwAuthenticationService;
    
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 发送给客户端消息
     * @param msg
     */
    @Override
    public void send(CgpMessage msg) {
        logger.info("发送消息【{}】", JSON.toJSONString(msg));
        //websocket消息先广播出去，在真正连接方推送消息
        wsService.send(msg);
        //获取用户与极光推送中registrationId的对应关系
        jPushService.send(msg);
    }

    @Override
    public void receive(CgpMessage msg) {

    }

    @Override
    public void checkBind() {
        logger.info("开始检查绑定是否失效");
        Set<String> sessionsKey = redisUtil.getKeys(Constant.SET_TOKEN_SESSIONIDS_PATTERN);
        Set<String> registrationidsKey = redisUtil.getKeys(Constant.STR_TOKEN_REGISTRATIONID_PATTERN);
        sessionsKey.addAll(registrationidsKey);
        List<String> list = new ArrayList<>();
        for (String sessionKey : sessionsKey){
            String token = sessionKey.split(":")[1];
            ResultInfo resultInfo = ydwAuthenticationService.checkToken(token);
            if (resultInfo.getCode() != 200){
                logger.info("此token【{}】过期，解除绑定关系！",token);
                list.add(sessionKey);
            }
        }
        if (!list.isEmpty()){
            redisUtil.del(list);
        }
    }

}
