package com.ydw.admin.model.vo;

import java.io.Serializable;

/**
 * @author xulh
 * @description: TODO
 * @date 2021/2/213:25
 */
public class ChargeSummaryVO implements Serializable {

    /**
     * 连接次数
     */
    private long connectCount;

    /**
     * 连接时长
     */
    private long connectTime;

    public long getConnectCount() {
        return connectCount;
    }

    public void setConnectCount(long connectCount) {
        this.connectCount = connectCount;
    }

    public long getConnectTime() {
        return connectTime;
    }

    public void setConnectTime(long connectTime) {
        this.connectTime = connectTime;
    }
}
