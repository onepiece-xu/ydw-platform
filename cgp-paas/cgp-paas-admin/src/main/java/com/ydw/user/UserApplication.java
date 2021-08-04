package com.ydw.user;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;



@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class UserApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(UserApplication.class);
    }
}
