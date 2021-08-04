package com.ydw.authentication.controller;


import com.ydw.authentication.model.db.User;
import com.ydw.authentication.model.vo.RegistUserModel;
import com.ydw.authentication.service.ILoginService;
import com.ydw.authentication.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController extends BaseController{
	
	@Autowired
	private ILoginService iLoginService;

	/**
	 *管理员登录
	 */
	@PostMapping("/loginUser")
	public ResultInfo loginUser(@RequestBody User user){
		return iLoginService.login(user);
	}

	/**
	 * 管理员退出
	 * @return
	 */
	@PostMapping("/logoutUser")
	public ResultInfo logoutUser(){
		return iLoginService.logoutUser(super.getToken());
	}

	/**
	 * 登录paas平台
	 * @return
	 */
	@GetMapping("/getPaasToken")
	public ResultInfo getPaasToken(){
		return iLoginService.getPaasToken();
	}

	/**
	 * 验证token
	 * @param token
	 * @return
	 */
	@GetMapping("/checkToken")
	public ResultInfo checkToken(@RequestParam String token){
		return iLoginService.checkToken(token);
	}

	/**
	 * 获取用户详情
	 * @param token
	 * @return
	 */
	@GetMapping("/getUserInfo")
	public ResultInfo getUserInfo(@RequestParam String token){
		return iLoginService.getUserInfo(token);
	}

	/**
	 * SDK短信发送接口
	 * @param user
	 * @return
	 */
    @PostMapping(value = "/login/code")
	public ResultInfo smsSend( @RequestBody RegistUserModel user) {
		return iLoginService.smsSend(user);
	}


	/**
	 * 预注册
	 * @param user
	 * @return
	 */
    @PostMapping(value = "/preregist/phone")
	public ResultInfo preregistByPhone(@RequestBody RegistUserModel user) {
		return iLoginService.preregistByPhone(user);
	}

	/**
     * 手机验证码 登录 注册
     * @param user
     * @return
     */
    @PostMapping(value = "/regist/phone")
    public ResultInfo addUserByPhone(@RequestBody RegistUserModel user) {
        return iLoginService.addUserByPhone(user);
    }

	/**
	 *
	 * 微信绑定手机
	 * @param user
	 * @return
	 */
    @PostMapping(value = "/regist/wechatBind")
	public ResultInfo wechatbind(@RequestBody RegistUserModel user) {
		return iLoginService.wechatbind(user);
	}

	/**
	 *
	 * qq绑定手机
	 * @param user
	 * @return
	 */
    @PostMapping(value = "/regist/qqBind")
	public ResultInfo qqBind(@RequestBody RegistUserModel user) {
		return iLoginService.qqBind(user);
	}

    /**
     * 手机端自动登录
     * @param map
     * @return
     */
	@PostMapping("/autoLogin")
	public ResultInfo autoLogin(@RequestBody HashMap<String,String> map){
        String registrationId = map.get("registrationId");
        String token = map.get("token");
        return iLoginService.autoLogin(registrationId,token);
	}

	/**
	 * 刷新token
	 * @return
	 */
    @GetMapping("/refreshing")
	public ResultInfo refreshing() {
		return iLoginService.refreshing(super.getToken());
	}

    /**
     * 登出web客户端
     * @return
     */
    @PostMapping(value = "/userLogout")
    public ResultInfo userLogout() {
        return iLoginService.userLogout(super.getUserInfo(),super.getToken());
    }

	/**
	 * 登出安卓端
	 * @return
	 */
    @PostMapping(value = "/userLogoutAndroid")
	public ResultInfo userLogoutAndroid(@RequestBody String  body) {
		return iLoginService.userLogoutAndroid(super.getUserInfo(),body);
	}

    /**
     * 获取用户在线token
     * @param userId
     * @return
     */
    @GetMapping(value = "/getUserOnlineTokens")
    public ResultInfo getUserOnlineTokens(@RequestParam String userId) {
        return iLoginService.getUserOnlineTokens(userId);
    }

	/**
	 * 获取企业用户id
	 */
	@GetMapping("/getEnterpriseIdentification")
	public ResultInfo getEnterpriseIdentification(HttpServletRequest request) {
		return iLoginService.getEnterpriseIdentification(request);
	}
	/**
	 * 根据identification和secretKey获取token
	 */
	@PostMapping("/getTokenByIdentification")
	public ResultInfo getTokenByIdentification(@RequestBody Map<String,String> map) {
		return iLoginService.getTokenByIdentification(map);
	}

	/**
	 *获取验证码
	 */
	@GetMapping("/getCheckCode")
	public ResultInfo getCheckCode(HttpServletRequest request,HttpServletResponse response,@RequestParam String mobilePhone) {
		return iLoginService.getCheckCode(request,response,mobilePhone);
	}
	/**
	 *验证码检查
	 */
	@GetMapping("/checkCode")
	public ResultInfo checkCode(HttpServletRequest request,@RequestParam String mobilePhone,@RequestParam Object code) {
		return iLoginService.checkCode(request,mobilePhone,code);
	}

}
