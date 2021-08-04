package com.ydw.charge.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ydw.charge.model.constants.Constant;
import com.ydw.charge.model.vo.ResultInfo;
import com.ydw.charge.service.IYdwAuthenticationService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public HttpServletRequest getRequest(){
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		return servletRequestAttributes.getRequest();
	}

	public String getAccount(){
		String account = getRequest().getHeader("account");
		if (StringUtils.isBlank(account)) {
			throw new RuntimeException("获取token为空！");
		}
		return account;
	}

	@ExceptionHandler(Exception.class)
	public ResultInfo handle(Exception e) {
		logger.error("【{}】处发生异常: 【{}】", e.getStackTrace()[0], e.getMessage());
		return ResultInfo.fail("接口返回失败！");
	}
}
