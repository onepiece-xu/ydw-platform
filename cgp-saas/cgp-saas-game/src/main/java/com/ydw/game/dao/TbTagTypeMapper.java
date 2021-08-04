package com.ydw.game.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.game.model.db.TbTagType;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-06-29
 */
public interface TbTagTypeMapper extends BaseMapper<TbTagType> {

    Page<TbTagType> getTagTypeList(@Param("search") String search,Page buildPage);

    List<TbTagType> tagList(@Param("search") String search, Page buildPage);
}
