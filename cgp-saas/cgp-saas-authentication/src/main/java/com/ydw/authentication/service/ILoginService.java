package com.ydw.authentication.service;

import com.ydw.authentication.model.db.User;
import com.ydw.authentication.model.db.UserInfo;
import com.ydw.authentication.model.vo.RegistUserModel;
import com.ydw.authentication.utils.ResultInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface ILoginService{

	/**
	 * 获取pass的token
	 * @return
	 */
	ResultInfo getPaasToken();

	/**
	 * 判断token是否有效
	 * @param token
	 * @return
	 */
	ResultInfo checkToken(String token);

	/**
	 * 获取用户详情
	 * @param token
	 * @return
	 */
	ResultInfo getUserInfo(String token);

	/**
	 * 微信扫码 登录 注册
	 *
	 * @param user
	 * @return
	 */
	ResultInfo addUserByWechat(RegistUserModel user);

	/**
	 * web登出
	 * @return
	 */
	ResultInfo userLogout(UserInfo userInfo, String token);



	/**
	 * 发送短信
	 * @param user
	 * @return
	 */
	ResultInfo smsSend(RegistUserModel user);

	/**
	 * 手机验证码 登录 注册
	 *
	 * @param user
	 * @return
	 */
	ResultInfo addUserByPhone(RegistUserModel user);

	/**
	 * web端 QQ扫码 登录 注册
	 *w
	 * @param
	 * @return
	 */
	ResultInfo addUserByQq(String  body,String openId);


	/**
	 * 微信扫码后绑定手机
	 * @param user
	 * @return
	 */
	ResultInfo wechatbind(RegistUserModel user);

	/**
	 * qq扫码后绑定手机
	 * @param user
	 * @return
	 */
	ResultInfo qqBind(RegistUserModel user);

	/**
	 * 刷token
	 * @param token
	 * @return
	 */
	ResultInfo refreshing(String token);

	/**
	 * 安卓用户授权后接口
	 * @param access_token
	 * @param openId
	 * @return
	 */
	ResultInfo indexAndroid(String access_token, String openId,String registrationId);

	/**
	 * 安卓退出接口
	 * @param body
	 * @return
	 */
	ResultInfo userLogoutAndroid(UserInfo userInfo, String body);

	ResultInfo login(User user);

	ResultInfo logoutUser(String token);

	/**
	 * 获取用户在线token
	 * @param userId
	 * @return
	 */
    ResultInfo getUserOnlineTokens(String userId);

	ResultInfo getEnterpriseIdentification(HttpServletRequest request);

	ResultInfo getTokenByIdentification(Map<String, String> map);

	/**
	 * 自动登录
	 * @param registrationId
	 * @param token
	 * @return
	 */
    ResultInfo autoLogin(String registrationId, String token);

	/**
	 * 禁用此处登录
	 * @param userId
	 */
	void disableLogin(String userId);

	/**
	 * 预注册
	 * @param user
	 * @return
	 */
    ResultInfo preregistByPhone(RegistUserModel user);

    ResultInfo getCheckCode(HttpServletRequest request, HttpServletResponse response,String mobilePhone);

	ResultInfo checkCode(HttpServletRequest request, String mobilePhone,Object code);
}
