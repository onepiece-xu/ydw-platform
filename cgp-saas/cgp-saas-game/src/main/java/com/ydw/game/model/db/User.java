package com.ydw.game.model.db;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2020-08-04
 */
@TableName("tb_user")
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    /**
     * 登录名
     */
    private String loginname;

    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别
     */
    private String gender;

    /**
     * 手机号
     */
    private String phone;

    /**
     * email
     */
    private String email;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 注册时间
     */
    private Date registerTime;

    /**
     * 修改时间
     */
    private Date modifiedTime;

    /**
     * 用户类型
     * type
     */
    private Integer type;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" +
        "id=" + id +
        ", loginname=" + loginname +
        ", password=" + password +
        ", nickname=" + nickname +
        ", gender=" + gender +
        ", phone=" + phone +
        ", email=" + email +
        ", avatar=" + avatar +
        ", registerTime=" + registerTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
