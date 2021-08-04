package com.ydw.open.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.open.service.IYdwAuthenticationService;
import com.ydw.open.utils.ResultInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class BaseController {

	Logger logger = LoggerFactory.getLogger(getClass());

	public HttpServletRequest getRequest(){
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return servletRequestAttributes.getRequest();
	}

	public HttpServletResponse getResponse(){
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		return servletRequestAttributes.getResponse();
	}

	@Autowired
	private IYdwAuthenticationService ydwAuthenticationService;

	private JSONObject getUserInfo(){
		JSONObject jsonObject = null;
		ResultInfo resultInfo = ydwAuthenticationService.getUserInfo(getToken());
		if(resultInfo == null || resultInfo.getCode() != 200){
			logger.error("获取用户信息失败！");
			return jsonObject;
		}
		jsonObject = JSON.parseObject(JSON.toJSONString(resultInfo.getData()));
		return jsonObject;
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

	public String getAccount(){
		return getUserInfo().getString("id");
	}

	public String getIdentification(){
		return getUserInfo().getString("identification");
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

}