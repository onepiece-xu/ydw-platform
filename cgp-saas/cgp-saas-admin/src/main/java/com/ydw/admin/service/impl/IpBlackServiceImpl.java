package com.ydw.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.model.db.IpBlack;
import com.ydw.admin.dao.IpBlackMapper;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IIpBlackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.admin.util.SequenceGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * ip黑名单 服务实现类
 * </p>
 *
 * @author xulh
 * @since 2021-05-10
 */
@Service
public class IpBlackServiceImpl extends ServiceImpl<IpBlackMapper, IpBlack> implements IIpBlackService {

    @Override
    public ResultInfo getIpBlackList(String search, Page buildPage) {
        QueryWrapper<IpBlack> qw = new QueryWrapper<>();
        if (StringUtils.isNotBlank(search)){
            qw.like("ip", search);
        }
        Page page = super.page(buildPage, qw);
        return ResultInfo.success(page);
    }

    @Override
    public ResultInfo addIpBlack(String ip) {
        IpBlack ipBlack = new IpBlack();
        ipBlack.setId(SequenceGenerator.sequence());
        ipBlack.setIp(ip);
        ipBlack.setCreateTime(new Date());
        save(ipBlack);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo delIpBlack(List<String> ids) {
        for (String id : ids) {
            super.removeById(id);
        }
        return ResultInfo.success();
    }
}
