package com.ydw.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.ydw.user.model.db.SyncApp;
import com.ydw.user.model.vo.App;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-12-28
 */
public interface SyncAppMapper extends BaseMapper<SyncApp> {

    List<App> getSyncAppList(@Param("id") String enterpriseId);
}
