package com.ydw.recharge.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.recharge.model.vo.ResultInfo;
import com.ydw.recharge.service.IYdwAuthenticationService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public HttpServletRequest getRequest(){
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		return servletRequestAttributes.getRequest();
	}
	
	public HttpServletResponse getResponse(){
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		return servletRequestAttributes.getResponse();
	}

	@Autowired
	private IYdwAuthenticationService ydwAuthenticationService;

	public JSONObject getUserInfo(){
		String userInfoByToken = ydwAuthenticationService.getUserInfoByToken(getToken());
		if(userInfoByToken == null ||
				JSON.parseObject(userInfoByToken).getIntValue("code") != 200){
			logger.error("获取用户信息失败！");
			return null;
		}
		return JSON.parseObject(userInfoByToken).getJSONObject("data");
	}

	public String getAccount(){
		String account = getRequest().getHeader("account");
		if (StringUtils.isBlank(account)) {
			throw new RuntimeException("获取token为空！");
		}
		return account;
	}

	/**
	 * 获取token
	 * @author xulh
	 * @date 2020年5月25日
	 * @return String
	 */
	public String getToken() {
		String token = getRequest().getHeader("Authorization");
		if (StringUtils.isBlank(token)) {
			throw new RuntimeException("获取token为空！");
		}
		return token;
	}

	public Page buildPage(Object object) {
		int current = 1;// 当前页
		int size = 20;// 每页显示条数
		String pageNum = this.getRequest().getParameter("pageNum");
		if (StringUtils.isNotBlank(pageNum)) {
			current = Integer.parseInt(pageNum);
		}else{
			if(object != null){
				JSONObject parseObject = JSON.parseObject(JSON.toJSONString(object));
				Integer integer = parseObject.getInteger("pageNum");
				if(integer != null){
					current = integer;
				}
			}
		}
		String pageSize = this.getRequest().getParameter("pageSize");
		if (StringUtils.isNotBlank(pageSize)) {
			size = Integer.parseInt(pageSize);// 设置每页条数
		}else{
			if(object != null){
				JSONObject parseObject = JSON.parseObject(JSON.toJSONString(object));
				Integer integer = parseObject.getInteger("pageSize");
				if(integer != null){
					size = integer;
				}
			}
		}
		return new Page(current, size);
	}

	public Page buildPage() {
		int current = 1;// 当前页
		int size = 20;// 每页显示条数
		String pageNum = this.getRequest().getParameter("pageNum");
		if (StringUtils.isNotBlank(pageNum)) {
			current = Integer.parseInt(pageNum);
		}else{
			current=1;
		}
		String pageSize = this.getRequest().getParameter("pageSize");
		if (StringUtils.isNotBlank(pageSize)) {
			size = Integer.parseInt(pageSize);// 设置每页条数
		}else{
			size=10;
		}
		return new Page(current, size);
	}

	@ExceptionHandler(Exception.class)
	public ResultInfo handle(Exception e) {
		logger.error("【{}】处发生异常: 【{}】", e.getStackTrace()[0], e.getMessage());
		return ResultInfo.fail("接口返回失败！");
	}
}
