package com.ydw.platform.model.db;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2020-08-04
 */
public class Clusters implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id 自动生成随机ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;

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
    private int accessPort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
