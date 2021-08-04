package com.ydw.charge.model.db;

import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2020-10-07
 */
@TableName("tb_charge")
public class Charge implements Serializable {

    private static final long serialVersionUID=1L;

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
    @TableField("connect_Id")
    private String connectId;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * appId
     */
    private String appId;

    /**
     * 本次扣费时长(分钟）
     */
    private Long chargeDuration;

    /**
     * 本次计费时间
     */
    private LocalDateTime createTime;

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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Long getChargeDuration() {
        return chargeDuration;
    }

    public void setChargeDuration(Long chargeDuration) {
        this.chargeDuration = chargeDuration;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Charge{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", connectId='" + connectId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", appId='" + appId + '\'' +
                ", chargeDuration=" + chargeDuration +
                ", createTime=" + createTime +
                '}';
    }
}
