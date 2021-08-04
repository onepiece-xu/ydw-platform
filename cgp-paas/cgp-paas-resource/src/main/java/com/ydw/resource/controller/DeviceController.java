package com.ydw.resource.controller;

import com.ydw.resource.service.IDeviceService;
import com.ydw.resource.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/device")
public class DeviceController {

	@Autowired
	private IDeviceService deviceService;

	///重启
    @PostMapping(value = "/reboot")
    public ResultInfo reboot(@RequestBody String[] devids) {
        return deviceService.reboot(devids);
    }

    ///重建
    @PostMapping(value = "/rebuild")
    public ResultInfo rebuild(@RequestBody String[] devids) {
        return deviceService.rebuild(devids);
    }

    //释放
    @PostMapping(value = "/forcerelease")
    public ResultInfo forseRelease(@RequestBody String[] devids) {
        return deviceService.forseRelease(devids);
    }

    ///关机
    @PostMapping(value = "/shutdown")
    public ResultInfo shutdown(@RequestBody String[] devids) {
        return deviceService.shutdown(devids);
    }

    ///开机
    @PostMapping(value = "/open")
    public ResultInfo open(@RequestBody String[] devids) {
        return deviceService.open(devids);
    }

    ///启用
    @PostMapping(value = "/enable")
    public ResultInfo enable(@RequestBody String[] devids) {
        return deviceService.enable(devids);

    }

    /**
     * 禁用
     * @param
     * @param devids
     * @return
     */
    @PostMapping(value = "/disable")
    public ResultInfo disable(@RequestBody String[] devids) {
        return deviceService.disable(devids);
    }

    ///设置设置id
    @PostMapping(value = "/setid")
    public ResultInfo setId(@RequestBody String[] devids) {
        return deviceService.setId(devids);
    }

    ///设置设置id
    @GetMapping(value = "/getid/{devid}")
    public ResultInfo getId(@PathVariable String devid) {
        return deviceService.getId(devid);
    }
    
    /**
     * 初始化配置文件，arm专用
     * @param devids
     * @return
     */
    @PostMapping(value = "/initDeviceInfo")
    public ResultInfo initDeviceInfo(@RequestBody String[] devids) {
        return deviceService.initDeviceInfo(devids);
    }

    /**
     * 修改外网ip
     * @param map
     * @return
     */
    @PostMapping(value = "/updateExternalIp")
    public ResultInfo updateExternalIp(@RequestBody HashMap<String,String> map){
        String gateway = map.get("gateway");
        String newIp = map.get("newIp");
        return deviceService.updateExternalIp(gateway, newIp);
    }

    /**
     * 边缘节点上报设备状态
     * @param deviceId
     * @param macAddr
     * @param status
     * @return
     */
    @GetMapping(value = "/reportStatus")
    public ResultInfo reportStatus(@RequestParam(required = false) String deviceId,
                                   @RequestParam(required = false) String macAddr,
                                   @RequestParam Integer status){
        return deviceService.reportStatus(deviceId, macAddr, status);
    }
}
