package com.ydw.platform.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.platform.model.db.GameGroup;

import com.ydw.platform.model.vo.ResultInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heao
 * @since 2020-07-14
 */
public interface IGameGroupService extends IService<GameGroup> {

    ResultInfo getGameGroupAppList(HttpServletRequest request, String body);

    ResultInfo getHotGameList(HttpServletRequest request, String body);

    ResultInfo getNewGameList(HttpServletRequest request);

    /**
     * 未使用
     * @param request
     * @param body
     * @return
     */
    ResultInfo searchApp(HttpServletRequest request, String body);

    ResultInfo addApps(HttpServletRequest request, String body);

    ResultInfo getGroupAppList(HttpServletRequest request, Integer id, Page b);

    ResultInfo getGameList(HttpServletRequest request);

    ResultInfo updateApps(HttpServletRequest request, String body);

    ResultInfo deleteApps(HttpServletRequest request, List<Integer> ids);

    ResultInfo getGameGroupLists(HttpServletRequest request);

    ResultInfo getRecommendList(HttpServletRequest request);

    ResultInfo getTopGames(HttpServletRequest request);

    ResultInfo getHotGameListAndroid(HttpServletRequest request, String body);

    ResultInfo getNewGameListAndroid(HttpServletRequest request);

    ResultInfo getRecommendApps(HttpServletRequest request);

    ResultInfo getHotGameApps(HttpServletRequest request);

    ResultInfo getNewApps(HttpServletRequest request);

    ResultInfo getTopApps(HttpServletRequest request);

    ResultInfo getGameGroupApps(HttpServletRequest request);

    ResultInfo getGroupById(HttpServletRequest request, Integer id);
}
