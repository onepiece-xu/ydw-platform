package com.ydw.edge.config;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Feign;
import okhttp3.ConnectionPool;

@Configuration
@ConditionalOnClass(Feign.class)
@AutoConfigureBefore(FeignAutoConfiguration.class)
public class FeignOkHttpConfig {
	
    @Bean
    public okhttp3.OkHttpClient okHttpClient(){
        return new okhttp3.OkHttpClient.Builder()
            .readTimeout(120, TimeUnit.SECONDS) 
            .connectTimeout(120, TimeUnit.SECONDS) 
            .writeTimeout(120, TimeUnit.SECONDS) 
            .connectionPool(new ConnectionPool())
            .build();
    }
}