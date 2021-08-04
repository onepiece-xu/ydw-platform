package com.ydw.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.user.model.db.TbUserAppsRelated;
import com.ydw.user.utils.ResultInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heao
 * @since 2020-08-06
 */
public interface ITbUserAppsRelatedService extends IService<TbUserAppsRelated> {

//    ResultInfo getAppList(String body, Page buildPage);
//
//    ResultInfo addApps(String body);
//
//    ResultInfo getAppApproveList(String body, String search, Page buildPage);
//
//    ResultInfo appApprove(String body);
//
//    ResultInfo getAppApproves(String body, Page buildPage, String id);

    ResultInfo getOwnerAppList(String body);

    ResultInfo getSyncAppList(String body);

    ResultInfo syncAppListDelete(String body);

//    ResultInfo getGameListApproved(String id, String search, Page buildPage);
//
//    ResultInfo cancelApproved(List<Integer> body);
//
//    ResultInfo getAppApproved(String body, String search, Page buildPage);
}
