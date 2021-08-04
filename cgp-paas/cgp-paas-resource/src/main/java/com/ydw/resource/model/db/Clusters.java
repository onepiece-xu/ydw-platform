package com.ydw.resource.model.db;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2020-07-30
 */
@TableName("tb_clusters")
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
     * 0/1：中心节点/边缘节点
     */
    private Boolean type;

    /**
     * 0/1：否/是  本地节点
     */
    private Boolean isLocal;

    private String description;

    /**
     * Api url地址，用于访问边缘节点的api。
     */
    private String apiUrl;

    /**
     * Api url地址，用于访问边缘节点的api。
     */
    private String nodeUrl;

    /**
     * 节点主IP，用于外部访问的主IP，一般是负载IP，兼容ipv6地址
     */
    private String accessIp;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 1:启用/  0停用 
     */
    private Boolean schStatus;

    /**
     * 集群的所属设备数量
     */
    private Integer deviceNum;

    /**
     * 是否有效 :   是否有效 :     0无效 1有效（软删除）默认1
     */
    private Boolean valid;


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

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public Boolean getLocal() {
        return isLocal;
    }

    public void setLocal(Boolean isLocal) {
        this.isLocal = isLocal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getAccessIp() {
        return accessIp;
    }

    public void setAccessIp(String accessIp) {
        this.accessIp = accessIp;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getSchStatus() {
        return schStatus;
    }

    public void setSchStatus(Boolean schStatus) {
        this.schStatus = schStatus;
    }

    public Integer getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(Integer deviceNum) {
        this.deviceNum = deviceNum;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getNodeUrl() {
        return nodeUrl;
    }

    public void setNodeUrl(String nodeUrl) {
        this.nodeUrl = nodeUrl;
    }

    @Override
    public String toString() {
        return "Clusters{" +
        "id=" + id +
        ", name=" + name +
        ", type=" + type +
        ", isLocal=" + isLocal +
        ", description=" + description +
        ", apiUrl=" + apiUrl +
        ", accessIp=" + accessIp +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", schStatus=" + schStatus +
        ", deviceNum=" + deviceNum +
        ", valid=" + valid +
        "}";
    }
}
