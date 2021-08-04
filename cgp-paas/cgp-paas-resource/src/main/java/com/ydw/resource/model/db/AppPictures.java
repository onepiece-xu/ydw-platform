package com.ydw.resource.model.db;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2020-10-19
 */
@TableName("tb_app_pictures")
public class AppPictures implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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

    private String appId;

    private String logoPic;

    private Boolean valid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "AppPictures{" +
        "id=" + id +
        ", bigPic=" + bigPic +
        ", midPic=" + midPic +
        ", smallPic=" + smallPic +
        ", appId=" + appId +
        ", logoPic=" + logoPic +
        ", valid=" + valid +
        "}";
    }
}
