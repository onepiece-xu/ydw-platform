package com.ydw.admin.controller;


import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IMissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 日常任务：清理每个用户的任务中心任务状态
     */
    @GetMapping("/clearMission")
    public ResultInfo clearMission() {
        return iMissionService.clearMission();
    }
}



