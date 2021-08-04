package com.ydw.platform.dao;

import com.ydw.platform.model.db.AppPictures;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.platform.model.vo.AppPicDetailsVO;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2020-09-28
 */
public interface AppPicturesMapper extends BaseMapper<AppPictures> {

    AppPicDetailsVO getAppPicDetails(String id);
}
