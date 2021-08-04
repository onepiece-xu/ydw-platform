package com.ydw.game.model.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author heao
 * @since 2020-07-14
 */
public class TbGameGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 游戏组ID
     */
    private Integer gamegroupId;
    /**
     * 游戏ID
     */
    private String gameId;
    /**
     * 游戏在组内排序
     */
    private Integer gameOrder;
    /**
     * 权值1，对应于order，表示按照什么权值进行排序（可选）例如：按照游戏推荐度排序，每个游戏可以获得一个推荐度权值，如：90%
     */
    private String weight1;
    /**
     * 权值2

     */
    private String weight2;
    /**
     * 权值3

     */
    private String weight3;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGamegroupId() {
        return gamegroupId;
    }

    public void setGamegroupId(Integer gamegroupId) {
        this.gamegroupId = gamegroupId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
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

    @Override
    public String toString() {
        return "TbGameGroup{" +
        ", id=" + id +
        ", gamegroupId=" + gamegroupId +
        ", gameId=" + gameId +
        ", gameOrder=" + gameOrder +
        ", weight1=" + weight1 +
        ", weight2=" + weight2 +
        ", weight3=" + weight3 +
        "}";
    }
}
