package com.ydw.platform.model.vo;

import javax.swing.*;
import java.util.Date;
import java.util.List;

public class GameDetailVO {
    private  Integer id;
    private String appName;
    private  String  description;
    private  Integer gameOrder;
    private String weight1;
    private String weight2;
    private String weight3;
    private List<String> tags;
    private String bigPic;
    private Date createTime;
    private String midPic;
    private  Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 游戏id
     */
    private String smallPic;

    private String appId;

    private String logoPic;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getLogoPic() {
        return logoPic;
    }

    public void setLogoPic(String logoPic) {
        this.logoPic = logoPic;
    }
}
