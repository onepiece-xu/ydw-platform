package com.ydw.charge.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.charge.dao.ChargeMapper;
import com.ydw.charge.model.constants.Constant;
import com.ydw.charge.model.db.CardBag;
import com.ydw.charge.model.db.Charge;
import com.ydw.charge.model.enums.MessageTopicEnum;
import com.ydw.charge.model.vo.Msg;
import com.ydw.charge.model.vo.ResultInfo;
import com.ydw.charge.service.*;
import com.ydw.charge.task.ThreadPool;
import com.ydw.charge.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
public class ChargeServiceImpl extends ServiceImpl<ChargeMapper, Charge> implements IChargeService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IYdwPlatformService ydwPlatformService;

    @Autowired
    private IYdwMessageService ydwMessageService;

    @Autowired
    private IYdwAuthenticationService ydwAuthenticationService;

	@Autowired
	private ICardBagService cardBagService;

	@Value("${charge.period}")
	private long chargePeriod;

    @Value("${charge.notice}")
    private Long[] chargeNotice;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
    private ThreadPool threadPool;

    @Override
    public void doCharge() {
        //时长卡计量
        Set<Object> onlineConnects = redisUtil.zRange(Constant.ZSET_ONLINE_CONNECT, Double.MIN_VALUE, Double.MAX_VALUE);
        if (onlineConnects != null && !onlineConnects.isEmpty()){
            for (Object o : onlineConnects){
                threadPool.submit(() -> doCardBagCharge((String)o));
            }
        }
        onlineConnects = redisUtil.zRange(Constant.ZSET_ONLINE_CONNECT_PLAY, Double.MIN_VALUE, Double.MAX_VALUE);
        if (onlineConnects != null && !onlineConnects.isEmpty()){
            for (Object o : onlineConnects){
                threadPool.submit(() -> doPlayCardBagCharge((String)o));
            }
        }
    }

    private void doCardBagCharge(String connectId){
        logger.info("本次连接{}周期计费开始！", connectId);
        //根据连接id获取本次连接的详情
        String connectInfo = ydwPlatformService.getConnectInfo(connectId);
        JSONObject jsonObject = JSON.parseObject(connectInfo);
        if(jsonObject.getIntValue("code") != 200){
            logger.error("获取连接详情失败！connectId = {}",connectId);
        }
        JSONObject connectDetail = jsonObject.getJSONObject("data");
        //是否本次连接已结束
        if(StringUtils.isNotBlank(connectDetail.getString("totalTime"))){
            logger.error("本次连接已结束！connectId = {}",connectId);
            redisUtil.zRemove(Constant.ZSET_ONLINE_CONNECT,connectId);
            return;
        }
        List<CardBag> cardBags = cardBagService.consumeCardBags(
                connectDetail.getString("userId"), chargePeriod);
        if (cardBags == null || cardBags.isEmpty()){
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("connectId", connectId);
            hashMap.put("deviceId", connectDetail.getString("deviceId"));
            hashMap.put("account", connectDetail.getString("userId"));
            hashMap.put("appId", connectDetail.getString("appId"));
            logger.info("没有可用的卡包了！准备下机！{}",hashMap.toString());
            ydwPlatformService.release(hashMap);
        }else{
            //通知
            logger.info("通知客户端的{}本次连接{}消费",connectDetail.getString("userId"),connectId);
            notice(connectId,connectDetail.getString("userId"),cardBags);
        }
        logger.info("本次连接{}周期计费结束！", connectId);
    }


    private void doPlayCardBagCharge(String connectId){
        logger.info("本次连接的畅玩卡{}周期计费开始！", connectId);
        //根据连接id获取本次连接的详情
        String connectInfo = ydwPlatformService.getConnectInfo(connectId);
        JSONObject jsonObject = JSON.parseObject(connectInfo);
        if(jsonObject.getIntValue("code") != 200){
            logger.error("获取连接详情失败！connectId = {}",connectId);
        }
        JSONObject connectDetail = jsonObject.getJSONObject("data");
        //是否本次连接已结束
        if(StringUtils.isNotBlank(connectDetail.getString("totalTime"))){
            logger.error("本次连接已结束！connectId = {}",connectId);
            redisUtil.zRemove(Constant.ZSET_ONLINE_CONNECT_PLAY,connectId);
            return;
        }
        List<CardBag> cardBags = cardBagService.consumePlayCardBags(
                connectDetail.getString("userId"), chargePeriod);
        if (cardBags == null || cardBags.isEmpty()){
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("connectId", connectId);
            hashMap.put("deviceId", connectDetail.getString("deviceId"));
            hashMap.put("account", connectDetail.getString("userId"));
            hashMap.put("appId", connectDetail.getString("appId"));
            logger.info("没有可用的的畅玩卡包了！准备下机！{}",hashMap.toString());
            ydwPlatformService.release(hashMap);
        }else{
            //通知
            logger.info("通知客户端的{}本次连接的畅玩卡{}消费",connectDetail.getString("userId"),connectId);
            notice(connectId,connectDetail.getString("userId"),cardBags);
        }
        logger.info("本次连接的畅玩卡{}周期计费结束！", connectId);
    }

    @Override
    public ResultInfo startCharge(String connectId, String userId) {
        ResultInfo resultInfo = chargeAble(userId);
        if((boolean)resultInfo.getData()){
            //检查本次连接剩余时长是否在提示的范围内
            long useAbleContinuityTime = cardBagService.getChargeAbleContinuityTime(userId);
            for (long t : chargeNotice){
                if (useAbleContinuityTime <= t){
                    notice(connectId, userId, useAbleContinuityTime);
                    break;
                }
            }
            redisUtil.zSet(Constant.ZSET_ONLINE_CONNECT,connectId,System.currentTimeMillis());
        }
        return resultInfo;
    }

    private void notice(String connectId, String userId, List<CardBag> cardBags){
        long useAbleContinuityTime = 0l;
        if (cardBags != null && !cardBags.isEmpty()){
            useAbleContinuityTime = cardBagService.getChargeAbleContinuityTime(cardBags);
        }else{
            useAbleContinuityTime = cardBagService.getChargeAbleContinuityTime(userId);
        }
        for (long t : chargeNotice){
            if (useAbleContinuityTime == t){
                notice(connectId, userId, useAbleContinuityTime);
                break;
            }
        }
    }

    private void notice(String connectId, String userId, long useAbleContinuityTime){
        logger.info("本次连接{}剩余可用时长为{}",connectId,useAbleContinuityTime);
        Msg msg = new Msg();
        HashMap<String,Object> map = new HashMap();
        map.put("connectId", connectId);
        map.put("type",MessageTopicEnum.CHARGE.getTopic());
        map.put("surplusTime",useAbleContinuityTime);
        msg.setData(JSON.toJSONString(map));
        String result = ydwAuthenticationService.getUserOnlineTokens(userId);
        List<String> tokens = JSON.parseObject(result).getJSONArray("data").toJavaList(String.class);
        msg.setReceivers(tokens);
        msg.setType(MessageTopicEnum.CHARGE.getTopic());
        ydwMessageService.sendMsg(msg);
    }

    private Long getNextChargeTime(String userId){
        //获取有效的卡
        List<CardBag> cardBags = cardBagService.getUseAbleCardBagOrderByConsume(userId);
        LocalDateTime now = LocalDateTime.now();
        LocalTime localTime = now.toLocalTime();
        Long nextTime = now.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        for(CardBag cardBag : cardBags){
            long periodTime = chargePeriod;
            //这里有特殊处理，如果最新过期的是时段卡，但是此时没有到达时段卡开始时间，这先消费后面的卡，
            //下一次扣费时间考虑此时段卡的开始时间
            if(StringUtils.isNotBlank(cardBag.getBeginPeriod())
                    && StringUtils.isNotBlank(cardBag.getEndPeriod())){
                boolean useable = true;
                //时段卡 时段是否跨天
                boolean acrossDay = Duration.between(LocalTime.parse(cardBag.getBeginPeriod()),
                        LocalTime.parse(cardBag.getEndPeriod())).toMinutes() <= 0 ;
                if(acrossDay){
                    useable = Duration.between(localTime,LocalTime.parse(cardBag.getBeginPeriod())).toMinutes() >= 0
                            || Duration.between(localTime,LocalTime.parse(cardBag.getEndPeriod())).toMinutes() <= 0;
                }else{
                    useable = Duration.between(localTime,LocalTime.parse(cardBag.getBeginPeriod())).toMinutes() <= 0
                            || Duration.between(localTime,LocalTime.parse(cardBag.getEndPeriod())).toMinutes() >= 0;
                }
                if(!useable){
                    periodTime = Duration.between(localTime,LocalTime.parse(cardBag.getBeginPeriod())).toMinutes();
                }else{
                    periodTime = Duration.between(localTime,LocalTime.parse(cardBag.getEndPeriod())).toMinutes();
                }
            }
            Long dueTime = Duration.between(now,cardBag.getEndTime()).toMinutes();
            Long surplusDuration = cardBag.getDuration() - cardBag.getUsedDuration();
            //取过期时长，剩余时长，默认计费周期 中最小值
            long min = Math.min(Math.min(Math.min(chargePeriod, periodTime), surplusDuration), dueTime);
            if(min <= 0){
                continue;
            }
            //计算得到下一次扣费时间
            nextTime = now.plusMinutes(min).toInstant(ZoneOffset.of("+8")).toEpochMilli();
            break;
        }
        return nextTime;
    }

    /**
     * 获取此次连接最后一次计费时间
     * @param connectId
     * @return
     */
    public LocalDateTime getConnectLastChargeTime(String connectId){
        LocalDateTime previousChargeTime = null;
        QueryWrapper<Charge> qw = new QueryWrapper<>();
        qw.eq("connect_id",connectId);
        qw.orderByDesc("create_time");
        List<Charge> charges = this.list(qw);
        if(charges != null && !charges.isEmpty()){
            previousChargeTime = charges.get(0).getCreateTime();
        }
        return previousChargeTime;
    }

    @Override
    public ResultInfo endCharge(String connectId) {
        redisUtil.zRemove(Constant.ZSET_ONLINE_CONNECT, connectId);
        String connectInfo = ydwPlatformService.getConnectInfo(connectId);
        JSONObject jsonObject = JSON.parseObject(connectInfo);
        if(jsonObject.getIntValue("code") != 200){
            logger.error("获取连接详情失败！connectId = {}",connectId);
        }
        JSONObject connectDetail = jsonObject.getJSONObject("data");
        if(connectDetail == null){
            logger.error("无此连接的计量信息！connectId = {}",connectId);
            return ResultInfo.success();
        }
        //是否本次连接已结束
        if(StringUtils.isNotBlank(connectDetail.getString("totalTime"))){
            logger.error("本次连接已结束！connectId = {}",connectId);
        }
        //扣费
        cardBagService.consumeCardBags(
                connectDetail.getString("userId"), chargePeriod);
        logger.info("扣费结束，移除在线连接{}",connectId);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo chargeAble(String userId) {
        boolean able = false;
        //获取有效的卡
        List<CardBag> cardBags = cardBagService.getUseAbleCardBagOrderByConsume(userId);
        if(cardBags != null && !cardBags.isEmpty()){
            LocalDateTime now = LocalDateTime.now();
            for(CardBag cardBag : cardBags){
                if (able){
                    break;
                }
                able = cardBagService.chargeAble(cardBag, now);
            }
        }
        return ResultInfo.success(able);
    }

    @Override
    public ResultInfo playCardChargeAble(String userId) {
        boolean able = false;
        //获取有效的卡
        List<CardBag> cardBags = cardBagService.getUseAblePlayCardBag(userId);
        if(cardBags != null && !cardBags.isEmpty()){
            LocalDateTime now = LocalDateTime.now();
            for(CardBag cardBag : cardBags){
                if (able){
                    break;
                }
                able = cardBagService.chargeAble(cardBag, now);
            }
        }
        return ResultInfo.success(able);
    }

    @Override
    public ResultInfo startPlayCardCharge(String connectId, String userId) {
        ResultInfo resultInfo = playCardChargeAble(userId);
        if((boolean)resultInfo.getData()){
            //检查本次连接剩余时长是否在提示的范围内
            long useAbleContinuityTime = cardBagService.getChargeAbleContinuityTime(userId);
            for (long t : chargeNotice){
                if (useAbleContinuityTime <= t){
                    notice(connectId, userId, useAbleContinuityTime);
                    break;
                }
            }
            redisUtil.zSet(Constant.ZSET_ONLINE_CONNECT_PLAY,connectId,System.currentTimeMillis());
        }
        return resultInfo;
    }

    @Override
    public ResultInfo endPlayCardCharge(String connectId, String userId) {
        redisUtil.zRemove(Constant.ZSET_ONLINE_CONNECT_PLAY, connectId);
        //扣费
        String connectInfo = ydwPlatformService.getConnectInfo(connectId);
        JSONObject jsonObject = JSON.parseObject(connectInfo);
        if(jsonObject.getIntValue("code") != 200){
            logger.error("获取连接详情失败！connectId = {}",connectId);
        }
        JSONObject connectDetail = jsonObject.getJSONObject("data");
        if(connectDetail == null){
            logger.error("无此连接的计量信息！connectId = {}",connectId);
            return ResultInfo.success();
        }
        //是否本次连接已结束
        if(StringUtils.isNotBlank(connectDetail.getString("totalTime"))){
            logger.error("本次连接已结束！connectId = {}",connectId);
        }
        cardBagService.consumePlayCardBags(
                connectDetail.getString("userId"), chargePeriod);
        logger.info("扣费结束，移除在线连接{}",connectId);
        return ResultInfo.success();
    }
}
