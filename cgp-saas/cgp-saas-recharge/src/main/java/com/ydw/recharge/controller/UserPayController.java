package com.ydw.recharge.controller;


import com.alibaba.fastjson.JSONObject;
import com.ydw.recharge.model.vo.ResultInfo;
import com.ydw.recharge.model.vo.UserPayVO;
import com.ydw.recharge.service.IUserPayService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户支付绑定 前端控制器
 * </p>
 *
 * @author xulh
 * @since 2020-12-21
 */
@RestController
@RequestMapping("/userPay")
public class UserPayController extends BaseController{

    @Autowired
    private IUserPayService userPayService;

    /**
     * 绑定用户支付账号
     * @param userPayVO
     * @return
     */
    @PostMapping("/bindUserPay")
    public ResultInfo bindUserPay(@RequestBody UserPayVO userPayVO){
        if (StringUtils.isBlank(userPayVO.getPayAccount()) ||
                StringUtils.isBlank(userPayVO.getPayeeName()) ||
                StringUtils.isBlank(userPayVO.getValidateCode())){
            return ResultInfo.fail("参数不正确");
        }
        userPayVO.setPayee(super.getAccount());
        return userPayService.bindUserPay(userPayVO);
    }

    /**
     * 绑定用户支付账号
     * @param userPayVO
     * @return
     */
    @PostMapping("/updateBindUserPay")
    public ResultInfo updateBindUserPay(@RequestBody UserPayVO userPayVO){
        if (StringUtils.isBlank(userPayVO.getPayAccount()) ||
                StringUtils.isBlank(userPayVO.getPayeeName()) ||
                StringUtils.isBlank(userPayVO.getValidateCode())){
            return ResultInfo.fail("参数不正确");
        }
        userPayVO.setPayee(super.getAccount());
        return userPayService.updateBindUserPay(userPayVO);
    }

    /**
     * 绑定支付信息获取验证码
     * @param type 1绑定 2修改
     * @return
     */
    @GetMapping("/getBindValidateCode")
    public ResultInfo getBindValidateCode(@RequestParam int type){
        JSONObject userInfo = super.getUserInfo();
        String mobileNumber = userInfo.getString("mobileNumber");
        if (type == 1){
            return userPayService.getBindValidateCode(mobileNumber);
        }else{
            return userPayService.getUpdateBindValidateCode(mobileNumber);
        }

    }

    /**
     * 获取用户支付账号
     * @param userId
     * @return
     */
    @GetMapping("/getUserPay")
    public ResultInfo getUserPay(@RequestParam(required = false) String userId){
        if (StringUtils.isBlank(userId)){
            userId = super.getAccount();
        }
        return userPayService.getUserPay(userId);
    }
}

