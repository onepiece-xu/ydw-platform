package com.ydw.advert.controller;

import com.ydw.advert.config.jwt.JwtUtil;
import com.ydw.advert.model.constant.Constant;
import com.ydw.advert.model.vo.ResultInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	/**
	 * 获取用户名
	 * @author xulh
	 * @date 2020年5月25日
	 * @return String
	 */
	public String getAccount() {
		String token = getToken();
		return JwtUtil.getClaim(token, Constant.ACCOUNT);
	}
	
	/**
	 * 获取用户名
	 * @author xulh
	 * @date 2020年5月25日
	 * @return String
	 */
	public String getEnterpriseId() {
		String token = getToken();
		return JwtUtil.getClaim(token, Constant.IDENTIFICATION);
	}
	
	// 获取客户端IP
	public String getIpAddr() {
		HttpServletRequest request = getRequest();
		String ip = getRequest().getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	@ExceptionHandler(Exception.class)
	public ResultInfo handle(Exception e) {
		logger.error("【{}】处发生异常: 【{}】", e.getStackTrace()[0], e.getMessage());
		return ResultInfo.fail("接口返回失败！");
	}
}
