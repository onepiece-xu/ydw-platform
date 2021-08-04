package com.ydw.authentication.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.authentication.dao.UserInfoMapper;
import com.ydw.authentication.dao.UserMapper;
import com.ydw.authentication.model.constant.Constant;
import com.ydw.authentication.model.db.User;
import com.ydw.authentication.model.db.UserInfo;
import com.ydw.authentication.model.vo.ModifyPasswordVO;
import com.ydw.authentication.model.vo.UserShareInfoVO;
import com.ydw.authentication.service.ILoginService;
import com.ydw.authentication.service.IUserInfoService;
import com.ydw.authentication.service.IYdwPlatformService;
import com.ydw.authentication.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户关系树 服务实现类
 * </p>
 *
 * @author xulh
 * @since 2020-12-21
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ILoginService loginService;

    @Autowired
    private IYdwPlatformService ydwPlatformService;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${share.shareLink}")
    private String shareLink;

    @Override
    public ResultInfo getUserShareInfo(String userId) {
        UserShareInfoVO vo = userInfoMapper.getUserShareInfo(userId);
        vo.setShareLink(shareLink + "?shareCode=" + vo.getShareCode());
        return ResultInfo.success(vo);
    }

    @Override
    public ResultInfo userWithdrawSuccess(String userId, BigDecimal amount) {
        UserInfo userInfo = userInfoMapper.selectById(userId);
        userInfo.setBalance(userInfo.getBalance().subtract(amount));
        userInfoMapper.updateById(userInfo);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo userDistributionAward(String userId, BigDecimal amount) {
        UpdateWrapper<UserInfo> uw = new UpdateWrapper<>();
        uw.eq("id", userId);
        uw.setSql("profit = profit + " + amount + " , balance = balance + " + amount);
        update(uw);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo getUserRecommender(String userId) {
        UserInfo userRecommender = userInfoMapper.getUserRecommender(userId);
        return ResultInfo.success("ok", userRecommender);
    }

    @Override
    public ResultInfo disableUser(List<String> userId, Integer type) {
        for(String id: userId){
            UpdateWrapper<UserInfo> uw = new UpdateWrapper<>();
            uw.eq("id", id);
            uw.set("status", 1);
            update(uw);
            loginService.disableLogin(id);
            if (type == 0){
                ydwPlatformService.releaseUserRes(id);
            }
        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo enableUser(List<String> userId) {
        for(String id: userId){
            UpdateWrapper<UserInfo> uw = new UpdateWrapper<>();
            uw.eq("id", id);
            uw.set("status", 0);
            update(uw);
        }
        return ResultInfo.success();
    }

    @Override
    public UserInfo getUserByMobileNumber(String mobileNumber){
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile_number", mobileNumber);
        return getOne(queryWrapper);
    }

    @Override
    public ResultInfo updatePassword(ModifyPasswordVO user,String token) {

        String account = JwtUtil.getClaim(token, Constant.ACCOUNT);
        String userNewPassword = user.getNewPassword();
        String oldPassword = user.getOldPassword();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("loginname",account);
        User one = userMapper.selectOne(wrapper);
        String oldOnePassword = one.getPassword();
        String newPassword = PasswordUtil.md5(userNewPassword);
        if(null!=one){
           if(PasswordUtil.md5(oldPassword).equals(oldOnePassword)){
               one.setPassword(newPassword);
               userMapper.updateById(one);
           }else {
               return ResultInfo.fail("修改密码失败！");
           }
        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo addUser(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        String loginname = user.getLoginname();
        if(!StringUtils.isNotEmpty(loginname)){
            return ResultInfo.fail("登录名不可为空！");
        }
        wrapper.eq("loginname",loginname);
        User one = userMapper.selectOne(wrapper);
        if(null!=one){
            return ResultInfo.fail("账户名已存在！");
        }

        wrapper.eq("loginname",user.getLoginname());
        String password = user.getPassword();
        if(!StringUtils.isNotEmpty(password)){
            ResultInfo.fail("密码不可为空！");
        }
        user.setPassword(PasswordUtil.md5(password));
        user.setId(SequenceGenerator.sequence());
        user.setRegisterTime(new Date());
        userMapper.insert(user);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo cancel(HttpServletRequest request, String userId,String code) {


//        userId="6724944814833784233";
        UserInfo user = userInfoMapper.selectById(userId);
        //验证码验证
        String mobileNumber = user.getMobileNumber();
        Object validateCode = redisUtil.get(Constant.CANCEL + mobileNumber);
        if (null == validateCode) {
            return ResultInfo.fail(SystemConstants.CodeExpire);
        }
        // 验证码
        if (!code.equals(validateCode)) {
            return ResultInfo.fail(SystemConstants.CodeError);
        }
        //注销设置为删除状态3
        user.setStatus(3);
        userInfoMapper.updateById(user);
        return ResultInfo.success();

    }
}
