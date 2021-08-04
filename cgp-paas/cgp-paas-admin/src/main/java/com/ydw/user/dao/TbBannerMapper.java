package com.ydw.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.user.model.db.TbBanner;


import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-06-10
 */
public interface TbBannerMapper extends BaseMapper<TbBanner> {

    List<TbBanner> getBanners();

    List<TbBanner>  getByOrderNumber(Integer orderNumber);
}
