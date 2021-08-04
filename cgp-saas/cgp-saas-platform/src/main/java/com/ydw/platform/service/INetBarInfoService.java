package com.ydw.platform.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.platform.model.db.NetBarInfo;
import com.ydw.platform.model.vo.ResultInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heao
 * @since 2020-12-09
 */
public interface INetBarInfoService extends IService<NetBarInfo> {


    ResultInfo getNetBarList(HttpServletRequest request, String name, String province, String city, String district, Page buildPage);

    ResultInfo getNetBarInfo(String id);

    ResultInfo getAllNetBarList(HttpServletRequest request, Page buildPage);


    ResultInfo getAllNetBarStatus(HttpServletRequest request, Page buildPage);

    ResultInfo getNetBarListAndroid(HttpServletRequest request, String name, String province, String city, String district, Page buildPage);

    ResultInfo getNetBarStatus(HttpServletRequest request, String name, String province, String city, String district, Page buildPage);
}
