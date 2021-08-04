package com.ydw.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.admin.dao.CouponCardMapper;
import com.ydw.admin.model.db.CouponCard;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.ICouponCardService;
import com.ydw.admin.util.SequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 优惠券卡 服务实现类
 * </p>
 *
 * @author xulh
 * @since 2021-04-26
 */
@Service
public class CouponCardServiceImpl extends ServiceImpl<CouponCardMapper, CouponCard> implements ICouponCardService {

    @Autowired
    private CouponCardMapper couponCardMapper;

    @Override
    public ResultInfo getCouponList(String search, Page buildPage) {
        buildPage = couponCardMapper.getCouponList(search, buildPage);
        return ResultInfo.success(buildPage);
    }

    @Override
    public ResultInfo addCoupon(CouponCard couponCard) {
        couponCard.setId(SequenceGenerator.sequence());
        couponCard.setCreateTime(new Date());
        couponCard.setValid(false);
        save(couponCard);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo updateCoupon(CouponCard couponCard) {
        LambdaUpdateWrapper<CouponCard> uw = Wrappers.lambdaUpdate();
        uw.eq(CouponCard::getId, couponCard.getId());
        uw.set(CouponCard::getDescription, couponCard.getDescription());
        uw.set(CouponCard::getDiscount, couponCard.getDiscount());
        uw.set(CouponCard::getDrawableNum, couponCard.getDrawableNum());
        uw.set(CouponCard::getDuration, couponCard.getDuration());
        uw.set(CouponCard::getName, couponCard.getName());
        uw.set(CouponCard::getPromotionType, couponCard.getPromotionType());
        uw.set(CouponCard::getRechargeCardId, couponCard.getRechargeCardId());
        uw.set(CouponCard::getReduction, couponCard.getReduction());
        uw.set(CouponCard::getUserDrawableNum, couponCard.getUserDrawableNum());
        uw.set(CouponCard::getValidBeginTime, couponCard.getValidBeginTime());
        uw.set(CouponCard::getValidEndTime, couponCard.getValidEndTime());
        uw.set(CouponCard::getValidityType, couponCard.getValidityType());
        update(uw);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo available(List ids, int type) {
        UpdateWrapper<CouponCard> uw = new UpdateWrapper<>();
        uw.in("id", ids);
        if (type == 0){
            uw.set("valid", false);
        }else{
            uw.set("valid", true);
        }
        update(uw);
        return ResultInfo.success();
    }
}
