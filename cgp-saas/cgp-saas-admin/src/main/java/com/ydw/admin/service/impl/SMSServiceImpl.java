package com.ydw.admin.service.impl;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.ISMSService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author xulh
 * @description: TODO
 * @date 2021/1/514:08
 */
@Service
public class SMSServiceImpl implements ISMSService {

    @Value("${sms.appId}")
    private int appId;

    @Value("${sms.appKey}")
    private String appKey;

    @Override
    public ResultInfo sendSms(String mobileNumber, int templateId, ArrayList<String> params) {
        SmsSingleSender ssender = new SmsSingleSender(appId, appKey);
        // 签名参数未提供或者为空时，会使用默认签名发送短信
        try {
            SmsSingleSenderResult result = ssender.sendWithParam("86", mobileNumber, templateId, params, "易点玩云游戏", "", "");
        } catch (HTTPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultInfo.success();
    }
}
