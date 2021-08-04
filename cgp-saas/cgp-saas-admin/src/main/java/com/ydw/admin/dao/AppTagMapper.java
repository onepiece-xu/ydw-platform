package com.ydw.admin.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.model.db.AppTag;
import com.ydw.admin.model.vo.AppBindTagVO;
import com.ydw.admin.model.vo.AppTagTypeVO;
import com.ydw.admin.model.vo.TagBindVO;
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
public interface AppTagMapper extends BaseMapper<AppTag> {

    AppTag getAppTag(@Param("tagId") Integer tagId, @Param("appId") String id);

    Page<TagBindVO> getAppTagByTagId(@Param("tagId") Integer tagId, Page buildPage);

    Page<TagBindVO> getUnBindAppTagByTagId(@Param("tagId") Integer tagId, Page buildPage);

//    List<String> getAppListByTag(@Param("id") int parseInt);

    List<String> getAppListByTagName(@Param("name") String toString);

    List<TagVO> getAppTagNameByAppId(@Param("id") String i);

    List<String>getAppListByTagNames(@Param("name") String toString, @Param("search") String search);


    Page<AppBindTagVO> getAppBindTag(@Param("appId")String appId, Page buildPage);

    Page<AppBindTagVO> getAppUnBindTag(@Param("appId")String appId, Page buildPage);

    List<AppBindTagVO> getAppUnBindTagList(@Param("appId")String appId);

    List<TagBindVO> getAppTagListByTagId(Integer id);

    List<AppTagTypeVO> getTagTypeByAppId(String appId);

    List<AppTag> getAppTagByAppId(String appId);
}