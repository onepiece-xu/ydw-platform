package com.ydw.recharge.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.recharge.model.db.CouponBag;
import com.ydw.recharge.model.vo.CouponCardBagVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 优惠券包 Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2021-04-22
 */
public interface CouponBagMapper extends BaseMapper<CouponBag> {

    List<CouponCardBagVO> getUserCouponBag(@Param("userId") String userId,
                                           @Param("status") List<Integer> status,
                                           @Param("rechargeCardId") String rechargeCardId);
}
