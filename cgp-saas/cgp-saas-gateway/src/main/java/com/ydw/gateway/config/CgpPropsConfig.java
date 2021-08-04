package com.ydw.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

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