package com.ydw.message.service.impl;

import cn.jiguang.common.ClientConfig;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ydw.message.model.constants.Constant;
import com.ydw.message.model.vo.CgpMessage;
import com.ydw.message.redis.RedisUtil;
import com.ydw.message.service.IJPushService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/11/1310:18
 */
@Service
public class JPushServiceImpl implements IJPushService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${jpush.appKey}")
    private String appKey;

    @Value("${jpush.masterSecret}")
    private String masterSecret;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void bind(String token, String registrationId) {
        String key = MessageFormat.format(Constant.STR_TOKEN_REGISTRATIONID,token);
        redisUtil.set(key,registrationId);
    }

    @Override
    public void unbind(String token, String registrationId) {
        String key = MessageFormat.format(Constant.STR_TOKEN_REGISTRATIONID,token);
        redisUtil.del(key,registrationId);
    }

    @Override
    public void send(CgpMessage msg) {
        List<String> registrationIds= getReceiversRegistrationId(msg.getReceivers());
        logger.info("发送极光消息给registrationId：{}", registrationIds);
        if ((msg.getCommunicationType() == 0 || msg.getCommunicationType() == 1)
                && registrationIds.isEmpty()){
            return;
        }
        //Android，ios端消息
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, ClientConfig.getInstance());
        String data = msg.getData();
        JSONObject jsonObject = JSON.parseObject(data);
        jsonObject.put("type",msg.getType());
        jsonObject.put("disposalType",msg.getDealType());
        if (StringUtils.isBlank(jsonObject.getString("token"))){
            jsonObject.put("token","FFFFFF");
        }
        // For push, all you need do is to build PushPayload object.
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId(registrationIds))
                .setMessage(Message.content(jsonObject.toJSONString()))
                .setOptions(Options.newBuilder()
                        .setTimeToLive(msg.getTimeToLive())
                        .build())
                .build();
        try {
            jpushClient.sendPush(payload);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private List<String> getReceiversRegistrationId(String[] receivers){
        List<String> registrationIds = new LinkedList<>();
        for (String receiver : receivers){
            registrationIds.add((String)redisUtil.get(MessageFormat.format(Constant.STR_TOKEN_REGISTRATIONID,receiver)));
        }
        return registrationIds;
    }
}
