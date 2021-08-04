package com.ydw.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.platform.dao.UserMissionMapper;
import com.ydw.platform.model.db.Mission;
import com.ydw.platform.dao.MissionMapper;
import com.ydw.platform.model.db.UserMission;
import com.ydw.platform.model.vo.MissionVO;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.IMissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.platform.service.IPostMessageService;
import com.ydw.platform.service.ITemplateTypeService;
import com.ydw.platform.service.IYdwRechargeService;
import com.ydw.platform.util.SequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulh
 * @since 2021-06-01
 */
@Service
public class MissionServiceImpl extends ServiceImpl<MissionMapper, Mission> implements IMissionService {
    @Autowired
    private MissionMapper missionMapper;
    @Autowired
    private UserMissionMapper userMissionMapper;
    @Autowired
    private IYdwRechargeService iYdwRechargeService;
    @Value("${send.minute}")
    private String sendMinute;
    @Value("${send.amount}")
    private Double sendAmount;
    @Autowired
    private ITemplateTypeService templateTypeService;
    @Autowired
    private IPostMessageService iPostMessageService;
    @Override
    public ResultInfo getMissionList(String userId,Page buildPage) {
//        userId="777";
        QueryWrapper<UserMission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        Page<UserMission> selectPage = userMissionMapper.selectPage(buildPage, queryWrapper);
        List<UserMission>  records = selectPage.getRecords();
        int size = records.size();
        if(0==size){
            QueryWrapper<Mission> wrapper = new QueryWrapper<>();
            wrapper.eq("valid",1);
            List<Mission> missions = missionMapper.selectList(wrapper);
            //给当前用户添加任务
            for(Mission m:missions){
                UserMission userMission = new UserMission();
                userMission.setId(SequenceGenerator.sequence());
                userMission.setUserId(userId);
                userMission.setStatus(0);
                userMission.setMissionId(m.getId());
                userMissionMapper.insert(userMission);
            }
        }
        //查询出用户列表
        Page<MissionVO> missionList = missionMapper.getMissionList(userId, buildPage);
        return ResultInfo.success(missionList);
    }

    @Override
    public ResultInfo changeStatus(String userId,MissionVO mission) {
//        userId="6781168642329213938";
        Map<String,Object> message = new HashMap<>();
        message.put("userId",userId);

        Integer id = mission.getId();
        QueryWrapper<UserMission> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.eq("mission_id",id);
        UserMission userMission = userMissionMapper.selectOne(wrapper);
        Integer missionId = userMission.getMissionId();
        QueryWrapper<Mission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",missionId);
        queryWrapper.eq("valid",1);
        Mission selectOne = missionMapper.selectOne(queryWrapper);
        Integer type = selectOne.getType();
        String couponId = selectOne.getCouponId();
        Integer status = mission.getStatus();
        //当type为1时候给签到时间
        if(type==1){
            HashMap<String,String> params = new HashMap<>();
            params.put("userId", userId);
            params.put("time", sendMinute);

            if(1==status) {
                iYdwRechargeService.sendDuration(params);
                try {
                    //添加站内信
                    ResultInfo signin = templateTypeService.getTemplateId("day_share");
                    message.put("templateId", signin.getData());
                    //发送类型为系统通知2
                    message.put("type", 2);
                    iPostMessageService.add(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //当type为2 给优惠券
        if(type==2){
            HashMap<String,String> params = new HashMap<>();
            params.put("id", couponId);
            params.put("userId",userId);
            //查是否充满金额
            Double userRechargeAmount = missionMapper.getUserRechargeAmount(userId);
            if(null==userRechargeAmount){
                return ResultInfo.fail("领取失败！");
            }
            if(sendAmount>userRechargeAmount){
                return ResultInfo.fail("充值金额未达到赠送优惠券标准！");
            }
            if(1==status) {
                iYdwRechargeService.drawCoupon(params);
                try {
                    //添加站内信
                    ResultInfo signin = templateTypeService.getTemplateId("day_charge");
                    message.put("templateId", signin.getData());
                    //发送类型为系统通知2
                    message.put("type", 2);
                    iPostMessageService.add(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        userMission.setStatus(status);
        userMissionMapper.updateById(userMission);
        return ResultInfo.success();
    }
}
