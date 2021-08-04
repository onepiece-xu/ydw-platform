package com.ydw.user.service;

import com.ydw.user.model.db.TbAppPictures;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.user.utils.ResultInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heao
 * @since 2020-12-28
 */
public interface ITbAppPicturesService extends IService<TbAppPictures> {

    ResultInfo upload(HttpServletRequest request, String appId);
}
