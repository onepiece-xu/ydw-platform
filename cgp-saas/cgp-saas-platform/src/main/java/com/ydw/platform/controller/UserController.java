package com.ydw.platform.controller;

import com.ydw.platform.model.vo.ResultInfo;

import com.ydw.platform.service.IUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/userInfo")
public class UserController extends BaseController{
    @Autowired
    private IUserInfoService iUserInfoService;

    /**
     * 查询用户的上下机器记录
     */
    @GetMapping("/getUsageRecord")
    public ResultInfo getUsageRecord(@RequestParam(required = false) String userId){
        if(!StringUtils.isNotBlank(userId)){
            userId = getAccount();
        }
        return  iUserInfoService.getUsageRecord(userId,buildPage());
    }
    /**
     * 上传用户日志
     */
    @PostMapping("/sendLog")
    public ResultInfo sendLog(HttpServletRequest request,@RequestParam(required = false) String userId){
        if(!StringUtils.isNotBlank(userId)){
            userId = getAccount();
        }
        return iUserInfoService.sendLog(request,userId);
    }
}
