package com.ydw.user.controller;


import com.ydw.user.service.ITbAppPicturesService;
import com.ydw.user.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author heao
 * @since 2020-12-28
 */

@Controller
@RequestMapping("/tbAppPictures")
public class TbAppPicturesController {
    @Autowired
    private ITbAppPicturesService iTbAppPicturesService;

    /**
     *app 图片上传
     */
    @PostMapping(value = "/upload")
    @ResponseBody
    public ResultInfo upload(HttpServletRequest request, @RequestParam String appId){
        return iTbAppPicturesService.upload(request,appId);
    }

}

