package com.ydw.authentication.model.db;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author huzh
 * @since 2020-07-29
 */
@TableName("tb_user_info")
public class UserInfo implements Serializable {

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
     * 企业secret key
自动生成的64bit的字符串

     */
    @TableField("secretKey")
    private String secretKey;

    /**
     * 2平台管理用户 3企业用户 4 个人用户
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
     * 账户余额
     */
    private Integer amount;

    /**
     * 消费总额
     */
    private Long payAmount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime modifiedTime;

    /**
     * 状态 启用 禁用
     */
    private Boolean schStatus;

    /**
     * 是否有效 :     0无效 1有效（软删除）默认1
     */
    private Boolean valid;


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

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Long payAmount) {
        this.payAmount = payAmount;
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

    public Boolean getSchStatus() {
        return schStatus;
    }

    public void setSchStatus(Boolean schStatus) {
        this.schStatus = schStatus;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
        "id=" + id +
        ", enterpriseName=" + enterpriseName +
        ", loginName=" + loginName +
        ", password=" + password +
        ", identification=" + identification +
        ", secretKey=" + secretKey +
        ", type=" + type +
        ", email=" + email +
        ", mobileNumber=" + mobileNumber +
        ", telNumber=" + telNumber +
        ", description=" + description +
        ", amount=" + amount +
        ", payAmount=" + payAmount +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", schStatus=" + schStatus +
        ", valid=" + valid +
        "}";
    }
}
