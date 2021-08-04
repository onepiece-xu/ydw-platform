package com.ydw.open.model.vo;

import java.io.Serializable;

public class AppDevicesVO implements Serializable        {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 资源名称
     */
    private String name;

    private String groupName;

    private String clusterName;

    /**
     * 外网访问IP，可以是路由器Nat地址
     */
    private String ip;

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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "AppDevicesVO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", groupName='" + groupName + '\'' +
                ", clusterName='" + clusterName + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }
}
