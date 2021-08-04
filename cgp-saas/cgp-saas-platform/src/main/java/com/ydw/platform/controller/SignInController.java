package com.ydw.platform.controller;


import com.ydw.platform.model.db.SignIn;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.ISignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 用户签到表 前端控制器
 * </p>
 *
 * @author hea
 * @since 2020-10-06
 */

@Controller
@RequestMapping("/signIn")
public class SignInController extends  BaseController{

    @Autowired
    private ISignInService iSignInService;

    @PostMapping("/signIn")
    public ResultInfo signIn(@RequestBody SignIn sign){
        return  iSignInService.signIn(sign);
    }
    @PostMapping("/signInList")
    public ResultInfo signInList(@RequestBody SignIn sign){
        return  iSignInService.signInList(sign);
    }


}

