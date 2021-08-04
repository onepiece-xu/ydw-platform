package com.ydw.admin.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.admin.model.db.GameGroupInfo;
import com.ydw.admin.model.vo.ResultInfo;

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
public interface IGameGroupInfoService extends IService<GameGroupInfo> {

    ResultInfo getGameGroupList(HttpServletRequest request, Page b);

    ResultInfo addGameGroup(HttpServletRequest request, GameGroupInfo tbGamegroupInfo);

    ResultInfo updateGameGroup(HttpServletRequest request, GameGroupInfo tbGamegroupInfo);

    ResultInfo deleteGameGroup(HttpServletRequest request, List<Integer> ids);
}
