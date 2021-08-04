package com.ydw.schedulejob.model.db;

import java.io.Serializable;
import java.util.Date;

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
	 * 主键
	 */
	private String id;
	/**
	 * 任务名称（组内唯一）
	 */
    private String jobName;
    /**
     * 任务组
     */
    private String jobGroup;
    /**
     * 状态
     */
    private String status;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 频率
     */
    private Integer frequency;
    /**
     * cron表达式
     */
    private String cron;
	/**
	 * 服务
	 */
	private String server;
    /**
     * 地址路径
     */
    private String path;
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
    private String params;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
