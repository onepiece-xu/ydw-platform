package com.ydw.resource.model.db;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2021-05-06
 */
@TableName("tb_task_cluster")
public class TaskCluster implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 集群id
     */
    private String clusterId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    @Override
    public String toString() {
        return "TaskCluster{" +
        "id=" + id +
        ", taskId=" + taskId +
        ", clusterId=" + clusterId +
        "}";
    }
}
