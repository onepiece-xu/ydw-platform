package com.ydw.platform.controller;


import com.ydw.platform.model.db.TimeLimitedMission;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.ITimeLimitedMissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xulh
 * @since 2021-07-06
 */
@RestController
@RequestMapping("/timeLimitedMission")
public class TimeLimitedMissionController extends BaseController{
   @Autowired
    private ITimeLimitedMissionService iTimeLimitedMissionService;

    @GetMapping("/getList")
    public ResultInfo getList(){
        String userId="";
        if(!StringUtils.isNotBlank(userId)){
            userId = getAccount();
        }
        return  iTimeLimitedMissionService.getList(userId,buildPage());
    }

    /**
     * 用户领取活动
     */
    @PostMapping("/changeStatus")
    public ResultInfo changeStatus(@RequestBody TimeLimitedMission timeLimitedMission){
        String userId="";
        if(!StringUtils.isNotBlank(userId)){
            userId = getAccount();
        }
        return iTimeLimitedMissionService.changeStatus(userId,timeLimitedMission);
    }

}

