package com.ydw.open.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import com.ydw.open.model.db.TbGamegroupInfo;
import com.ydw.open.utils.ResultInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heao
 * @since 2020-07-16
 */
public interface ITbGamegroupInfoService extends IService<TbGamegroupInfo> {

    ResultInfo getGameGroupList(HttpServletRequest request, Page buildPage);

    ResultInfo addGameGroup(HttpServletRequest request, TbGamegroupInfo tbGamegroupInfo);

    ResultInfo updateGameGroup(HttpServletRequest request, TbGamegroupInfo tbGamegroupInfo);

    ResultInfo deleteGameGroup(HttpServletRequest request, List<Integer> ids);
}
