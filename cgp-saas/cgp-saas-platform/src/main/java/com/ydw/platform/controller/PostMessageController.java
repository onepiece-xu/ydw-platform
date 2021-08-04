package com.ydw.platform.controller;


import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.IPostMessageService;
import com.ydw.platform.service.ITemplateTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hea
 * @since 2020-10-08
 */
@RestController
@RequestMapping("/postMessage")
public class PostMessageController extends  BaseController {
    @Autowired
    private IPostMessageService iPostMessageService;

    @Autowired
    private ITemplateTypeService iTemplateTypeService;

    @GetMapping("/getMessages")
    public ResultInfo getMessages(@RequestParam(required = false) String userId,@RequestParam(required = false) Integer type){
        if(!StringUtils.isNotBlank(userId)){
            userId = getAccount();
        }
        return  iPostMessageService.getMessages(userId,type,buildPage());
    }
    @GetMapping("/info")
    public ResultInfo info(@RequestParam String id){
        return  iPostMessageService.info(id);
    }

    /**
     * userId
     * templateId
     * @param map
     * @return
     */
    @PostMapping("/add")
    public ResultInfo add(@RequestBody HashMap<String ,Object >map){
        return iPostMessageService.add(map);
    }

    /**
     * 获取用户未读的用户的消息数
     */
    @GetMapping("/getUnreadCountByUserId")
    public ResultInfo getUnreadCount(@RequestParam(required = false) String userId){
        if(!StringUtils.isNotBlank(userId)){
            userId = getAccount();
        }
        return  iPostMessageService.getUnreadCountByUserId(userId);
    }
    /**
     * 一键已读
     */
    @GetMapping("/allRead")
    public ResultInfo allRead(@RequestParam(required = false) String userId,@RequestParam(required = false) Integer type){
        if(!StringUtils.isNotBlank(userId)){
            userId = getAccount();
        }
        return  iPostMessageService.allRead(userId,type);
    }
}

