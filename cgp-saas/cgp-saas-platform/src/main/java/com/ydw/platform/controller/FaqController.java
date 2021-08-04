package com.ydw.platform.controller;


import com.ydw.platform.model.db.Faq;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.IFaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hea
 * @since 2020-10-06
 */

@RestController
@RequestMapping("/faq")
public class FaqController  extends  BaseController{
    @Autowired
    private IFaqService iFaqService;

    @GetMapping(value = "/getFaqList")
    public ResultInfo getFaqList(){
        return  iFaqService.getFaqList(buildPage());
    }
    @PostMapping(value = "addFaq")
    public ResultInfo addFaq(@RequestBody Faq faq){
        return  iFaqService.addFaq(faq);
    }

    @PostMapping(value = "/updateFaq")
    public ResultInfo updateFaq(@RequestBody Faq faq){
        return iFaqService.updateFaq(faq);
    }

    @PostMapping(value = "/deleteFaqs")
    public ResultInfo deleteFaqs(@RequestBody List<Integer> ids){
        return iFaqService.deleteFaqs(ids);
    }

    @GetMapping(value = "/getFaq")
    public ResultInfo getFaq(@RequestParam Integer id){
        return  iFaqService.getFaq(id);
    }

}

