package com.ydw.admin.model.db;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2021-06-02
 */
public class UserMission implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    private String userId;

    private Integer missionId;

    /**
     * 状态0 待完成 1 已完成 2 待领取
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

    public Integer getMissionId() {
        return missionId;
    }

    public void setMissionId(Integer missionId) {
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
        return "UserMission{" +
        "id=" + id +
        ", userId=" + userId +
        ", missionId=" + missionId +
        ", status=" + status +
        "}";
    }
}
