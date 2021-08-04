package com.ydw.open.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ydw.open.model.db.TbUserApps;
import com.ydw.open.model.vo.AppTagVO;
import com.ydw.open.model.vo.AppVO;

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

    Page<AppVO> getUserAppsVo(@Param("name") String name,
                              @Param("accessId") String accessId, @Param("type") Integer type
            , @Param("strategyName") String strategyName, @Param("identification") String identification, @Param("status") String status, @Param("search") String search,
                              @Param("schStatus") String schStatus);

    Page<TbUserApps> getInstallApps(@Param("id") String id);

    Page<TbUserApps> getUnInstallApps(@Param("id") String id);

    /**
     * 获取全部游戏
     * @param search
     * @return
     */
    List<AppTagVO> getWebApps(@Param("search") String search);


    List<String> getAppIdBySearch(String search);

    List<String> getAppListByTag(@Param("list") List<Integer> tagIds, @Param("size") int size, @Param("search") String search);

    Page<TbUserApps>  getGameAppList(Page buildPage, @Param("id")String id);
}
