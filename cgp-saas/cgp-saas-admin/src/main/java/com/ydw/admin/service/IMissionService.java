package com.ydw.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.model.db.Mission;
import com.ydw.admin.model.vo.ResultInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2021-06-01
 */
public interface IMissionService extends IService<Mission> {


    ResultInfo clearMission();
}
