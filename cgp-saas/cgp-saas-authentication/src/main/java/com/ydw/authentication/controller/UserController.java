package com.ydw.authentication.controller;

import com.alibaba.fastjson.JSONObject;
import com.ydw.authentication.model.db.User;
import com.ydw.authentication.model.vo.ModifyPasswordVO;
import com.ydw.authentication.service.IUserInfoService;
import com.ydw.authentication.utils.ResultInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/12/2316:13
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    private IUserInfoService userInfoService;


    /**
     * 用户分享->分享中心：累计邀请人数，累计收益，可提现余额，用户分享二维码连接
     * @return
     */
    @GetMapping("/getUserShareInfo")
    public ResultInfo getUserShareInfo(){
        return userInfoService.getUserShareInfo(super.getUserInfo().getId());
    }

    /**
     * 获取用户推荐人
     * @return
     */
    @GetMapping("/getUserRecommender")
    public ResultInfo getUserRecommender(@RequestParam String userId){
        return userInfoService.getUserRecommender(userId);
    }

    /**
     * 用户提现成功
     * @return
     */
    @PostMapping("/userWithdrawSuccess")
    public ResultInfo userWithdrawSuccess(@RequestBody JSONObject param){
        String userId = param.getString("userId");
        BigDecimal amount = param.getBigDecimal("amount");
        return userInfoService.userWithdrawSuccess(userId, amount);
    }

    /**
     * 用户充值奖励
     * @return
     */
    @PostMapping("/userDistributionAward")
    public ResultInfo userDistributionAward(@RequestBody JSONObject param){
        String userId = param.getString("userId");
        BigDecimal amount = param.getBigDecimal("amount");
        return userInfoService.userDistributionAward(userId, amount);
    }

    /**
     * 禁用用户
     * @param
     * @return
     */
    @PostMapping("/disableUser")
    public ResultInfo disableUser(@RequestBody HashMap<String,Object> param){
        int value =0;
        Object type = param.get("type");
        if (type != null){
             value=(Integer)type;
        }
        List<String>ids = (List<String>)param.get("ids");
        return userInfoService.disableUser(ids, value);
    }

    /**
     * 启用用户
     * @param userId
     * @return
     */
    @PostMapping("/enableUser")
    public ResultInfo enableUser(@RequestBody List<String> userId){
        return userInfoService.enableUser(userId);
    }

    /**
     * 修改管理员密码
     * @param user
     * @return
     */
    @PostMapping("/updatePassword")
    public ResultInfo updatePassword(@RequestBody ModifyPasswordVO user){

        return userInfoService.updatePassword(user,super.getToken());
    }
    /**
     * 管理员添加密码
     * @param user
     * @return
     */
    @PostMapping("/addUser")
    public ResultInfo addUser(@RequestBody User user){

        return userInfoService.addUser(user);
    }

    /**
     * 用户注销
     */
    @GetMapping("/cancel")
    public ResultInfo cancel(HttpServletRequest request, @RequestParam(required = false) String userId,@RequestParam(required = false) String code){


        if(!StringUtils.isNotBlank(userId)){
            userId = getAccount();
        }
        return userInfoService.cancel(request,userId,code);
    }
}
