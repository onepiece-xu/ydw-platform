package com.ydw.edge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
public class EdgeApplication  extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(EdgeApplication.class, args);
    }

}
