package com.ydw.message.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.ydw.message.interceptor.LogInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.mediaType("json", MediaType.APPLICATION_JSON);
		configurer.mediaType("xml", MediaType.APPLICATION_XML);
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	/**
	 * 配置消息转换器
	 * 
	 * @param converters
	 */
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		// 需要定义一个convert转换消息的对象;
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		// 添加fastJson的配置信息;
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		// 全局时间配置
		fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
		fastJsonConfig.setCharset(Charset.forName("UTF-8"));
		// 处理中文乱码问题
		List<MediaType> fastMediaTypes = new ArrayList<>();
		fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		// 在convert中添加配置信息.
		fastConverter.setSupportedMediaTypes(fastMediaTypes);
		fastConverter.setFastJsonConfig(fastJsonConfig);
		converters.add(0, fastConverter);
	}

	/**
	 * 日志拦截器
	 */
	@Bean
	LogInterceptor getLogInterceptor() {
		return new LogInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		List<String> excludePatternsList = new ArrayList<>();
		// 静态资源放开不拦截
		excludePatternsList.add("/templates/**");
		// 日志拦截器
		registry.addInterceptor(getLogInterceptor()).addPathPatterns("/**").excludePathPatterns(excludePatternsList);
		// 上传文件拦截器
	}
}
