package com.ydw.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.platform.dao.DistributionAwardMapper;
import com.ydw.platform.model.db.DistributionAward;
import com.ydw.platform.model.db.DistributionAwardRule;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.IDistributionAwardRuleService;
import com.ydw.platform.service.IDistributionAwardService;
import com.ydw.platform.service.IYdwRechargeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * <p>
 * 分销奖励表 服务实现类
 * </p>
 *
 * @author xulh
 * @since 2020-12-21
 */
@Service
public class DistributionAwardServiceImpl extends ServiceImpl<DistributionAwardMapper, DistributionAward> implements IDistributionAwardService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IDistributionAwardRuleService distributionAwardRuleService;

    @Autowired
    private IYdwRechargeService ydwRechargeService;

    @Override
    public ResultInfo pullNewAward(String recommender, String inferior) {
        DistributionAwardRule distributionAwardRule = distributionAwardRuleService.getPullNewRule();
        if (StringUtils.isNotBlank(distributionAwardRule.getCardId())){
            ResultInfo cardBagNum = ydwRechargeService.getCardBagNum(recommender, distributionAwardRule.getCardId());
            if ((int)cardBagNum.getData() < distributionAwardRule.getLimitNum()){
                String[] cardIds = distributionAwardRule.getCardId().split(",");
                for (String cardId : cardIds){
                    HashMap<String,String> params = new HashMap<>();
                    params.put("userId", recommender);
                    params.put("cardId",cardId);
                    ydwRechargeService.addCardBag(params);
                }
            }
        }else{

        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo registerAward(String userId) {
        DistributionAwardRule distributionAwardRule = distributionAwardRuleService.getRegisterAwardRule();
        if (StringUtils.isNotBlank(distributionAwardRule.getCardId())){
            String[] cardIds = distributionAwardRule.getCardId().split(",");
            for (String cardId : cardIds){
                HashMap<String,String> params = new HashMap<>();
                params.put("userId", userId);
                params.put("cardId",cardId);
                ydwRechargeService.addCardBag(params);
            }
        }else{

        }
        return null;
    }
}
