package com.ydw.user.dao;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ydw.user.model.db.TbWebrtcConfig;
import com.ydw.user.model.vo.WebRtcConfigVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-08-07
 */
public interface TbWebrtcConfigMapper extends BaseMapper<TbWebrtcConfig> {

    Page<WebRtcConfigVO> getList(Page buildPage, QueryWrapper<WebRtcConfigVO> wrapper);

    TbWebrtcConfig getById(@Param("id") Integer i);
}
