package com.ydw.authentication.controller;


import com.alibaba.fastjson.JSON;
import com.ydw.authentication.dao.LoginLogMapper;
import com.ydw.authentication.dao.UserInfoMapper;
import com.ydw.authentication.model.constant.Constant;
import com.ydw.authentication.model.db.LoginLog;
import com.ydw.authentication.model.db.UserInfo;
import com.ydw.authentication.model.vo.RegistUserModel;
import com.ydw.authentication.model.vo.UserVO;
import com.ydw.authentication.service.IUserInfoService;
import com.ydw.authentication.service.IYdwMessageService;
import com.ydw.authentication.service.impl.LoginServiceImpl;
import com.ydw.authentication.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/wechat")
public class WechatController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LoginServiceImpl loginService;
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private IYdwMessageService iYdwMessageService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private LoginLogMapper loginLogMapper;

    @Value("${wechat.url.http}")
    private String http;

    @Value("${shiro.accessTokenExpireTime}")
    private Long accessTokenExpireTime;

    @RequestMapping("/getCode")
    public ResultInfo getCode() throws Exception {
        // 拼接url
        StringBuilder url = new StringBuilder();
        url.append("https://open.weixin.qq.com/connect/qrconnect?");
        // appid
        url.append("appid=" + Constant.WECHATAPPID);
        String redirect_uri = http + "#/wechatCallBack";
        // 转码
        url.append("&redirect_uri=" + UrlUtil.getURLEncoderString(redirect_uri));
        url.append("&response_type=code");
        url.append("&scope=snsapi_login");
        return ResultInfo.success(url.toString());
    }

    /**
     * 微信回调
     */
    @RequestMapping("/callback")
    public ResultInfo callback(String code) throws Exception {
        String result = null;
        if (StringUtils.isNotEmpty(code)) {
            StringBuilder url = new StringBuilder();
            url.append("https://api.weixin.qq.com/sns/oauth2/access_token?");
            url.append("appid=" + Constant.WECHATAPPID);
            url.append("&secret=" + Constant.WECHATSECRETKEY);
            // 这是微信回调给你的code
            url.append("&code=" + code);
            url.append("&grant_type=authorization_code");
            result = HttpClientUtils.get(url.toString(), "UTF-8");
        }
        if (result != null) {
            String token = "";
            String openId = "";
            String unionid = "";
            JSONObject object = new JSONObject(result);
            token = object.getString("access_token");
            openId = object.getString("openid");

            String nickname = null;
            String headimgurl = null;
            //获取用户个人信息（UnionID 机制）
            if (openId != null) {
                if (StringUtils.isNotEmpty(code)) {
                    StringBuilder url = new StringBuilder();
                    url.append("https://api.weixin.qq.com/sns/userinfo?access_token=");
                    url.append(token);
                    url.append("&openid=" + openId);
                    result = HttpClientUtils.get(url.toString(), "UTF-8");
                    System.out.println("result:" + result.toString());
                    JSONObject obj = new JSONObject(result);
                    unionid = object.getString("unionid");
                    nickname = obj.getString("nickname");

                    headimgurl = obj.getString("headimgurl");
                }
            }


            //判断openid用户是否已存在
            UserInfo userInfo = userInfoMapper.selectByWXOpenId(unionid);
            if (null != userInfo) {
                String mobileNumber = userInfo.getMobileNumber();
                if (null != mobileNumber) {
                    if (userInfo.getStatus() != 0){
                        return ResultInfo.fail("账号被禁用！");
                    }
                    //微信用户扫码用户且手机号也绑定了
                    //notice前端
                    loginService.notice(userInfo.getId(), 3);


                    //登录信息
                    UserVO userVO = new UserVO();
                    BeanUtils.copyProperties(userInfo, userVO);

                    //改最新的头像
                    UserInfo tbUserInfo = new UserInfo();
                    tbUserInfo.setId(userInfo.getId());
                    tbUserInfo.setNickname(nickname);
                    tbUserInfo.setAvatar(headimgurl);
                    userInfoMapper.updateById(tbUserInfo);

                    token = JwtUtil.sign(userInfo.getId(), "user");
                    redisUtil.set(Constant.PREFIX_SHIRO_TOKEN + userInfo.getId(), token, accessTokenExpireTime);
                    redisUtil.set(Constant.USER + userInfo.getId(), JSON.toJSONString(userInfo));
                    userVO.setToken(token);
                    userVO.setNickname(nickname);
                    userVO.setAvatar(headimgurl);

                    //添加记录登录日志信息
                    String id = userVO.getId();
                    try {
                        LoginLog loginLog = new LoginLog();
                        loginLog.setLoginTime(new Date());
                        loginLog.setUserId(id);
                        loginLogMapper.insert(loginLog);
                    } catch (Exception e) {
                        logger.error(id+"用户-----------登录日志记录失败！");
                        e.printStackTrace();
                    }

                    return ResultInfo.success(userVO);
                } else {
                    //微信号已注册，未绑定手机号的
                    UserVO userVO = new UserVO();
                    BeanUtils.copyProperties(userInfo, userVO);
                    userVO.setIsOldUser(false);
                    return ResultInfo.success(userVO);
                }
            } else {
                RegistUserModel registUserModel = new RegistUserModel();
                registUserModel.setAccessToken(token);
                registUserModel.setWechatOpenId(openId);
//				registUserModel.setWechatUnionId(unionid);
                return loginService.addUserByWechat(registUserModel);
            }
        } else {
            return ResultInfo.fail("扫码超时，请重试");
        }
    }

    /**
     * 微信回调
     */
    @RequestMapping("/callbackAndroid")
    public ResultInfo callbackAndroid(String code, String state, String registrationId) throws Exception {
        logger.info("微信登录回调code:{},registrationId:{}", code, registrationId);
        String result = null;
        if (StringUtils.isNotEmpty(code)) {
            StringBuilder url = new StringBuilder();
            url.append("https://api.weixin.qq.com/sns/oauth2/access_token?");
            url.append("appid=" + Constant.WECHATAPPIDAndroid);
            url.append("&secret=" + Constant.WECHATSECRETKEYAndroid);
            // 这是微信回调给你的code
            url.append("&code=" + code);
            url.append("&grant_type=authorization_code");
            result = HttpClientUtils.get(url.toString(), "UTF-8");
            System.out.println("result:" + result.toString());
        }
        if (result != null) {
            String token = "";
            String openId = "";
            String unionid = "";
            JSONObject object = new JSONObject(result);
            token = object.getString("access_token");
            openId = object.getString("openid");

            String nickname = null;
            String headimgurl = null;


            //获取用户个人信息（UnionID 机制）
            if (openId != null) {
                if (StringUtils.isNotEmpty(code)) {
                    StringBuilder url = new StringBuilder();
                    url.append("https://api.weixin.qq.com/sns/userinfo?access_token=");
                    url.append(token);
                    url.append("&openid=" + openId);
                    result = HttpClientUtils.get(url.toString(), "UTF-8");
                    System.out.println("result:" + result.toString());
                    JSONObject obj = new JSONObject(result);
                    unionid = object.getString("unionid");
                    nickname = obj.getString("nickname");
                    headimgurl = obj.getString("headimgurl");
                }
            }

            //判断openid用户是否已存在
            UserInfo userInfo = userInfoMapper.selectByWXOpenId(unionid);
            if (null != userInfo) {
                String mobileNumber = userInfo.getMobileNumber();
                if (null != mobileNumber) {
                    if (userInfo.getStatus() == 1){
                        return ResultInfo.fail("账号被禁用！");
                    }
                    if (userInfo.getStatus() == 3){
                        return ResultInfo.fail("账号被注销！");
                    }


                    //notice前端
                    loginService.notice(userInfo.getId(), 3);
                    //微信用户扫码用户且手机号也绑定了
                    //登录信息
                    UserVO userVO = new UserVO();
                    BeanUtils.copyProperties(userInfo, userVO);
                    //改最新的头像
                    UserInfo tbUserInfo = new UserInfo();
                    tbUserInfo.setId(userInfo.getId());
                    tbUserInfo.setNickname(nickname);
                    tbUserInfo.setAvatar(headimgurl);
                    userInfoMapper.updateById(tbUserInfo);
                    userVO.setNickname(nickname);
                    userVO.setAvatar(headimgurl);
                    token = JwtUtil.sign(userInfo.getId(), registrationId);
                    redisUtil.set(Constant.PREFIX_SHIRO_TOKEN + userInfo.getId(), token, accessTokenExpireTime);
                    redisUtil.set(Constant.USER + userInfo.getId(), JSON.toJSONString(userInfo));
                    userVO.setToken(token);
                    if (StringUtil.isNotBlank(registrationId)) {
                        //调用接口
                        HashMap<String, String> params = new HashMap<>();
                        params.put("token", token);
                        params.put("registrationId", registrationId);
                        iYdwMessageService.bind(params);

                    }
                    //添加记录登录日志信息
                    String id = userVO.getId();
                    try {
                        LoginLog loginLog = new LoginLog();
                        loginLog.setLoginTime(new Date());
                        loginLog.setUserId(id);
                        loginLogMapper.insert(loginLog);
                    } catch (Exception e) {
                        logger.error(id+"用户-----------登录日志记录失败！");
                        e.printStackTrace();
                    }
                    return ResultInfo.success(userVO);
                } else {
                    //微信号已注册，未绑定手机号的
                    UserVO userVO = new UserVO();
                    BeanUtils.copyProperties(userInfo, userVO);
                    userVO.setIsOldUser(false);
                    return ResultInfo.success(userVO);
                }
            } else {
                RegistUserModel registUserModel = new RegistUserModel();
                registUserModel.setAccessToken(token);
                registUserModel.setWechatOpenId(openId);
                return loginService.addUserByWechat(registUserModel);
            }
        } else {
            return ResultInfo.fail("扫码超时，请重试");
        }
    }
}
