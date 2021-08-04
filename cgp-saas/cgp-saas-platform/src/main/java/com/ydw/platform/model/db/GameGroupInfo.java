package com.ydw.platform.model.db;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author heao
 * @since 2020-07-16
 */
public class GameGroupInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 游戏组ID
     */
    @TableId(value = "gamegroup_id", type = IdType.AUTO)
    private Integer gamegroupId;
    /**
     * 游戏组名称
     */
    private String gamegroupName;
    /**
     * 游戏组图片地址
     */
    private String gamegroupPic;
    /**
     * 游戏组大小
     */
    private Integer gamegroupSize;
    /**
     * 游戏组描述
     */
    private String gamegroupDescription;

    private Boolean valid;

    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getGamegroupId() {
        return gamegroupId;
    }

    public void setGamegroupId(Integer gamegroupId) {
        this.gamegroupId = gamegroupId;
    }

    public String getGamegroupName() {
        return gamegroupName;
    }

    public void setGamegroupName(String gamegroupName) {
        this.gamegroupName = gamegroupName;
    }

    public String getGamegroupPic() {
        return gamegroupPic;
    }

    public void setGamegroupPic(String gamegroupPic) {
        this.gamegroupPic = gamegroupPic;
    }

    public Integer getGamegroupSize() {
        return gamegroupSize;
    }

    public void setGamegroupSize(Integer gamegroupSize) {
        this.gamegroupSize = gamegroupSize;
    }

    public String getGamegroupDescription() {
        return gamegroupDescription;
    }

    public void setGamegroupDescription(String gamegroupDescription) {
        this.gamegroupDescription = gamegroupDescription;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "GameGroupInfo{" +
                "gamegroupId=" + gamegroupId +
                ", gamegroupName='" + gamegroupName + '\'' +
                ", gamegroupPic='" + gamegroupPic + '\'' +
                ", gamegroupSize=" + gamegroupSize +
                ", gamegroupDescription='" + gamegroupDescription + '\'' +
                ", valid=" + valid +
                '}';
    }
}
