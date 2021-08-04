package com.ydw.resource.controller;

import com.ydw.resource.model.constant.Constant;
import com.ydw.resource.utils.ResultInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	public Integer getSaas(){
		String saas = getRequest().getHeader(Constant.SAAS_HEADER_NAME);
		if (StringUtils.isBlank(saas)) {
			throw new RuntimeException("获取saas为空！");
		}
		return new Integer(saas);
    }

    public String getEnterpriseId(){
		String identification = getRequest().getHeader(Constant.IDENTIFICATION_HEADER_NAME);
		if (StringUtils.isBlank(identification)) {
			throw new RuntimeException("获取identification为空！");
		}
		return identification;
    }

	@ExceptionHandler(Exception.class)
	public ResultInfo handle(Exception e) {
		logger.error("【{}】处发生异常: 【{}】", e.getStackTrace()[0], e.getMessage());
		return ResultInfo.fail("接口返回失败！");
	}
}
