package com.ydw.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.user.model.db.TbDeviceApps;
import com.ydw.user.utils.ResultInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heao
 * @since 2020-04-14
 */
public interface ITbDeviceAppsService extends IService<TbDeviceApps> {
    ResultInfo createApp(HttpServletRequest request,String deviceId,
                          List<String> appIds);

    ResultInfo updateApp(HttpServletRequest request,String deviceId,
                         List<String> appIds);

    ResultInfo deleteApp(HttpServletRequest request, List<Integer> ids);

    ResultInfo getAppList(HttpServletRequest request,String deviceId,Integer appId,Integer status, Page buildPage);

    ResultInfo InstallToDevices(HttpServletRequest request, String appId, List<String> deviceIds);

    ResultInfo UninstallToDevices(HttpServletRequest request, String appId, List<String> deviceIds);
}
