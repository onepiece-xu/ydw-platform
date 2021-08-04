package com.ydw.authentication.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ydw.authentication.service.ILoginService;
import com.ydw.authentication.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/qq")
public class QqController {
    @Autowired
    private ILoginService iTbUserInfoService;

    @Value("${qq.url.http}")
    private String http;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 返回的URL
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/login")
    public ResultInfo qq() throws UnsupportedEncodingException {
        //QQ互联中的回调地址
        String backUrl = http + "#/qqCallBack";

        //Step1：获取Authorization Code
        String url = "https://graph.qq.com/oauth2.0/authorize?response_type=code"+
                "&client_id=" + QQHttpClient.APPID +
                "&redirect_uri=" + URLEncoder.encode(backUrl, "gbk")+
                "&state=" + SequenceGenerator.sequence();
        return  ResultInfo.success(url);
    }

    @GetMapping("/index")
    public ResultInfo qqCallBack(@RequestParam String code, @RequestParam String state) throws Exception {
        //Step2：通过Authorization Code获取Access Token
        String backUrl = http + "#/qqCallBack";
        String url = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code" +
                "&client_id=" + QQHttpClient.APPID +
                "&client_secret=" + QQHttpClient.APPKEY +
                "&code=" + code +
                "&redirect_uri=" + backUrl;
        String access_token = QQHttpClient.getAccessToken(url);

        //Step3: 获取回调后的 openid 值
        url = "https://graph.qq.com/oauth2.0/me?access_token=" + access_token;
        String openId = QQHttpClient.getOpenID(url);

        //Step4：获取QQ用户信息
        url = "https://graph.qq.com/user/get_user_info?access_token=" + access_token +
                "&oauth_consumer_key=" + QQHttpClient.APPID +
                "&openid=" + openId;

        //返回用户的信息
        JSONObject jsonObject = QQHttpClient.getUserInfo(url);

        //查qq用户的unionid
        String unionUrl = "https://graph.qq.com/oauth2.0/me?access_token=" + access_token +
                "&unionid=1";

        String result = HttpClientUtils.get(unionUrl);
        System.out.println("++++============="+result);
        String substring=result.substring(result.indexOf("(")+1,result.indexOf(")"));
        JSONObject obj = JSON.parseObject(substring);
        String unionid = obj.getString("unionid");

        //响应重定向到home路径
        if (null != jsonObject) {
            return  iTbUserInfoService.addUserByQq(jsonObject.toJSONString(),unionid);
        } else {
            return ResultInfo.fail("扫码超时，请重试");
        }
    }

    @GetMapping("/indexAndroid")
    public ResultInfo indexAndroid(HttpServletRequest request, @RequestParam String access_token,  @RequestParam String openId, @RequestParam(required = false)String registrationId){
        return  iTbUserInfoService.indexAndroid(access_token,openId,registrationId);
    }

}
