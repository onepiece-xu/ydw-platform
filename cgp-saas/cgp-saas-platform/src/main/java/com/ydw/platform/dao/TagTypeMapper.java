package com.ydw.platform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.platform.model.db.TagType;


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
public interface TagTypeMapper extends BaseMapper<TagType> {

    Page<TagType> getTagTypeList(@Param("search") String search,Page buildPage);

    List<TagType> tagList(@Param("search") String search, Page buildPage);
}
