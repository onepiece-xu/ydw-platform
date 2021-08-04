package com.ydw.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import com.ydw.admin.dao.RechargeCardMapper;
import com.ydw.admin.model.db.RechargeCard;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IRechargeCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Autowired
    private RechargeCardMapper rechargeCardMapper;

    @Override
    public ResultInfo getRechargeCards() {
        QueryWrapper<RechargeCard> qw = new QueryWrapper<>();
        qw.eq("valid", true);
        //只获取充值的
        qw.eq("obtain_type", 0);
        List<RechargeCard> list = this.list(qw);
        return ResultInfo.success(list);
    }

    @Override
    public RechargeCard getSignCard() {
        QueryWrapper<RechargeCard> qw = new QueryWrapper<>();
        qw.eq("obtain_type", "1");
        qw.eq("valid", true);
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
    public ResultInfo addCard(RechargeCard rechargeCard) {

        rechargeCard.setValid(false);
        rechargeCard.setCreateTime(new Date());
        rechargeCardMapper.insert(rechargeCard);
        return ResultInfo.success("卡片添加成功！");
    }

    @Override
    public ResultInfo cardList(String search,Page buildPage) {
        QueryWrapper<RechargeCard> queryWrapper = new QueryWrapper();
        if(!StringUtils.isNullOrEmpty(search)){
            queryWrapper.like("name", search);
            queryWrapper.orderByDesc("create_time");
        }
        Page page = rechargeCardMapper.selectPage(buildPage, queryWrapper);
        return ResultInfo.success(page);

    }

    @Override
    public ResultInfo updateCard(RechargeCard rechargeCard) {

        rechargeCardMapper.updateById(rechargeCard);
        return  ResultInfo.success("编辑成功！");
    }

    @Override
    public ResultInfo available(String body) {

        JSONObject object = JSON.parseObject(body);
        JSONArray ids = object.getJSONArray("ids");
        Integer type = object.getInteger("type");
        for (Object o: ids) {
            RechargeCard rechargeCard = rechargeCardMapper.selectById(o.toString());
            if(type==0){
                rechargeCard.setValid(false);
                rechargeCardMapper.updateById(rechargeCard);

            }else{
                rechargeCard.setValid(true);
                rechargeCardMapper.updateById(rechargeCard);

            }
        }
        if(type==1){

            return ResultInfo.success("上架成功！");
        }else{
            return ResultInfo.success("下架成功！");
        }
    }

    @Override
    public ResultInfo cardInfo(String id) {
        RechargeCard rechargeCard = rechargeCardMapper.selectById(id);
        return ResultInfo.success(rechargeCard);
    }

    @Override
    public ResultInfo getSendCardList() {
        QueryWrapper<RechargeCard> wrapper = new QueryWrapper<>();
        wrapper.eq("obtain_type",5);
        List<RechargeCard> rechargeCards = rechargeCardMapper.selectList(wrapper);
        return ResultInfo.success(rechargeCards);
    }

}
