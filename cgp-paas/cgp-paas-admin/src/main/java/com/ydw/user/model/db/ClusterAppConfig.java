package com.ydw.user.model.db;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author heao
 * @since 2021-05-17
 */
@TableName("tb_cluster_app_config")
public class ClusterAppConfig implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 集群id
     */
    private String clusterId;

    /**
     * app的id
     */
    private String appId;

    /**
     * 包文件名
     */
    private String packageFileName;

    /**
     * 启动包的路径
     */
    private String packageFilePath;

    /**
     * 用户账号路径
     */
    private String accountPath;

    private String savePath;

    /**
     * 启动脚本
     */
    private String startShell;

    /**
     * 关闭脚本
     */
    private String closeShell;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPackageFileName() {
        return packageFileName;
    }

    public void setPackageFileName(String packageFileName) {
        this.packageFileName = packageFileName;
    }

    public String getPackageFilePath() {
        return packageFilePath;
    }

    public void setPackageFilePath(String packageFilePath) {
        this.packageFilePath = packageFilePath;
    }

    public String getAccountPath() {
        return accountPath;
    }

    public void setAccountPath(String accountPath) {
        this.accountPath = accountPath;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
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

    @Override
    public String toString() {
        return "ClusterAppConfig{" +
        "id=" + id +
        ", clusterId=" + clusterId +
        ", appId=" + appId +
        ", packageFileName=" + packageFileName +
        ", packageFilePath=" + packageFilePath +
        ", accountPath=" + accountPath +
        ", savePath=" + savePath +
        ", startShell=" + startShell +
        ", closeShell=" + closeShell +
        "}";
    }
}
