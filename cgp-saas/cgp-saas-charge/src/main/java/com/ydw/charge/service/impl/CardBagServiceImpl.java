package com.ydw.charge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.charge.dao.CardBagMapper;
import com.ydw.charge.model.db.CardBag;
import com.ydw.charge.service.ICardBagService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
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

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 获取用户可消费卡包（普通卡）
     *
     * @param userId
     * @return
     */
    @Override
    public List<CardBag> getUseAbleCardBag(String userId) {
        QueryWrapper<CardBag> qw = new QueryWrapper<>();
        qw.eq("user_id",userId);
        qw.eq("valid",true);
        qw.eq("type", 0);
        List<CardBag> userCardBags = list(qw);
        LocalDateTime now = LocalDateTime.now();
        //可以用的卡
        List<CardBag> canUseCardBag = new ArrayList<>();
        //过期卡
        List<String> dueCardBagIds = new ArrayList<>();
        for(CardBag c : userCardBags){
            if((c.getEndTime() == null || Duration.between(now, c.getEndTime()).toMinutes() >= 0)
                    && c.getDuration() - c.getUsedDuration() >= 0){
                canUseCardBag.add(c);
            }else {
                dueCardBagIds.add(c.getId());
            }
        }
        if(!dueCardBagIds.isEmpty()){
            UpdateWrapper<CardBag> uw = new UpdateWrapper<>();
            uw.set("valid", false);
            uw.in("id",dueCardBagIds);
            update(uw);
        }
        return canUseCardBag;
    }

    @Override
    public List<CardBag> getUseAbleCardBagOrderByConsume(String userId){
        List<CardBag> canUseCardBag = getUseAbleCardBag(userId);
        // 消费顺序规则
        // 过期时间靠前的先消费，然后时段卡>周期卡>时长卡
        if(!canUseCardBag.isEmpty()){
            canUseCardBag.sort((o1, o2) -> {
                //比较过期时间，先过期的优先消费；
                LocalDateTime dueTime1 = o1.getEndTime();
                LocalDateTime dueTime2 = o2.getEndTime();
                if(dueTime1 == null && dueTime2 == null){
                    //如果都不是
                    return 0;
                }else if(dueTime1 == null){
                    return 1;
                }else if(dueTime2 == null){
                    return -1;
                }else{
                    return o1.getEndTime().compareTo(o2.getEndTime());
                }
            });
        }
        return canUseCardBag;
    }

    @Override
    public long getChargeAbleContinuityTime(String userId){
        //获取所有可用卡
        List<CardBag> canUseCardBag = getUseAbleCardBagOrderByConsume(userId);
        return getChargeAbleContinuityTime(canUseCardBag);
    }

    @Override
    public long getChargeAbleContinuityTime(List<CardBag> canUseCardBag){
        long useTime = 0L;
        //需要使用的时间点，开始是当前时间
        LocalDateTime needUseDateTime = LocalDateTime.now();
        for (CardBag cardBag : canUseCardBag){
            long cardBagUseAbleTime = 0l;
            if((cardBag.getEndTime() == null || Duration.between(needUseDateTime , cardBag.getEndTime()).toMinutes() > 0 )
                    && Duration.between(cardBag.getBeginTime(), needUseDateTime).toMinutes() >= 0){
                //距离过期可用时长
                long dueTime = cardBag.getEndTime() == null ? Integer.MAX_VALUE : Duration.between(needUseDateTime , cardBag.getEndTime()).toMinutes();
                //周期内可用的时间（不考虑过期时间和已用时间）默认取过期时间，非时段卡即过期时间
                long periodTime = dueTime;
                //特殊判断时段卡
                if(StringUtils.isNotBlank(cardBag.getBeginPeriod())
                        && StringUtils.isNotBlank(cardBag.getEndPeriod())){
                    //需要使用的时间
                    LocalTime needUseTime = needUseDateTime.toLocalTime();
                    //时段卡 时段是否跨天
                    boolean acrossDay = Duration.between(LocalTime.parse(cardBag.getBeginPeriod()),
                            LocalTime.parse(cardBag.getEndPeriod())).toMinutes() <= 0 ;
                    long minutes1 = Duration.between(LocalTime.parse(cardBag.getBeginPeriod()), needUseTime).toMinutes();
                    long minutes2 = Duration.between(needUseTime, LocalTime.parse(cardBag.getEndPeriod())).toMinutes();
                    boolean useable = false;
                    if(acrossDay){
                        useable = minutes1 >= 0 || minutes2 > 0;
                    }else{
                        useable = minutes1 >= 0 && minutes2 > 0;
                    }
                    //如果可用，判断本张卡周期内（不考虑过期时间和已用时间）可用多长时间
                    if (useable){
                        if (minutes1 >= 0){
                            periodTime = Duration.between(needUseTime, LocalTime.MAX).toMinutes() +
                                    Duration.between(LocalTime.MIN, LocalTime.parse(cardBag.getEndPeriod())).toMinutes();
                        }else{
                            periodTime = Duration.between(needUseTime, LocalTime.parse(cardBag.getEndPeriod())).toMinutes();
                        }
                    }else{
                        continue;
                    }
                }
                cardBagUseAbleTime = Math.min(Math.min(periodTime,dueTime), cardBag.getDuration() - cardBag.getUsedDuration());
            }
            useTime += cardBagUseAbleTime;
            needUseDateTime = needUseDateTime.plusMinutes(cardBagUseAbleTime);
        }
        return useTime;
    }

    @Override
    public List<CardBag> consumeCardBags(String userId, long chargeDuration) {
        logger.info("本次用户{}消费时长{}分钟", userId, chargeDuration);
        List<CardBag> cardBags = getUseAbleCardBagOrderByConsume(userId);
        Iterator<CardBag> iterator = cardBags.iterator();
        LocalDateTime now = LocalDateTime.now();
        while (iterator.hasNext()){
            CardBag cardBag = iterator.next();
            boolean useable = chargeAble(cardBag, now);
            if (useable){
                logger.info("此卡包{}可用",cardBag.getId());
                UpdateWrapper<CardBag> uw = new UpdateWrapper<>();
                uw.eq("id",cardBag.getId());
                uw.setSql("used_duration = used_duration + " + chargeDuration);
                if(cardBag.getDuration() <= cardBag.getUsedDuration() + chargeDuration ||
                        (cardBag.getEndTime() != null
                                && Duration.between(now, cardBag.getEndTime()).toMinutes() <= 0)){
                    uw.set("valid",false);
                    iterator.remove();
                }
                update(uw);
                break;
            }else{
                logger.info("此卡包{}不可用",cardBag.getId());
                iterator.remove();
            }
        }
        logger.info("本次计费后用户{}可用卡包数量为{}",userId,cardBags.size());
        return cardBags;
    }

    /**
     * 获取用户可用时长（分钟）,只算没有过期的
     *
     * @param userId
     * @return
     */
    @Override
    public long getUserUseableTime(String userId) {
        Long time = 0L;
        List<CardBag> list = getUseAbleCardBag(userId);
        if(list != null && !list.isEmpty()){
            for(CardBag c: list){
                time += c.getDuration() - c.getUsedDuration();
            }
        }
        return time;
    }

    @Override
    public boolean chargeAble(CardBag cardBag, LocalDateTime localDateTime) {
        boolean able = false;
        if (!cardBag.getValid() || cardBag.getDuration() <= cardBag.getUsedDuration()){
            return able;
        }
        if((cardBag.getEndTime() == null || Duration.between(localDateTime, cardBag.getEndTime()).toMinutes() > 0 )
                && Duration.between(cardBag.getBeginTime(), localDateTime).toMinutes() >= 0){
            LocalTime localTime = localDateTime.toLocalTime();
            //时段卡
            if(StringUtils.isNotBlank(cardBag.getBeginPeriod())
                    && StringUtils.isNotBlank(cardBag.getEndPeriod())){
                //时段卡 时段是否跨天
                boolean acrossDay = Duration.between(LocalTime.parse(cardBag.getBeginPeriod()),
                        LocalTime.parse(cardBag.getEndPeriod())).toMinutes() <= 0 ;
                if(acrossDay){
                    able = Duration.between(LocalTime.parse(cardBag.getBeginPeriod()),localTime).toMinutes() >= 0
                            || Duration.between(localTime,LocalTime.parse(cardBag.getEndPeriod())).toMinutes() >= 0;
                }else{
                    able = Duration.between(LocalTime.parse(cardBag.getBeginPeriod()),localTime).toMinutes() >= 0
                            && Duration.between(localTime,LocalTime.parse(cardBag.getEndPeriod())).toMinutes() >= 0;
                }
            }else{
                able = true;
            }
        }
        return able;
    }

    @Override
    public List<CardBag> getUseAblePlayCardBag(String userId) {
        QueryWrapper<CardBag> qw = new QueryWrapper<>();
        qw.eq("user_id",userId);
        qw.eq("valid",true);
        qw.eq("type", 1);
        List<CardBag> userCardBags = list(qw);
        LocalDateTime now = LocalDateTime.now();
        //可以用的卡
        List<CardBag> canUseCardBag = new ArrayList<>();
        //过期卡
        List<String> dueCardBagIds = new ArrayList<>();
        for(CardBag c : userCardBags){
            if((c.getEndTime() == null || Duration.between(now, c.getEndTime()).toMinutes() >= 0)
                    && c.getDuration() - c.getUsedDuration() >= 0){
                canUseCardBag.add(c);
            }else {
                dueCardBagIds.add(c.getId());
            }
        }
        if(!dueCardBagIds.isEmpty()){
            UpdateWrapper<CardBag> uw = new UpdateWrapper<>();
            uw.set("valid", false);
            uw.in("id",dueCardBagIds);
            update(uw);
        }
        return canUseCardBag;
    }

    @Override
    public List<CardBag> getUseAblePlayCardBagOrderByConsume(String userId){
        List<CardBag> canUseCardBag = getUseAblePlayCardBag(userId);
        if(!canUseCardBag.isEmpty()){
            canUseCardBag.sort((o1, o2) -> {
                //比较过期时间，先过期的优先消费；
                LocalDateTime dueTime1 = o1.getEndTime();
                LocalDateTime dueTime2 = o2.getEndTime();
                if(dueTime1 == null && dueTime2 == null){
                    //如果都不是
                    return 0;
                }else if(dueTime1 == null){
                    return 1;
                }else if(dueTime2 == null){
                    return -1;
                }else{
                    return o1.getEndTime().compareTo(o2.getEndTime());
                }
            });
        }
        return canUseCardBag;
    }

    @Override
    public List<CardBag> consumePlayCardBags(String userId, long chargeDuration) {
        logger.info("本次用户{}消费时长{}分钟", userId, chargeDuration);
        List<CardBag> cardBags = getUseAblePlayCardBagOrderByConsume(userId);
        Iterator<CardBag> iterator = cardBags.iterator();
        LocalDateTime now = LocalDateTime.now();
        while (iterator.hasNext()){
            CardBag cardBag = iterator.next();
            boolean useable = chargeAble(cardBag, now);
            if (useable){
                logger.info("此卡包{}可用",cardBag.getId());
                UpdateWrapper<CardBag> uw = new UpdateWrapper<>();
                uw.eq("id",cardBag.getId());
                uw.setSql("used_duration = used_duration + " + chargeDuration);
                if(cardBag.getDuration() <= cardBag.getUsedDuration() + chargeDuration ||
                        (cardBag.getEndTime() != null
                                && Duration.between(now, cardBag.getEndTime()).toMinutes() <= 0)){
                    uw.set("valid",false);
                    iterator.remove();
                }
                update(uw);
                break;
            }else{
                logger.info("此卡包{}不可用",cardBag.getId());
                iterator.remove();
            }
        }
        logger.info("本次计费后用户{}可用卡包数量为{}",userId,cardBags.size());
        return cardBags;
    }
}
