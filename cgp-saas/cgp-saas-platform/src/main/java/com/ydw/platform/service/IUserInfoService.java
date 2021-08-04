package com.ydw.platform.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import com.ydw.platform.model.db.UserInfo;
import com.ydw.platform.model.vo.ResultInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hea
 * @since 2020-10-30
 */
public interface IUserInfoService extends IService<UserInfo> {


    ResultInfo getUsageRecord(String userId, Page buildPage);

    ResultInfo sendLog(HttpServletRequest request,String userId);
}
