package com.ydw.message.service;

import com.ydw.message.model.vo.ResultInfo;

import java.util.ArrayList;

/**
 * @author xulh
 * @description: TODO
 * @date 2021/1/514:05
 */
public interface ISMSService {

    ResultInfo sendSms(String mobileNumber, int templateId, ArrayList<String> params);
}
