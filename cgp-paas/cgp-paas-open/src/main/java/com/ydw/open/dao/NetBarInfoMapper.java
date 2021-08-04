package com.ydw.open.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.open.model.db.NetBarInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.open.model.vo.BaseInfo;
import com.ydw.open.model.vo.CountVO;
import com.ydw.open.model.vo.NetbarInfoVO;
import com.ydw.open.model.vo.NetbarListVO;
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

    Page <NetbarInfoVO> getNetBarList(@Param("identification")String identification,@Param("name")String name, @Param("province") String province, @Param("city")String city
            , @Param("district") String distinct, Page buildPage);

    Page<NetbarListVO> getAllNetBarList(Page buildPage);

    List<BaseInfo> getBaseStation();

    List<BaseInfo>  getMatchStation();

    CountVO getDeviceCount(String id);
}
