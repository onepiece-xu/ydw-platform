package com.ydw.platform.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.platform.model.db.App;
import com.ydw.platform.model.vo.EnableApps;
import com.ydw.platform.model.vo.ResultInfo;

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

    ResultInfo getApps(String body, Page buildPage);

    ResultInfo getRecordPlayList(String id);

    ResultInfo getRecordPlayListAndroid(String userId);

    ResultInfo enableApps(EnableApps vo);

    ResultInfo getPlatformList();

    ResultInfo getPCAppsForVirtualKey(Page buildPage);

    ResultInfo getPlayList(String userId);

    App getCloudComputer();

    ResultInfo getAppInfo(String id);
}
