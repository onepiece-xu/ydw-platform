package com.ydw.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.user.model.db.TbUserApps;

import com.ydw.user.utils.ResultInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heao
 * @since 2020-04-23
 */
public interface ITbUserAppsService extends IService<TbUserApps> {

    ResultInfo createUserApp(HttpServletRequest request, TbUserApps app);

    ResultInfo updateUserApp(HttpServletRequest request, TbUserApps app);

    ResultInfo deleteUserApp(HttpServletRequest request, List<String> ids);

    ResultInfo getUserApps(HttpServletRequest request, String name, String accessId, Integer type, String strategyName, String identification, String status, String search, String schStatus, Page   buildPage);

    ResultInfo getUserApp(HttpServletRequest request, String id);

    ResultInfo getInstallApps(HttpServletRequest request,String id, Page   buildPage);

    ResultInfo getUnInstallApps(HttpServletRequest request,String id,Page   buildPage);

    ResultInfo disableApps(HttpServletRequest request, List<String> ids);

    ResultInfo enableApps(HttpServletRequest request, List<String> ids);

    ResultInfo ajaxUploadExcel(HttpServletRequest request, HttpServletResponse response);

    Integer  insertDb(TbUserApps app);


    ResultInfo getAppListByTag(HttpServletRequest request, String body);

    ResultInfo getAppPictures();

    ResultInfo appFileUpload(HttpServletRequest request, String appId);

    ResultInfo getPlatformList();
}
