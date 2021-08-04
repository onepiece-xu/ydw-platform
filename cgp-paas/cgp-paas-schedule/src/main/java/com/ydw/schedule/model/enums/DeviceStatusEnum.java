package com.ydw.schedule.model.enums;

/**
 * 设备状态枚举
 * @author xulh
 *
 */
public enum DeviceStatusEnum {

	IDLE(1),		//空闲
	USED(2),		//已使用
	ERROR(3),	    //错误
	INSTALLING(4),	//app安装、卸载中
	REBOOTING(5),	//重启中
    PREPARING(6);   //准备使用
	
	private Integer status;

	DeviceStatusEnum(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public static DeviceStatusEnum getEnum(Integer status){
		DeviceStatusEnum[] values = DeviceStatusEnum.values();
		for(DeviceStatusEnum e : values){
			if(e.getStatus() == status){
				return e;
			}
		}
		return null;
	}
}
