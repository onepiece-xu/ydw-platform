package com.ydw.user.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.user.model.db.ClusterSignal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.user.model.db.SignalServer;
import com.ydw.user.model.db.TurnServer;
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
public interface ClusterSignalMapper extends BaseMapper<ClusterSignal> {

    List<ClusterSignal> getBySignalServer(@Param("id") Integer id);

    ClusterSignal getByClusterId(@Param("id")String toString);

    Page<ClusterBindVO> bindList(@Param("id")Integer id, Page page);

    Page<ClusterBindVO> unbindList(@Param("id")Integer id, Page buildPage);
}
