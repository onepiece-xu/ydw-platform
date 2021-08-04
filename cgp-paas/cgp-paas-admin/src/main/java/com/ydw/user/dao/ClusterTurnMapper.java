package com.ydw.user.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.user.model.db.ClusterTurn;
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
 * @since 2020-08-24
 */
public interface ClusterTurnMapper extends BaseMapper<ClusterTurn> {

    ClusterTurn getByClusterId(@Param("id") String toString);

    List<ClusterTurn> getByTurnServer(@Param("id")Integer id);

    Page<ClusterBindVO> bindList(@Param("id")Integer id, Page buildPage);

    Page<ClusterBindVO> unbindList(@Param("id")Integer id, Page buildPage);
}
