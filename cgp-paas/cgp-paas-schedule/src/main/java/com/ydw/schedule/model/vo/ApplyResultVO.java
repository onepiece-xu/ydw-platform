package com.ydw.schedule.model.vo;

import java.io.Serializable;

import com.ydw.schedule.model.enums.ApplyStatusEnum;

public class ApplyResultVO implements Serializable{

	private Integer status;
	
	private ConnectVO connectVO;
	
	private QueueVO queueVO;
	
	public ApplyResultVO(ConnectVO connectVO) {
		this.connectVO = connectVO;
		status = ApplyStatusEnum.GAIN.getStatus();
	}
	
	public ApplyResultVO(QueueVO queueVO) {
		this.queueVO = queueVO;
		status = ApplyStatusEnum.QUEUE.getStatus();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public ConnectVO getConnectVO() {
		return connectVO;
	}

	public void setConnectVO(ConnectVO connectVO) {
		this.connectVO = connectVO;
	}

	public QueueVO getQueueVO() {
		return queueVO;
	}

	public void setQueueVO(QueueVO queueVO) {
		this.queueVO = queueVO;
	}

	
}
