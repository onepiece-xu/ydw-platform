package com.ydw.platform.model.db;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author heao
 * @since 2020-12-11
 */
public class ClusterNetbar implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer id;

    private String clusterId;

    private String netbarId;




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

    public String getNetbarId() {
        return netbarId;
    }

    public void setNetbarId(String netbarId) {
        this.netbarId = netbarId;
    }



    @Override
    public String toString() {
        return "ClusterNetbar{" +
        "id=" + id +
        ", clusterId=" + clusterId +
        ", netbarId=" + netbarId +
        "}";
    }
}
