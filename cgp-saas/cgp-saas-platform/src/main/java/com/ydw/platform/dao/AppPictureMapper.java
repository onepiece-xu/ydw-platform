package com.ydw.platform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.ydw.platform.model.db.AppPicture;
import com.ydw.platform.model.vo.AppPictureVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2021-02-18
 */
public interface AppPictureMapper extends BaseMapper<AppPicture> {

    List<AppPictureVO> getByAppId(String appId);
}
