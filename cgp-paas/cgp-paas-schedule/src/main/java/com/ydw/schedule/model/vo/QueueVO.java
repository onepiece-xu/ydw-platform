package com.ydw.schedule.model.vo;

/**
 * app的排队数
 * @author xulh
 *
 */
public class QueueVO extends QueueDetail{

	/**
	 * 排队数
	 */
	private Long rank;
	
	/**
	 * 排队时间
	 */
	private Long queueTime;

	public QueueVO() {
	}

	public QueueVO(Long rank, Long queueTime) {
		this.rank = rank;
		this.queueTime = queueTime;
	}

	public QueueVO(QueueDetail queueDetail, Long rank, Long queueTime) {
		this.appId = queueDetail.appId;
		this.clusterIds = queueDetail.clusterIds;
		this.baseId = queueDetail.baseId;
		this.rank = rank;
		this.queueTime = queueTime;
	}

	public Long getRank() {
		return rank;
	}

	public void setRank(Long rank) {
		this.rank = rank;
	}

	public Long getQueueTime() {
		return queueTime;
	}

	public void setQueueTime(Long queueTime) {
		this.queueTime = queueTime;
	}
}
