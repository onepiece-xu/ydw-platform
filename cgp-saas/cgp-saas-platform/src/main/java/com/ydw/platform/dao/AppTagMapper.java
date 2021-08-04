package com.ydw.platform.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.platform.model.db.AppTag;
import com.ydw.platform.model.vo.TagBindVO;
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
public interface AppTagMapper extends BaseMapper<AppTag> {

    AppTag getAppTag(@Param("tagId") Integer tagId, @Param("appId") String id);

    Page<TagBindVO> getAppTagByTagId(@Param("tagId") Integer tagId, Page buildPage);

    Page<TagBindVO> getUnBindAppTagByTagId(@Param("tagId") Integer tagId, Page buildPage);

//    List<String> getAppListByTag(@Param("id") int parseInt);

    List<String> getAppListByTagName(@Param("name") String toString);

    List<TagVO> getAppTagNameByAppId(@Param("id") String i);

    List<String>getAppListByTagNames(@Param("name") String toString, @Param("search") String search);

    /**
     * 根据应用id获取app的标签
     * @param id
     * @return
     */
    List<String> getTagsByAppId(String id);
}