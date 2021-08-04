package com.ydw.platform.controller;

import com.ydw.platform.model.vo.ApplyParameter;
import com.ydw.platform.model.vo.ConnectVO;
import com.ydw.platform.model.vo.PrepareParams;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.IResourceService;
import com.ydw.platform.service.IUserHangupService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;


@RestController
@RequestMapping("/resource")
public class ResourceController extends BaseController {

    @Autowired
    private IResourceService resourceService;

    @Autowired
    private IUserHangupService userHangupService;

    /**
     * 是否可以租号申请
     * @param appId
     * @return
     */
    @GetMapping(value = "rentalApplyAble")
    public ResultInfo rentalApplyAble(String appId){
        return resourceService.rentalApplyAble(super.getAccount(), appId);
    }

    /**
     * 申请设备
     * @param param
     * @return
     */
    @PostMapping(value = "/apply")
    public ResultInfo apply(@RequestBody ApplyParameter param) {
        param.setAccount(super.getAccount());
        param.setCustomIp(getIpAddr());
        return resourceService.apply(param);
    }

    /**
     * 申请设备(租号玩)
     * @param param
     * @return
     */
    @PostMapping(value = "/rentalApply")
    public ResultInfo rentalApply(@RequestBody ApplyParameter param) {
        param.setAccount(super.getAccount());
        param.setCustomIp(getIpAddr());
        return resourceService.rentalApply(param);
    }

    /**
     * 获取排名
     * @return
     */
    @GetMapping(value = "/getRank")
    public ResultInfo getRank() {
        return resourceService.getRank(super.getAccount());
    }

    /**
     * 取消连接
     * @param param
     * @return
     */
    @PostMapping(value = "/cancelConnect")
    public ResultInfo cancelConnect(@RequestBody PrepareParams param) {
        param.setAccount(super.getAccount());
        param.setCustomIp(getIpAddr());
        return resourceService.cancelConnect(param);
    }

    /**
     * 准备设备
     * @param param
     * @return
     */
    @PostMapping(value = "/prepare")
    public ResultInfo apply(@RequestBody PrepareParams param) {
        param.setAccount(super.getAccount());
        param.setCustomIp(getIpAddr());
        return resourceService.prepare(param);
    }

    /**
     * 在网吧中申请设备
     * @param param
     * @return
     */
    @PostMapping(value = "/applyByNetbar")
    public ResultInfo applyByNetbar(@RequestBody ApplyParameter param) {
        param.setAccount(super.getAccount());
        param.setCustomIp(getIpAddr());
        return resourceService.applyByNetbar(param);
    }

    /**
     * 退出排队
     * @return
     */
    @GetMapping(value = "/queueOut")
    public ResultInfo queueOut() {
        return resourceService.queueOut(super.getAccount());
    }

    /**
     * 重连设备
     * @return
     */
    @PostMapping(value = "/reconnect")
    public ResultInfo reconnect(@RequestBody ConnectVO vo) {
    	vo.setAccount(getAccount());
        return resourceService.reconnect(vo);
    }

    /**
     * 释放设备
     * @return
     */
    @PostMapping(value = "/release")
    public ResultInfo release(@RequestBody ConnectVO vo) {
        if(StringUtils.isBlank(vo.getAccount())){
            vo.setAccount(getAccount());
        }
        return resourceService.release(vo);
    }

    /**
     * 获取用户连接状态
     * @return
     */
    @GetMapping(value = "/getUserConnectStatus")
    public ResultInfo getUserConnectStatus() {
        return resourceService.getUserConnectStatus(super.getAccount());
    }

    /**
     * 挂机
     * @param map
     * @return
     */
    @PostMapping("/hangup")
    public ResultInfo hangup(@RequestBody HashMap<String,Object> map){
        //连接id
        String connectId = (String)map.get("connectId");
        //挂机时长
        int hangupDuration = (int)map.get("hangupDuration");
        return resourceService.hangup(super.getAccount(),connectId,hangupDuration);
    }

    /**
     * 取消挂机
     * @return
     */
    @GetMapping("/cancelHangup")
    public ResultInfo cancelHangup(@RequestParam String connectId){
        return resourceService.cancelHangup(connectId);
    }

    /**
     * 取消挂机
     * @return
     */
    @GetMapping("/getHangupEndTime")
    public ResultInfo getHangupEndTime(@RequestParam String connectId){
        return resourceService.getHangupEndTime(connectId);
    }

    /**
     * 挂机结束，由定时任务模块触发
     * @param map
     * @return
     */
    @PostMapping("/hangupEnd")
    public ResultInfo hangupEnd(@RequestBody HashMap<String,Object> map){
        //连接id
        String connectId = (String)map.get("connectId");
        //挂机时长
        Date hangupEndTime = new Date((long)map.get("hangupEndTime"));
        return resourceService.hangupEnd(connectId,hangupEndTime);
    }

    /**
     * 获取用户单日剩余可挂机时长
     * @return
     */
    @GetMapping("/getUserOneDaySubHangupDuration")
    public ResultInfo getUserOneDaySubHangupDuration(){
        int userOneDaySubHangupDuration = userHangupService.getUserOneDaySubHangupDuration(getAccount());
        return ResultInfo.success(userOneDaySubHangupDuration);
    }

    /**
     * 禁用用户，释放资源，由auth模块调用
     * @return
     */
    @GetMapping("/releaseUserRes")
    public ResultInfo releaseUserRes(@RequestParam String userId){
        return resourceService.releaseUserRes(userId);
    }

    /**
     * 打开游戏
     * @param param
     * @return
     */
    @PostMapping("/openApp")
    public ResultInfo openApp(@RequestBody HashMap<String, String> param){
        String appId = param.get("appId");
        String deviceId = param.get("deviceId");
        String connectId = param.get("connectId");
        String account = super.getAccount();
        return resourceService.openApp(account, connectId, deviceId, appId);
    }

    /**
     * 排队通知短信回调，给schedulejob使用
     * @param param
     * @return
     */
    @PostMapping("/queueMessageNotice")
    public ResultInfo queueMessageNotice(@RequestBody HashMap<String, String> param){
        String connectId = param.get("connectId");
        String appName = param.get("appName");
        String account = param.get("account");
        return resourceService.queueMessageNotice(account, appName, connectId);
    }
}
