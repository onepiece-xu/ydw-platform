package com.ydw.open.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.open.model.db.SignalServer;

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
