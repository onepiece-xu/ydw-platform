package com.ydw.schedulejob.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.ydw.schedulejob.util.SpringBeanUtil;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.ydw.schedulejob.util.HttpUtil;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

public class HttpJob implements Job {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 目前指支持参数以json或者param的形式回调
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
	    //获取nacos客户端
        DiscoveryClient discoveryClient = SpringBeanUtil.getBean(DiscoveryClient.class);
        //获取job执行内容
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		String server = (String) jobDataMap.get("server");
		String path = (String) jobDataMap.get("path");
		String method = (String) jobDataMap.get("method");
		Object headerObj = jobDataMap.get("headers");
		Object bodyObj = jobDataMap.get("params");
        //获取真正执行的url
        String url = server;
        List<ServiceInstance> instances = discoveryClient.getInstances(server);
        if(instances != null && !instances.isEmpty()){
            ServiceInstance serviceInstance = instances.get(new Random().nextInt(instances.size()));
            url = "http://"+serviceInstance.getHost()+":"+serviceInstance.getPort() + "/" + server;
        }
        url = url + "/" + path;

        Map<String, String> headers = new HashMap<String, String>();
		if (headerObj != null) {
			headers = (Map) JSON.parse(headerObj.toString());
		}
		Map params = null;
		if (bodyObj != null) {
			params = (Map) JSON.parse(bodyObj.toString());
		}
		if (method.equalsIgnoreCase("GET")) {
			HttpUtil.doGet(url, headers, params);
		} else if (method.equalsIgnoreCase("POST")) {
			HttpUtil.doJsonPost(url, headers, params);
		} else if (method.equalsIgnoreCase("PUT")) {
			HttpUtil.doPut(url, headers, params);
		} else if (method.equalsIgnoreCase("DELETE")) {
			HttpUtil.doDelete(url, headers, params);
		}
	}
}
