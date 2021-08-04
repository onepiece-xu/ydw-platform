package com.ydw.authentication.model.db;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2020-10-12
 */
@TableName("tb_user_approve")
public class UserApprove implements Serializable {

    private static final long serialVersionUID=1L;

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
     *  1企业用户 2个人用户
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
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime modifiedTime;

    /**
     * 状态  0审批中 1审批通过2审批失败 
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

    /**
     * 审批结果
     */
    private String result;

    /**
     * 待审批服务id
     */
    private String serviceId;


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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDateTime modifiedTime) {
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

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String toString() {
        return "UserApprove{" +
        "id=" + id +
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
        ", result=" + result +
        ", serviceId=" + serviceId +
        "}";
    }
}
