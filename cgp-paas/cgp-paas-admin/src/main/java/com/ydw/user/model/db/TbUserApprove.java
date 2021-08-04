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
public class TbUserApprove implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键 自动生成随机ID
     */
    private String id;
    /**
     * 企业名称
     */
    private String enterpriseName;
    /**
     * 用户登录名
     */
    private String loginName;
    /**
     * 密码
     */
    private String password;
    /**
     * 认证号码 企业登记号或者个人身份证号
     */
    private String identification;
    /**
     * 1平台管理用户 2企业用户 3 个人用户
     */
    private Integer type;
    private String email;
    /**
     * 手机号
     */
    private String mobileNumber;
    /**
     * 电话 座机
     */
    private String telNumber;
    /**
     * 备注
     */
    private String description;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifiedTime;
    /**
     * 状态   1审批成功 2审批失败 0审批中
     */
    private Integer schStatus;
    /**
     * 是否有效 :     0无效 1有效（软删除）默认1
     */
    private Boolean valid;
    /**
     * identification相关上传图片
     */
    private String userPic;
    private String result;

    /**
     * 审批服务id
     */
    private String serviceId;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Integer getSchStatus() {
        return schStatus;
    }

    public void setSchStatus(Integer schStatus) {
        this.schStatus = schStatus;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "TbUserApprove{" +
        ", id=" + id +
        ", enterpriseName=" + enterpriseName +
        ", loginName=" + loginName +
        ", password=" + password +
        ", identification=" + identification +
        ", type=" + type +
        ", email=" + email +
        ", mobileNumber=" + mobileNumber +
        ", telNumber=" + telNumber +
        ", description=" + description +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", schStatus=" + schStatus +
        ", valid=" + valid +
        ", userPic=" + userPic +
        "}";
    }
}
