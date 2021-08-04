package com.ydw.platform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.platform.model.db.Banner;


import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-06-10
 */
public interface BannerMapper extends BaseMapper<Banner> {

    List<Banner> getBanners();

    List<Banner>  getByOrderNumber(Integer orderNumber);
}
