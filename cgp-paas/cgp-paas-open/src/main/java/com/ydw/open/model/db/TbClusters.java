package com.ydw.open.model.db;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author heao
 * @since 2020-05-13
 */
public class TbClusters implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private Integer type;
    /**
     * 0/1：否/是  本地节点
     */
    private Integer isLocal;
    private String description;
    /**
     * Api url地址，用于访问边缘节点的api。
     */
    private String apiUrl;
    /**
     * 节点主IP，用于外部访问的主IP，一般是负载IP，兼容ipv6地址
     */
    private String accessIp;
    private Date createTime;
    private Date updateTime;
    /**
     * 0/1:启用/停用 
     */
    private Boolean schStatus;
    /**
     * 集群的所属设备数量
     */
    private Integer deviceNum;
    /**
     * 是否有效 :   0有效  1 无效（软删除）
     */
    private Boolean valid;

    private String innerIp;



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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsLocal() {
        return isLocal;
    }

    public void setIsLocal(Integer isLocal) {
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
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

    public String getInnerIp() {
        return innerIp;
    }

    public void setInnerIp(String innerIp) {
        this.innerIp = innerIp;
    }

    @Override
    public String toString() {
        return "TbClusters{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", isLocal=" + isLocal +
                ", description='" + description + '\'' +
                ", apiUrl='" + apiUrl + '\'' +
                ", accessIp='" + accessIp + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", schStatus=" + schStatus +
                ", deviceNum=" + deviceNum +
                ", valid=" + valid +
                ", innerIp='" + innerIp + '\'' +
                '}';
    }
}
