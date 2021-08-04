package com.ydw.recharge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.recharge.dao.CouponCardMapper;
import com.ydw.recharge.model.db.CouponBag;
import com.ydw.recharge.model.db.CouponCard;
import com.ydw.recharge.model.vo.ResultInfo;
import com.ydw.recharge.service.ICouponBagService;
import com.ydw.recharge.service.ICouponCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulh
 * @since 2021-04-22
 */
@Service
public class CouponCardServiceImpl extends ServiceImpl<CouponCardMapper, CouponCard> implements ICouponCardService {

    @Autowired
    private ICouponBagService couponBagService;

    @Override
    public ResultInfo getDrawableCoupon(String userId) {
        QueryWrapper<CouponCard> qw = new QueryWrapper<>();
        LocalDateTime now = LocalDateTime.now();
        qw.lt("valid_begin_time", now);
        qw.gt("valid_end_time", now);
        qw.eq("valid", true);
        List<CouponCard> couponCards = super.list(qw);
        if (couponCards != null && !couponCards.isEmpty()){
            QueryWrapper<CouponBag> qwc = new QueryWrapper<>();
            qwc.eq("user_id", userId);
            List<CouponBag> list = couponBagService.list(qwc);
            Map<String, Integer> couponCountMap = new HashMap<>();
            for (CouponBag couponBag : list){
                Integer count = couponCountMap.get(couponBag.getCouponId());
                if (count == null){
                    count = 1;
                }else{
                    count += 1;
                }
                couponCountMap.put(couponBag.getCouponId(), count);
            }
            Iterator<CouponCard> iterator = couponCards.iterator();
            while (iterator.hasNext()){
                CouponCard couponCard = iterator.next();
                Integer count = couponCountMap.get(couponCard.getId());
                if (count != null && count >= couponCard.getUserDrawableNum()){
                    iterator.remove();
                }
            }
        }
        return ResultInfo.success(couponCards);
    }
}
