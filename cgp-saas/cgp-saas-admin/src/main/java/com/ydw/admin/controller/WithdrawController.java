package com.ydw.admin.controller;

import com.ydw.admin.model.db.WithdrawRecord;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IWithdrawRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/withdraw")
public class WithdrawController extends BaseController{

    @Autowired
    private IWithdrawRecordService withdrawRecordService;

    /**
     * 获取用户的提现汇总
     * @param userId
     * @return
     */
    @GetMapping("/getUserWithdrawSummary")
    public ResultInfo getUserWithdrawSummary(@RequestParam String userId){
        return withdrawRecordService.getUserWithdrawSummary(userId);
    }

    /**
     * 获取用户的提现记录
     * @param userId
     * @return
     */
    @GetMapping("/getUserWithdrawRecord")
    public ResultInfo getUserWithdrawRecord(@RequestParam String userId,
                                            @RequestParam(required = false)String beginDate,
                                            @RequestParam(required = false)String endDate){
        Date bdate = null,edate=null;
        try {
            if (StringUtils.isNotBlank(beginDate)){
                bdate = new SimpleDateFormat("yyyy-MM-dd").parse(beginDate);
            }
            if (StringUtils.isNotBlank(beginDate)){
                edate = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return ResultInfo.fail("日期输入格式不正确！");
        }
        return withdrawRecordService.getUserWithdrawRecord(buildPage(), userId, bdate, edate);
    }

    /**
     * 审批的提现申请
     * @param withdrawRecord
     * @return
     */
    @PostMapping("/approvalWithdraw")
    public ResultInfo approvalWithdraw(@RequestBody WithdrawRecord withdrawRecord){
        return withdrawRecordService.approvalWithdraw(withdrawRecord);
    }


    /**
     * 获取需要审批记录
     * @return
     */
    @GetMapping("/getWithdrawRecord")
    public ResultInfo getWithdrawRecord(@RequestParam(required = false) String search,
                                       @RequestParam(required = false) String beginDate,
                                       @RequestParam(required = false) String endDate,
                                        @RequestParam(required = false)Integer status){
        Date bdate = null,edate=null;
        try {
            if (StringUtils.isNotBlank(beginDate)){
                bdate = new SimpleDateFormat("yyyy-MM-dd").parse(beginDate);
            }
            if (StringUtils.isNotBlank(beginDate)){
                edate = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return ResultInfo.fail("日期输入格式不正确！");
        }
        return withdrawRecordService.getWithdrawRecord(buildPage(),search,bdate,edate, status);
    }
}
