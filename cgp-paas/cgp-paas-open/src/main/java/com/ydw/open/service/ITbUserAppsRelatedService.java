package com.ydw.open.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.open.model.db.TbUserAppsRelated;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.open.utils.ResultInfo;

import javax.servlet.http.HttpServletRequest;
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

    ResultInfo getAppList(String body, Page buildPage);

    ResultInfo addApps(String body);

    ResultInfo getAppApproveList(String body,String search ,Page buildPage);

    ResultInfo appApprove(HttpServletRequest request,String body);

    ResultInfo getAppApproves(String body, Page buildPage,String id);

    ResultInfo getOwnerAppList(String body);

    ResultInfo getGameListApproved(String id, String search,Page buildPage);

    ResultInfo cancelApproved(HttpServletRequest request,List<Integer> body);

    ResultInfo getAppApproved(String body,String search ,Page buildPage);
}
