package com.ydw.advert.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.ydw.advert.model.constant.Constant;
import com.ydw.advert.model.vo.ResultInfo;
import com.ydw.advert.redis.RedisUtil;
import com.ydw.advert.util.HttpUtil;

@Component
public class YdwPaasTokenService {
	
	@Value("${url.paasApi}")
	private String paasUrl;
	
	@Value("${paas.identification}")
	private String identification;
	
	@Value("${paas.secretKey}")
	private String secretKey;
	
	@Autowired
	private RedisUtil redisUtil;
	
	@Value("${shiro.accessTokenExpireTime}")
	private Integer accessTokenExpireTime;
	
	/**
	 * 企业在pass的token
	 * @return
	 */
	public String getPaasToken(){
		String paasToken = (String) redisUtil.get(Constant.TOKEN_PAAS);
		if(StringUtils.isBlank(paasToken)){
			Map<String,Object> params = new HashMap<>();
			params.put(Constant.IDENTIFICATION, identification);
			params.put(Constant.SECRETKEY, secretKey);
			params.put(Constant.SAAS, Constant.ApplyParamConfig.SAAS_TYPE_ADVERT);
			String doJsonPost = HttpUtil.doJsonPost(paasUrl + Constant.Url.URL_PAAS_ENTERPRISE_LOGIN, params);
			JSONObject jsonObject = JSON.parseObject(doJsonPost);
			if (jsonObject.getIntValue("code") == 200) {
				JSONObject data = jsonObject.getJSONObject("data");
				paasToken = data.getString("token");
				Date expireDate = data.getDate("expireDate");
				LocalDateTime expireDateTime = LocalDateTime.ofInstant(expireDate.toInstant(), ZoneId.systemDefault());
				Long seconds = Duration.between(LocalDateTime.now(), expireDateTime).getSeconds();
				if (seconds <= 0) {
					return getPaasToken();
				}
				boolean setnx = redisUtil.setnx(Constant.TOKEN_PAAS, paasToken, seconds);
				if (!setnx) {
					paasToken = (String) redisUtil.get(Constant.TOKEN_PAAS);
				}
			}else{
				throw new RuntimeException("登录paas平台失败！");
			}
		}
		return paasToken;
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
