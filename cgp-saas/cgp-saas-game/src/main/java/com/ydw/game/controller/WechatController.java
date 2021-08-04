package com.ydw.game.controller;


import com.ydw.game.model.vo.RegistUserModel;
import com.ydw.game.model.vo.ResultInfo;
import com.ydw.game.service.ILoginService;
import com.ydw.game.util.HttpClientUtils;
import com.ydw.game.util.UrlUtil;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/wechat")
public class WechatController {

	@Autowired
	private ILoginService iTbUserInfoService;

	@RequestMapping("/getCode")
	public String getCode() throws Exception {
		// 拼接url
		StringBuilder url = new StringBuilder();
		url.append("https://open.weixin.qq.com/connect/qrconnect?");
		// appid
		url.append("appid=" + "wx9a421f1091c8354d");
		// 回调地址 ,回调地址要进行Encode转码

		String redirect_uri = "http://ccmp.geekydn.com/ccmp-user/wechat/callback";

		// 转码
		url.append("&redirect_uri=" + UrlUtil.getURLEncoderString(redirect_uri));
		url.append("&response_type=code");
		url.append("&scope=snsapi_login");
		HttpClientUtils.get(url.toString(), "GBK");
		return url.toString();
	}

	/**
	 * 微信回调
	 */
	@RequestMapping("/callback")
	public ResultInfo callback(HttpServletRequest request, String code, String state) throws Exception {
		System.out.println("====" + code + "===" + state + "====");
//		if (code != null && state != null) {
//			// 验证
//			String decrypt = AesUtil.decrypt(AesUtil.parseHexStr2Byte(state), AesUtil.PASSWORD_SECRET_KEY, 16);
//			if (!decrypt.equals(SystemConstants.PWD_MD5 + DateUtils.getYYYYMMdd())) {
//			}
//		}
			String result = null;
			if (StringUtils.isNotEmpty(code)) {
				StringBuilder url = new StringBuilder();
				url.append("https://api.weixin.qq.com/sns/oauth2/access_token?");
				url.append("appid=" + "wx9a421f1091c8354d");
				url.append("&secret=" + "e2d3b5f71dad02f36f7a39a604f28dc6");
				// 这是微信回调给你的code
				url.append("&code=" + code);
				url.append("&grant_type=authorization_code");
				result = HttpClientUtils.get(url.toString(), "UTF-8");
				System.out.println("result:" + result.toString());
			}
			if (result != null) {
				String token = "";

				String openId = "";
				JSONObject object = new JSONObject(result);
				token = object.getString("access_token");
				openId = object.getString("openid");
				RegistUserModel registUserModel = new RegistUserModel();
				registUserModel.setAccessToken(token);
				registUserModel.setOpenId(openId);
				return iTbUserInfoService.addUserByWechat(request, registUserModel);
			} else {

				return ResultInfo.fail("扫码超时，请重试");
			}
		}
	
	/**
	 * 微信回调
	 */
	@RequestMapping("/mobilecallback")
	public ResultInfo mobilecallback(HttpServletRequest request, String code, String state) throws Exception {
		System.out.println("====" + code + "===" + state + "====");
//		if (code != null && state != null) {
//			// 验证
//			String decrypt = AesUtil.decrypt(AesUtil.parseHexStr2Byte(state), AesUtil.PASSWORD_SECRET_KEY, 16);
//			if (!decrypt.equals(SystemConstants.PWD_MD5 + DateUtils.getYYYYMMdd())) {
//			}
//		}
		String result = null;
		if (StringUtils.isNotEmpty(code)) {
			StringBuilder url = new StringBuilder();
			url.append("https://api.weixin.qq.com/sns/oauth2/access_token?");
			url.append("appid=" + "wx2aef221cb0fb5302");
			url.append("&secret=" + "8f90d4becaa8d9287ab146189b030eb5");
			// 这是微信回调给你的code
			url.append("&code=" + code);
			url.append("&grant_type=authorization_code");
			result = HttpClientUtils.get(url.toString(), "UTF-8");
			System.out.println("result:" + result.toString());
		}
		if (result != null) {
			String token = "";
			
			String openId = "";
			JSONObject object = new JSONObject(result);
			token = object.getString("access_token");
			openId = object.getString("openid");
			RegistUserModel registUserModel = new RegistUserModel();
			registUserModel.setAccessToken(token);
			registUserModel.setOpenId(openId);
			return iTbUserInfoService.addUserByWechat(request, registUserModel);
		} else {
			
			return ResultInfo.fail("扫码超时，请重试");
		}
	}
	
}
