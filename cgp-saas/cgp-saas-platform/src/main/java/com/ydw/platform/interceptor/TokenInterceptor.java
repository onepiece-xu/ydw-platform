package com.ydw.platform.interceptor;

import com.ydw.platform.model.constant.Constant;
import com.ydw.platform.util.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {

	private Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

	@Autowired
	private TokenUtil tokenUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(Constant.AUTHORIZATION_HEADER_NAME);
        if (StringUtils.isNotBlank(token)){
            tokenUtil.setToken(token);
        }
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String token = request.getHeader(Constant.AUTHORIZATION_HEADER_NAME);
        if (StringUtils.isNotBlank(token)){
            tokenUtil.removeToken();
        }
	}

}