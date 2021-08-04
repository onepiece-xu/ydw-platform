package com.ydw.user.dao;

import com.ydw.user.model.db.ClusterTurn;
import com.ydw.user.model.db.TurnServer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-08-22
 */
public interface TurnServerMapper extends BaseMapper<TurnServer> {

    TurnServer getByClusterId(String id);
}
