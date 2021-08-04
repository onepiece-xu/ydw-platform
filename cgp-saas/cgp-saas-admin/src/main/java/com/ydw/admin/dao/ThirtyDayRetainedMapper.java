package com.ydw.admin.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.model.db.ThirtyDayRetained;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2021-03-16
 */
public interface ThirtyDayRetainedMapper extends BaseMapper<ThirtyDayRetained> {

    Page<ThirtyDayRetained> getThirtyDayRetainedRate(@Param("startTime") String startTime, @Param("endTime") String endTime, Page buildPage);
}
