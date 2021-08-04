package com.ydw.user.model.db;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author heao
 * @since 2020-05-19
 */
public class TbAppApprove implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 名称

     */
    private String name;
    /**
     * 英文名
     */
    private String englishName;
    /**
     * 游戏制作公司
     */
    private String gameMaker;
    /**
     * 游戏发布公司
     */
    private String gamePublisher;
    /**
     * 游戏类型
     */
    private String gameType;
    /**
     * 合作类型
     */
    private Integer cooperationType;
    /**
     * IP及授权期
     */
    private String authPeriod;


    /**
     * 应用安装文件名
     */
    private String packageFileName;
    /**
     * 包名
     */
    private String packageName;
    /**
     * 大小
     */
    private Integer size;
    /**
     * 安装后大小
     */
    private Integer realSize;
    /**
     * 应用类型 0：arm 1：x86
     */
    private Integer type;
    /**
     * 账号数据存放路径
     */
    private String accountPath;
    /**
     * 存档数据存放路径
     */
    private String dataPath;
    /**
     * 描述
     */
    private String description;
    /**
     * 安装包获取路径
     */
    private String packagePath;
    /**
     * 游戏运营广告授权书上传图
     */
    private String insurePic;

    /**
     * 是否有版号  有版号1  2 无 3 办理中
     *
     */
    private Integer hasAccessId;
    /**
     * 游戏版号
     */
    private String accessId;

    /**
     * 游戏版号
     */
    private String accessIdPic;
    /**
     * 审批状态：
     0待提交
     1审核中
     2审核不通过
     3审核通过
     4已上架

     */
    private Integer status;
    /**
     * 0无效1 有效
     */
    private Boolean valid;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifiedTime;

    private String result;


    private String schInstall;

    private Integer installMaxNumber;

    private String  identification;

    private Integer screen;

    public Integer getScreen() {
        return screen;
    }

    public void setScreen(Integer screen) {
        this.screen = screen;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getGameMaker() {
        return gameMaker;
    }

    public void setGameMaker(String gameMaker) {
        this.gameMaker = gameMaker;
    }

    public String getGamePublisher() {
        return gamePublisher;
    }

    public void setGamePublisher(String gamePublisher) {
        this.gamePublisher = gamePublisher;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public Integer getCooperationType() {
        return cooperationType;
    }

    public void setCooperationType(Integer cooperationType) {
        this.cooperationType = cooperationType;
    }

    public String getAuthPeriod() {
        return authPeriod;
    }

    public void setAuthPeriod(String authPeriod) {
        this.authPeriod = authPeriod;
    }

    public String getPackageFileName() {
        return packageFileName;
    }

    public void setPackageFileName(String packageFileName) {
        this.packageFileName = packageFileName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getRealSize() {
        return realSize;
    }

    public void setRealSize(Integer realSize) {
        this.realSize = realSize;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAccountPath() {
        return accountPath;
    }

    public void setAccountPath(String accountPath) {
        this.accountPath = accountPath;
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getInsurePic() {
        return insurePic;
    }

    public void setInsurePic(String insurePic) {
        this.insurePic = insurePic;
    }

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }


    public Integer getHasAccessId() {
        return hasAccessId;
    }

    public void setHasAccessId(Integer hasAccessId) {
        this.hasAccessId = hasAccessId;
    }

    public String getAccessIdPic() {
        return accessIdPic;
    }

    public void setAccessIdPic(String accessIdPic) {
        this.accessIdPic = accessIdPic;
    }

    public String getSchInstall() {
        return schInstall;
    }

    public void setSchInstall(String schInstall) {
        this.schInstall = schInstall;
    }

    public Integer getInstallMaxNumber() {
        return installMaxNumber;
    }

    public void setInstallMaxNumber(Integer installMaxNumber) {
        this.installMaxNumber = installMaxNumber;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    @Override
    public String toString() {
        return "TbAppApprove{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", englishName='" + englishName + '\'' +
                ", gameMaker='" + gameMaker + '\'' +
                ", gamePublisher='" + gamePublisher + '\'' +
                ", gameType='" + gameType + '\'' +
                ", cooperationType=" + cooperationType +
                ", authPeriod='" + authPeriod + '\'' +
                ", packageFileName='" + packageFileName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", size=" + size +
                ", realSize=" + realSize +
                ", type=" + type +
                ", accountPath='" + accountPath + '\'' +
                ", dataPath='" + dataPath + '\'' +
                ", description='" + description + '\'' +
                ", packagePath='" + packagePath + '\'' +
                ", insurePic='" + insurePic + '\'' +
                ", hasAccessId=" + hasAccessId +
                ", accessId='" + accessId + '\'' +
                ", accessIdPic='" + accessIdPic + '\'' +
                ", status=" + status +
                ", valid=" + valid +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                ", result='" + result + '\'' +
                ", schInstall='" + schInstall + '\'' +
                ", installMaxNumber='" + installMaxNumber + '\'' +
                ", identification='" + identification + '\'' +
                '}';
    }
}
