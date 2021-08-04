package com.ydw.admin.model.db;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2021-03-03
 */
public class NetbarApp implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    /**
     * 网吧id
     */
    private String netbarId;

    /**
     * appid
     */
    private String appId;

    /**
     * 包名
     */
    private String packageName;

    /**
     * app路径
     */
    private String appPath;

    /**
     * 开启游戏脚本
     */
    private String startShell;

    /**
     * 游戏关闭脚本
     */
    private String closeShell;

    /**
     * 存档路径
     */
    private String savePath;

    /**
     * 游戏类型（0：arm，1：pc）
     */
    private Integer type;

    /**
     * 是否有效
     */
    private Boolean valid;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNetbarId() {
        return netbarId;
    }

    public void setNetbarId(String netbarId) {
        this.netbarId = netbarId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppPath() {
        return appPath;
    }

    public void setAppPath(String appPath) {
        this.appPath = appPath;
    }

    public String getStartShell() {
        return startShell;
    }

    public void setStartShell(String startShell) {
        this.startShell = startShell;
    }

    public String getCloseShell() {
        return closeShell;
    }

    public void setCloseShell(String closeShell) {
        this.closeShell = closeShell;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
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

    @Override
    public String toString() {
        return "NetbarApp{" +
        "id=" + id +
        ", netbarId=" + netbarId +
        ", appId=" + appId +
        ", packageName=" + packageName +
        ", appPath=" + appPath +
        ", startShell=" + startShell +
        ", closeShell=" + closeShell +
        ", savePath=" + savePath +
        ", type=" + type +
        ", valid=" + valid +
        "}";
    }
}
