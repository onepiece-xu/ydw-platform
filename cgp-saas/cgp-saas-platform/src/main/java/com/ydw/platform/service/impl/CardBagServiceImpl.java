package com.ydw.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.platform.dao.CardBagMapper;
import com.ydw.platform.dao.RechargeCardMapper;
import com.ydw.platform.model.db.CardBag;
import com.ydw.platform.model.db.RechargeCard;
import com.ydw.platform.model.vo.CardBagVO;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.ICardBagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 用户资产汇总表 服务实现类
 * </p>
 *
 * @author xulh
 * @since 2020-10-07
 */
@Service
public class CardBagServiceImpl extends ServiceImpl<CardBagMapper, CardBag> implements ICardBagService {


    @Autowired
    private CardBagMapper cardBagMapper;

    @Autowired
    private RechargeCardMapper rechargeCardMapper;

    @Override
    public ResultInfo getUserUseableTime(String userId) {
        Long time = 0L;
        QueryWrapper<CardBag> qw = new QueryWrapper<>();
        qw.eq("user_id", userId);
        qw.eq("valid", true);
        List<CardBag> list = list(qw);
        if (list != null && !list.isEmpty()) {
            for (CardBag c : list) {
                time += c.getDuration() - c.getUsedDuration();
            }
        }
        return ResultInfo.success(time);
    }

    @Override
    public ResultInfo getCardBag(String userId) {

        QueryWrapper<CardBag> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("valid", 1);
        List<CardBag> cardBags = cardBagMapper.selectList(wrapper);
//      List<CardBagVO> list = new ArrayList<>();
        HashMap<Object, Object> map1 = new HashMap<>();
        HashMap<Object, Object> map2 = new HashMap<>();
        HashMap<Object, Object> map3 = new HashMap<>();
        HashMap<Object, Object> map4 = new HashMap<>();
        List<CardBagVO> list1 = new ArrayList<>();
        List<CardBagVO> list2 = new ArrayList<>();
        List<CardBagVO> list3 = new ArrayList<>();
        int i1=0;
        int i2=0;
        int i3=0;
        int i4=0;
        int j1=0;
        int j2=0;
        int j3=0;
        int j4=0;
        for (CardBag cb : cardBags) {
            CardBagVO cardBagVO = new CardBagVO();
            String id = cb.getId();
            String cardId = cb.getCardId();
            Integer duration = cb.getDuration();
            Integer usedDuration = cb.getUsedDuration();

            //rest time
            int rest = duration - usedDuration;
        //total
            i1+=rest;
            j1++;

            RechargeCard rechargeCard = rechargeCardMapper.selectById(cardId);
            Integer type = rechargeCard.getType();
            String name = rechargeCard.getName();
            LocalDateTime endTime = cb.getEndTime();
            if(null!=endTime){
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String end = df.format(endTime);
                cardBagVO.setEndTime(end);
            }else{
                cardBagVO.setEndTime("长期有效");
            }

            cardBagVO.setCardId(id);
            cardBagVO.setCardName(name);
            cardBagVO.setRestTime(rest);
            if (type == 1) {
                if(cardBagVO.getCardName().equals("签到卡")){
                    i2+=rest;
                    j2++;
                    list1.add(0,cardBagVO);
                }else {
                    i2+=rest;
                    j2++;
                    list1.add(cardBagVO);
                }

            }
            if (type == 2) {
                i3+=rest;
                j3++;
                list2.add(cardBagVO);
            }
            if (type == 3) {
                i4+=rest;
                j4++;
                list3.add(cardBagVO);
            }

        }
        map2.put("list",list1);
        map2.put("total",i2);
        map2.put("number",j2);

        map3.put("list",list2);
        map3.put("total",i3);
        map3.put("number",j3);

        map4.put("list",list3);
        map4.put("total",i4);
        map4.put("number",j4);

        map1.put("cycleCard", map2);
        map1.put("durationCard", map3);
        map1.put("periodCard", map4);
        map1.put("number", j1);
        map1.put("total", i1);
        return ResultInfo.success(map1);
    }
}
