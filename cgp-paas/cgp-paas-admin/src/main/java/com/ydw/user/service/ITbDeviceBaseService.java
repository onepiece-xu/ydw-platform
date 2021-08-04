package com.ydw.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.user.model.db.TbDeviceBase;
import com.ydw.user.utils.ResultInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heao
 * @since 2020-07-24
 */
public interface ITbDeviceBaseService extends IService<TbDeviceBase> {

    ResultInfo getBaseList(HttpServletRequest request, String search, Page buildPage);

    ResultInfo addDeviceBase(HttpServletRequest request, TbDeviceBase tbDeviceBase);

    ResultInfo updateDeviceBase(HttpServletRequest request, TbDeviceBase tbDeviceBase);

    ResultInfo deleteDeviceBase(HttpServletRequest request, List<Integer> ids);

    ResultInfo getBaseById(HttpServletRequest request, Integer id);
}
