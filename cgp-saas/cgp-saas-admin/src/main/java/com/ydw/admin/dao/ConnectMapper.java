package com.ydw.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.admin.model.db.Connect;
import com.ydw.admin.model.vo.ChargeSummaryVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2020-08-04
 */
public interface ConnectMapper extends BaseMapper<Connect> {

    ChargeSummaryVO chargeSummary(@Param("search") String search);

}
