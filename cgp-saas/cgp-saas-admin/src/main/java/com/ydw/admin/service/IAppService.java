package com.ydw.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.admin.model.db.App;
import com.ydw.admin.model.vo.CheckTagsVO;
import com.ydw.admin.model.vo.EnableApps;
import com.ydw.admin.model.vo.ResultInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2020-08-05
 */
public interface IAppService extends IService<App> {

	ResultInfo getAllApps();

    ResultInfo getSyncApps(String body);

    ResultInfo getApps(String search,  String valid, Integer type,Page buildPage);

    ResultInfo getRecordPlayList(String id);

    ResultInfo getRecordPlayListAndroid(String userId);

    ResultInfo getAppBind(String appId,Page buildPage);

    ResultInfo getAppUnBind(String appId,Page buildPage);

    ResultInfo bindTags(HttpServletRequest request, String body);

    ResultInfo appPictureUpload(HttpServletRequest request, String appId);

    ResultInfo enableApps(EnableApps vo);

    ResultInfo checkTags(HttpServletRequest request,  String appId);

    ResultInfo Tagging(HttpServletRequest request, CheckTagsVO vo);

    ResultInfo updateApp(App app);

    ResultInfo getPlatformList();
}
