package com.ydw.user.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.user.model.db.Area;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.user.model.vo.ClusterBindVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-09-04
 */
public interface AreaMapper extends BaseMapper<Area> {

    Page<ClusterBindVO> getUnbindList(@Param("id") Integer id,Page buildPage);

    Page<ClusterBindVO> getBindList(@Param("id") Integer id,Page buildPage);
}
