package com.ydw.admin.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.admin.model.db.Banner;
import com.ydw.admin.model.db.BannerType;
import com.ydw.admin.model.vo.ResultInfo;

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
public interface IBannerService extends IService<Banner> {

    ResultInfo addBanner(HttpServletRequest request, Banner tbBanner);

    ResultInfo updateBanner(HttpServletRequest request, Banner tbBanner);

    ResultInfo getBanners(HttpServletRequest request, Integer type, Page page);

    ResultInfo addBanners(HttpServletRequest request, String body);

    ResultInfo deleteBanners(HttpServletRequest request, List<Integer> ids);

    ResultInfo addBannerType(BannerType bannerType);

    ResultInfo getBannerTypes();

    ResultInfo updateBannerType(BannerType bannerType);

    ResultInfo deleteBannerType(List<Integer> ids);
}
