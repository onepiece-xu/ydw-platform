package com.ydw.platform.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.platform.model.db.Banner;

import com.ydw.platform.model.vo.ResultInfo;

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

        ResultInfo getBanners(HttpServletRequest request, Integer type, Integer platform,Page page);
}
