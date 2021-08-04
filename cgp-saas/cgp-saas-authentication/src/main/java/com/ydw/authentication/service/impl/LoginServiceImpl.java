package com.ydw.authentication.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.ydw.authentication.dao.*;
import com.ydw.authentication.model.constant.Constant;
import com.ydw.authentication.model.db.*;
import com.ydw.authentication.model.vo.Msg;
import com.ydw.authentication.model.vo.RegistUserModel;
import com.ydw.authentication.model.vo.UserVO;
import com.ydw.authentication.service.*;
import com.ydw.authentication.utils.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.apache.commons.lang.StringUtils.equalsIgnoreCase;

@Service
public class LoginServiceImpl implements ILoginService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${shiro.accessTokenExpireTime}")
    private Long accessTokenExpireTime;

    @Value("${centre.domain}")
    private String centreDomain;
    @Value("${refresh.time}")
    private Integer refreshTime;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Autowired
    private IYdwMessageService iYdwMessageService;

    @Autowired
    private IYdwPlatformService ydwPlatformService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IUserRelationalService userRelationalService;
    @Autowired
    private LoginLogMapper loginLogMapper;

    @Autowired
    private IUserInfoService userInfoService;

    @Override
    public ResultInfo getPaasToken() {
        Enterprise enterprise = enterpriseMapper.selectOne(new QueryWrapper<>());
        String token = null;
        String key = MessageFormat.format(Constant.PAAS_TOKEN, enterprise.getIdentification());
        token = (String) redisUtil.get(key);
        if (StringUtils.isBlank(token)) {
            synchronized (this) {
                String url = centreDomain + "/cgp-paas-authentication/login/saasLogin";
                Map<String, Object> params = new HashMap<>();
                params.put("identification", enterprise.getIdentification());
                params.put("secretKey", enterprise.getSecretKey());
                params.put("saas", enterprise.getSaas());
                String result = HttpUtil.doJsonPost(url, params);
                logger.info("获取paas平台token返回结果：{}", result);
                JSONObject jsonObject = JSON.parseObject(result);
                if (jsonObject.getIntValue("code") == 200) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    token = data.getString("token");
                    Date expireDate = data.getDate("expireDate");
                    LocalDateTime expireDateTime = LocalDateTime.ofInstant(expireDate.toInstant(), ZoneId.systemDefault());
                    Long seconds = Duration.between(LocalDateTime.now(), expireDateTime).getSeconds();
                    if (seconds <= 0) {
                        return getPaasToken();
                    }
                    boolean setnx = redisUtil.setnx(key, token, seconds);
                    if (!setnx) {
                        token = (String) redisUtil.get(key);
                    }
                    return ResultInfo.success("获取paas平台token成功！", token);
                } else {
                    return ResultInfo.fail();
                }
            }
        }
        return ResultInfo.success("获取paas平台token成功！", token);
    }

    @Override
    public ResultInfo checkToken(String token) {
        String account = JwtUtil.getClaim(token, Constant.ACCOUNT);
        String redisToken = (String)(redisUtil.get(Constant.PREFIX_SHIRO_TOKEN + account));
        if(StringUtil.nullOrEmpty(redisToken)){
            ResultInfo info = new ResultInfo();
            info.setCode(401);
            info.setMsg("身份信息已失效，请重新登陆！");
            return info;
        }else{
            if (redisToken.equals(token)) {
                return ResultInfo.success("登录成功！", account);
            } else {
                ResultInfo info = new ResultInfo();
                info.setCode(401);
                info.setMsg("身份信息已失效，请重新登陆！");
                return info;
            }
        }
    }

    @Override
    public ResultInfo getUserInfo(String token) {
        String account = JwtUtil.getClaim(token, Constant.ACCOUNT);
        UserInfo userInfo = userInfoMapper.selectById(account);
        Enterprise enterprise = enterpriseMapper.selectOne(new QueryWrapper<>());
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userInfo, userVO);
        userVO.setEnterpriseId(enterprise.getIdentification());
        return ResultInfo.success(userVO);
    }

    @Override
    public ResultInfo addUserByWechat(RegistUserModel user) {
        String result = null;
        //获取微信授权token
        String accessToken = user.getAccessToken();
        String openId = user.getWechatOpenId();
        // 拼接url
        StringBuilder url = new StringBuilder();
        // access_token=ACCESS_TOKEN&openid=OPENID
        url.append("https://api.weixin.qq.com/sns/userinfo?");
        // appid
        url.append("access_token=" + accessToken);
        url.append("&" + "openid=" + openId);

        try {
            result = HttpUtil.doGet(url.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result != null) {
            JSONObject obj = JSON.parseObject(result);
            String wechatUnionId = obj.getString("unionid");
            UserInfo userInfo = userInfoMapper.selectByWXOpenId(wechatUnionId);
            if (userInfo == null){
                String nickname = obj.getString("nickname");
                String headimgurl = obj.getString("headimgurl");
                userInfo = new UserInfo();
                userInfo.setWechatOpenId(wechatUnionId);
                userInfo.setId(SequenceGenerator.sequence());
                userInfo.setRegisterTime(new Date());
                userInfo.setNickname(nickname);
                userInfo.setAvatar(headimgurl);
                userInfo.setWechat(wechatUnionId);
                userInfoMapper.insert(userInfo);
                UserVO userVO = new UserVO();
                BeanUtils.copyProperties(userInfo, userVO);
                userVO.setIsOldUser(false);
                return ResultInfo.success(userVO);
            }else{
                //登录信息
                if (userInfo.getStatus() != 0){
                    return ResultInfo.fail("此账号已被禁用！");
                }
                UserVO userVO = new UserVO();
                BeanUtils.copyProperties(userInfo, userVO);
                if (StringUtil.isNotBlank(userInfo.getMobileNumber())){
                    //notice前端
                    notice(userInfo.getId(), 3);
                    String token = JwtUtil.sign(userInfo.getId(),user.getRegistrationId());
                    redisUtil.set(Constant.PREFIX_SHIRO_TOKEN + userInfo.getId(), token, accessTokenExpireTime);
                    redisUtil.set(Constant.USER + userInfo.getId(), JSON.toJSONString(userInfo));
                    userVO.setToken(token);
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
                }else{
                    userVO.setIsOldUser(false);
                }
                return ResultInfo.success(userVO);
            }
        } else {
            return ResultInfo.fail("扫码超时,请重新扫码");
        }
    }

    @Override
    public ResultInfo userLogout(UserInfo userInfo, String token) {
        String userId = userInfo.getId();
        redisUtil.del(Constant.PREFIX_SHIRO_TOKEN + userId);
        redisUtil.del(Constant.USER + userId);
        QueryWrapper<LoginLog> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.isNull("logout_time");
        wrapper.orderByDesc("login_time");
        try {
            List<LoginLog> loginLogs = loginLogMapper.selectList(wrapper);
            LoginLog loginLog = loginLogs.get(0);
            loginLog.setLogoutTime(new Date());
            loginLogMapper.updateById(loginLog);
        } catch (Exception e) {
            logger.error(userId+"用户-----------退出登录日志记录失败！");
            e.printStackTrace();
        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo smsSend(RegistUserModel user) {
        String mobileNumber = user.getMobileNumber();
        Integer type = user.getType();
        if (type == 4) {
            UserInfo selectByMobileNumber = userInfoMapper.selectByMobileNumber(mobileNumber);
            if (selectByMobileNumber != null) {
                type = 6;
            }
        }
        sendMessageCode(mobileNumber, type);
        return ResultInfo.success("success");
    }


    /**
     * 发送短信验证码
     *
     * @param phoneNum 需要发送给哪个手机号码
     * @return 验证码，若为000000，则发送失败
     */
    private String sendMessageCode(String phoneNum, Integer type) {
        String key = null;
        String str = "000000";
        int templateId = 729402;//模板 ID`729402`只是示例
        if(type ==0){
            key = Constant.CANCEL;
            templateId = Constant.CANCEL_TEMPLATE_ID;
        }
        if (type == 1) {
            key = Constant.PREFIX_lOCALREGISTCODE_SESSION;
        }
        if (type == 2) {
            key = Constant.PREFIX_WXREGISTCODE_SESSION;
            templateId = Constant.LoginTemplateId;
        }
        if (type == 3) {
            key = Constant.PREFIX_QQREGISTCODE_SESSION;
            templateId = Constant.LoginTemplateId;
        }
        if (type == 4) {
            //手机号注册
            key = Constant.PREFIX_MOBILEREGISTCODE_SESSION;
            templateId = Constant.RegistTemplateId;
        }
        if (type == 6) {
            key = Constant.PREFIX_MOBILELOGINCODE_SESSION;
            templateId = Constant.LoginTemplateId;
        }
        if (type == 5) {
            key = Constant.PREFIX_FORGETCODE_SESSION;
        }

        try {
            // 生成的6位数赋值验证码
            String strTemp = (int) ((Math.random() * 9 + 1) * 100000) + "";
            redisUtil.set(key + phoneNum, strTemp, 60 * 30);
            Object validateCode = redisUtil.get(key + phoneNum);
            System.out.println(validateCode.toString());
            // 数组具体的元素个数和模板中变量个数必须一致
            // 比如你模板中需要填写验证码和有效时间，{1}，{2}
            // 那你这里的参数就应该填两个
            String[] params = {strTemp, "5"};
            SmsSingleSender ssender = new SmsSingleSender(Constant.appid, Constant.appkey);
            // 签名参数未提供或者为空时，会使用默认签名发送短信
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNum, templateId, params, Constant.smsSign, "", "");
            System.out.println("result = " + result);
            // 发送成功则给验证码赋值
            if (result.result == 0) {
                str = strTemp;
            }
        } catch (HTTPException e1) {
            // HTTP响应码错误
            e1.printStackTrace();
        } catch (JSONException e2) {
            // json解析错误
            e2.printStackTrace();
        } catch (IOException e3) {
            // 网络IO错误
            e3.printStackTrace();
        }
        return str;
    }

    @Override
    public ResultInfo addUserByPhone(RegistUserModel user) {
        UserVO userVO = new UserVO();
        // token
        String token = null;
        // 待绑定的账号
        String mobileNumber = user.getMobileNumber();
        //手机绑定流程
        UserInfo userInfo = userInfoService.getUserByMobileNumber(mobileNumber);
        // 判断老用户新登录
        if (null != userInfo) {
            if (userInfo.getStatus() == 1){
                return ResultInfo.fail("此账号已被禁用！");
            }
            Object validateCode = redisUtil.get(Constant.PREFIX_MOBILELOGINCODE_SESSION + mobileNumber);
            if (null == validateCode) {
                return ResultInfo.fail(SystemConstants.CodeExpire);
            }
            if (!validateCode.equals(user.getMessageCode())) {
                return ResultInfo.fail(SystemConstants.CodeError);
            }
            if (userInfo.getStatus() == 2){
                QueryWrapper<UserRelational> qw = new QueryWrapper<>();
                qw.eq("inferior",userInfo.getId());
                UserRelational userRelational = userRelationalService.getOne(qw);
                if (userRelational != null){
                    HashMap<String,String> map = new HashMap<>();
                    map.put("recommender",userRelational.getRecommender());
                    map.put("inferior",userRelational.getInferior());
                    ydwPlatformService.pullNewAward(map);
                }
                userInfo.setStatus(0);
                userInfoService.updateById(userInfo);
                ydwPlatformService.registerAward(userInfo.getId());
            }
            String userId = userInfo.getId();
            token = JwtUtil.sign(userInfo.getId(), user.getRegistrationId());
            redisUtil.set(Constant.PREFIX_SHIRO_TOKEN + userId, token, accessTokenExpireTime);
            redisUtil.set(Constant.USER + userId, JSON.toJSONString(userInfo));
            BeanUtils.copyProperties(userInfo, userVO);
            userVO.setToken(token);
        } else {
            Object validateCode = redisUtil.get(Constant.PREFIX_MOBILEREGISTCODE_SESSION + mobileNumber);
            if (null == validateCode) {
                return ResultInfo.fail(SystemConstants.CodeExpire);
            }
            if (!validateCode.equals(user.getMessageCode())) {
                return ResultInfo.fail(SystemConstants.CodeError);
            }

            userInfo = new UserInfo();
            String userId = SequenceGenerator.sequence();
            userInfo.setId(userId);
            userInfo.setMobileNumber(mobileNumber);
            userInfo.setRegisterTime(new Date());
            userInfo.setNickname(mobileNumber);
            userInfo.setShareCode(DigestUtils.md5Hex(userId));
            userInfo.setChannelId(user.getChannelId());
            if (StringUtil.isNotBlank(user.getShareCode())){
                //别人推荐进来的
                QueryWrapper<UserInfo> qw = new QueryWrapper<>();
                qw.eq("share_code",user.getShareCode());
                UserInfo recommender = userInfoMapper.selectOne(qw);
                if (recommender != null){
                    //保存推荐关系
                    UserRelational ur = new UserRelational();
                    ur.setId(SequenceGenerator.sequence());
                    ur.setCreateTime(new Date());
                    ur.setInferior(userId);
                    ur.setRecommender(recommender.getId());
                    userRelationalService.save(ur);
                    //给推荐人生成推荐奖励
                    HashMap<String,String> map = new HashMap<>();
                    map.put("recommender",recommender.getId());
                    map.put("inferior",userId);
                    ydwPlatformService.pullNewAward(map);
                }
            }
            userInfoMapper.insert(userInfo);
            ydwPlatformService.registerAward(userId);
            // 注册
            token = JwtUtil.sign(userId, user.getRegistrationId());
            //notice通知前端发消息
            notice(userId, 3);
            redisUtil.set(Constant.PREFIX_SHIRO_TOKEN + userId, token, accessTokenExpireTime);
            redisUtil.set(Constant.USER + userId, JSON.toJSONString(userInfo));
            BeanUtils.copyProperties(userInfo, userVO);
            userVO.setToken(token);
        }
        bind(token, user.getRegistrationId());

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
    }

    /**
     * 预注册
     *
     * @param user
     * @return
     */
    @Override
    public ResultInfo preregistByPhone(RegistUserModel user) {
        // 待绑定的账号
        String mobileNumber = user.getMobileNumber();

        //手机绑定流程
        UserInfo userInfo = userInfoService.getUserByMobileNumber(mobileNumber);

        // 判断老用户新登录
        if (null != userInfo) {
            return ResultInfo.fail("此账号已被注册！");
        } else {
            userInfo = new UserInfo();
            String userId = SequenceGenerator.sequence();
            userInfo.setId(userId);
            userInfo.setMobileNumber(mobileNumber);
            userInfo.setRegisterTime(new Date());
            userInfo.setNickname(mobileNumber);
            userInfo.setShareCode(DigestUtils.md5Hex(userId));
            userInfo.setChannelId(user.getChannelId());
            userInfo.setStatus(2);
            if (StringUtil.isNotBlank(user.getShareCode())){
                //别人推荐进来的
                QueryWrapper<UserInfo> qw = new QueryWrapper<>();
                qw.eq("share_code",user.getShareCode());
                UserInfo recommender = userInfoMapper.selectOne(qw);
                if (recommender != null){
                    //保存推荐关系
                    UserRelational ur = new UserRelational();
                    ur.setId(SequenceGenerator.sequence());
                    ur.setCreateTime(new Date());
                    ur.setInferior(userId);
                    ur.setRecommender(recommender.getId());
                    userRelationalService.save(ur);
                }
            }
            userInfoMapper.insert(userInfo);
        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo getCheckCode(HttpServletRequest request, HttpServletResponse response,String mobilePhone) {
        String stringCode="";
        try {
//            OutputStream out = new FileOutputStream("D://tttt/"+mobilePhone+".jpg");
            OutputStream out = response.getOutputStream();
            Map<String,Object> map = VerificationCodeUtil.generateCodeAndPic();
            ImageIO.write((RenderedImage) map.get("codePic"), "jpeg", out);
            redisUtil.set(Constant.VERIFICATIONCODE + mobilePhone,map.get("code"),600);
            stringCode=map.get("codePic").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  ResultInfo.success(stringCode);
    }

    @Override
    public ResultInfo checkCode(HttpServletRequest request, String mobilePhone,Object code) {
        Object VERIFICATIONCODE = redisUtil.get(Constant.VERIFICATIONCODE + mobilePhone);
        if(code == null ||!equalsIgnoreCase(VERIFICATIONCODE.toString(),code.toString())){
            return ResultInfo.fail("验证码不正确或者已失效！");
        }
        try {
            QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
            wrapper.eq("mobile_number",mobilePhone);
            UserInfo userInfo = userInfoMapper.selectOne(wrapper);
            if(null!=userInfo){
                Integer status = userInfo.getStatus();
                if(status==3){
                    return ResultInfo.fail("此用户已被注销，请联系客服确认！");
                }
            }
        } catch (Exception e) {
            logger.error("用户注销状态异常！");
            e.printStackTrace();
        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo addUserByQq(String body, String unionId) {
        //获取QQ授权
        if (body != null) {
            JSONObject jsonObject = JSONObject.parseObject(body);
            UserInfo userInfo = userInfoMapper.selectByQQOpenId(unionId);
            if (userInfo == null){
                String nickname = jsonObject.getString("nickname");
                String headimgurl = jsonObject.getString("figureurl_qq_2");
                userInfo = new UserInfo();
                userInfo.setId(SequenceGenerator.sequence());
                userInfo.setRegisterTime(new Date());
                userInfo.setNickname(nickname);
                userInfo.setAvatar(headimgurl);
                userInfo.setQqOpenId(unionId);
                userInfoMapper.insert(userInfo);
                UserVO userVO = new UserVO();
                BeanUtils.copyProperties(userInfo, userVO);
                userVO.setIsOldUser(false);
                return ResultInfo.success(userInfo);
            }else{
                //登录信息
                if (userInfo.getStatus() != 0){
                    return ResultInfo.fail("此账号已被禁用或已被注销！");
                }
                UserVO userVO = new UserVO();
                BeanUtils.copyProperties(userInfo, userVO);
                if (StringUtil.isNotBlank(userInfo.getMobileNumber())){
                    //notice前端
                    notice(userInfo.getId(), 3);
                    String token = JwtUtil.sign(userInfo.getId());
                    redisUtil.set(Constant.PREFIX_SHIRO_TOKEN + userInfo.getId(), token, accessTokenExpireTime);
                    redisUtil.set(Constant.USER + userInfo.getId(), JSON.toJSONString(userInfo));
                    userVO.setToken(token);

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
                }else{
                    userVO.setIsOldUser(false);
                }
                return ResultInfo.success(userVO);
            }
        } else {
            return ResultInfo.fail("扫码超时,请重新扫码");
        }
    }

    @Override
    @Transactional
    public ResultInfo wechatbind(RegistUserModel user) {
        //验证码验证
        String mobileNumber = user.getMobileNumber();
        Object validateCode = redisUtil.get(Constant.PREFIX_WXREGISTCODE_SESSION + mobileNumber);
        if (null == validateCode) {
            return ResultInfo.fail(SystemConstants.CodeExpire);
        }
        // 验证码
        String code = user.getMessageCode();
        if (!code.equals(validateCode)) {
            return ResultInfo.fail(SystemConstants.CodeError);
        }
        // 获取openId
        String wechatOpenId = user.getWechatOpenId();
        if (wechatOpenId == null) {
            return ResultInfo.fail("无效的微信授权!");
        }
        //根据微信获取用户，若用户信息手机号为空，则需将手机号绑定
        UserInfo info = userInfoMapper.selectByWXOpenId(wechatOpenId);
        if (info == null || StringUtils.isNotBlank(info.getMobileNumber())){
            return ResultInfo.fail("此微信号未注册或者已绑定过其他手机号！");
        }else{
            if (info.getStatus() == 1){
                return ResultInfo.fail("此账号已被禁用！");
            }
            UserVO userVO = new UserVO();
            UserInfo mobileNumberUser = userInfoMapper.selectByMobileNumber(mobileNumber);
            if (null != mobileNumberUser) {
                if (StringUtil.isNotBlank(mobileNumberUser.getWechatOpenId())) {
                    return ResultInfo.fail(SystemConstants.PHONEUSED);
                }
                if (mobileNumberUser.getStatus() == 2){
                    QueryWrapper<UserRelational> qw = new QueryWrapper<>();
                    qw.eq("inferior",mobileNumberUser.getId());
                    UserRelational userRelational = userRelationalService.getOne(qw);
                    if (userRelational != null){
                        HashMap<String,String> map = new HashMap<>();
                        map.put("recommender",userRelational.getRecommender());
                        map.put("inferior",userRelational.getInferior());
                        ydwPlatformService.pullNewAward(map);
                    }
                    mobileNumberUser.setStatus(0);
                    ydwPlatformService.registerAward(mobileNumberUser.getId());
                }
                mobileNumberUser.setModifiedTime(info.getModifiedTime());
                mobileNumberUser.setAvatar(info.getAvatar());
                mobileNumberUser.setWechatOpenId(info.getWechatOpenId());
                mobileNumberUser.setNickname(info.getNickname());
                //手机用户信息和微信信息绑定更新
                userInfoMapper.updateById(mobileNumberUser);
                //删除微信老用户
                userInfoMapper.deleteById(info);
                BeanUtils.copyProperties(mobileNumberUser, userVO);
            } else {
                info.setMobileNumber(mobileNumber);
                info.setChannelId(user.getChannelId());
                info.setRegisterTime(new Date());
                info.setShareCode(DigestUtils.md5Hex(info.getId()));
                userInfoMapper.updateById(info);
                BeanUtils.copyProperties(info, userVO);
                ydwPlatformService.registerAward(info.getId());
            }
            String id = userVO.getId();
            // 注册
            String token = JwtUtil.sign(id, user.getRegistrationId());
            redisUtil.set(Constant.PREFIX_SHIRO_TOKEN + id, token, accessTokenExpireTime);
            redisUtil.set(Constant.USER + id, JSON.toJSONString(userVO));
            userVO.setToken(token);
            //notice通知前端发消息
            notice(id, 3);
            bind(token, user.getRegistrationId());

            //添加记录登录日志信息
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
        }
    }

    @Override
    public ResultInfo qqBind(RegistUserModel user) {
        // 待绑定的账号
        String mobileNumber = user.getMobileNumber();
        Object validateCode = redisUtil.get(Constant.PREFIX_QQREGISTCODE_SESSION + mobileNumber);
        if (null == validateCode) {
            return ResultInfo.fail(SystemConstants.CodeExpire);
        }
        // 验证码
        String code = user.getMessageCode();
        if (!code.equals(validateCode)) {
            return ResultInfo.fail(SystemConstants.CodeError);
        }
        String qqOpenId = user.getQqOpenId();
        if (qqOpenId == null) {
            return ResultInfo.fail("无效的QQ授权!");
        }
        //根据微信获取用户，若用户信息手机号为空，则需将手机号绑定
        UserInfo info = userInfoMapper.selectByQQOpenId(qqOpenId);
        if (info == null || StringUtils.isNotBlank(info.getMobileNumber())){
            return ResultInfo.fail("此QQ号未注册或者已绑定过其他手机号！");
        }else {
            if (info.getStatus() == 1){
                return ResultInfo.fail("此账号已被禁用！");
            }
            UserVO userVO = new UserVO();
            UserInfo mobileNumberUser = userInfoMapper.selectByMobileNumber(mobileNumber);
            if (null != mobileNumberUser) {
                if (StringUtils.isNotBlank(mobileNumberUser.getQqOpenId())) {
                    return ResultInfo.fail(SystemConstants.PHONEUSED);
                }
                if (mobileNumberUser.getStatus() == 2){
                    QueryWrapper<UserRelational> qw = new QueryWrapper<>();
                    qw.eq("inferior",mobileNumberUser.getId());
                    UserRelational userRelational = userRelationalService.getOne(qw);
                    if (userRelational != null){
                        HashMap<String,String> map = new HashMap<>();
                        map.put("recommender",userRelational.getRecommender());
                        map.put("inferior",userRelational.getInferior());
                        ydwPlatformService.pullNewAward(map);
                    }
                    mobileNumberUser.setStatus(0);
                    ydwPlatformService.registerAward(mobileNumberUser.getId());
                }
                mobileNumberUser.setModifiedTime(info.getModifiedTime());
                mobileNumberUser.setAvatar(info.getAvatar());
                mobileNumberUser.setQqOpenId(info.getQqOpenId());
                mobileNumberUser.setNickname(info.getNickname());
                //手机用户信息和微信信息绑定更新
                userInfoMapper.updateById(mobileNumberUser);
                //删除老用户
                userInfoMapper.deleteById(info);
                BeanUtils.copyProperties(mobileNumberUser, userVO);
            } else {
                info.setMobileNumber(mobileNumber);
                info.setChannelId(user.getChannelId());
                info.setRegisterTime(new Date());
                info.setShareCode(DigestUtils.md5Hex(info.getId()));
                userInfoMapper.updateById(info);
                BeanUtils.copyProperties(info, userVO);
                try {
                    //  奖励
                    ydwPlatformService.registerAward(info.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String id = userVO.getId();
            // 注册
            String token = JwtUtil.sign(id, user.getRegistrationId());
            redisUtil.set(Constant.PREFIX_SHIRO_TOKEN + id, token, accessTokenExpireTime);
            redisUtil.set(Constant.USER + id, JSON.toJSONString(userVO));
            userVO.setToken(token);
            //notice通知前端发消息
            notice(id, 3);
            bind(token, user.getRegistrationId());

            //添加记录登录日志信息

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
        }

    }

    @Override
    public ResultInfo refreshing(String token) {
        String account = JwtUtil.getClaim(token, Constant.ACCOUNT);
        String redisToken = redisUtil.get(Constant.PREFIX_SHIRO_TOKEN + account).toString();
        redisUtil.set(Constant.PREFIX_SHIRO_TOKEN + account, redisToken, accessTokenExpireTime);
        String time = JwtUtil.getClaim(token, Constant.TIME);
//        logger.info(time+":---------time----");
        try {
            //判断token是否为前一天登录
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String nowStr = dateFormat.format(System.currentTimeMillis());
//            logger.info(nowStr+"---------nowStr----");
            long t = Long.parseLong(time);
//            logger.info(t+"---------long  time----");
            String oldStr = dateFormat.format(t);
//            logger.info(oldStr+"---------oldStr----");
            if(!nowStr.equals(oldStr)){
                //添加记录登录日志信息
                    LoginLog loginLog = new LoginLog();
                    loginLog.setLoginTime(new Date());
                    loginLog.setUserId(account);
                    loginLogMapper.insert(loginLog);
                logger.info(account+"用户--"+nowStr+"至------"+oldStr+"-----------隔日登录日志已记录！");
            }
        } catch (Exception e) {
            logger.error(account+"用户-----------登录日志记录失败！");
            e.printStackTrace();
        }
        return ResultInfo.success(refreshTime);
    }

    @Override
    public ResultInfo indexAndroid(String access_token, String openId, String registrationId) {
        //Step：获取QQ用户信息
        String url = "https://graph.qq.com/user/get_user_info?access_token=" + access_token +
                "&oauth_consumer_key=" + QQHttpClient.AndroidAPPID +
                "&openid=" + openId;
        JSONObject jsonObject = null;
        try {
            //返回用户的信息
            jsonObject = QQHttpClient.getUserInfo(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String nickname = jsonObject.getString("nickname");
        String img = jsonObject.getString("figureurl_qq_2");
        //查qq用户的unionid
        String unionUrl = "https://graph.qq.com/oauth2.0/me?access_token=" + access_token +
//                "&oauth_consumer_key=" + QQHttpClient.APPID +
                "&unionid=1";

        String result = null;
        try {
            result = HttpClientUtils.get(unionUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("++++=============" + result);
        String substring = result.substring(result.indexOf("(") + 1, result.indexOf(")"));
        JSONObject obj = JSON.parseObject(substring);
        String unionId = obj.getString("unionid");


        //查qq类型的identityType=，是否有注册过
        UserInfo byIdentifier = userInfoMapper.selectByQQOpenId(unionId);

        //若为新登录(老用户新登录和新用户新登录)
        if (null == byIdentifier) {
            UserInfo tbUserInfo = new UserInfo();
            // 插info表
            tbUserInfo.setId(SequenceGenerator.sequence());
            tbUserInfo.setRegisterTime(new Date());
            tbUserInfo.setNickname(nickname);
            tbUserInfo.setAvatar(img);
            tbUserInfo.setQqOpenId(unionId);
            userInfoMapper.insert(tbUserInfo);

            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(tbUserInfo, userVO);
            userVO.setIsOldUser(false);
            return ResultInfo.success(userVO);
        } else {
            try {
                //  判断用户是否被注销
                String mobilePhone = byIdentifier.getMobileNumber();
                QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
                wrapper.eq("mobile_number",mobilePhone);
                UserInfo userInfo = userInfoMapper.selectOne(wrapper);
                Integer status = userInfo.getStatus();
                if(status==3){
                    return ResultInfo.fail("此用户已被注销，请联系客服确认！");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //老用户判断手机号是否存在

            if (!StringUtil.isNotBlank(byIdentifier.getMobileNumber())){
                UserVO userVO = new UserVO();
                BeanUtils.copyProperties(byIdentifier, userVO);
                userVO.setIsOldUser(false);
                return ResultInfo.success(userVO);
            }

            //老用户老登陆
            String token = "";
            String id = byIdentifier.getId();
            UserInfo selectByInfoId = userInfoMapper.selectByInfoId(id);
            if (selectByInfoId.getStatus() == 1){
                return ResultInfo.fail("此账号已被禁用！");
            }
            //登录信息
            //notice通知前端发消息
            notice(id, 3);
            token = JwtUtil.sign(id, registrationId);
            redisUtil.set(Constant.PREFIX_SHIRO_TOKEN + id, token, accessTokenExpireTime);
            redisUtil.set(Constant.USER + id, JSON.toJSONString(selectByInfoId));

            //改头像
            selectByInfoId.setNickname(nickname);
            selectByInfoId.setAvatar(img);
            userInfoMapper.updateById(selectByInfoId);
            //返回前端
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(selectByInfoId, userVO);
            userVO.setToken(token);

            //添加记录登录日志信息
            try {
                LoginLog loginLog = new LoginLog();
                loginLog.setLoginTime(new Date());
                loginLog.setUserId(id);
                loginLogMapper.insert(loginLog);
            } catch (Exception e) {
                logger.error(id+"用户-----------登录日志记录失败！");
                e.printStackTrace();
            }

            bind(token,registrationId);
            return ResultInfo.success(userVO);
        }
    }

    @Override
    public ResultInfo userLogoutAndroid(UserInfo userInfo, String body) {
        JSONObject object = JSON.parseObject(body);
        String registrationId = object.getString("registrationId");
        String userId = userInfo.getId();
        UpdateWrapper<UserInfo> uw = new UpdateWrapper<>();
        uw.eq("id", userId);
        uw.set("online_status", false);
        userInfoMapper.update(null, uw);
        Object token = redisUtil.get(Constant.PREFIX_SHIRO_TOKEN + userId);
        redisUtil.del(Constant.PREFIX_SHIRO_TOKEN + userId);
        redisUtil.del(Constant.USER + userId);
        //调用接口
        if (!StringUtil.nullOrEmpty(registrationId)) {
            HashMap<String, String> params = new HashMap<>();
            params.put("userId", userId);
            params.put("registrationId", registrationId);
            iYdwMessageService.unbind(params);
        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo login(User user) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("loginname", user.getLoginname());
        String password = user.getPassword();
        String md5 = PasswordUtil.md5(password);
        qw.eq("password", md5);
        User userInfo = userMapper.selectOne(qw);
        if (userInfo != null) {
            String token = JwtUtil.sign(user.getLoginname());
            redisUtil.set(Constant.PREFIX_SHIRO_TOKEN + user.getLoginname(), token, accessTokenExpireTime);
            return ResultInfo.success("登录成功！", token);
        }
        return ResultInfo.fail("登陆失败！ ");

    }

    @Override
    public ResultInfo logoutUser(String token) {
        String account = JwtUtil.getClaim(token, Constant.ACCOUNT);
        redisUtil.del(Constant.PREFIX_SHIRO_TOKEN + account);
        return ResultInfo.success();
    }

    /**
     * 登录和登出通知
     * 登录成功时，先通知 该用户在其他设备登录
     * status 1登录成功 2登出成功 3在其他端登录
     *
     * @param userId
     * @param status
     */
    public void notice(String userId, int status) {
        String token = (String) redisUtil.get(Constant.PREFIX_SHIRO_TOKEN + userId);
        if (StringUtil.isNotBlank(token)) {
            Msg msg = new Msg();
            HashMap<String, Object> data = new HashMap<>();
            data.put("userId", userId);
            data.put("token", token);
            data.put("status", status);
            msg.setData(JSON.toJSONString(data));
            List<String> receivers = new ArrayList<>();
            receivers.add(token);
            msg.setReceivers(receivers);
            msg.setType("login");
            iYdwMessageService.sendMsg(msg);
        }
    }

    /**
     * 移动端极光推送id绑定
     * @param token
     * @param registrationId
     */
    public void bind(String token, String registrationId) {
        //通知前端
        if (StringUtils.isNotBlank(token) && StringUtils.isNotBlank(registrationId)) {
            //调用接口
            HashMap<String, String> params = new HashMap<>();
            params.put("token", token);
            params.put("registrationId", registrationId);
            iYdwMessageService.bind(params);
        }
    }

    /**
     * 移动端极光推送id绑定
     * @param token
     * @param registrationId
     */
    public void unbind(String token, String registrationId) {
        //通知前端
        if (StringUtils.isNotBlank(token) && StringUtils.isNotBlank(registrationId)) {
            //调用接口
            HashMap<String, String> params = new HashMap<>();
            params.put("token", token);
            params.put("registrationId", registrationId);
            iYdwMessageService.unbind(params);
        }
    }

    /**
     * 获取用户在线token
     *
     * @param userId
     * @return
     */
    @Override
    public ResultInfo getUserOnlineTokens(String userId) {
        Object o = redisUtil.get(Constant.PREFIX_SHIRO_TOKEN + userId);
        return ResultInfo.success(new Object[]{o});
    }

    @Override
    public ResultInfo getEnterpriseIdentification(HttpServletRequest request) {
        Enterprise enterprise = enterpriseMapper.selectOne(new QueryWrapper<>());
        String  identification=enterprise.getIdentification();
        return ResultInfo.success(identification);
    }

    @Override
    public ResultInfo getTokenByIdentification(Map<String, String> map) {
        String identification = map.get("identification");
        String secretKey = map.get("secretKey");
        String token=null;
        Enterprise enterprise = enterpriseMapper.selectOne(new QueryWrapper<>());
        if(identification.equals(enterprise.getIdentification())&&secretKey.equals(enterprise.getSecretKey())){
             token = JwtUtil.sign(identification);
            redisUtil.set(Constant.PREFIX_SHIRO_TOKEN + identification, token, accessTokenExpireTime);
        }
        return ResultInfo.success(token);
    }

    /**
     * 自动登录
     *
     * @param registrationId
     * @param token
     * @return
     */
    @Override
    public ResultInfo autoLogin(String registrationId, String token) {
        try{
            String claim = JwtUtil.getClaim(token, Constant.ROLE);
            if (!registrationId.equals(claim)){
                ResultInfo info = new ResultInfo();
                info.setCode(401);
                info.setMsg("身份信息已失效，请重新登陆！");
                return info;
            }
            String account = JwtUtil.getClaim(token, Constant.ACCOUNT);
            String redisToken = (String)(redisUtil.get(Constant.PREFIX_SHIRO_TOKEN + account));
            if(!token.equals(redisToken)){
                ResultInfo info = new ResultInfo();
                info.setCode(401);
                info.setMsg("身份信息已失效，请重新登陆！");
                return info;
            }
            UserInfo userInfo = userInfoMapper.selectById(account);
            //添加记录登录日志信息
            try {
                LoginLog loginLog = new LoginLog();
                loginLog.setLoginTime(new Date());
                loginLog.setUserId(account);
                loginLogMapper.insert(loginLog);
            } catch (Exception e) {
                logger.error(account+"用户-----------退出登录日志记录失败！");
                e.printStackTrace();
            }
            return ResultInfo.success(userInfo);
        }catch (Exception e){
            ResultInfo info = new ResultInfo();
            info.setCode(401);
            info.setMsg("身份信息已失效，请重新登陆！");
            return info;
        }
    }

    /**
     * 禁用此处登录
     *
     * @param userId
     */
    @Override
    public void disableLogin(String userId) {
        redisUtil.del(Constant.PREFIX_SHIRO_TOKEN + userId);
        redisUtil.del(Constant.USER + userId);
    }
}
