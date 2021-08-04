package com.ydw.edge.model.vo;

import java.io.Serializable;

public class DeviceSize implements Serializable {

	private static final long serialVersionUID = -3837589472597079692L;

	/**
     * 设备编号
     */
    private String id;

    /**
     * 设备总大小
     */
    private Integer totalSize ;
    /**
     * 设备已用大小
     */
    private Integer usedSize ;
    /**
     * 设备可用大小
     */
    private Integer availableSize;
    /**
     * 使用率
     */
    private Integer usedRate ;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    public Integer getUsedSize() {
        return usedSize;
    }

    public void setUsedSize(Integer usedSize) {
        this.usedSize = usedSize;
    }

    public Integer getAvailableSize() {
        return availableSize;
    }

    public void setAvailableSize(Integer availableSize) {
        this.availableSize = availableSize;
    }

    public Integer getUsedRate() {
        return usedRate;
    }

    public void setUsedRate(Integer usedRate) {
        this.usedRate = usedRate;
    }

    @Override
    public String toString() {
        return "DeviceSize{" +
                ", id=" + id +
                ", totalSize=" + totalSize +
                ", usedSize=" + usedSize +
                ", availableSize=" + availableSize +
                ", usedRate=" + usedRate +
                "}";
    }


}
