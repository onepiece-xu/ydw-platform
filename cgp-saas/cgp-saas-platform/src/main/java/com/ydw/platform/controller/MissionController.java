package com.ydw.platform.controller;


import com.ydw.platform.model.db.Mission;
import com.ydw.platform.model.vo.MissionVO;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.IMissionService;
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
 * @since 2021-06-01
 */
@Controller
@RequestMapping("/mission")
public class MissionController extends BaseController {
    @Autowired
    private IMissionService iMissionService;

    @GetMapping("/getMissionList")
    @ResponseBody
    public ResultInfo getMissionList(){
        String userId="";
        if(!StringUtils.isNotBlank(userId)){
            userId = getAccount();
        }
        return  iMissionService.getMissionList(userId,buildPage());
    }

    @PostMapping("/changeStatus")
    @ResponseBody
    public ResultInfo changeStatus(@RequestBody MissionVO mission){
        String userId="";
        if(!StringUtils.isNotBlank(userId)){
            userId = getAccount();
        }
        return  iMissionService.changeStatus(userId,mission);
    }
}

