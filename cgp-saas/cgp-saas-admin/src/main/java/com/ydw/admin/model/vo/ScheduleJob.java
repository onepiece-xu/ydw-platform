package com.ydw.admin.model.vo;

import java.io.Serializable;

/**
 * @author xulh
 * @since 2019-10-11
 */
public class ScheduleJob implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5593371715813331817L;
	/**
	 * 任务名称（也唯一）
	 */
    private String jobName;
    /**
     * 任务组
     */
    private String jobGroup;
    /**
     * cron表达式
     */
    private String cron;
    /**
     * 回调地址
     */
    private String url;
    /**
     * 请求方式（get、post等）
     */
    private String method;
    /**
     * 请求头
     */
    private String headers;
    /**
     * 请求体
     */
    private Object body;
    
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public String getCron() {
		return cron;
	}
	public void setCron(String cron) {
		this.cron = cron;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getHeaders() {
		return headers;
	}
	public void setHeaders(String headers) {
		this.headers = headers;
	}
	public Object getBody() {
		return body;
	}
	public void setBody(Object body) {
		this.body = body;
	}
    
}
