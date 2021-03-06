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
                logger.info("??????paas??????token???????????????{}", result);
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
                    return ResultInfo.success("??????paas??????token?????????", token);
                } else {
                    return ResultInfo.fail();
                }
            }
        }
        return ResultInfo.success("??????paas??????token?????????", token);
    }

    @Override
    public ResultInfo checkToken(String token) {
        String account = JwtUtil.getClaim(token, Constant.ACCOUNT);
        String redisToken = (String)(redisUtil.get(Constant.PREFIX_SHIRO_TOKEN + account));
        if(StringUtil.nullOrEmpty(redisToken)){
            ResultInfo info = new ResultInfo();
            info.setCode(401);
            info.setMsg("??????????????????????????????????????????");
            return info;
        }else{
            if (redisToken.equals(token)) {
                return ResultInfo.success("???????????????", account);
            } else {
                ResultInfo info = new ResultInfo();
                info.setCode(401);
                info.setMsg("??????????????????????????????????????????");
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
        //??????????????????token
        String accessToken = user.getAccessToken();
        String openId = user.getWechatOpenId();
        // ??????url
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
                //????????????
                if (userInfo.getStatus() != 0){
                    return ResultInfo.fail("????????????????????????");
                }
                UserVO userVO = new UserVO();
                BeanUtils.copyProperties(userInfo, userVO);
                if (StringUtil.isNotBlank(userInfo.getMobileNumber())){
                    //notice??????
                    notice(userInfo.getId(), 3);
                    String token = JwtUtil.sign(userInfo.getId(),user.getRegistrationId());
                    redisUtil.set(Constant.PREFIX_SHIRO_TOKEN + userInfo.getId(), token, accessTokenExpireTime);
                    redisUtil.set(Constant.USER + userInfo.getId(), JSON.toJSONString(userInfo));
                    userVO.setToken(token);
                    //??????????????????????????????
                    String id = userVO.getId();
                    try {
                        LoginLog loginLog = new LoginLog();
                        loginLog.setLoginTime(new Date());
                        loginLog.setUserId(id);
                        loginLogMapper.insert(loginLog);
                    } catch (Exception e) {
                        logger.error(id+"??????-----------???????????????????????????");
                        e.printStackTrace();
                    }
                }else{
                    userVO.setIsOldUser(false);
                }
                return ResultInfo.success(userVO);
            }
        } else {
            return ResultInfo.fail("????????????,???????????????");
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
            logger.error(userId+"??????-----------?????????????????????????????????");
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
     * ?????????????????????
     *
     * @param phoneNum ?????????????????????????????????
     * @return ??????????????????000000??????????????????
     */
    private String sendMessageCode(String phoneNum, Integer type) {
        String key = null;
        String str = "000000";
        int templateId = 729402;//?????? ID`729402`????????????
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
            //???????????????
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
            // ?????????6?????????????????????
            String strTemp = (int) ((Math.random() * 9 + 1) * 100000) + "";
            redisUtil.set(key + phoneNum, strTemp, 60 * 30);
            Object validateCode = redisUtil.get(key + phoneNum);
            System.out.println(validateCode.toString());
            // ???????????????????????????????????????????????????????????????
            // ?????????????????????????????????????????????????????????{1}???{2}
            // ???????????????????????????????????????
            String[] params = {strTemp, "5"};
            SmsSingleSender ssender = new SmsSingleSender(Constant.appid, Constant.appkey);
            // ????????????????????????????????????????????????????????????????????????
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNum, templateId, params, Constant.smsSign, "", "");
            System.out.println("result = " + result);
            // ?????????????????????????????????
            if (result.result == 0) {
                str = strTemp;
            }
        } catch (HTTPException e1) {
            // HTTP???????????????
            e1.printStackTrace();
        } catch (JSONException e2) {
            // json????????????
            e2.printStackTrace();
        } catch (IOException e3) {
            // ??????IO??????
            e3.printStackTrace();
        }
        return str;
    }

    @Override
    public ResultInfo addUserByPhone(RegistUserModel user) {
        UserVO userVO = new UserVO();
        // token
        String token = null;
        // ??????????????????
        String mobileNumber = user.getMobileNumber();
        //??????????????????
        UserInfo userInfo = userInfoService.getUserByMobileNumber(mobileNumber);
        // ????????????????????????
        if (null != userInfo) {
            if (userInfo.getStatus() == 1){
                return ResultInfo.fail("????????????????????????");
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
                //?????????????????????
                QueryWrapper<UserInfo> qw = new QueryWrapper<>();
                qw.eq("share_code",user.getShareCode());
                UserInfo recommender = userInfoMapper.selectOne(qw);
                if (recommender != null){
                    //??????????????????
                    UserRelational ur = new UserRelational();
                    ur.setId(SequenceGenerator.sequence());
                    ur.setCreateTime(new Date());
                    ur.setInferior(userId);
                    ur.setRecommender(recommender.getId());
                    userRelationalService.save(ur);
                    //??????????????????????????????
                    HashMap<String,String> map = new HashMap<>();
                    map.put("recommender",recommender.getId());
                    map.put("inferior",userId);
                    ydwPlatformService.pullNewAward(map);
                }
            }
            userInfoMapper.insert(userInfo);
            ydwPlatformService.registerAward(userId);
            // ??????
            token = JwtUtil.sign(userId, user.getRegistrationId());
            //notice?????????????????????
            notice(userId, 3);
            redisUtil.set(Constant.PREFIX_SHIRO_TOKEN + userId, token, accessTokenExpireTime);
            redisUtil.set(Constant.USER + userId, JSON.toJSONString(userInfo));
            BeanUtils.copyProperties(userInfo, userVO);
            userVO.setToken(token);
        }
        bind(token, user.getRegistrationId());

        //??????????????????????????????
        String id = userVO.getId();
        try {
            LoginLog loginLog = new LoginLog();
            loginLog.setLoginTime(new Date());
            loginLog.setUserId(id);
            loginLogMapper.insert(loginLog);
        } catch (Exception e) {
            logger.error(id+"??????-----------???????????????????????????");
            e.printStackTrace();
        }
        return ResultInfo.success(userVO);
    }

    /**
     * ?????????
     *
     * @param user
     * @return
     */
    @Override
    public ResultInfo preregistByPhone(RegistUserModel user) {
        // ??????????????????
        String mobileNumber = user.getMobileNumber();

        //??????????????????
        UserInfo userInfo = userInfoService.getUserByMobileNumber(mobileNumber);

        // ????????????????????????
        if (null != userInfo) {
            return ResultInfo.fail("????????????????????????");
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
                //?????????????????????
                QueryWrapper<UserInfo> qw = new QueryWrapper<>();
                qw.eq("share_code",user.getShareCode());
                UserInfo recommender = userInfoMapper.selectOne(qw);
                if (recommender != null){
                    //??????????????????
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
            return ResultInfo.fail("????????????????????????????????????");
        }
        try {
            QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
            wrapper.eq("mobile_number",mobilePhone);
            UserInfo userInfo = userInfoMapper.selectOne(wrapper);
            if(null!=userInfo){
                Integer status = userInfo.getStatus();
                if(status==3){
                    return ResultInfo.fail("????????????????????????????????????????????????");
                }
            }
        } catch (Exception e) {
            logger.error("???????????????????????????");
            e.printStackTrace();
        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo addUserByQq(String body, String unionId) {
        //??????QQ??????
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
                //????????????
                if (userInfo.getStatus() != 0){
                    return ResultInfo.fail("???????????????????????????????????????");
                }
                UserVO userVO = new UserVO();
                BeanUtils.copyProperties(userInfo, userVO);
                if (StringUtil.isNotBlank(userInfo.getMobileNumber())){
                    //notice??????
                    notice(userInfo.getId(), 3);
                    String token = JwtUtil.sign(userInfo.getId());
                    redisUtil.set(Constant.PREFIX_SHIRO_TOKEN + userInfo.getId(), token, accessTokenExpireTime);
                    redisUtil.set(Constant.USER + userInfo.getId(), JSON.toJSONString(userInfo));
                    userVO.setToken(token);

                    //??????????????????????????????
                    String id = userVO.getId();
                    try {
                        LoginLog loginLog = new LoginLog();
                        loginLog.setLoginTime(new Date());
                        loginLog.setUserId(id);
                        loginLogMapper.insert(loginLog);
                    } catch (Exception e) {
                        logger.error(id+"??????-----------???????????????????????????");
                        e.printStackTrace();
                    }
                }else{
                    userVO.setIsOldUser(false);
                }
                return ResultInfo.success(userVO);
            }
        } else {
            return ResultInfo.fail("????????????,???????????????");
        }
    }

    @Override
    @Transactional
    public ResultInfo wechatbind(RegistUserModel user) {
        //???????????????
        String mobileNumber = user.getMobileNumber();
        Object validateCode = redisUtil.get(Constant.PREFIX_WXREGISTCODE_SESSION + mobileNumber);
        if (null == validateCode) {
            return ResultInfo.fail(SystemConstants.CodeExpire);
        }
        // ?????????
        String code = user.getMessageCode();
        if (!code.equals(validateCode)) {
            return ResultInfo.fail(SystemConstants.CodeError);
        }
        // ??????openId
        String wechatOpenId = user.getWechatOpenId();
        if (wechatOpenId == null) {
            return ResultInfo.fail("?????????????????????!");
        }
        //????????????????????????????????????????????????????????????????????????????????????
        UserInfo info = userInfoMapper.selectByWXOpenId(wechatOpenId);
        if (info == null || StringUtils.isNotBlank(info.getMobileNumber())){
            return ResultInfo.fail("?????????????????????????????????????????????????????????");
        }else{
            if (info.getStatus() == 1){
                return ResultInfo.fail("????????????????????????");
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
                //?????????????????????????????????????????????
                userInfoMapper.updateById(mobileNumberUser);
                //?????????????????????
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
            // ??????
            String token = JwtUtil.sign(id, user.getRegistrationId());
            redisUtil.set(Constant.PREFIX_SHIRO_TOKEN + id, token, accessTokenExpireTime);
            redisUtil.set(Constant.USER + id, JSON.toJSONString(userVO));
            userVO.setToken(token);
            //notice?????????????????????
            notice(id, 3);
            bind(token, user.getRegistrationId());

            //??????????????????????????????
            try {
                LoginLog loginLog = new LoginLog();
                loginLog.setLoginTime(new Date());
                loginLog.setUserId(id);
                loginLogMapper.insert(loginLog);
            } catch (Exception e) {
                logger.error(id+"??????-----------???????????????????????????");
                e.printStackTrace();
            }

            return ResultInfo.success(userVO);
        }
    }

    @Override
    public ResultInfo qqBind(RegistUserModel user) {
        // ??????????????????
        String mobileNumber = user.getMobileNumber();
        Object validateCode = redisUtil.get(Constant.PREFIX_QQREGISTCODE_SESSION + mobileNumber);
        if (null == validateCode) {
            return ResultInfo.fail(SystemConstants.CodeExpire);
        }
        // ?????????
        String code = user.getMessageCode();
        if (!code.equals(validateCode)) {
            return ResultInfo.fail(SystemConstants.CodeError);
        }
        String qqOpenId = user.getQqOpenId();
        if (qqOpenId == null) {
            return ResultInfo.fail("?????????QQ??????!");
        }
        //????????????????????????????????????????????????????????????????????????????????????
        UserInfo info = userInfoMapper.selectByQQOpenId(qqOpenId);
        if (info == null || StringUtils.isNotBlank(info.getMobileNumber())){
            return ResultInfo.fail("???QQ????????????????????????????????????????????????");
        }else {
            if (info.getStatus() == 1){
                return ResultInfo.fail("????????????????????????");
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
                //?????????????????????????????????????????????
                userInfoMapper.updateById(mobileNumberUser);
                //???????????????
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
                    //  ??????
                    ydwPlatformService.registerAward(info.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String id = userVO.getId();
            // ??????
            String token = JwtUtil.sign(id, user.getRegistrationId());
            redisUtil.set(Constant.PREFIX_SHIRO_TOKEN + id, token, accessTokenExpireTime);
            redisUtil.set(Constant.USER + id, JSON.toJSONString(userVO));
            userVO.setToken(token);
            //notice?????????????????????
            notice(id, 3);
            bind(token, user.getRegistrationId());

            //??????????????????????????????

            try {
                LoginLog loginLog = new LoginLog();
                loginLog.setLoginTime(new Date());
                loginLog.setUserId(id);
                loginLogMapper.insert(loginLog);
            } catch (Exception e) {
                logger.error(id+"??????-----------???????????????????????????");
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
            //??????token????????????????????????
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String nowStr = dateFormat.format(System.currentTimeMillis());
//            logger.info(nowStr+"---------nowStr----");
            long t = Long.parseLong(time);
//            logger.info(t+"---------long  time----");
            String oldStr = dateFormat.format(t);
//            logger.info(oldStr+"---------oldStr----");
            if(!nowStr.equals(oldStr)){
                //??????????????????????????????
                    LoginLog loginLog = new LoginLog();
                    loginLog.setLoginTime(new Date());
                    loginLog.setUserId(account);
                    loginLogMapper.insert(loginLog);
                logger.info(account+"??????--"+nowStr+"???------"+oldStr+"-----------??????????????????????????????");
            }
        } catch (Exception e) {
            logger.error(account+"??????-----------???????????????????????????");
            e.printStackTrace();
        }
        return ResultInfo.success(refreshTime);
    }

    @Override
    public ResultInfo indexAndroid(String access_token, String openId, String registrationId) {
        //Step?????????QQ????????????
        String url = "https://graph.qq.com/user/get_user_info?access_token=" + access_token +
                "&oauth_consumer_key=" + QQHttpClient.AndroidAPPID +
                "&openid=" + openId;
        JSONObject jsonObject = null;
        try {
            //?????????????????????
            jsonObject = QQHttpClient.getUserInfo(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String nickname = jsonObject.getString("nickname");
        String img = jsonObject.getString("figureurl_qq_2");
        //???qq?????????unionid
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


        //???qq?????????identityType=?????????????????????
        UserInfo byIdentifier = userInfoMapper.selectByQQOpenId(unionId);

        //???????????????(???????????????????????????????????????)
        if (null == byIdentifier) {
            UserInfo tbUserInfo = new UserInfo();
            // ???info???
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
                //  ???????????????????????????
                String mobilePhone = byIdentifier.getMobileNumber();
                QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
                wrapper.eq("mobile_number",mobilePhone);
                UserInfo userInfo = userInfoMapper.selectOne(wrapper);
                Integer status = userInfo.getStatus();
                if(status==3){
                    return ResultInfo.fail("????????????????????????????????????????????????");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //????????????????????????????????????

            if (!StringUtil.isNotBlank(byIdentifier.getMobileNumber())){
                UserVO userVO = new UserVO();
                BeanUtils.copyProperties(byIdentifier, userVO);
                userVO.setIsOldUser(false);
                return ResultInfo.success(userVO);
            }

            //??????????????????
            String token = "";
            String id = byIdentifier.getId();
            UserInfo selectByInfoId = userInfoMapper.selectByInfoId(id);
            if (selectByInfoId.getStatus() == 1){
                return ResultInfo.fail("????????????????????????");
            }
            //????????????
            //notice?????????????????????
            notice(id, 3);
            token = JwtUtil.sign(id, registrationId);
            redisUtil.set(Constant.PREFIX_SHIRO_TOKEN + id, token, accessTokenExpireTime);
            redisUtil.set(Constant.USER + id, JSON.toJSONString(selectByInfoId));

            //?????????
            selectByInfoId.setNickname(nickname);
            selectByInfoId.setAvatar(img);
            userInfoMapper.updateById(selectByInfoId);
            //????????????
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(selectByInfoId, userVO);
            userVO.setToken(token);

            //??????????????????????????????
            try {
                LoginLog loginLog = new LoginLog();
                loginLog.setLoginTime(new Date());
                loginLog.setUserId(id);
                loginLogMapper.insert(loginLog);
            } catch (Exception e) {
                logger.error(id+"??????-----------???????????????????????????");
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
        //????????????
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
            return ResultInfo.success("???????????????", token);
        }
        return ResultInfo.fail("??????????????? ");

    }

    @Override
    public ResultInfo logoutUser(String token) {
        String account = JwtUtil.getClaim(token, Constant.ACCOUNT);
        redisUtil.del(Constant.PREFIX_SHIRO_TOKEN + account);
        return ResultInfo.success();
    }

    /**
     * ?????????????????????
     * ??????????????????????????? ??????????????????????????????
     * status 1???????????? 2???????????? 3??????????????????
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
     * ?????????????????????id??????
     * @param token
     * @param registrationId
     */
    public void bind(String token, String registrationId) {
        //????????????
        if (StringUtils.isNotBlank(token) && StringUtils.isNotBlank(registrationId)) {
            //????????????
            HashMap<String, String> params = new HashMap<>();
            params.put("token", token);
            params.put("registrationId", registrationId);
            iYdwMessageService.bind(params);
        }
    }

    /**
     * ?????????????????????id??????
     * @param token
     * @param registrationId
     */
    public void unbind(String token, String registrationId) {
        //????????????
        if (StringUtils.isNotBlank(token) && StringUtils.isNotBlank(registrationId)) {
            //????????????
            HashMap<String, String> params = new HashMap<>();
            params.put("token", token);
            params.put("registrationId", registrationId);
            iYdwMessageService.unbind(params);
        }
    }

    /**
     * ??????????????????token
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
     * ????????????
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
                info.setMsg("??????????????????????????????????????????");
                return info;
            }
            String account = JwtUtil.getClaim(token, Constant.ACCOUNT);
            String redisToken = (String)(redisUtil.get(Constant.PREFIX_SHIRO_TOKEN + account));
            if(!token.equals(redisToken)){
                ResultInfo info = new ResultInfo();
                info.setCode(401);
                info.setMsg("??????????????????????????????????????????");
                return info;
            }
            UserInfo userInfo = userInfoMapper.selectById(account);
            //??????????????????????????????
            try {
                LoginLog loginLog = new LoginLog();
                loginLog.setLoginTime(new Date());
                loginLog.setUserId(account);
                loginLogMapper.insert(loginLog);
            } catch (Exception e) {
                logger.error(account+"??????-----------?????????????????????????????????");
                e.printStackTrace();
            }
            return ResultInfo.success(userInfo);
        }catch (Exception e){
            ResultInfo info = new ResultInfo();
            info.setCode(401);
            info.setMsg("??????????????????????????????????????????");
            return info;
        }
    }

    /**
     * ??????????????????
     *
     * @param userId
     */
    @Override
    public void disableLogin(String userId) {
        redisUtil.del(Constant.PREFIX_SHIRO_TOKEN + userId);
        redisUtil.del(Constant.USER + userId);
    }
}
