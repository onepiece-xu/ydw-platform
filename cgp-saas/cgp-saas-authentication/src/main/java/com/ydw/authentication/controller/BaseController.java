package com.ydw.authentication.controller;

import com.alibaba.fastjson.JSON;
import com.ydw.authentication.model.constant.Constant;
import com.ydw.authentication.model.db.UserInfo;
import com.ydw.authentication.utils.JwtUtil;
import com.ydw.authentication.utils.RedisUtil;
import com.ydw.authentication.utils.ResultInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RedisUtil redisUtil;

	public HttpServletRequest getRequest(){
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return servletRequestAttributes.getRequest();
	}


	@ExceptionHandler(Exception.class)
	public ResultInfo handle(Exception e) {
		logger.error("【{}】处发生异常: 【{}】", e.getStackTrace()[0], e.getMessage());
		return ResultInfo.fail("接口返回失败！");
	}

	/**
	 * 获取token
	 * @author xulh
	 * @date 2020年5月25日
	 * @return String
	 */
	public String getToken() {
		String token = getRequest().getHeader(Constant.PAAS_AUTHORIZATION_HEADER_NAME);
		if (StringUtils.isBlank(token)) {
			throw new RuntimeException("获取token为空！");
		}
		return token;
	}

	public String getAccount(){
		return getUserInfo().getId();
	}

	public UserInfo getUserInfo(){
		String account = JwtUtil.getClaim(getToken(), Constant.ACCOUNT);
		String userString = (String) redisUtil.get(Constant.USER + account);
		UserInfo tbUserInfo = JSON.parseObject(userString, UserInfo.class);
		return tbUserInfo;
	}
}
