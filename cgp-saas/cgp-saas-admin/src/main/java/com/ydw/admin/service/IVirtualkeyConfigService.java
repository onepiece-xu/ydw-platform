package com.ydw.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.admin.model.db.VirtualkeyConfig;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.model.vo.VirtualkeyConfigVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2021-01-13
 */
public interface IVirtualkeyConfigService extends IService<VirtualkeyConfig> {

    ResultInfo updateVirtualkeyConfig(VirtualkeyConfigVO vo);

    ResultInfo delVirtualkeyConfig(List<String> configIds);

    ResultInfo getVirtualkeyConfigs(IPage page, String search);

    ResultInfo uploadVirtualkeyConfig(VirtualkeyConfigVO vo);
}
