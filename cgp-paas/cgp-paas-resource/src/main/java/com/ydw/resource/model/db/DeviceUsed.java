package com.ydw.resource.model.db;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2020-07-30
 */
@TableName("tb_device_used")
public class DeviceUsed implements Serializable {

    /**
     * 主键
     */
    private String id;

    /**
     * 资源ID
     */
    private String deviceId;

    /**
     * 应用表中的应用ID，每一个应用都需要进行申请后才可以使用，方便调度
     */
    private String appId;

    /**
     * 应用所属企业ID
     */
    private String enterpriseId;

    /**
     * 终端用户ID
     */
    private String customId;

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
     * 时长（分钟）
     */
    private Integer totalTime;

    /**
     * 连接类型0:apk,1:webrtc
     */
    private Integer client;

    /**
     * 连接类型0:apk,1:webrtc
     */
    private Integer type;

    /**
     * 连接类型0:apk,1:webrtc
     */
    private Integer saas;

    /**
     * 是否有效 :   0有效  1 无效（软删除）
     */
    private Boolean valid = true;

    public DeviceUsed() {
    }

    public DeviceUsed(String id, String deviceId, String appId, String enterpriseId, String customId,
                      String fromIp, LocalDateTime beginTime, LocalDateTime endTime, Integer totalTime,
                      Integer client, Integer type, Integer saas, Boolean valid) {
        this.id = id;
        this.deviceId = deviceId;
        this.appId = appId;
        this.enterpriseId = enterpriseId;
        this.customId = customId;
        this.fromIp = fromIp;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.totalTime = totalTime;
        this.client = client;
        this.type = type;
        this.saas = saas;
        this.valid = valid;
    }

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

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
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

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSaas() {
		return saas;
	}

	public void setSaas(Integer saas) {
		this.saas = saas;
	}

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "DeviceUsed{" +
        "id=" + id +
        ", deviceId=" + deviceId +
        ", appId=" + appId +
        ", enterpriseId=" + enterpriseId +
        ", customId=" + customId +
        ", fromIp=" + fromIp +
        ", beginTime=" + beginTime +
        ", endTime=" + endTime +
        ", totalTime=" + totalTime +
        ", valid=" + valid +
        "}";
    }
}
