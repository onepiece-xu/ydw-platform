package com.ydw.game.service;

import com.ydw.game.model.db.User;
import com.ydw.game.model.vo.LoginUserVO;
import com.ydw.game.model.vo.RegistUserModel;
import com.ydw.game.model.vo.ResultInfo;
import com.ydw.game.model.vo.updatePasswordVO;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2020-07-28
 */
public interface ILoginService{
//
//	ResultInfo mockLogin(User user);

//	ResultInfo mockLoginout(String token);

	ResultInfo login(User user);

	ResultInfo logout(String account);

	ResultInfo updatePassword(HttpServletRequest request,updatePasswordVO user);

	/**
	 * 获取二维码
	 * @param request
	 * @param
	 * @return
	 */
	ResultInfo getQRCode(HttpServletRequest request);

	/**
	 * 登出
	 *
	 * @param request
	 * @return
	 */
	ResultInfo userLogout(HttpServletRequest request);

	/**
	 * 账号密码注册
	 */
	ResultInfo addUserByLocal(HttpServletRequest request, @RequestBody RegistUserModel user);

	/**
	 * 发送短信
	 * @param request
	 * @param user
	 * @return
	 */
	ResultInfo smsSend(HttpServletRequest request, @RequestBody RegistUserModel user);

	/**
	 * 账号密码登录
	 *
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 */
	ResultInfo loginLocal(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginUserVO user);

	/**
	 * 手机验证码 登录 注册
	 *
	 * @param request
	 * @param user
	 * @return
	 */
	ResultInfo addUserByPhone(HttpServletRequest request, HttpServletResponse response,
							  @RequestBody RegistUserModel user);

	/**
	 * 微信登录
	 * @param request
	 * @param user
	 * @return
	 */
	ResultInfo loginWechat(HttpServletRequest request, @RequestBody LoginUserVO user);

	/**
	 * 微信扫码 登录 注册
	 *
	 * @param request
	 * @param user
	 * @return
	 */
	ResultInfo addUserByWechat(HttpServletRequest request, @RequestBody RegistUserModel user);
}
