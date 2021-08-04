package com.ydw.user.model.db;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author heao
 * @since 2020-04-14
 */
public class TbDeviceApps implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 资源id
     */
    private String deviceId;
    /**
     * 应用表中的应用ID，每一个应用都需要进行申请后才可以使用，方便调度
     */
    private String appId;
    private Date setupTime;
    private Date uninstallTime;
    private Boolean valid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Date getSetupTime() {
        return setupTime;
    }

    public void setSetupTime(Date setupTime) {
        this.setupTime = setupTime;
    }

    public Date getUninstallTime() {
        return uninstallTime;
    }

    public void setUninstallTime(Date uninstallTime) {
        this.uninstallTime = uninstallTime;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "TbDeviceApps{" +
                "id=" + id +
                ", deviceId='" + deviceId + '\'' +
                ", appId='" + appId + '\'' +
                ", setupTime=" + setupTime +
                ", uninstallTime=" + uninstallTime +
                ", valid=" + valid +
                '}';
    }
}
