package com.ydw.admin.controller;


import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IPictureTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xulh
 * @since 2021-02-19
 */
@Controller
@RequestMapping("/pictureType")
public class PictureTypeController extends BaseController {
    @Autowired
    private IPictureTypeService iPictureTypeService;

    @GetMapping("/getTypeList")
    public ResultInfo getTypeList(){
        return  iPictureTypeService.getList();
    }
}

