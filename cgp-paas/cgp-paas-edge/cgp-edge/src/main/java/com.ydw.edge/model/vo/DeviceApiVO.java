package com.ydw.edge.model.vo;

import java.io.Serializable;

/**
 * @author xulh
 * @description: TODO
 * @date 2021/5/813:11
 */
public class DeviceApiVO implements Serializable {

    private String clusterId;

    private String rebootUrl;

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public String getRebootUrl() {
        return rebootUrl;
    }

    public void setRebootUrl(String rebootUrl) {
        this.rebootUrl = rebootUrl;
    }
}
