package com.ydw.user.model.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author heao
 * @since 2020-08-24
 */
public class ClusterTurn implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 集群ID
     */
    private String clusterId;

    /**
     * turn服务器ID
     */
    private Integer turnServer;

    /**
     * 0删除 1有效
     */
    private Boolean valid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public Integer getTurnServer() {
        return turnServer;
    }

    public void setTurnServer(Integer turnServer) {
        this.turnServer = turnServer;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "ClusterTurn{" +
        "id=" + id +
        ", clusterId=" + clusterId +
        ", turnServer=" + turnServer +
        ", valid=" + valid +
        "}";
    }
}
