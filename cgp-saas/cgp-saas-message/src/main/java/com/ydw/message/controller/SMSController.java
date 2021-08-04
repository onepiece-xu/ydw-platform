package com.ydw.message.controller;

import com.ydw.message.model.vo.ResultInfo;
import com.ydw.message.service.ISMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/12/2216:25
 */
@RestController
@RequestMapping("/sms")
public class SMSController {

    @Autowired
    private ISMSService smsService;

    @PostMapping("/sendSms")
    public ResultInfo sendSms(@RequestBody HashMap<String,Object> map){
        String mobileNumber = (String)map.get("mobileNumber");
        int templateId = (int)map.get("templateId");
        ArrayList<String> params = (ArrayList<String>)map.get("params");
        return smsService.sendSms(mobileNumber, templateId, params);
    }
}
