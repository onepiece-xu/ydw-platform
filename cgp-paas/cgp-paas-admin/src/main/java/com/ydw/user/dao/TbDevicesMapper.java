package com.ydw.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.user.model.db.TbDeviceBase;
import com.ydw.user.model.db.TbDevices;
import com.ydw.user.model.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-04-14
 */
public interface TbDevicesMapper extends BaseMapper<TbDevices> {

    DeviceVO selectByDeviceId(@Param("id")String id);

    Page<TbDevices> getDevices(@Param("name") String name,
                               @Param("baseId") Integer baseId,
                               @Param("nodeId") String nodeId,
                               @Param("idc")  String idc,
                               @Param("cabinet") String cabinet,
                               @Param("location") String location,
                               @Param("slot") String slot,
                               @Param("clusterId") Integer clusterId,
                               @Param("managerIp") String managerIp,
                               @Param("managerPort") Integer managerPort,
                               @Param("innerIp") String innerIp,
                               @Param("innerPort") Integer innerPort,
                               @Param("ip") String ip,
                               @Param("port") Integer port,
                               @Param("status") String status);

    List<TbDevices> getDevicesByClusterId( @Param("clusterId")String id);

    Page<DeviceVO> getDevicesVoByClusterId(@Param("id")String id,@Param("name") String name,
                                               @Param("baseType") Integer baseType,
                                               @Param("clusterId") String clusterId,
                                               @Param("groupId") Integer groupId,
                                               @Param("idc")  String idc,
                                               @Param("cabinet") String cabinet,
                                               @Param("location") String location,
                                               @Param("slot") String slot,
                                               @Param("managerIp") String managerIp,
                                               @Param("managerPort") Integer managerPort,
                                               @Param("innerIp") String innerIp,
                                               @Param("innerPort") Integer innerPort,
                                               @Param("ip") String ip,
                                               @Param("port") Integer port,
                                               @Param("status") String status,
                                               @Param("schStatus") String schStatus,
                                               @Param("search") String search, Page buildPage);

    List<TbDeviceBase> getDeviceBases();

    Page<AppDevicesVO> getInstallDevicesByAppId( @Param("appId")String appId,  @Param("clusterId")String clusterId,  @Param("groupId")Integer groupId,  @Param("type")Integer type,Page buildPage);


    Page<AppDevicesVO> getUninstallDevicesByAppId(@Param("appId")String appId, @Param("clusterId")String clusterId, @Param("groupId")Integer groupId, Page buildPage);


    Page<DeviceVO> getDevicesList(@Param("id") String id, @Param("name") String name, @Param("innerMac") String innerMac, @Param("clusterName") String clusterName, @Param("idc") String idc
            , @Param("cabinet") String cabinet, @Param("location") String location, @Param("slot") String slot, @Param("groupName") String groupName,
                                  @Param("managerIp") String managerIp, @Param("managerPort") Integer managerPort, @Param("innerIp") String innerIp, @Param("innerPort") Integer innerPort,
                                  @Param("ip") String ip, @Param("port") Integer port,
                                  @Param("description") String description, @Param("schStatus") Integer schStatus, @Param("status") String status, @Param("baseType") Integer baseType, @Param("search") String search, Page buildPage);

    List<DeviceStatusCountVO> getDeviceStatusCount( @Param("clusterId")String clusterId);

    Page<DeviceUsageCountVO> getDeviceUsageRate(@Param("clusterId")String clusterId, Page buildPage);

    Page<DeviceUsageCountHistoryVO> getDeviceUsageHistory(@Param("startTime")String startTime, @Param("endTime")String endTime, @Param("clusterId")String clusterId,@Param("day")long day,Page buildPage);

    /**
     * 查询已安装应用的设备列表
     * @param appId
     * @param clusterId
     * @param groupId
     * @return
     */
    Page<AppDevicesVO> getDevicesByInstallAppId( @Param("appId")String appId,  @Param("clusterId")String clusterId,  @Param("groupId")Integer groupId, Page buildPage);

    String getTotalUsage(@Param("startTime")String startTime, @Param("endTime")String endTime,@Param("day")long day);

    List<AppDeviceCountVO> getAppInstallCount(Page buildPage);

    ClusterDevicesStatusVO getClusterDevicesStatus(@Param("id")String clusterId);

    Page<DeviceVO> getAddDevices(@Param("clusterId")String clusterId, @Param("groupId")Integer groupId,Page buildPage);

    Page<DeviceVO> getDevicesVoByGroupId(@Param("clusterId")String clusterId, @Param("groupId")Integer groupId, @Param("id") String id, @Param("name") String name, @Param("innerMac") String innerMac, @Param("baseType") Integer baseType
                                         , @Param("clusterName")String clusterName, @Param("idc")String idc
            , @Param("cabinet") String cabinet, @Param("location")String location,  @Param("slot") String slot
            ,  @Param("managerIp") String managerIp, @Param("managerPort")Integer  managerPort, @Param("innerIp") String innerIp,  @Param("innerPort") Integer innerPort,  @Param("ip") String ip,
                                         @Param("port") Integer port,  @Param("status")String status,  @Param("description")String description,@Param("schStatus")Integer schStatus, @Param("search") String search,Page buildPage);

    List<String> getRelatedGroupName(@Param("deviceId")String id);

    String  getDeviceUsageOneDay(@Param("startTime")String startTime, @Param("endTime")String endTime,@Param("clusterId")String clusterId);
}
