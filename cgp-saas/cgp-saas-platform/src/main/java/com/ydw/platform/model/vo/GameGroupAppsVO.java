package com.ydw.platform.model.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 新VO
 * @date 2021/02/19
 */
public class GameGroupAppsVO {
    private String appName;
    private String appId;
    private  String  description;
    private  Integer gameOrder;
    private String weight1;
    private String weight2;
    private String weight3;
    private Integer free;
    private Integer type;
    private  Integer platform;
    private String tag;
    private List<String> tags;
    private BigDecimal point;

    public BigDecimal getPoint() {
        return point;
    }

    public void setPoint(BigDecimal point) {
        this.point = point;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getGameOrder() {
        return gameOrder;
    }

    public void setGameOrder(Integer gameOrder) {
        this.gameOrder = gameOrder;
    }

    public String getWeight1() {
        return weight1;
    }

    public void setWeight1(String weight1) {
        this.weight1 = weight1;
    }

    public String getWeight2() {
        return weight2;
    }

    public void setWeight2(String weight2) {
        this.weight2 = weight2;
    }

    public String getWeight3() {
        return weight3;
    }

    public void setWeight3(String weight3) {
        this.weight3 = weight3;
    }

    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
