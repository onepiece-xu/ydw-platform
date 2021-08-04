package com.ydw.platform.model.db;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2020-08-04
 */
@TableName("tb_connect")
public class Connect implements Serializable {

    /**
     * 连接ID
     */
    private String id;

    /**
     * 资源ID
     */
    private String deviceId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 应用表中的应用ID，每一个应用都需要进行申请后才可以使用，方便调度
     */
    private String appId;

    /**
     * 终端用户ID
     */
    private String userId;

    /**
     * 集群id
     */
    private String clusterId;

    /**
     * 集群名称
     */
    private String clusterName;

    /**
     * 访问来源IP
     */
    private String fromIp;

    /**
     * 开始时间
     */
    private LocalDateTime beginTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 异常断开时间
     */
    private LocalDateTime abnormalTime;

    /**
     * 时长（秒）
     */
    private Long totalTime;

    /**
     * 0-移动客户端， 1-pc客户端
     */
    private Integer client;

    /**
     * 0-apk / 1-webrtc
     */
    private Integer type;

    /**
     * 是否有效 :   0无效  1 有效（软删除）
     */
    private Boolean valid;

    /**
     * 网吧id
     */
    private String netbarId;

    /**
     * 租号参数
     */
    private String rentalParams;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getFromIp() {
        return fromIp;
    }

    public void setFromIp(String fromIp) {
        this.fromIp = fromIp;
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

    public LocalDateTime getAbnormalTime() {
        return abnormalTime;
    }

    public void setAbnormalTime(LocalDateTime abnormalTime) {
        this.abnormalTime = abnormalTime;
    }

    public Long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Long totalTime) {
        this.totalTime = totalTime;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getNetbarId() {
        return netbarId;
    }

    public void setNetbarId(String netbarId) {
        this.netbarId = netbarId;
    }

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

    public String getRentalParams() {
        return rentalParams;
    }

    public void setRentalParams(String rentalParams) {
        this.rentalParams = rentalParams;
    }

    @Override
    public String toString() {
        return "Connect{" +
        "id=" + id +
        ", deviceId=" + deviceId +
        ", appId=" + appId +
        ", fromIp=" + fromIp +
        ", beginTime=" + beginTime +
        ", endTime=" + endTime +
        ", abnormalTime=" + abnormalTime +
        ", totalTime=" + totalTime +
        ", client=" + client +
        ", type=" + type +
        ", valid=" + valid +
        "}";
    }
}
