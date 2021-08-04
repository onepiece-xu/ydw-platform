package com.ydw.platform.model.db;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2021-07-06
 */
public class UserLimitedMission implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 任务id
     */
    private String missionId;

    /**
     * 0待完成 1 已完成待领取 2 已领取
     */
    private Integer status;


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

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserLimitedMission{" +
        "id=" + id +
        ", userId=" + userId +
        ", missionId=" + missionId +
        ", status=" + status +
        "}";
    }
}
