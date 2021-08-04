package com.ydw.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import com.ydw.user.model.db.AppPictures;
import com.ydw.user.model.db.TbUserApps;

import com.ydw.user.model.vo.*;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-04-23
 */
public interface TbUserAppsMapper extends BaseMapper<TbUserApps> {
//        Page<TbUserApps> getUserApps(String name, String accessId, Integer type, Integer strategyId, String userId, String status);

    Page<AppVO> getUserAppsVo(@Param("name")String name,
                              @Param("accessId")String accessId, @Param("type") Integer type
                                    , @Param("strategyName") String strategyName, @Param("identification")String identification, @Param("status")String status, @Param("search")String search,
                              @Param("schStatus")String schStatus,Page   buildPage);

    Page<TbUserApps> getInstallApps(@Param("id") String id,Page   buildPage);

    Page<TbUserApps> getUnInstallApps(@Param("id") String id,Page   buildPage);

    List<DemoApps> getApps();

    List<DemoAppVO> getDemoAppsList(@Param("type") Integer type);


    List<AppTagVO> getWebApps(@Param("search")String search);


    List<String> getAppIdBySearch(String search);

    List<String> getAppListByTag(@Param("list") List<Integer> tagIds,@Param("size") int size,@Param("search") String search);

    List<TbUserApps> getByStrategyId(@Param("id")Integer id);

    List<AppPictures> getAppPictures();

    AppInfo getUserAppInfo(@Param("appId") String appId, @Param("clusterId") String clusterId);
}
