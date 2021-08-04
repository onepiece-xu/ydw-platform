package com.ydw.admin.service;

import com.ydw.admin.model.db.PictureType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.admin.model.vo.ResultInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2021-02-19
 */
public interface IPictureTypeService extends IService<PictureType> {

    ResultInfo getList();
}
