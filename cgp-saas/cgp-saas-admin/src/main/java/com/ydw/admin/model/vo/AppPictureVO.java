package com.ydw.admin.model.vo;

import java.util.Date;

public class AppPictureVO {

    /**
     * 自动生成ID
     */
    private String id;

    /**
     * 应用名称
     */
    private String name;

    /**
     * 用类型：arm/x86     0: arm  1: x86
     */
    private Integer type;

    /**
     * 应用描述
     */
    private String description;

    /**
     * 图片地址
     */
    private String bigPic;

    /**
     * 图片的类型 1，大图 2中图 3小图 4logo图
     */
    private String midPic;

    /**
     * 游戏id
     */
    private String smallPic;



    private String logoPic;

    /**
     * 发布时间
     */
    private Date createTime;

    private  Boolean valid;


    private  Integer free;

    private Integer platform;

    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
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

    public String getBigPic() {
        return bigPic;
    }

    public void setBigPic(String bigPic) {
        this.bigPic = bigPic;
    }

    public String getMidPic() {
        return midPic;
    }

    public void setMidPic(String midPic) {
        this.midPic = midPic;
    }

    public String getSmallPic() {
        return smallPic;
    }

    public void setSmallPic(String smallPic) {
        this.smallPic = smallPic;
    }



    public String getLogoPic() {
        return logoPic;
    }

    public void setLogoPic(String logoPic) {
        this.logoPic = logoPic;
    }

    @Override
    public String toString() {
        return "AppPictureVO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", bigPic='" + bigPic + '\'' +
                ", midPic='" + midPic + '\'' +
                ", smallPic='" + smallPic + '\'' +
                ", logoPic='" + logoPic + '\'' +
                ", createTime=" + createTime +
                ", valid=" + valid +
                ", free=" + free +
                ", platform=" + platform +
                '}';
    }
}
