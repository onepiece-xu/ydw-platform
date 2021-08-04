package com.ydw.resource.model.enums;

public enum ConnectStatus {
	/**
	 * 0:未连接，1:申请连接，2:连接中，3:退出连接，4:客户端未连接警告，5:连接异常
	 */
	UNCONNECT(0),APPLYCONNECT(1),CONNECTING(2),OUTCONNECT(3),CONNECTERROR(4),CONTROLRROR(5);
	
	private int code;
	
	ConnectStatus(int code){
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
