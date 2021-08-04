package com.ydw.resource.model.vo;

import java.util.List;


public class ApplyParams extends SaasCommonParams {
    /**
     * 游戏id
     */
    private String appId;
    /**
     * 区服信息
     */
    private List<String> clusterIds;
    /**
     * 型号id
     */
    private String baseId;

    /**
     * 是否排队
     */
    private boolean queue = true;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public List<String> getClusterIds() {
        return clusterIds;
    }

    public void setClusterIds(List<String> clusterIds) {
        this.clusterIds = clusterIds;
    }

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public boolean getQueue() {
        return queue;
    }

    public void setQueue(boolean queue) {
        this.queue = queue;
    }

    @Override
    public String toString() {
        return "ApplyParams{" +
                "appId='" + appId + '\'' +
                ", clusterIds=" + clusterIds +
                ", baseId='" + baseId + '\'' +
                ", queue=" + queue +
                ", enterpriseId='" + enterpriseId + '\'' +
                ", saas=" + saas +
                ", account='" + account + '\'' +
                '}';
    }
}


