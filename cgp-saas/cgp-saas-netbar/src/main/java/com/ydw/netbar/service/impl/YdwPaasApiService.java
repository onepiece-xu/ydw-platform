package com.ydw.netbar.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ydw.netbar.config.jwt.JwtUtil;
import com.ydw.netbar.dao.EnterpriseMapper;
import com.ydw.netbar.model.constant.Constant;
import com.ydw.netbar.model.db.Enterprise;
import com.ydw.netbar.model.vo.ResultInfo;
import com.ydw.netbar.util.HttpUtil;
import com.ydw.netbar.util.RedisUtil;

@Component
public class YdwPaasApiService {
	
	@Autowired
	private EnterpriseMapper enterpriseMapper;
	
	@Value("${url.paas}")
	private String paasUrl;
	
	@Autowired
	private RedisUtil redisUtil;
	
	public String getAccount(){
		return JwtUtil.getClaim((String) SecurityUtils.getSubject().getPrincipal(), Constant.ACCOUNT);
	}

	public String getPaasToken(){
		String saasToken = (String) SecurityUtils.getSubject().getPrincipal();
		String paasToken = (String)redisUtil.hget(Constant.TOKEN_MAP_SAAS_PAAS, saasToken);
		if(StringUtils.isBlank(paasToken)){
			Enterprise enterprise = enterpriseMapper.selectOne(new QueryWrapper<>());
			Map<String,String> params = new HashMap<>();
			params.put(Constant.ACCOUNT, JwtUtil.getClaim(saasToken, Constant.ACCOUNT));
			params.put(Constant.IDENTIFICATION, enterprise.getIdentification());
			params.put(Constant.SECRETKEY, enterprise.getSecretKey());
			String doJsonPost = HttpUtil.doJsonPost(paasUrl + Constant.Url.URL_PAAS_LOGIN, params);
			ResultInfo resultInfo = JSON.parseObject(doJsonPost, ResultInfo.class);
			if(resultInfo.getCode() == 200){
				paasToken = (String)resultInfo.getData();
				redisUtil.hset(Constant.TOKEN_MAP_SAAS_PAAS, saasToken, paasToken);
			}else{
				throw new RuntimeException("登录paas平台失败！");
			}
		}
		return paasToken;
	}
	
	public void buildHeaders(Map<String, String> header){
		header.put(Constant.PAAS_AUTHORIZATION_HEADER_NAME, getPaasToken());
	}
	
	public Map<String, String> buildHeaders(){
		Map<String, String> header = new HashMap<>();
		header.put(Constant.PAAS_AUTHORIZATION_HEADER_NAME, getPaasToken());
		return header;
	}
}
