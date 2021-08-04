package com.ydw.schedule.model.vo;

import java.io.Serializable;

public class ClusterQueueStatistics implements Serializable {

    /**
     * 集群id
     */
    private String clusterId;

    /**
     * 集群名称
     */
    private String clusterName;

    /**
     * 排队数量
     */
    private long queueNum;

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

    public long getQueueNum() {
        return queueNum;
    }

    public void setQueueNum(long queueNum) {
        this.queueNum = queueNum;
    }
}
