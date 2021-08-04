package com.ydw.message.controller;

import com.ydw.message.service.IJPushService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/11/1310:14
 */
@RestController
@RequestMapping("/jpush")
public class JPushController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IJPushService IJPushService;

    /**
     * 用户和极光推送的registerid绑定
     * @param map
     */
    @PostMapping("/bind")
    public void bind(@RequestBody HashMap<String,String> map){
        String registrationId = map.get("registrationId");
        String token = map.get("token");
        if (StringUtils.isBlank(registrationId) || StringUtils.isBlank(token)){
            logger.error("token={},registrationId={}");
            return;
        }
        IJPushService.bind(token,registrationId);
    }

    /**
     * 用户和极光推送的registerid绑定
     * @param map
     */
    @PostMapping("/unbind")
    public void unbind(@RequestBody HashMap<String,String> map){
        String registrationId = map.get("registrationId");
        String token = map.get("token");
        if (StringUtils.isBlank(registrationId) || StringUtils.isBlank(token)){
            logger.error("token={},registrationId={}");
            return;
        }
        IJPushService.unbind(token,registrationId);
    }
}
