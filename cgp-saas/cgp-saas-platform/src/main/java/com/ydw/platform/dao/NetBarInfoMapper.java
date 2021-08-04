package com.ydw.platform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.platform.model.db.NetBarInfo;
import com.ydw.platform.model.vo.*;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-12-09
 */
public interface NetBarInfoMapper extends BaseMapper<NetBarInfo> {

    Page <NetbarInfoVO> getNetBarList(@Param("identification") String identification, @Param("name") String name
            , @Param("province") String province, @Param("city") String city
            , @Param("district") String distinct, Page buildPage);

    Page <NetbarListAppVO> getNetBarListAndroid(@Param("identification") String identification, @Param("name") String name
            , @Param("province") String province, @Param("city") String city
            , @Param("district") String distinct, Page buildPage);


    Page<NetbarListVO> getAllNetBarList(Page buildPage);

    List<BaseInfo> getBaseStation();

    List<BaseInfo>  getMatchStation();

    List <NetbarClusterVO> getNetbarClusterIds(@Param("name") String name
            , @Param("province") String province, @Param("city") String city
            , @Param("district") String distinct, Page buildPage);

//    CountVO getDeviceCount(String id);
}
