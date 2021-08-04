package com.ydw.platform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.platform.model.db.NetbarPic;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-12-10
 */
public interface NetbarPicMapper extends BaseMapper<NetbarPic> {

    List<String> getPics(String id);
}
