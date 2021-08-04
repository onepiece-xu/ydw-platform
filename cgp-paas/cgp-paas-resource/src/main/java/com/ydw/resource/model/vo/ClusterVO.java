package com.ydw.resource.model.vo;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2020-07-30
 */
public class ClusterVO implements Serializable {

	/**
     * id 自动生成随机ID
     */
    private String clusterId;

    /**
     * 名称
     */
    private String clusterName;

    /**
     * 描述
     */
    private String description;

    /**
     * 节点主IP，用于外部访问的主IP，一般是负载IP，兼容ipv6地址
     */
    private String accessIp;

    /**
     * 节点主IP，用于外部访问的主IP，一般是负载IP，兼容ipv6地址
     */
    private int accessPort = 7000;

	public String getClusterId() {
		return clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAccessIp() {
		return accessIp;
	}

	public void setAccessIp(String accessIp) {
		this.accessIp = accessIp;
	}

    public int getAccessPort() {
        return accessPort;
    }

    public void setAccessPort(int accessPort) {
        this.accessPort = accessPort;
    }
}
