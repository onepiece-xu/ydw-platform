package com.ydw.user.dao;

import com.ydw.user.model.db.SignalServer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-08-22
 */
public interface SignalServerMapper extends BaseMapper<SignalServer> {

    SignalServer getByClusterId(String id);
}
