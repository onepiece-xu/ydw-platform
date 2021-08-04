package com.ydw.platform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.platform.model.db.UserInfo;
import com.ydw.platform.model.vo.UsageRecordVO;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hea
 * @since 2020-10-30
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    Page<UsageRecordVO> getUsageRecord(Page buildPage, String userId);
}
