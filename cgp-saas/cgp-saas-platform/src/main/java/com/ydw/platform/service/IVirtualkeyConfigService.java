package com.ydw.platform.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.platform.model.db.VirtualkeyConfig;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.model.vo.VirtualkeyConfigVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2021-01-13
 */
public interface IVirtualkeyConfigService extends IService<VirtualkeyConfig> {

    ResultInfo uploadVirtualkeyConfig(VirtualkeyConfigVO vo);

    ResultInfo updateVirtualkeyConfig(VirtualkeyConfigVO vo);

    ResultInfo delVirtualkeyConfig(String configId, String userId);

    ResultInfo getVirtualkeyConfigs(String userId, String appId, int keyType, Page page);
}
