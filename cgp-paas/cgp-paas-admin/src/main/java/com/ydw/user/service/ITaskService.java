package com.ydw.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.user.model.db.Task;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.user.utils.ResultInfo;

import java.util.ArrayList;

/**
 * <p>
 * 任务表 服务类
 * </p>
 *
 * @author heao
 * @since 2021-05-26
 */
public interface ITaskService extends IService<Task> {

    ResultInfo getTaskList(String search, Page buildPage);

    ResultInfo addTask(Task task);

    ResultInfo updateTask(Task task);

    ResultInfo delTasks(ArrayList<String> ids);

    ResultInfo enableTask(ArrayList<String> ids);

    ResultInfo disableTask(ArrayList<String> ids);
}
