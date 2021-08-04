package com.ydw.admin.controller;


import com.ydw.admin.model.vo.FaqUploadVO;
import com.ydw.admin.service.IFaqService;
import com.ydw.admin.model.db.Faq;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IFaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
public class FaqController extends  BaseController{
    @Autowired
    private IFaqService iFaqService;

    @GetMapping(value = "/getFaqList")
    public ResultInfo getFaqList(@RequestParam(required = false)String search ){
        return  iFaqService.getFaqList(search,buildPage());
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

    /**
     * Faq 富文本内容编辑 图片文件上传
     */
    /**
     * 应用上传图片
     * @param request
     * @param
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value="/upload")
    public FaqUploadVO upload(HttpServletRequest request) throws Exception {
        return iFaqService.upload(request);
    }
}

