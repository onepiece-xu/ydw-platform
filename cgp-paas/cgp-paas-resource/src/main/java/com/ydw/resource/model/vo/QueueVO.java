package com.ydw.resource.model.vo;

import java.io.Serializable;
import java.util.List;

/**
 * app的排队数
 * @author xulh
 *
 */
public class QueueVO implements Serializable{

	/**
	 * 游戏id
	 */
	private String appId;
	
	/**
	 * 游戏名称
	 */
	private String appName;
	
	/**
	 * 节点id
	 */
	private List<String> clusterIds;

	/**
	 * 排队数
	 */
	private Long rank;

	/**
	 * 排队时间
	 */
	private Long queueTime;

	/**
	 * 排队时长
	 */
	private Long queuedTime;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Long getRank() {
		return rank;
	}

	public void setRank(Long rank) {
		this.rank = rank;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public List<String> getClusterIds() {
		return clusterIds;
	}

	public void setClusterIds(List<String> clusterIds) {
		this.clusterIds = clusterIds;
	}

	public Long getQueueTime() {
		return queueTime;
	}

	public void setQueueTime(Long queueTime) {
		this.queueTime = queueTime;
	}

	public Long getQueuedTime() {
		return queuedTime;
	}

	public void setQueuedTime(Long queuedTime) {
		this.queuedTime = queuedTime;
	}
}