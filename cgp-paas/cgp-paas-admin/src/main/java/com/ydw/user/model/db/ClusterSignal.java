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
public class ClusterSignal implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 集群id
     */
    private String clusterId;

    /**
     * 信令服务器id
     */
    private Integer signalServer;

    private boolean valid;


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

    public Integer getSignalServer() {
        return signalServer;
    }

    public void setSignalServer(Integer signalServer) {
        this.signalServer = signalServer;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "ClusterSignal{" +
        "id=" + id +
        ", clusterId=" + clusterId +
        ", signalServer=" + signalServer +
        ", valid=" + valid +
        "}";
    }
}
