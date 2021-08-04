package com.ydw.game.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import com.ydw.game.model.db.TbGamegroupInfo;
import com.ydw.game.model.vo.ResultInfo;


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

    ResultInfo getGameGroupList(HttpServletRequest request, Page b);

    ResultInfo addGameGroup(HttpServletRequest request, TbGamegroupInfo tbGamegroupInfo);

    ResultInfo updateGameGroup(HttpServletRequest request, TbGamegroupInfo tbGamegroupInfo);

    ResultInfo deleteGameGroup(HttpServletRequest request, List<Integer> ids);
}
