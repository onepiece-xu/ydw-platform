package com.ydw.platform.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.platform.model.db.Mission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.platform.model.vo.MissionVO;
import com.ydw.platform.model.vo.ResultInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2021-06-01
 */
public interface IMissionService extends IService<Mission> {

    ResultInfo getMissionList(String userId,Page buildPage);

    ResultInfo changeStatus(String userId,MissionVO mission);
}
