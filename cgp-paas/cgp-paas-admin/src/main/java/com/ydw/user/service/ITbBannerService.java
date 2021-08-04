package com.ydw.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.user.model.db.TbBanner;

import com.ydw.user.utils.ResultInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heao
 * @since 2020-06-10
 */
public interface ITbBannerService extends IService<TbBanner> {

    ResultInfo addBanner(HttpServletRequest request, TbBanner tbBanner);

    ResultInfo updateBanner(HttpServletRequest request, TbBanner tbBanner);

    ResultInfo getBanners(HttpServletRequest request );

    ResultInfo addBanners(HttpServletRequest request, String body);
}
