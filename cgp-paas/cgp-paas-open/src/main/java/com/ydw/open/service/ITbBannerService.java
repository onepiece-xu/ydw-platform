package com.ydw.open.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.ydw.open.model.db.TbBanner;
import com.ydw.open.utils.ResultInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    ResultInfo getBanners(HttpServletRequest request);

    ResultInfo addBanners(HttpServletRequest request, String body);

    ResultInfo deleteBanners(HttpServletRequest request, List<Integer> ids);
}
