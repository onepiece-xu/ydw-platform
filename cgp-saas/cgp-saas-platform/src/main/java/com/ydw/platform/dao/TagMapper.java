package com.ydw.platform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.platform.model.db.Tag;

import com.ydw.platform.model.vo.GuessYouLikeVO;
import com.ydw.platform.model.vo.TagVO;

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


    Tag getTagById(@Param("id")Integer id);

    List<Tag> getByTagType(@Param("id")Integer id);

    Integer getTagIdByName(@Param("name") String  name);

    List<Tag> getOtherTags();

    List<TagVO> getAllTags();

    List<Tag> getTagByAppId(@Param("appId")String appId);

    List<GuessYouLikeVO> getTopAppById(Integer id);
}
