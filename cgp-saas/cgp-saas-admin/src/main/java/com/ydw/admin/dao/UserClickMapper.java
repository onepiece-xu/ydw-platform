package com.ydw.admin.dao;

import com.ydw.admin.model.db.UserClick;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.admin.model.vo.UserClickVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2021-04-14
 */
public interface UserClickMapper extends BaseMapper<UserClick> {

    List<UserClickVO> getUserClick();
}
