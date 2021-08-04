package com.ydw.game.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.game.model.db.TbGameGroup;
import com.ydw.game.model.vo.ResultInfo;

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
public interface ITbGameGroupService extends IService<TbGameGroup> {

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
}
