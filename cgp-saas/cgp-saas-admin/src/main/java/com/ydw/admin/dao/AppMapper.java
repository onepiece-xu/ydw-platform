package com.ydw.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.model.db.App;
import com.ydw.admin.model.vo.AppPictureVO;
import com.ydw.admin.model.vo.AppTagVO;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2020-08-05
 */
public interface AppMapper extends BaseMapper<App> {

    List<AppTagVO> getWebApps(@Param("search") String search);

    List<String> getAppListByTag(@Param("list") List<Integer> tagIds, @Param("size") int size, @Param("search") String search);


    Page<AppPictureVO> getAppsAndPics(@Param("search") String search, @Param("valid") String valid, @Param("type")Integer type,Page buildPage);
}
