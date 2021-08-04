package com.ydw.platform.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.platform.dao.SignAwardMapper;
import com.ydw.platform.dao.SignInMapper;
import com.ydw.platform.model.db.SignAward;
import com.ydw.platform.model.db.SignIn;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.model.vo.SignInDto;
import com.ydw.platform.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

/**
 * <p>
 * 用户签到表 服务实现类
 * </p>
 *
 * @author xulh
 * @since 2020-10-06
 */
@Service
public class SignInServiceImpl extends ServiceImpl<SignInMapper, SignIn> implements ISignInService {
    @Autowired
    private  SignInMapper signInMapper;
    @Autowired
    private SignAwardMapper signAwardMapper;
    @Autowired
    private IYdwRechargeService iYdwRechargeService;
    @Autowired
    private IYdwChargeService ydwChargeService;
    @Autowired
    private IPostMessageService iPostMessageService;
    @Autowired
    private ITemplateTypeService templateTypeService;
    @Value("${send.minute}")
    private String sendMinute;
    @Value("${send.signMinute}")
    private String signMinute;

    @Override
    public ResultInfo signIn(SignIn sign) {
        String userId = sign.getUserId();
        // 查询用户是否签过到
        QueryWrapper<SignIn> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        SignIn signIn = signInMapper.selectOne(queryWrapper);
        HashMap<Object, Object> map = new HashMap<>();
        if(null!=signIn){
            Integer days = signIn.getContinueDays();
            if(days==7){
                signInMapper.deleteById(signIn.getId());
                signIn=null;
            }

        }

        Map<String,Object> message = new HashMap<>();
        message.put("userId",userId);
        /*没有签过到, 直接新增*/
        if (null == signIn) {
            SignIn in = new SignIn();
            in.setUpdateTime(new Date());
            in.setUserId(userId);
            signInMapper.insert(in);
            //签到送时间
            HashMap<String,String> params = new HashMap<>();
            params.put("userId", userId);
            params.put("time", sendMinute);
            iYdwRechargeService.sendDuration(params);

//            iYdwRechargeService.signRecharge(userId);
            try {
                //添加站内信
                ResultInfo signin = templateTypeService.getTemplateId("signin");
                message.put("templateId", signin.getData());
                //发送类型为系统通知2
                message.put("type", 2);
                iPostMessageService.add(message);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            /*签过到*/
            // 判断最后签到日期与当前日期是否超过一天
            Date    signInTime =  signIn.getUpdateTime();
            Date  currTime =   new Date();
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            if(fmt.format(currTime).equals(fmt.format(signInTime))){
                return ResultInfo.fail("签到失败，重复签到！");
            }

            Calendar cal1 = Calendar.getInstance();
            //当前时间的前一天
            cal1.add(Calendar.DAY_OF_MONTH, -1);
            //上次签到时间
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(signInTime);
            //判断上次签到时间和当前时间前一天是否同一天
            boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                    .get(Calendar.YEAR);
            boolean isSameMonth = isSameYear
                    && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
            boolean isSameDate = isSameMonth
                    && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                    .get(Calendar.DAY_OF_MONTH);

            if (!isSameDate) {
                // 1, 超过一天, 把连续签到的天数重置为 1
                signIn.setContinueDays(1);
            } else {
                // 2, 没有超过一天, 把连续签到的天数+1
                signIn.setContinueDays(signIn.getContinueDays() + 1);
            }
            signIn.setUpdateTime(new Date());
            Integer continueDays = signIn.getContinueDays();
            if(continueDays==7){
                try {
                    //加签到时间
                    iYdwRechargeService.signRecharge(userId);

                    //并且加签到时间额外的7天
                    iYdwRechargeService.continueSignRecharge(userId);

                    ResultInfo continuity_signin = templateTypeService.getTemplateId("continuity_signin");
                    message.put("templateId", continuity_signin.getData());
                    //发送类型为系统通知2
                    message.put("type", 2);
                    iPostMessageService.add(message);
                    //额外加签到时间7天

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    //添加站内信
                    ResultInfo signin = templateTypeService.getTemplateId("signin");
                    message.put("templateId", signin.getData());
                    //发送类型为系统通知2
                    message.put("type", 2);
                    iPostMessageService.add(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //加签到时间 //            iYdwRechargeService.signRecharge(userId);
            if(continueDays>=1&&continueDays<=3){
                HashMap<String,String> params = new HashMap<>();
                params.put("userId", userId);
                params.put("time", sendMinute);
                iYdwRechargeService.sendDuration(params);
            }
            if(continueDays>=4&&continueDays<=6){
                HashMap<String,String> params = new HashMap<>();
                params.put("userId", userId);
                params.put("time", signMinute);
                iYdwRechargeService.sendDuration(params);
            }

            signInMapper.updateById(signIn);
        }
        QueryWrapper<SignIn> query = new QueryWrapper<>();
        query.eq("user_id",userId);
        SignIn byId = signInMapper.selectOne(query);
        Date updateTime = byId.getUpdateTime();
        Date  currTime =   new Date();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        if(fmt.format(currTime).equals(fmt.format(updateTime))){
            map.put("isSign",true);
        }else {
            map.put("isSign",false);
        }

        QueryWrapper<SignAward> wrapper = new QueryWrapper<>();
        if(signIn!=null){
            Integer continueDays = byId.getContinueDays();
            map.put("continueDays",continueDays);
            wrapper.eq("continue_days",continueDays);
            SignAward signAward = signAwardMapper.selectOne(wrapper);
            map.put("signAward",signAward);
        }else {
            map.put("continueDays",1);
            Integer continueDays = byId.getContinueDays();
            wrapper.eq("continue_days",continueDays);
            SignAward signAward = signAwardMapper.selectOne(wrapper);
            map.put("signAward",signAward);
        }
        String userUseableTime = ydwChargeService.getUserUseableTime(userId);
        JSONObject jsonObject = JSONObject.parseObject(userUseableTime);
        Object data = jsonObject.get("data");
        map.put("userUseableTime",data);
        return ResultInfo.success(map);

    }

    @Override
    public ResultInfo signInList(SignIn sign) {
        String userId = sign.getUserId();
        QueryWrapper<SignIn> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SignIn::getUserId, userId);
        SignIn signIn = signInMapper.selectOne(queryWrapper);
        List<SignInDto> list = new ArrayList<>(7);
        if (null == signIn) {
            // 没有签过到

            for (int i = 1; i < 8; i++) {
                QueryWrapper<SignAward> wrapper = new QueryWrapper<>();
                wrapper.eq("continue_days",i);
                SignAward signAward = signAwardMapper.selectOne(wrapper);
                String pcAward = signAward.getPcAward();
                Integer pcMinute = signAward.getPcMinute();
                Integer phoneMinute = signAward.getPhoneMinute();
//                String phoneAward = signAward.getPhoneAward();
                list.add(new SignInDto(i, 0,pcAward,pcMinute,phoneMinute));
            }
        } else {
            // 签过到
            Integer continueDays = signIn.getContinueDays();

            // 1, 前六天的 flag是要固定的
            if (continueDays <= 7) {
                for (int i = 1; i < 8; i++) {
                    if (i <= continueDays) {
                        QueryWrapper<SignAward> wrapper = new QueryWrapper<>();
                        wrapper.eq("continue_days",i);
                        SignAward signAward = signAwardMapper.selectOne(wrapper);
                        String pcAward = signAward.getPcAward();
                        Integer pcMinute = signAward.getPcMinute();
                        Integer phoneMinute = signAward.getPhoneMinute();
//                        String phoneAward = signAward.getPhoneAward();

                        list.add(new SignInDto(i, 1,pcAward,pcMinute,phoneMinute));
                    } else {
                        QueryWrapper<SignAward> wrapper = new QueryWrapper<>();
                        wrapper.eq("continue_days",i);
                        SignAward signAward = signAwardMapper.selectOne(wrapper);
                        String pcAward = signAward.getPcAward();
                        Integer pcMinute = signAward.getPcMinute();
                        Integer phoneMinute = signAward.getPhoneMinute();
//                        String phoneAward = signAward.getPhoneAward();
                        list.add(new SignInDto(i, 0,pcAward,pcMinute,phoneMinute));
                    }
                }
            } else {
                // 2, 6天后的签到天数要跟随日期增加
                for (int i = 5; i > -2; i--) {
                    if (i > -1) {
                        QueryWrapper<SignAward> wrapper = new QueryWrapper<>();
                        wrapper.eq("continue_days",i);
                        SignAward signAward = signAwardMapper.selectOne(wrapper);
                        String pcAward = signAward.getPcAward();
                        Integer pcMinute = signAward.getPcMinute();
                        Integer phoneMinute = signAward.getPhoneMinute();

                        list.add(new SignInDto(continueDays - i, 1,pcAward,pcMinute,phoneMinute));
                    } else {
                        QueryWrapper<SignAward> wrapper = new QueryWrapper<>();
                        wrapper.eq("continue_days",i);
                        SignAward signAward = signAwardMapper.selectOne(wrapper);
                        String pcAward = signAward.getPcAward();
                        Integer pcMinute = signAward.getPcMinute();
                        Integer phoneMinute = signAward.getPhoneMinute();
//                        String phoneAward = signAward.getPhoneAward();
                        list.add(new SignInDto(continueDays + 1, 0,pcAward,pcMinute,phoneMinute));
                    }
                }
            }
        }
        HashMap<Object, Object> map = new HashMap<>();
        map.put("list",list);

        if(null!=signIn){
            map.put("continueDays",signIn.getContinueDays());
            Date updateTime = signIn.getUpdateTime();
            Date  currTime =   new Date();
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            if(fmt.format(currTime).equals(fmt.format(updateTime))){
                map.put("isSign",true);
            }else {
                map.put("isSign",false);
            }
        }else {
            map.put("continueDays",0);
            map.put("isSign",false);
        }
        //  查奖励信息
        QueryWrapper<SignAward> wrapper = new QueryWrapper<>();
        wrapper.eq("continue_days",map.get("continueDays"));
        SignAward signAward = signAwardMapper.selectOne(wrapper);
        map.put("signAward",signAward);
      //查可用时间
        String userUseableTime = ydwChargeService.getUserUseableTime(userId);
        JSONObject jsonObject = JSONObject.parseObject(userUseableTime);
        Object data = jsonObject.get("data");
        map.put("userUseableTime",data);
        return ResultInfo.success(map);
    }
}
