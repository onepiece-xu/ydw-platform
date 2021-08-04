package com.ydw.game.model.enums;

public enum ConnectStatusEnum {

	UNCONNECT(0),			//未连接
	APPLYCONNECT(1),		//申请连接
	CONNECTING(2),			//连接中
	CONNECTOUT(3),			//退出连接
	ABNORMALCONNECT(4);		//异常连接
	
	private Integer status;
	
	private ConnectStatusEnum(Integer status){
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public static ConnectStatusEnum getEnum(Integer status){
		ConnectStatusEnum[] values = ConnectStatusEnum.values();
		for(ConnectStatusEnum connectStatusEnum : values){
			if(connectStatusEnum.getStatus() == status){
				return connectStatusEnum;
			}
		}
		return null;
	}
}
