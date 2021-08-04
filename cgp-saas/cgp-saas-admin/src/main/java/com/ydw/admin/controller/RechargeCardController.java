package com.ydw.admin.controller;


import com.ydw.admin.model.db.RechargeCard;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IRechargeCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 充值卡 前端控制器
 * </p>
 *
 * @author xulh
 * @since 2020-10-07
 */

@RestController
@RequestMapping("/rechargeCard")
public class RechargeCardController extends BaseController {

    @Autowired
    private IRechargeCardService rechargeCardService;

    @GetMapping("/getRechargeCards")
    public ResultInfo getRechargeCards(){
        return rechargeCardService.getRechargeCards();
    }

    @GetMapping("/getRechargeCardsByType")
    public ResultInfo getRechargeCardsByType(@RequestParam int type){
        return rechargeCardService.getRechargeCardsByType(type);
    }

    /**
     * 新增充值卡
     */
    @PostMapping("/addCard")
    public ResultInfo   addCard(@RequestBody RechargeCard rechargeCard){
        return  rechargeCardService.addCard(rechargeCard);
    }

    /**
     * 编辑充值卡
     */
    @PostMapping("/updateCard")
    public ResultInfo   updateCard(@RequestBody RechargeCard rechargeCard){
        return  rechargeCardService.updateCard(rechargeCard);
    }

    /**
     * 上架/下架充值卡
     */
    @PostMapping("/available")
    public ResultInfo   available(@RequestBody String  body){
        return  rechargeCardService.available(body);
    }


    /**
     * 充值卡列表
     */
    @GetMapping("/cardList")
    public ResultInfo   cardList(@RequestParam(required = false) String search){
        return  rechargeCardService.cardList(search,buildPage());
    }

    /**
     * 充值卡列表
     */
    @GetMapping("/cardInfo")
    public ResultInfo   cardInfo(@RequestParam String id){
        return  rechargeCardService.cardInfo(id);
    }

    /**
     * 查询赠送的补偿卡
     */
    @GetMapping("/getSendCardList")
    public ResultInfo  getSendCardList(){
        return  rechargeCardService.getSendCardList();
    }
}

