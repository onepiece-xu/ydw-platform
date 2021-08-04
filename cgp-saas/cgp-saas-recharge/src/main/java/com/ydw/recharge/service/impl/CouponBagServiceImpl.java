package com.ydw.recharge.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.recharge.dao.CouponBagMapper;
import com.ydw.recharge.dao.CouponCardMapper;
import com.ydw.recharge.model.db.CouponBag;
import com.ydw.recharge.model.db.CouponCard;
import com.ydw.recharge.model.vo.CouponCardBagVO;
import com.ydw.recharge.service.ICouponBagService;
import com.ydw.recharge.util.SequenceGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 优惠券包 服务实现类
 * </p>
 *
 * @author xulh
 * @since 2021-04-22
 */
@Service
public class CouponBagServiceImpl extends ServiceImpl<CouponBagMapper, CouponBag> implements ICouponBagService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CouponCardMapper couponCardMapper;

    @Autowired
    private CouponBagMapper couponBagMapper;

    @Override
    public CouponCardBagVO preConsumeCouponBag(String rechargeCardId, String couponBagId) {
        CouponBag couponBag = couponBagMapper.selectById(couponBagId);
        CouponCard couponCard = couponCardMapper.selectById(couponBag.getCouponId());
        if (couponCard.getRechargeCardId().equals(rechargeCardId)){
            UpdateWrapper<CouponBag> uw = new UpdateWrapper<>();
            uw.eq("id", couponBagId);
            uw.eq("status", 0);
            uw.set("status", 1);
            boolean update = update(uw);
            if (update){
                CouponCardBagVO vo = new CouponCardBagVO(couponCard, couponBag);
                return vo;
            }
        }
        return null;
    }

    @Override
    public boolean consumeCouponBag(String couponBagId) {
        UpdateWrapper<CouponBag> uw = new UpdateWrapper<>();
        uw.eq("id", couponBagId);
        uw.eq("status", 1);
        uw.set("status", 2);
        uw.set("used_time", LocalDateTime.now());
        return update(uw);
    }

    @Override
    public boolean releaseCouponBag(String couponBagId) {
        UpdateWrapper<CouponBag> uw = new UpdateWrapper<>();
        uw.eq("id", couponBagId);
        uw.eq("status", 1);
        uw.set("status", 0);
        return update(uw);
    }

    @Override
    public List<CouponCardBagVO> getUserCouponBag(String userId, List<Integer> status) {
        return getUserCouponBag(userId, status, null);
    }

    @Override
    public List<CouponCardBagVO> getUserCouponBag(String userId, List<Integer> status, String rechargeCardId) {
        List<CouponCardBagVO> userCouponBag = couponBagMapper.getUserCouponBag(userId, status, rechargeCardId);
        if(userCouponBag != null){
            Iterator<CouponCardBagVO> iterator = userCouponBag.iterator();
            LocalDateTime localDateTime = LocalDateTime.now();
            List<String> list = new ArrayList<>();
            while (iterator.hasNext()){
                CouponCardBagVO next = iterator.next();
                if (next.getValidEndTime() != null && next.getValidEndTime().isBefore(localDateTime)){
                    next.setStatus(2);
                    list.add(next.getBagId());
                }
            }
            if (!list.isEmpty()){
                doDueCouponBags(list);
            }
        }
        return userCouponBag;
    }

    public void doDueCouponBags(List<String> couponBagIds){
        UpdateWrapper<CouponBag> uw = new UpdateWrapper<>();
        uw.in("id", couponBagIds);
        uw.set("status", 3);
        update(uw);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public CouponCardBagVO drawCoupon(String userId, String couponId) {
        CouponCard couponCard = couponCardMapper.selectById(couponId);
        if (couponCard == null || !couponCard.getValid()){
            logger.error("用户{}领取优惠券{}时，优惠券{}不存在或者已失效", userId, couponId, couponCard);
            return null;
        }
        LocalDateTime now = LocalDateTime.now();
        if (couponCard.getValidityType() == 0
                && (couponCard.getValidBeginTime().isAfter(now)
                    || couponCard.getValidEndTime().isBefore(now))){
            logger.error("用户{}领取优惠券{}时，优惠券{}已过期或者还未开始", userId, couponId, couponCard);
            return null;
        }
        if (couponCard.getUserDrawableNum() != -1){
            UpdateWrapper<CouponBag> uw = new UpdateWrapper<>();
            uw.eq("user_id", userId);
            uw.eq("coupon_id", couponId);
            int count = super.count(uw);
            if (count >= couponCard.getUserDrawableNum()){
                logger.error("用户{}领取优惠券{}时，已超过优惠券个人可领额度{}", userId, couponId, count);
                return null;
            }
        }
        boolean drawable = couponCard.getDrawableNum() == -1 ? true : couponCard.getDrawedNum() < couponCard.getDrawableNum();
        boolean drawed = false;
        while(!drawed && drawable){
            UpdateWrapper<CouponCard> uw = new UpdateWrapper<>();
            uw.setSql("drawed_num = drawed_num + 1");
            uw.eq("id", couponId);
            uw.eq("drawed_num", couponCard.getDrawedNum());
            if (couponCard.getDrawableNum() != -1){
                uw.gt("drawable_num", couponCard.getDrawedNum());
            }
            int update = couponCardMapper.update(null, uw);
            if (update > 0){
                drawed = true;
            }else{
                couponCard = couponCardMapper.selectById(couponId);
                drawable = couponCard.getDrawableNum() == -1 ? true : couponCard.getDrawedNum() < couponCard.getDrawableNum();
            }
        }
        if (drawed){
            logger.info("用户{}领取优惠券{}成功！", userId, couponId);
        }else{
            logger.error("用户{}领取优惠券{}失败！", userId, couponId);
            return null;
        }
        CouponBag cb = new CouponBag();
        cb.setCouponId(couponId);
        cb.setId(SequenceGenerator.sequence());
        cb.setStatus(0);
        cb.setUserId(userId);
        cb.setCreateTime(now);
        cb.setValidBeginTime(now);
        //优惠券的有效结束时间
        LocalDateTime cardEndTime = couponCard.getValidEndTime();
        //有效时长
        Integer duration = couponCard.getDuration();
        //卡包的有效结束时间
        LocalDateTime bagEndTime;
        if (duration == -1){
            bagEndTime = cardEndTime;
        }else if (duration == 0){
            bagEndTime = LocalDateTime.of(now.toLocalDate(), LocalTime.MAX);
            bagEndTime = cardEndTime == null ? bagEndTime : bagEndTime.isBefore(cardEndTime) ? bagEndTime : cardEndTime;
        }else{
            bagEndTime = now.plusDays(duration);
            bagEndTime = cardEndTime == null ? bagEndTime : bagEndTime.isBefore(cardEndTime) ? bagEndTime : cardEndTime;
        }
        cb.setValidEndTime(bagEndTime);
        super.save(cb);
        CouponCardBagVO c = new CouponCardBagVO(couponCard, cb);
        return c;
    }
}
