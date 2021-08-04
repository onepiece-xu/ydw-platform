package com.ydw.recharge.dao;

import com.ydw.recharge.model.db.CardBag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户资产汇总表 Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2020-10-07
 */
public interface CardBagMapper extends BaseMapper<CardBag> {

    List<CardBag> getCardsByUserIdAndType(@Param("userId") String userId, @Param("type") int type);
}
