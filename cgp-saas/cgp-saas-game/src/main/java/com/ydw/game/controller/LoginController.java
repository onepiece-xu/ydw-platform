package com.ydw.game.controller;

import com.alibaba.fastjson.JSON;
import com.ydw.game.config.jwt.JwtUtil;
import com.ydw.game.model.constant.Constant;
import com.ydw.game.model.vo.LoginUserVO;
import com.ydw.game.model.vo.RegistUserModel;
import com.ydw.game.model.vo.updatePasswordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ydw.game.model.db.User;
import com.ydw.game.model.vo.ResultInfo;
import com.ydw.game.service.ILoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController extends BaseController{

	@Autowired
	private ILoginService loginService;

//	@PostMapping("/mockLogin")
//	public ResultInfo mockLogin(@RequestBody User user){
//		return loginService.mockLogin(user);
//	}

//	@GetMapping("/mockLoginout")
//	public ResultInfo mockLoginout(){
//		super.getToken();
//		return loginService.mockLoginout(super.getAccount());
//	}

	@GetMapping("/refeshToken")
	public void refeshToken(){
	}

	public ResultInfo openLogin(){
		return null;
	}

	@PostMapping("/login")
	public ResultInfo login(@RequestBody User user){
		return loginService.login(user);
	}

	@PostMapping("/logout")
	public ResultInfo logout(){
		super.getToken();
		return loginService.logout(super.getAccount());
	}
	@PostMapping("/updatePassword")
	public ResultInfo updatePassword(HttpServletRequest request, @RequestBody updatePasswordVO user){
		return loginService.updatePassword(request,user);
	}

	/**
	 * 微信扫码  登录 绑定
	 * TODO
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/regist/wechat", method = RequestMethod.POST)
	@ResponseBody
	public ResultInfo addUserByWechat(HttpServletRequest request, @RequestBody RegistUserModel user) {

		return loginService.addUserByWechat(request, user);
	}


	/**
	 * 登出
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/userLogout", method = RequestMethod.POST)
	@ResponseBody
	public ResultInfo userLogout(HttpServletRequest request) {
		return loginService.userLogout(request);
	}

	/**
	 * 获取二维码
	 * @param request
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/user/qrcode", method = RequestMethod.GET)
	@ResponseBody
	public ResultInfo getQRCode(HttpServletRequest request){
		return loginService.getQRCode(request);
	}

	/**
	 * 账号密码注册
	 *
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/regist/local", method = RequestMethod.POST)
	@ResponseBody
	public ResultInfo addUserByLocal(HttpServletRequest request, @RequestBody RegistUserModel user) {

		return loginService.addUserByLocal(request, user);
	}

	/**
	 * SDK短信发送接口
	 *
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/login/code", method = RequestMethod.POST)
	@ResponseBody
	public ResultInfo smsSend(HttpServletRequest request, @RequestBody RegistUserModel user) {
		return loginService.smsSend(request, user);
	}

	/**
	 * 账号密码登录
	 *
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/login/local", method = RequestMethod.POST)
	@ResponseBody
	public ResultInfo loginLocal(HttpServletRequest request, HttpServletResponse response,
								 @RequestBody LoginUserVO user) {
		return loginService.loginLocal(request, response, user);
	}

	/**
	 * 手机验证码 登录 注册
	 *
	 * @param request
	 * @param user
	 * @return
	 */
		@RequestMapping(value = "/regist/phone", method = RequestMethod.POST)
	@ResponseBody
	public ResultInfo addUserByPhone(HttpServletRequest request, HttpServletResponse response, @RequestBody RegistUserModel user) {

		return loginService.addUserByPhone(request, response, user);
	}

	/**
	 *
	 * 微信登录
	 * TODO
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/login/wechatbind", method = RequestMethod.POST)
	@ResponseBody
	public ResultInfo loginWechat(HttpServletRequest request, @RequestBody LoginUserVO user) {
		return loginService.loginWechat(request, user);
	}
}
