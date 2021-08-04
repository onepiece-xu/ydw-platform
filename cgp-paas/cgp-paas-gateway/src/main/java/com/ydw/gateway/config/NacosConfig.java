package com.ydw.gateway.config;

import java.lang.management.ManagementFactory;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.alibaba.cloud.nacos.registry.NacosAutoServiceRegistration;

@Component
public class NacosConfig implements ApplicationRunner {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired(required = false)
	private NacosAutoServiceRegistration registration;

	@Value("${spring.cloud.nacos.discovery.port:-1}")
	private Integer port;

	@Override
	public void run(ApplicationArguments args) {
		if (registration != null) {
			if (port == -1) {
				port = new Integer(getTomcatPort());
			}
			if (port != -1) {
				registration.setPort(port);
				registration.start();
			}
		}
	}

	/**
	 * 获取外部tomcat端口
	 */
	public String getTomcatPort() {
		String port = "-1";
		try {
			MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
			Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"),
					Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
			port = objectNames.iterator().next().getKeyProperty("port");
		} catch (Exception e) {
			logger.warn("此环境非外部servlet环境！");
		}
		return port;
	}
}