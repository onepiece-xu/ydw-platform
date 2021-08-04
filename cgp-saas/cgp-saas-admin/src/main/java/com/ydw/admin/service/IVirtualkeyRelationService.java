package com.ydw.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.admin.model.db.VirtualkeyRelation;
import com.ydw.admin.model.vo.ResultInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2021-01-13
 */
public interface IVirtualkeyRelationService extends IService<VirtualkeyRelation> {

    ResultInfo bindApp(String configId, String[] appIds);

    ResultInfo unbindApp(String configId, String[] appIds);

    ResultInfo getBindedApp(Page buildPage, String id, String search);

    ResultInfo getUnBindApp(Page buildPage, String id, String search);
}
