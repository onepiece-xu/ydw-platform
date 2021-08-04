package com.ydw.game.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ydw.game.model.db.TbTag;
import com.ydw.game.model.vo.TagVO;
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
public interface TbTagMapper extends BaseMapper<TbTag> {

    Page<TagVO> getTags(@Param("search") String search, Page b);


    TbTag getTagById(@Param("id")Integer id);

    List<TbTag> getByTagType(@Param("id")Integer id);

    Integer getTagIdByName(@Param("name") String  name);
}
