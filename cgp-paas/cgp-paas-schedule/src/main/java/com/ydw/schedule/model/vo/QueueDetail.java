package com.ydw.schedule.model.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 排队详情
 */
public class QueueDetail implements Serializable {

    /**
     * 游戏id
     */
    protected String appId;

    /**
     * 节点id
     */
    protected List<String> clusterIds;

    /**
     * 配置id
     */
    protected String baseId;

    public QueueDetail() {
    }

    public QueueDetail(String appId, List<String> clusterIds, String baseId) {
        this.appId = appId;
        this.clusterIds = clusterIds;
        this.baseId = baseId;
    }

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
}
