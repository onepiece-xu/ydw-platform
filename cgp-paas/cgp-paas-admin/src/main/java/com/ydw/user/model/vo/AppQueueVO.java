package com.ydw.user.model.vo;

import java.io.Serializable;

/**
 * app的排队数
 * @author xulh
 *
 */
public class AppQueueVO implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = -4649004215622107185L;

    /**
     * 游戏id
     */
    private String appId;

    /**
     * 游戏名称
     */
    private String appName;

    /**
     * 节点id
     */
    private String clusterId;

    /**
     * 节点名称
     */
    private String clusterName;

    /**
     * 排队数
     */
    private Long queue;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Long getQueue() {
        return queue;
    }

    public void setQueue(Long queue) {
        this.queue = queue;
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
