package com.ydw.schedule.model.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 请求分配设备vo
 * @author xulh
 *
 */
public class ApplyConnectVO implements Serializable{

	/**
	 * 在哪些节点找设备
	 */
	private List<String> clusterIds;
	
	/**
	 * 玩什么app
	 */
	private String appId;
	
	/**
	 * 配置id
	 */
	private String baseId;
	
	/**
	 * 用户唯一标识
	 */
	private String account;

	/**
	 * 是否排队
	 */
	private boolean queue = true;
	
	public List<String> getClusterIds() {
		return clusterIds;
	}

	public void setClusterIds(List<String> clusterIds) {
		this.clusterIds = clusterIds;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public boolean getQueue() {
		return queue;
	}

	public void setQueue(boolean queue) {
		this.queue = queue;
	}
}
