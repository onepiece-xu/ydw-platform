package com.ydw.user.model.db;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author heao
 * @since 2020-05-14
 */
public class TbDeviceUsed implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 连接ID自动生成
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
    private Date beginTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 时长（分钟）
     */
    private Integer totalTime;
    /**
     * 是否有效 :   0无效  1 有效（软删除）
     */
    private Boolean valid;


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

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
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

    @Override
    public String toString() {
        return "TbDeviceUsed{" +
                ", id=" + id +
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
