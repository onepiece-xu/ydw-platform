package com.ydw.resource.model.vo;

public class ApplyResultVO extends ConnectVO{

    private String appName;

    private String clusterId;

    private String clusterName;

    private long connectedTime;

    public ApplyResultVO(ConnectVO vo){
        this.appId = vo.appId;
        this.connectId = vo.connectId;
        this.connectTime = vo.connectTime;
        this.deviceId = vo.deviceId;
        this.queuedTime = vo.queuedTime;
        this.connectedTime = (System.currentTimeMillis() - vo.getConnectTime()) / 1000 ;
    }

    public ApplyResultVO(String appName, String clusterId, String clusterName) {
        this.appName = appName;
        this.clusterId = clusterId;
        this.clusterName = clusterName;
    }

    public ApplyResultVO(String appName, String clusterId, String clusterName, ConnectVO vo){
        this.appName = appName;
        this.clusterId = clusterId;
        this.clusterName = clusterName;
        this.appId = vo.appId;
        this.connectId = vo.connectId;
        this.connectTime = vo.connectTime;
        this.connectedTime = (System.currentTimeMillis() - this.connectTime) / 1000 ;
        this.deviceId = vo.deviceId;
        this.queuedTime = vo.queuedTime;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }
}
