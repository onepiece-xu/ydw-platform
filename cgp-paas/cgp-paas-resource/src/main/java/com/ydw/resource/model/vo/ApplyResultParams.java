package com.ydw.resource.model.vo;

import java.io.Serializable;

import com.ydw.resource.model.enums.ApplyStatusEnum;

public class ApplyResultParams implements Serializable{

	private Integer status;
	
	private ConnectVO connectVO;
	
	private QueueVO queueVO;
	
	public ApplyResultParams() {
		super();
		status = ApplyStatusEnum.GOT.getStatus();
	}	
	
	public ApplyResultParams(ConnectVO connectVO) {
		this.connectVO = connectVO;
		status = ApplyStatusEnum.GAIN.getStatus();
	}
	
	public ApplyResultParams(QueueVO queueVO) {
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
