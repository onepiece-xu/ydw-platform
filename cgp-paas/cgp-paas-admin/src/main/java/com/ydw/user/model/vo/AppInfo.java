package com.ydw.user.model.vo;

import com.ydw.user.model.db.TbUserApps;

import java.io.Serializable;

public class AppInfo extends TbUserApps implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件存放路径，考虑用对象存储，需要用共享存储
     */
    private String packageFilePath;

    /**
     * 安装包文件名称
     */
    private String packageFileName;

    /**
     * 账号信息存放的路径
     */
    private String accountPath;

    /**
     * 用户数据存放路径
     */
    private String savePath;

    /**
     * 开始前执行脚本
     */
    private String startShell;

    /**
     * 关闭后执行脚本
     */
    private String closeShell;


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

}
