package com.ydw.open.model.db;

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

    /**
     * 1有效 0无效
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

    public String getNetbarId() {
        return netbarId;
    }

    public void setNetbarId(String netbarId) {
        this.netbarId = netbarId;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "ClusterNetbar{" +
        "id=" + id +
        ", clusterId=" + clusterId +
        ", netbarId=" + netbarId +
        ", valid=" + valid +
        "}";
    }
}
