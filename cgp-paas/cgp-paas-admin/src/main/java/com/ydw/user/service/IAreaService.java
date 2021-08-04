package com.ydw.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.user.model.db.Area;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.user.utils.ResultInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heao
 * @since 2020-09-04
 */
public interface IAreaService extends IService<Area> {

    ResultInfo getAreaList(Page buildPage);

    ResultInfo addArea(Area area);

    ResultInfo updateArea(Area area);

    ResultInfo deleteArea(List<Integer> ids);

    ResultInfo bind(String body);

    ResultInfo getUnbindList(Integer id,Page buildPage);

    ResultInfo getBindList(Integer id,Page buildPage);
}
