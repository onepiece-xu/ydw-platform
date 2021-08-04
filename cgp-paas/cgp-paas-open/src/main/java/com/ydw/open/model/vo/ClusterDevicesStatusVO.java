package com.ydw.open.model.vo;

public class ClusterDevicesStatusVO {
    private String clusterName;

    private String  idle;

    private String used;

    private String error;

    private String  installing;

    private String  rebooting;

    private String netbarName;

    public String getNetbarName() {
        return netbarName;
    }

    public void setNetbarName(String netbarName) {
        this.netbarName = netbarName;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getIdle() {
        return idle;
    }

    public void setIdle(String idle) {
        this.idle = idle;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getInstalling() {
        return installing;
    }

    public void setInstalling(String installing) {
        this.installing = installing;
    }

    public String getRebooting() {
        return rebooting;
    }

    public void setRebooting(String rebooting) {
        this.rebooting = rebooting;
    }
}
