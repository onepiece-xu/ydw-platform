package com.ydw.admin.controller;

import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IDistributionAwardService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/12/2413:49
 */
@RestController
@RequestMapping("/distributionAward")
public class DistributionAwardController extends BaseController{

    @Autowired
    private IDistributionAwardService distributionAwardService;

    /**
     * 用户收益汇总
     * @param userId
     * @return
     */
    @GetMapping("/getUserDistributionAwardSummary")
    public ResultInfo getUserDistributionAwardSummary(@RequestParam String userId){
        return distributionAwardService.getUserDistributionAwardSummary(userId);
    }

    /**
     * 用户收益列表
     * @param userId
     * @param beginDate
     * @param endDate
     * @param search
     * @return
     */
    @GetMapping("/getUserDistributionAward")
    public ResultInfo getUserDistributionAward(@RequestParam String userId,
                                               @RequestParam(required = false) String beginDate,
                                               @RequestParam(required = false) String endDate,
                                               @RequestParam(required = false) String search){
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
        return distributionAwardService.getUserDistributionAward(super.buildPage(),userId,bdate,edate,search);
    }
}
