package com.ydw.platform.model.db;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户挂机记录
 * </p>
 *
 * @author xulh
 * @since 2021-07-20
 */
public class UserHangup implements Serializable {

    /**
     * 主键
     */
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 连接id
     */
    private String connectId;

    /**
     * 预计挂机时长（分钟）
     */
    private int hangupDuration;

    /**
     * 开始时间
     */
    private LocalDateTime beginTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否有效
     */
    private boolean valid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getConnectId() {
        return connectId;
    }

    public void setConnectId(String connectId) {
        this.connectId = connectId;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public boolean getValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public int getHangupDuration() {
        return hangupDuration;
    }

    public void setHangupDuration(int hangupDuration) {
        this.hangupDuration = hangupDuration;
    }

    @Override
    public String toString() {
        return "UserHangup{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", connectId='" + connectId + '\'' +
                ", hangupDuration=" + hangupDuration +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", valid=" + valid +
                ", updateTime=" + updateTime +
                '}';
    }
}
