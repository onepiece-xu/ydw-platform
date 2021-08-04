package com.ydw.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.model.db.Tag;
import com.ydw.admin.model.vo.TagVO;
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
public interface TagMapper extends BaseMapper<Tag> {

    Page<TagVO> getTags(@Param("search") String search, Page b);


    Tag getTagById(@Param("id") Integer id);

    List<Tag> getByTagType(@Param("id") Integer id);

    Integer getTagIdByName(@Param("name") String name);

    List<Tag> getOtherTags();

    List<TagVO> getAllTags();

    List<Integer>  getBindTagId(@Param("id") String id);
}
