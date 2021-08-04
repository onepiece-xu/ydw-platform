package com.ydw.open.model.db;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 *
 *
 * </p>
 *
 * @author heao
 * @since 2020-05-14
 */
public class TbDeviceBase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 资源类型
     0-	arm
     1-	x86(pcfarm)

     */
    private String type;
    /**
     * 设备型号
     */
    private String model;
    /**
     * CPU型号规格
     */
    private String cpu;
    /**
     * CPU个数
     */
    private Integer cpuNumber;
    /**
     * CPU插槽数
     */
    private Integer cpuSlot;
    /**
     * 内存规格
     */
    private String memoryModel;
    /**
     * 内存
     */
    private String memory;
    /**
     * 插槽数
     */
    private String memorySlot;
    /**
     * 显卡型号
     */
    private String graphics;
    /**
     * 磁盘型号
     */
    private String diskModel;
    /**
     * 磁盘大小 ，单位Byte
     */
    private Integer diskSize;
    /**
     * 网卡规格
     */
    private String networkCard;
    /**
     * 是否有效 :     0无效 1有效（软删除）默认1
     */
    private Boolean valid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public Integer getCpuNumber() {
        return cpuNumber;
    }

    public void setCpuNumber(Integer cpuNumber) {
        this.cpuNumber = cpuNumber;
    }

    public Integer getCpuSlot() {
        return cpuSlot;
    }

    public void setCpuSlot(Integer cpuSlot) {
        this.cpuSlot = cpuSlot;
    }

    public String getMemoryModel() {
        return memoryModel;
    }

    public void setMemoryModel(String memoryModel) {
        this.memoryModel = memoryModel;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getMemorySlot() {
        return memorySlot;
    }

    public void setMemorySlot(String memorySlot) {
        this.memorySlot = memorySlot;
    }

    public String getGraphics() {
        return graphics;
    }

    public void setGraphics(String graphics) {
        this.graphics = graphics;
    }

    public String getDiskModel() {
        return diskModel;
    }

    public void setDiskModel(String diskModel) {
        this.diskModel = diskModel;
    }

    public Integer getDiskSize() {
        return diskSize;
    }

    public void setDiskSize(Integer diskSize) {
        this.diskSize = diskSize;
    }

    public String getNetworkCard() {
        return networkCard;
    }

    public void setNetworkCard(String networkCard) {
        this.networkCard = networkCard;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "TbDeviceBase{" +
                ", id=" + id +
                ", name=" + name +
                ", type=" + type +
                ", model=" + model +
                ", cpu=" + cpu +
                ", cpuNumber=" + cpuNumber +
                ", cpuSlot=" + cpuSlot +
                ", memoryModel=" + memoryModel +
                ", memory=" + memory +
                ", memorySlot=" + memorySlot +
                ", graphics=" + graphics +
                ", diskModel=" + diskModel +
                ", diskSize=" + diskSize +
                ", networkCard=" + networkCard +
                ", valid=" + valid +
                "}";
    }
}
