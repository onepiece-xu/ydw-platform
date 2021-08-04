package com.ydw.platform.controller;


import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.ITemplateTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hea
 * @since 2020-11-04
 */
@RestController
@RequestMapping("/templateType")
public class TemplateTypeController {
    @Autowired
    private ITemplateTypeService iTemplateTypeService;

    @GetMapping("/getTemplateId")
    @ResponseBody
    public ResultInfo getTemplateId(@RequestParam String term ){
        return  iTemplateTypeService.getTemplateId(term);
    }
}

