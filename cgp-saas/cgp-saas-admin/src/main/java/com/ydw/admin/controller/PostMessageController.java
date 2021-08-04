package com.ydw.admin.controller;



import com.ydw.admin.model.db.PostMessage;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IPostMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getMessages")
    public ResultInfo getMessages(@RequestParam String userId){
        return  iPostMessageService.getMessages(userId,buildPage());
    }
    @GetMapping("/info")
    public ResultInfo info(@RequestParam String id){
        return  iPostMessageService.info(id);
    }

    /**
     * userId
     * templateId
     * @param
     * @return
     */
    @PostMapping("/add")
    public ResultInfo add(@RequestBody PostMessage postMessage){
        return iPostMessageService.add(postMessage);
    }

    /**
     * userId
     * templateId 给特定用户的补偿发站内信
     * @param
     * @return
     */
    @PostMapping("/addALL")
    public ResultInfo addALL(@RequestBody PostMessage postMessage){
        return iPostMessageService.addALL(postMessage);
    }

}

