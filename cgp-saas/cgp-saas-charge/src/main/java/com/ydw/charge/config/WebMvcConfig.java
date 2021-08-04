package com.ydw.charge.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ydw.charge.interceptor.LogInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	/**
	 * 日志拦截器
	 */
	@Bean
	LogInterceptor getLogInterceptor() {
		return new LogInterceptor();
	}
	
	/**
	 * 重写添加拦截器方法并添加配置拦截器
	 * 
	 * @param registry
	 */

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		List<String> excludePatternsList = new ArrayList<>();
		// 静态资源放开不拦截
		excludePatternsList.add("/templates/**");
		// 日志拦截器
		registry.addInterceptor(getLogInterceptor()).addPathPatterns("/**").excludePathPatterns(excludePatternsList);
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.mediaType("json", MediaType.APPLICATION_JSON);
		configurer.mediaType("xml", MediaType.APPLICATION_XML);
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
}
