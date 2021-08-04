package com.ydw.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ydw.admin.model.constant.Constant;
import com.ydw.admin.service.IYdwAuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class YdwPaasTokenService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IYdwAuthenticationService ydwAuthenticationService;
	
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

	
	public void buildHeaders(Map<String, String> header){
		String paasToken = getPaasToken();
		header.put(Constant.PAAS_AUTHORIZATION_HEADER_NAME, paasToken);
	}
	
	public Map<String, String> buildHeaders(){
		Map<String, String> header = new HashMap<>();
		String paasToken = getPaasToken();
		header.put(Constant.PAAS_AUTHORIZATION_HEADER_NAME, paasToken);
		return header;
	}
}
