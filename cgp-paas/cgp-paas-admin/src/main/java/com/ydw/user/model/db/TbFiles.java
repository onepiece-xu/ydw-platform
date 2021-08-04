package com.ydw.user.model.db;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author heao
 * @since 2020-05-04
 */
public class TbFiles implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 文件名
     */
    private String name;
    /**
     * 大小
     */
    private Integer size;
    /**
     * 文件服务器存放路径
     */
    private String fileServerPath;
    /**
     * 文件客户机存放路径
     */
    private String fileClientPath;
    /**
     * 对应用户id
     */
    private String identification;
    /**
     * 0正常 1删除
     */
    private Boolean valid;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getFileServerPath() {
        return fileServerPath;
    }

    public void setFileServerPath(String fileServerPath) {
        this.fileServerPath = fileServerPath;
    }

    public String getFileClientPath() {
        return fileClientPath;
    }

    public void setFileClientPath(String fileClientPath) {
        this.fileClientPath = fileClientPath;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "TbFiles{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", fileServerPath='" + fileServerPath + '\'' +
                ", fileClientPath='" + fileClientPath + '\'' +
                ", identification='" + identification + '\'' +
                ", valid=" + valid +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
