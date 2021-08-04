package com.ydw.user.config;

//import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.ydw.user.interceptor.LogInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

import javax.servlet.MultipartConfigElement;
import static org.springframework.util.unit.DataUnit.MEGABYTES;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

//	@Value("${spring.application.servlet.multipart.max-file-size}")
//	private String MaxFileSize;
//	@Value("${spring.application.servlet.multipart.max-request-size}")
//	private String MaxRequestSize;

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.mediaType("json", MediaType.APPLICATION_JSON);
        configurer.mediaType("xml", MediaType.APPLICATION_XML);
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
        // 日志拦截器
        registry.addInterceptor(getLogInterceptor()).addPathPatterns("/**");
    }

//	@Bean
//	public MultipartConfigElement multipartConfigElement() {
//		MultipartConfigFactory factory = new MultipartConfigFactory();
//		//允许上传的文件最大值
//		factory.setMaxFileSize(DataSize.of(Long.parseLong(MaxFileSize),MEGABYTES)); //KB,MB
//		/// 设置总上传数据总大小
//		factory.setMaxRequestSize(DataSize.of(Long.parseLong(MaxRequestSize),MEGABYTES));
//		return factory.createMultipartConfig();
//	}
}
