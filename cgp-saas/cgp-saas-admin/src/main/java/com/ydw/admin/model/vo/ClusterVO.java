package com.ydw.admin.model.vo;

import java.io.Serializable;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/10/1520:24
 */
public class ClusterVO implements Serializable {

    private String clusterId;

    private String clusterName;

    private String description;

    private String accessIp;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccessIp() {
        return accessIp;
    }

    public void setAccessIp(String accessIp) {
        this.accessIp = accessIp;
    }
}
