package com.ydw.schedule.model.enums;

public enum ApplyStatusEnum {
	//刚申请到
	GAIN(0),
	//没申请到，在排队
	QUEUE(1), 
	//早已申请到
	GOT(2);
	
	private Integer status;
	
	ApplyStatusEnum(Integer status){
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
