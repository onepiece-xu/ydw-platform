package com.ydw.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.user.model.db.TbAppStrategy;
import com.ydw.user.model.vo.AppVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-04-29
 */
public interface TbAppStrategyMapper extends BaseMapper<TbAppStrategy> {

    Page<TbAppStrategy> getStrategyList(@Param("name")String name, @Param("fps")Integer fps, @Param("speed")Integer speed, @Param("output")Integer output, @Param("video")Integer video
            , @Param("encode")String encode, @Param("search")String search ,Page buildPage);

    Page<AppVO> getBindApps(@Param("strategyId") Integer strategyId,@Param("appName") String appName,@Param("enterpriseName")String enterpriseName,@Param("type")Integer type,Page buildPage);

    Page<AppVO> getUnBindApps(@Param("strategyId")Integer strategyId,@Param("appName") String appName,@Param("enterpriseName")String enterpriseName,@Param("type")Integer type,Page buildPage);

    TbAppStrategy getDefaultStrategy();
}
