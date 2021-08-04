package com.ydw.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class BaseController {

	protected ThreadLocal<HttpServletRequest> threadLocal = new ThreadLocal<>();

	@ModelAttribute
	public void setReqAndResp(HttpServletRequest request, HttpServletResponse response) {
		threadLocal.set(request);
	}

	public HttpServletRequest getRequest() {
		return threadLocal.get();
	}

	/**
	 * 获取token
	 * @author xulh
	 * @date 2019年8月1日
	 * @return
	 * String
	 */
	public String getToken(){
		return (String) SecurityUtils.getSubject().getPrincipal();
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