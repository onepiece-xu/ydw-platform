package com.ydw.platform.controller;

import com.ydw.platform.dao.AppMapper;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.IAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 查询最近玩过游戏
 */
@RestController
@RequestMapping("/record")
public class RecordController  extends  BaseController{

    @Autowired
    private IAppService iAppService;

    @GetMapping("/getRecordPlayList")
    public ResultInfo getRecordPlayList(@RequestParam String userId){
        return  iAppService.getRecordPlayList(userId);
    }

    @GetMapping("/getRecordPlayListAndroid")
    public ResultInfo getRecordPlayListAndroid(@RequestParam String userId){
        return  iAppService.getRecordPlayListAndroid(userId);
    }

    @GetMapping("/new/getPlayList")
    public ResultInfo getPlayList(@RequestParam String userId){
        return  iAppService.getPlayList(userId);
    }
}
