package com.ydw.user.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.ydw.user.model.db.TbDemo;
import com.ydw.user.utils.ResultInfo;


import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heao
 * @since 2020-06-09
 */
public interface ITbDemoService extends IService<TbDemo> {

    ResultInfo getAppsList(HttpServletRequest request);

    ResultInfo commit(HttpServletRequest request, String body);

    ResultInfo getDemoAppsList(HttpServletRequest request);

    ResultInfo getDemoAppsListPc(HttpServletRequest request,TbDemo tbDemo);
}
