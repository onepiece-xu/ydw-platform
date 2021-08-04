package com.ydw.user.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.user.model.db.TbUserInfo;
import com.ydw.user.model.vo.CreateUserVO;
import com.ydw.user.model.vo.modifyPasswordVO;
import com.ydw.user.utils.ResultInfo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author heao
 * @since 2020-04-13
 */
public interface ITbUserInfoService extends IService<TbUserInfo> {

    ResultInfo createUser(HttpServletRequest request, CreateUserVO user);

    ResultInfo updateUser(HttpServletRequest request, CreateUserVO user);

    ResultInfo deleteUser(HttpServletRequest request, List<String> ids);

    ResultInfo getUserList(HttpServletRequest request, String enterpriseName, String loginName,
                           String identification, Integer type, Integer schStatus, String mobileNumber,
                           String telNumber, String search, Page buildPage);


    ResultInfo resetPassword(HttpServletRequest request, TbUserInfo user);

    ResultInfo getUserInfo(HttpServletRequest request, String id);

    ResultInfo disableUser(HttpServletRequest request, Integer type, List<String> ids);

    ResultInfo updatePassword(HttpServletRequest request, modifyPasswordVO user);

    ResultInfo serviceUserList();
}
