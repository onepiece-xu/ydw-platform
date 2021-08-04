package com.ydw.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.user.model.db.TbGameGroup;

import com.ydw.user.utils.ResultInfo;

import javax.servlet.http.HttpServletRequest;

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

    ResultInfo searchApp(HttpServletRequest request, String body);
}
