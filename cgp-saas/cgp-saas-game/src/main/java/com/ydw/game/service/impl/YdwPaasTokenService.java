package com.ydw.game.service.impl;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ydw.game.config.jwt.JwtUtil;
import com.ydw.game.dao.EnterpriseMapper;
import com.ydw.game.model.constant.Constant;
import com.ydw.game.model.db.Enterprise;
import com.ydw.game.model.vo.ResultInfo;
import com.ydw.game.redis.RedisUtil;
import com.ydw.game.util.HttpUtil;

@Component
public class YdwPaasTokenService {
	
	@Autowired
	private EnterpriseMapper enterpriseMapper;
	
	@Value("${url.paasApi}")
	private String paasUrl;
	
	@Autowired
	private RedisUtil redisUtil;
	
	@Value("${shiro.accessTokenExpireTime}")
	private Integer accessTokenExpireTime;
	
	public String getAccount(){
		return JwtUtil.getClaim((String) SecurityUtils.getSubject().getPrincipal(), Constant.ACCOUNT);
	}
	
	/**
	 * 企业在pass的token
	 * @return
	 */
	public String getEnterprisePaasToken(){
		String paasToken = (String) redisUtil.get(Constant.TOKEN_PAAS);
		if(StringUtils.isBlank(paasToken)){
			Enterprise enterprise = enterpriseMapper.selectOne(new QueryWrapper<>());
			Map<String,Object> params = new HashMap<>();
			params.put(Constant.IDENTIFICATION, enterprise.getIdentification());
			params.put(Constant.SECRETKEY, enterprise.getSecretKey());
			params.put(Constant.SAAS, Constant.ApplyParamConfig.SAAS_TYPE_GAME);
			String doJsonPost = HttpUtil.doJsonPost(paasUrl + Constant.Url.URL_PAAS_ENTERPRISE_LOGIN, params);
			ResultInfo resultInfo = JSON.parseObject(doJsonPost, ResultInfo.class);
			if(resultInfo.getCode() == 200){
				paasToken = (String)resultInfo.getData();
				redisUtil.set(Constant.TOKEN_PAAS, paasToken, accessTokenExpireTime);
			}else{
				throw new RuntimeException("登录paas平台失败！");
			}
		}
		return paasToken;
	}

	/**
	 * 用户在pass的token
	 * @return
	 */
	public String getUserPaasToken(){
		String saasToken = (String) SecurityUtils.getSubject().getPrincipal();
		String paasToken = (String)redisUtil.get(MessageFormat.format(Constant.TOKEN_SAAS_PAAS, saasToken));
		if(StringUtils.isBlank(paasToken)){
			Enterprise enterprise = enterpriseMapper.selectOne(new QueryWrapper<>());
			Map<String,Object> params = new HashMap<>();
			params.put(Constant.ACCOUNT, JwtUtil.getClaim(saasToken, Constant.ACCOUNT));
			params.put(Constant.IDENTIFICATION, enterprise.getIdentification());
			params.put(Constant.SECRETKEY, enterprise.getSecretKey());
			params.put(Constant.SAAS, Constant.ApplyParamConfig.SAAS_TYPE_GAME);
			String doJsonPost = HttpUtil.doJsonPost(paasUrl + Constant.Url.URL_PAAS_USER_LOGIN, params);
			ResultInfo resultInfo = JSON.parseObject(doJsonPost, ResultInfo.class);
			if(resultInfo.getCode() == 200){
				paasToken = (String)resultInfo.getData();
				redisUtil.set(MessageFormat.format(Constant.TOKEN_SAAS_PAAS, saasToken), paasToken, accessTokenExpireTime);
			}else{
				throw new RuntimeException("登录paas平台失败！");
			}
		}
		return paasToken;
	}
	
	public void buildHeaders(Map<String, String> header){
		String paasToken = null;
		try {
			paasToken = getUserPaasToken();
		} catch (Exception e) {
			paasToken = getEnterprisePaasToken();
		}
		header.put(Constant.PAAS_AUTHORIZATION_HEADER_NAME, paasToken);
	}
	
	public Map<String, String> buildHeaders(){
		Map<String, String> header = new HashMap<>();
		String paasToken = null;
		try {
			paasToken = getUserPaasToken();
		} catch (Exception e) {
			paasToken = getEnterprisePaasToken();
		}
		header.put(Constant.PAAS_AUTHORIZATION_HEADER_NAME, paasToken);
		return header;
	}
}
