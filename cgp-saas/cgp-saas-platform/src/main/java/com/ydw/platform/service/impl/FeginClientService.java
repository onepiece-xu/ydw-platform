package com.ydw.platform.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ydw.platform.model.constant.Constant;
import com.ydw.platform.model.vo.Msg;
import com.ydw.platform.service.IYdwAuthenticationService;
import com.ydw.platform.service.IYdwMessageService;
import com.ydw.platform.service.IYdwSchedulejobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FeginClientService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IYdwAuthenticationService ydwAuthenticationService;

	@Autowired
	private IYdwMessageService ydwMessageService;

    @Autowired
    private IYdwSchedulejobService ydwSchedulejobService;
	
	/**
	 * 企业在pass的token
	 * @return
	 */
	public String getPaasToken(){
		String paasToken = ydwAuthenticationService.getPaasToken();
		JSONObject jsonObject = JSON.parseObject(paasToken);
		if(jsonObject.getIntValue("code") != 200){
			logger.error("获取paas的token失败！");
			throw new RuntimeException("获取paas的token失败！");
		}
		return jsonObject.getString("data");
	}
	
	public Map<String, String> buildHeaders(){
		Map<String, String> header = new HashMap<>();
		String paasToken = getPaasToken();
		header.put(Constant.AUTHORIZATION_HEADER_NAME, paasToken);
		return header;
	}

	public List<String> getUserOnlineTokens(String userId){
        List<String> tokens = new ArrayList<>();
        String userOnlineTokens = ydwAuthenticationService.getUserOnlineTokens(userId);
        JSONObject jsonObject = JSON.parseObject(userOnlineTokens);
        if (jsonObject.getIntValue("code") == 200){
            JSONArray data = jsonObject.getJSONArray("data");
            tokens = data.toJavaList(String.class);
        }
        return tokens;
    }

    public void addScheduleJob(HashMap<String, Object> job) {
        ydwSchedulejobService.addScheduleJob(job);
    }

    public void deleteScheduleJob(String scheduleJobName,String scheduleJobGroup){ydwSchedulejobService.deleteScheduleJob(scheduleJobName,scheduleJobGroup);}

	public JSONObject getScheduleJob(String scheduleJobName, String scheduleJobGroup){
		return ydwSchedulejobService.getScheduleJob(scheduleJobName,scheduleJobGroup);
	}

    public void sendMsg(Msg msg) {
        ydwMessageService.sendMsg(msg);
    }
}
