package com.ydw.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.admin.model.db.NetbarApp;
import com.ydw.admin.model.vo.NetbarAppVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2021-03-03
 */
public interface NetbarAppMapper extends BaseMapper<NetbarApp> {

    List<NetbarAppVO> getNetbarApps(String id);
}
