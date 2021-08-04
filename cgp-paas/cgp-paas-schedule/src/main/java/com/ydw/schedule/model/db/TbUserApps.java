package com.ydw.schedule.model.db;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2020-05-18
 */
public class TbUserApps implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 自动生成ID
     */
    private String id;

    /**
     * 应用名称
     */
    private String name;

    /**
     * 安装包名称
     */
    private String packageName;

    /**
     * 应用大小
     */
    private Integer size;

    /**
     * 安装后的实际Realsize大小
     */
    private Integer realSize;

    /**
     * 应用许可证号
     */
    private String accessId;

    /**
     * 用类型：arm/x86     0: arm  1: x86
     */
    private Integer type;

    /**
     * 应用描述
     */
    private String description;

    /**
     * 策略编号
     */
    private Integer strategyId;

    /**
     * 上传时间
     */
    private Date uploadTime;

    /**
     * 企业ID （企业用户或平台用户，个人用户不允许上传应用）
     */
    private String identification;

    /**
     * 状态
		0-	上传中
		1-	审批中
		2-	审批通过
		3-	审批拒绝
		4禁用

     */
    private Integer status;

    /**
     * 审批结果
     */
    private String resultDesc;

    /**
     * 审批用户ID
     */
    private String approvalId;

    /**
     * 审批时间
     */
    private Date approvalTime;

    /**
     * 删除用户id
     */
    private String deleteId;

    /**
     * 删除时间
     */
    private Date deleteTime;

    /**
     * 是否有效 :     0无效 1有效（软删除）默认1
     */
    private Boolean valid;

    /**
     * 0禁用 1启用
     */
    private Boolean schStatus;
    
    /**
     * 调度不到时是否默认安装
     */
    private Boolean schInstall;
    
    /**
     * 最大安装数量
     */
    private Integer installMaxNumber;
    
    /**
     * 当前已安装数
     */
    private Integer installCurrentNumber;


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

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public String getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(String approvalId) {
        this.approvalId = approvalId;
    }

    public Date getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(Date approvalTime) {
        this.approvalTime = approvalTime;
    }

    public String getDeleteId() {
        return deleteId;
    }

    public void setDeleteId(String deleteId) {
        this.deleteId = deleteId;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Boolean getSchStatus() {
        return schStatus;
    }

    public void setSchStatus(Boolean schStatus) {
        this.schStatus = schStatus;
    }

    public Boolean getSchInstall() {
		return schInstall;
	}

	public void setSchInstall(Boolean schInstall) {
		this.schInstall = schInstall;
	}

	public Integer getInstallMaxNumber() {
		return installMaxNumber;
	}

	public void setInstallMaxNumber(Integer installMaxNumber) {
		this.installMaxNumber = installMaxNumber;
	}

	public Integer getInstallCurrentNumber() {
		return installCurrentNumber;
	}

	public void setInstallCurrentNumber(Integer installCurrentNumber) {
		this.installCurrentNumber = installCurrentNumber;
	}

	@Override
    public String toString() {
        return "TbUserApps{" +
        "id=" + id +
        ", name=" + name +
        ", packageName=" + packageName +
        ", size=" + size +
        ", realSize=" + realSize +
        ", accessId=" + accessId +
        ", type=" + type +
        ", description=" + description +
        ", strategyId=" + strategyId +
        ", uploadTime=" + uploadTime +
        ", identification=" + identification +
        ", status=" + status +
        ", resultDesc=" + resultDesc +
        ", approvalId=" + approvalId +
        ", approvalTime=" + approvalTime +
        ", deleteId=" + deleteId +
        ", deleteTime=" + deleteTime +
        ", valid=" + valid +
        ", schStatus=" + schStatus +
        "}";
    }
}
