package com.ydw.open.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.open.model.db.TbDevices;
import com.ydw.open.utils.ResultInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heao
 * @since 2020-04-14
 */
public interface ITbDevicesService extends IService<TbDevices> {

    ResultInfo createDevice(HttpServletRequest request, TbDevices device);

    ResultInfo updateDevice(HttpServletRequest request, TbDevices device);

    ResultInfo deleteDevices(HttpServletRequest request, List<String> ids);

    ResultInfo getDevices(HttpServletRequest request, String id, String name, Integer baseId, String clusterId, Integer groupId, String idc
            , String cabinet, String location, String slot, String managerIp, Integer managerPort
            , String innerIp, Integer innerPort, String ip, Integer port, String status, String schStatus, String search, Page buildPage);

    ResultInfo getDevice(HttpServletRequest request, String id);

     Integer  insertDb(TbDevices device);

    ResultInfo ajaxUploadExcel(HttpServletRequest request, HttpServletResponse response) throws Exception;

    ResultInfo getDeviceBases(HttpServletRequest request);


    ResultInfo getInstallDevicesByAppId(HttpServletRequest request, String appId, String clusterId, Integer groupId, Page buildPage);

    ResultInfo getUninstallDevicesByAppId(HttpServletRequest request, String appId, String clusterId, Integer groupId, Page buildPage);

    /**
     * 设备管理查询
     * @param request
     * @param id
     * @param name
     * @param innerMac
     * @param nodeName
     * @param idc
     * @param cabinet
     * @param location
     * @param slot
     * @param clusterName
     * @param managerIp
     * @param managerPort
     * @param innerIp
     * @param innerPort
     * @param ip
     * @param port
     * @param status
     * @param description
     * @param schStatus
     * @param
     * @param
     * @return
     */
    ResultInfo getDevicesList(HttpServletRequest request, String id, String name, String innerMac, String nodeName, String idc, String cabinet, String location, String slot
            , String clusterName, String managerIp, Integer managerPort, String innerIp, Integer innerPort, String ip, Integer port, String status, String description, Integer schStatus,
                              Integer baseType, String search, Page buildPage);

    ResultInfo getDevicesByInstallAppId(HttpServletRequest request, String appId, String clusterId, Integer groupId, Page buildPage);

    ResultInfo getAddDevices(HttpServletRequest request, String clusterId, Integer groupId, Page buildPage);

    ResultInfo getGroupDevices(HttpServletRequest request, String clusterId, Integer groupId, String id, String name, String innerMac, Integer baseType, String clusterName,
                               String idc, String cabinet, String location, String slot, String managerIp, Integer managerPort, String innerIp, Integer innerPort,
                               String ip, Integer port, String status, String description, Integer schStatus, String search, Page buildPage);


    ResultInfo getNetBarDevices(String clusterId, String name, Page buildPage);
}
