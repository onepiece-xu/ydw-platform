package com.ydw.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.admin.model.db.AppPictures;
import com.ydw.admin.model.vo.AppPicDetailsVO;


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
