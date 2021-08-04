package com.ydw.user.controller;


import com.ydw.user.model.db.Task;
import com.ydw.user.service.ITaskService;
import com.ydw.user.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


/**
 * <p>
 * 任务表 前端控制器
 * </p>
 *
 * @author heao
 * @since 2021-05-26
 */
@RestController
@RequestMapping("/task")
public class TaskController extends BaseController{

    @Autowired
    private ITaskService taskService;

    @GetMapping("/getTaskList")
    public ResultInfo getTaskList(@RequestParam(required = false) String search){
        return  taskService.getTaskList(search, buildPage());
    }

    @PostMapping("/addTask")
    public ResultInfo addTask(@RequestBody Task task){
        return  taskService.addTask(task);
    }

    @PostMapping("/updateTask")
    public ResultInfo updateTask(@RequestBody Task task){
        return  taskService.updateTask(task);
    }

    @PostMapping("/delTasks")
    public ResultInfo delTasks(@RequestBody ArrayList<String> ids){
        return  taskService.delTasks(ids);
    }

    @PostMapping("/enableTask")
    public ResultInfo enableTask(@RequestBody ArrayList<String> ids){
        return  taskService.enableTask(ids);
    }

    @PostMapping("/disableTask")
    public ResultInfo disableTask(@RequestBody ArrayList<String> ids){
        return  taskService.disableTask(ids);
    }
}

