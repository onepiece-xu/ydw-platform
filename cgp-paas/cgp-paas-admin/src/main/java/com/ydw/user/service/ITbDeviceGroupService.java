package com.ydw.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.user.model.db.TbDeviceGroup;

import com.ydw.user.utils.ResultInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heao
 * @since 2020-05-13
 */
public interface ITbDeviceGroupService extends IService<TbDeviceGroup> {

    ResultInfo createDeviceGroup(HttpServletRequest request, TbDeviceGroup group);

    ResultInfo updateDeviceGroup(HttpServletRequest request, TbDeviceGroup group);

    ResultInfo deleteDeviceGroup(HttpServletRequest request, List<Integer> ids);

    ResultInfo getDeviceGroupList(HttpServletRequest request, String name, String description, Integer schStatus,String search, Page buildPage);

    ResultInfo addDevices(HttpServletRequest request, String getUninstallDevicesByAppId);

    ResultInfo removeDevices(HttpServletRequest request, String body);
}
