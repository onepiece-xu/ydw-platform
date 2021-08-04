package com.ydw.resource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ydw.resource.dao.DevicesMapper;
import com.ydw.resource.dao.UserAppsMapper;
import com.ydw.resource.model.db.Task;
import com.ydw.resource.dao.TaskMapper;
import com.ydw.resource.model.vo.AppInfo;
import com.ydw.resource.model.vo.DeviceInfo;
import com.ydw.resource.service.IConnectService;
import com.ydw.resource.service.ITaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 任务表 服务实现类
 * </p>
 *
 * @author xulh
 * @since 2021-05-06
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {

    @Autowired
    private DevicesMapper devMapper;

    @Autowired
    private UserAppsMapper userAppsMapper;

    @Autowired
    private IConnectService connectService;

    @Override
    public List<Task> getTurnOnTask() {
        return getTaskByScene(0);
    }

    @Override
    public List<Task> getPreResTask() {
        return getTaskByScene(1);
    }

    private List<Task> getTaskByScene(int scene){
        QueryWrapper<Task> qw = new QueryWrapper<>();
        qw.eq("scene", scene);
        qw.eq("valid", true);
        return list(qw);
    }

    @Override
    public void runTurnOnTask(String deviceId) {
        DeviceInfo deviceInfo = devMapper.getDeviceInfo(deviceId);
        //查看有没有开机后挂起任务
        List<Task> tasks = getTurnOnTask();
        if (tasks != null && !tasks.isEmpty()){
            for (Task task : tasks) {
                runTask(task, deviceInfo);
            }
        }
    }

    @Override
    public void runPreResTask(String deviceId){
        DeviceInfo deviceInfo = devMapper.getDeviceInfo(deviceId);
        runPreResTask(deviceInfo);
    }

    @Override
    public void runPreResTask(DeviceInfo deviceInfo){
        //查看有没有启动游戏前挂起任务
        List<Task> tasks = getPreResTask();
        if (tasks != null && !tasks.isEmpty()){
            for (Task task : tasks) {
                runTask(task, deviceInfo);
            }
        }
    }

    private void runTask(Task task, DeviceInfo deviceInfo){
        if (task.getType() == 1){
            String appId = task.getContent();
            AppInfo appInfo = userAppsMapper.getUserAppInfo(appId, deviceInfo.getClusterId());
            connectService.openApp(deviceInfo,appInfo, null, null);
        }else if(task.getType() == 2){
            String appId = task.getContent();
            AppInfo appInfo = userAppsMapper.getUserAppInfo(appId, deviceInfo.getClusterId());
            connectService.closeApp(deviceInfo,appInfo, null);
        }
    }
}
