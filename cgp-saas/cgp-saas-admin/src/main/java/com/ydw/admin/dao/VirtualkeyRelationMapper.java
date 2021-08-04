package com.ydw.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.model.db.App;
import com.ydw.admin.model.db.VirtualkeyRelation;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2021-01-13
 */
public interface VirtualkeyRelationMapper extends BaseMapper<VirtualkeyRelation> {

    Page<App> getBindedApp(Page page, @Param("id") String id, @Param("search") String search);

    Page<App> getUnBindApp(Page page, @Param("id") String id, @Param("search") String search);

}
