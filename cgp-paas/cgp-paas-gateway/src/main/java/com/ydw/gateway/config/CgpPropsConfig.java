package com.ydw.gateway.config;

import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cgp")
public class CgpPropsConfig {

    private Set<String> openUrls;

	public Set<String> getOpenUrls() {
		return openUrls;
	}

	public void setOpenUrls(Set<String> openUrls) {
		this.openUrls = openUrls;
	}

}