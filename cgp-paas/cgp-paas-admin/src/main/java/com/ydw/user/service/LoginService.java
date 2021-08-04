package com.ydw.user.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.ydw.user.model.db.TbUserInfo;
import com.ydw.user.utils.ResultInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author heao
 * @since 2020-04-13
 */

public interface LoginService extends IService<TbUserInfo> {

    ResultInfo login(HttpServletRequest request, HttpServletResponse response, TbUserInfo user);

    ResultInfo logout(HttpServletRequest request);

    ResultInfo getTokenByKey(HttpServletRequest request, HttpServletResponse response, TbUserInfo user);

    TbUserInfo getUserInfoFromRequest(HttpServletRequest request);

    ResultInfo refreshing(HttpServletRequest request, HttpServletResponse response);
}
