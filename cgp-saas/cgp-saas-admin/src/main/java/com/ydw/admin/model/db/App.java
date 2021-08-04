package com.ydw.admin.model.db;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author xulh
 * @since 2020-08-06
 */
@TableName("app")
public class App implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自动生成ID
     */
    private String id;

    /**
     * 应用名称
     */
    private String name;

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
     * 展示排序
     */
    private Integer orderNum;

    /**
     * 发布时间
     */
    private Date createTime;

    private Boolean valid;

    private Integer free;

    private Integer platform;

    private String introduction;

    private BigDecimal point;

    private String englishName;

    private String controlMode;

    private String publisher;

    private String developer;

    private String archiveMode;

    public String getArchiveMode() {
        return archiveMode;
    }

    public void setArchiveMode(String archiveMode) {
        this.archiveMode = archiveMode;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getControlMode() {
        return controlMode;
    }

    public void setControlMode(String controlMode) {
        this.controlMode = controlMode;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public BigDecimal getPoint() {
        return point;
    }

    public void setPoint(BigDecimal point) {
        this.point = point;
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

    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
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

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "App{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", accessId='" + accessId + '\'' +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", orderNum=" + orderNum +
                ", createTime=" + createTime +
                ", valid=" + valid +
                ", free=" + free +
                ", platform=" + platform +
                ", introduction='" + introduction + '\'' +
                ", point=" + point +
                ", englishName='" + englishName + '\'' +
                ", controlMode='" + controlMode + '\'' +
                ", publisher='" + publisher + '\'' +
                ", developer='" + developer + '\'' +
                ", archiveMode='" + archiveMode + '\'' +
                '}';
    }
}
