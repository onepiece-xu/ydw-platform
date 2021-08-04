package com.ydw.authentication.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ydw.authentication.model.db.TbUserInfo;
import com.ydw.authentication.model.db.UserApprove;
import com.ydw.authentication.model.db.UserInfo;
import com.ydw.authentication.model.vo.LoginVO;
import com.ydw.authentication.utils.ResultInfo;

import java.util.HashMap;

public interface ILoginService{

	ResultInfo saasLogin(LoginVO loginVO);

	ResultInfo checkToken(String token);

	ResultInfo openLogin(HttpServletRequest request, HttpServletResponse response, UserApprove user);

	ResultInfo backStageLogin(HttpServletRequest request, HttpServletResponse response, UserInfo user);

	ResultInfo logout(String token);

	ResultInfo getUserInfo(String token);

    ResultInfo clusterLogin(String clusterId);

	ResultInfo getIdentification(String token);
}
