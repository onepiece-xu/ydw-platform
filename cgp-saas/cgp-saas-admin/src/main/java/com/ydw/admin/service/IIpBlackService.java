package com.ydw.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.model.db.IpBlack;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.admin.model.vo.ResultInfo;

import java.util.List;

/**
 * <p>
 * ip黑名单 服务类
 * </p>
 *
 * @author xulh
 * @since 2021-05-10
 */
public interface IIpBlackService extends IService<IpBlack> {

    ResultInfo getIpBlackList(String search, Page buildPage);

    ResultInfo addIpBlack(String ip);

    ResultInfo delIpBlack(List<String> ids);
}
