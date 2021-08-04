package com.ydw.recharge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.recharge.dao.RechargeCardMapper;
import com.ydw.recharge.model.db.RechargeCard;
import com.ydw.recharge.model.vo.ResultInfo;
import com.ydw.recharge.service.IRechargeCardService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 充值卡 服务实现类
 * </p>
 *
 * @author xulh
 * @since 2020-10-07
 */
@Service
public class RechargeCardServiceImpl extends ServiceImpl<RechargeCardMapper, RechargeCard> implements IRechargeCardService {

    @Override
    public ResultInfo getRechargeCards() {
        QueryWrapper<RechargeCard> qw = new QueryWrapper<>();
        qw.eq("valid", true);
        //只获取充值的
        qw.eq("obtain_type", 0);
        List<RechargeCard> list = this.list(qw);
        Map<Integer, List<RechargeCard>> collect = new HashMap<>();
        collect.put(1,new ArrayList<>());
        collect.put(2,new ArrayList<>());
        collect.put(3,new ArrayList<>());
        for (RechargeCard rc : list){
            if (rc.getType() == 1){
                collect.get(1).add(rc);
            }else if(rc.getType() == 2){
                collect.get(2).add(rc);
            }else if(rc.getType() == 3){
                collect.get(3).add(rc);
            }
        }
        return ResultInfo.success(collect);
    }

    @Override
    public RechargeCard getSignCard() {
        QueryWrapper<RechargeCard> qw = new QueryWrapper<>();
        qw.eq("obtain_type","1");
        qw.eq("valid",true);
        return getOne(qw);
    }

    @Override
    public ResultInfo getRechargeCardsByType(int type) {
        QueryWrapper<RechargeCard> qw = new QueryWrapper<>();
        qw.eq("type", type);
        qw.eq("valid", true);
        //只获取充值的
        qw.eq("obtain_type", 0);
        List<RechargeCard> list = this.list(qw);
        return ResultInfo.success(list);
    }

    @Override
    public RechargeCard getSevenSignCard() {
        QueryWrapper<RechargeCard> qw = new QueryWrapper<>();
        //连续签到7天  obtain_type为 2  valid 为1
        qw.eq("obtain_type","2");
        qw.eq("valid",true);
        return getOne(qw);
    }

    @Override
    public RechargeCard getsendDurationCard() {
        QueryWrapper<RechargeCard> qw = new QueryWrapper<>();
        qw.eq("obtain_type","6");
        return getOne(qw);
    }

    @Override
    public ResultInfo getPlayRechargeCards() {
        QueryWrapper<RechargeCard> qw = new QueryWrapper<>();
        qw.eq("type", 4);
        qw.eq("valid", true);
        //只获取充值的
        qw.eq("obtain_type", 0);
        List<RechargeCard> list = this.list(qw);
        return ResultInfo.success(list);
    }

    @Override
    public ResultInfo getComboRechargeCards() {
        QueryWrapper<RechargeCard> qw = new QueryWrapper<>();
        qw.eq("type", 5);
        qw.eq("valid", true);
        //只获取充值的
        qw.eq("obtain_type", 0);
        List<RechargeCard> list = this.list(qw);
        return ResultInfo.success(list);
    }
}
