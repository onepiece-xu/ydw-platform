package com.ydw.platform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.platform.model.db.App;
import com.ydw.platform.model.vo.*;

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

    List<String> getAppListByTag(@Param("list") List<Integer> tagIds,@Param("size") int size,@Param("search") String search);


    CloudGameVO getAppInfo(String id);

    List<RecordListVO> getRecordPlayList(String userId);

    Page<AppVO> getAppList(@Param("platform")Integer platform, @Param("free")Integer free,
                           @Param("search")String search, @Param("type")Integer type,
                           @Param("list")List<Integer> tagIds,@Param("size") int size,Page b );

    List<PlayListVO> getPlayList(String userId);

    Page<AppListByTagsVO> getAppListByTags(@Param("platform")Integer platform, @Param("free")Integer free,
                                 @Param("search")String search, @Param("type")Integer type,
                                 @Param("list")List<Integer> tagIds,@Param("size") int size,Page b );
}
