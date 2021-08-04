package com.ydw.schedule.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ydw.schedule.model.vo.ResultInfo;

@RestControllerAdvice
public class BaseController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public HttpServletRequest getRequest(){
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		return servletRequestAttributes.getRequest();
	}
	
	@ExceptionHandler(Exception.class)
    public ResultInfo handle(Exception e) {
        logger.error("【{}】处发生异常: 【{}】",e.getStackTrace()[0],e.getMessage());
        return ResultInfo.fail("接口返回失败！");
    }
}
