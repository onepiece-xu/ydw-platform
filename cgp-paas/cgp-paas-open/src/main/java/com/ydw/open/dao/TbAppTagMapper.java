package com.ydw.open.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ydw.open.model.db.TbAppTag;
import com.ydw.open.model.vo.TagBindVO;
import com.ydw.open.model.vo.TagVO;

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
public interface TbAppTagMapper extends BaseMapper<TbAppTag> {

    TbAppTag getAppTag(@Param("tagId") Integer tagId, @Param("appId") String id);

    Page<TagBindVO> getAppTagByTagId(@Param("tagId") Integer tagId);

    Page<TbAppTag> getUnBindAppTagByTagId(@Param("tagId") Integer tagId);

//    List<String> getAppListByTag(@Param("id") int parseInt);

    List<String> getAppListByTagName(@Param("name") String toString);

    List<TagVO> getAppTagNameByAppId(@Param("id") String i);

    List<String>getAppListByTagNames(@Param("name") String toString, @Param("search") String search);
}
