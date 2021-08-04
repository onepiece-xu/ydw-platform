package com.ydw.user.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CreateUserVO {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 	用户真实姓名
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
     * key
     */
    @TableField("SecretKey")
    private String SecretKey;
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
    @TableField("Amount")
    private Integer amount;
    /**
     * 消费总额
     */
    @TableField("Pay_amount")
    private BigDecimal payAmount;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifiedTime;
    /**
     * 状态 启用 禁用 删除
     */
    private Boolean schStatus;
    /**
     * 是否有效 :     0无效 1有效（软删除）默认1
     */
    private Boolean valid;

    private List<String> clusterIds;

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
        return SecretKey;
    }

    public void setSecretKey(String secretKey) {
        SecretKey = secretKey;
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

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
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

    public List<String> getClusterIds() {
        return clusterIds;
    }

    public void setClusterIds(List<String> clusterIds) {
        this.clusterIds = clusterIds;
    }
}
