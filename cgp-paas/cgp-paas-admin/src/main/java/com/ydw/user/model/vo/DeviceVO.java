package com.ydw.user.model.vo;


import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author heao
 * @since 2020-04-28
 */
public class DeviceVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键，自动生成的不重复ID
     */
    private String id;
    /**
     * 资源名称
     */
    private String name;
    /**
     * 设备规格编号
     */
    private Integer baseId;
    /**
     * 集群编号
     */
    private String clusterId;
    /**
     * IDC机房
     */
    private String idc;
    /**
     * 机架编号
     */
    private String cabinet;
    /**
     * 服务器机架槽位
     */
    private String location;
    /**
     * 设备所在服务器内部槽位
     */
    private String slot;

    /**
     * 服务器管理口IP，用于扩展
     */
    private String managerIp;
    /**
     * 服务器管理端口
     */
    private Integer managerPort;
    /**
     * 内部IP地址
     */
    private String innerIp;
    /**
     * 内网管理端口
     */
    private Integer innerPort;
    /**
     * 外网访问IP，可以是路由器Nat地址
     */
    private String ip;
    /**
     * 外部访问端口，可以是路由器Nat端口
     */
    private Integer port;


    private String adbIp;

    private Integer adbPort;

    /**
     * 设备状态:
     * 1 IDLE 空闲
     * 2 USED使用
     * 3 ERROR错误
     * 4 INSTALLING 安装中
     * 5 REBOOTING  重启中
     *
     */
    private Integer status;
    private Date createTime;
    private Date updateTime;
    /**
     * 已用空间
     */
    private Integer usedSpace;
    /**
     * 可用空间
     */
    private Integer freeSpace;
    /**
     * 内部mac地址
     */
    private String innerMac;
    /**
     * 描述
     */
    private String description;
    /**
     * 可调度
     */
    private Boolean schStatus;
    /**
     * 是否有效 :     0无效 1有效（软删除）默认1
     */
    private Boolean valid;
    /**
     * 注册与否
     */
    private  Boolean init;

    private String baseName;

    private String baseType;


    private String model;

    private String cpu;

    private Integer cpuNumber;

    private Integer cpuSlot;

    private String memoryModel;
    private String memory;
    private String memorySlot;
    private String graphics;
    private String diskModel;
    private Integer diskSize;
    private String networkCard;

    private String groupName;

    private String ClusterName;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getSchStatus() {
        return schStatus;
    }

    public void setSchStatus(Boolean schStatus) {
        this.schStatus = schStatus;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Boolean getInit() {
        return init;
    }

    public void setInit(Boolean init) {
        this.init = init;
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


    public String getClusterName() {
        return ClusterName;
    }

    public void setClusterName(String clusterName) {
        ClusterName = clusterName;
    }


    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public String getBaseType() {
        return baseType;
    }

    public void setBaseType(String baseType) {
        this.baseType = baseType;
    }

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

    public Integer getBaseId() {
        return baseId;
    }

    public void setBaseId(Integer baseId) {
        this.baseId = baseId;
    }


    public String getIdc() {
        return idc;
    }

    public void setIdc(String idc) {
        this.idc = idc;
    }

    public String getCabinet() {
        return cabinet;
    }

    public void setCabinet(String cabinet) {
        this.cabinet = cabinet;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }


    public String getManagerIp() {
        return managerIp;
    }

    public void setManagerIp(String managerIp) {
        this.managerIp = managerIp;
    }

    public Integer getManagerPort() {
        return managerPort;
    }

    public void setManagerPort(Integer managerPort) {
        this.managerPort = managerPort;
    }

    public String getInnerIp() {
        return innerIp;
    }

    public void setInnerIp(String innerIp) {
        this.innerIp = innerIp;
    }

    public Integer getInnerPort() {
        return innerPort;
    }

    public void setInnerPort(Integer innerPort) {
        this.innerPort = innerPort;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
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

    public Integer getUsedSpace() {
        return usedSpace;
    }

    public void setUsedSpace(Integer usedSpace) {
        this.usedSpace = usedSpace;
    }

    public Integer getFreeSpace() {
        return freeSpace;
    }

    public void setFreeSpace(Integer freeSpace) {
        this.freeSpace = freeSpace;
    }

    public String getInnerMac() {
        return innerMac;
    }

    public void setInnerMac(String innerMac) {
        this.innerMac = innerMac;
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }



    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAdbIp() {
        return adbIp;
    }

    public void setAdbIp(String adbIp) {
        this.adbIp = adbIp;
    }

    public Integer getAdbPort() {
        return adbPort;
    }

    public void setAdbPort(Integer adbPort) {
        this.adbPort = adbPort;
    }

    @Override
    public String toString() {
        return "DeviceVO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", baseId=" + baseId +
                ", clusterId='" + clusterId + '\'' +
                ", idc='" + idc + '\'' +
                ", cabinet='" + cabinet + '\'' +
                ", location='" + location + '\'' +
                ", slot='" + slot + '\'' +
                ", managerIp='" + managerIp + '\'' +
                ", managerPort=" + managerPort +
                ", innerIp='" + innerIp + '\'' +
                ", innerPort=" + innerPort +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", adbIp='" + adbIp + '\'' +
                ", adbPort=" + adbPort +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", usedSpace=" + usedSpace +
                ", freeSpace=" + freeSpace +
                ", innerMac='" + innerMac + '\'' +
                ", description='" + description + '\'' +
                ", schStatus=" + schStatus +
                ", valid=" + valid +
                ", init=" + init +
                ", baseName='" + baseName + '\'' +
                ", baseType='" + baseType + '\'' +
                ", model='" + model + '\'' +
                ", cpu='" + cpu + '\'' +
                ", cpuNumber=" + cpuNumber +
                ", cpuSlot=" + cpuSlot +
                ", memoryModel='" + memoryModel + '\'' +
                ", memory='" + memory + '\'' +
                ", memorySlot='" + memorySlot + '\'' +
                ", graphics='" + graphics + '\'' +
                ", diskModel='" + diskModel + '\'' +
                ", diskSize=" + diskSize +
                ", networkCard='" + networkCard + '\'' +
                ", groupName='" + groupName + '\'' +
                ", ClusterName='" + ClusterName + '\'' +
                '}';
    }
}