package com.ydw.edge.model.vo;

import java.io.Serializable;

public class DeviceStatus implements Serializable {
    private static final long serialVersionUID = 1L;


    //设备id
    private String  devid;

    //设备id
    private String  macAddr;
    ///设备状态
    private Integer status ;

    public String getMacAddr() {
        return macAddr;
    }

    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr;
    }

    public String getDevid() {
        return devid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setDevid(String devid) {
        this.devid = devid;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "DeviceStatus{" +
                ", devid=" + devid +
                ", macAddr=" + macAddr +
                ", status=" + status.toString() +
                "}";
    }

}
