package com.ydw.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.user.model.db.Task;
import com.ydw.user.dao.TaskMapper;
import com.ydw.user.service.ITaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.user.utils.ResultInfo;
import com.ydw.user.utils.SequenceGenerator;
import com.ydw.user.utils.StringUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * <p>
 * 任务表 服务实现类
 * </p>
 *
 * @author heao
 * @since 2021-05-26
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {

    @Override
    public ResultInfo getTaskList(String search, Page buildPage) {
        QueryWrapper<Task> qw = new QueryWrapper<>();
        if (StringUtil.isNotBlank(search)){
            qw.like("name", search);
        }
        Page page = page(buildPage, qw);
        return ResultInfo.success(page);
    }

    @Override
    public ResultInfo addTask(Task task) {
        task.setId(SequenceGenerator.sequence());
        task.setCreateTime(LocalDateTime.now());
        save(task);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo updateTask(Task task) {
        UpdateWrapper<Task> uw = new UpdateWrapper<>();
        uw.eq("id", task.getId());
        uw.set("name", task.getName());
        uw.set("description", task.getDescription());
        uw.set("content", task.getContent());
        uw.set("scene",task.getScene());
        uw.set("type", task.getType());
        update(uw);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo delTasks(ArrayList<String> ids) {
        UpdateWrapper<Task> uw = new UpdateWrapper<>();
        uw.in("id" ,ids);
        remove(uw);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo enableTask(ArrayList<String> ids) {
        UpdateWrapper<Task> uw = new UpdateWrapper<>();
        uw.in("id" ,ids);
        uw.set("valid", true);
        update(uw);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo disableTask(ArrayList<String> ids) {
        UpdateWrapper<Task> uw = new UpdateWrapper<>();
        uw.in("id", ids);
        uw.set("valid", false);
        update(uw);
        return ResultInfo.success();
    }
}
