package com.ydw.resource.service;

import com.ydw.resource.model.db.Task;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.resource.model.vo.AppInfo;
import com.ydw.resource.model.vo.DeviceInfo;

import java.util.List;

/**
 * <p>
 * 任务表 服务类
 * </p>
 *
 * @author xulh
 * @since 2021-05-06
 */
public interface ITaskService extends IService<Task> {

    List<Task> getTurnOnTask();

    List<Task> getPreResTask();

    void runTurnOnTask(String deviceId);

    void runPreResTask(String deviceId);

    void runPreResTask(DeviceInfo deviceInfo);
}
