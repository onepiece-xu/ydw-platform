package com.ydw.open.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import com.ydw.open.model.db.TbTag;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-06-29
 */
public interface TbTagMapper extends BaseMapper<TbTag> {

    Page<TbTag> getTags(@Param("search") String search);
}
