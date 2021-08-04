package com.ydw.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.model.db.NetBarInfo;
import com.ydw.admin.model.vo.BaseInfo;
import com.ydw.admin.model.vo.CountVO;
import com.ydw.admin.model.vo.NetbarInfoVO;
import com.ydw.admin.model.vo.NetbarListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-12-09
 */
public interface NetBarInfoMapper extends BaseMapper<NetBarInfo> {

    Page<NetbarInfoVO> getNetBarList(@Param("name") String name,
                 @Param("province") String province, @Param("city") String city, @Param("district") String distinct,
                                     @Param("identification")String identification,Page buildPage);

    Page<NetbarListVO> getAllNetBarList(Page buildPage);

    List<BaseInfo> getBaseStation();

    List<BaseInfo> getMatchStation();

//    CountVO getDeviceCount(String id);
}
