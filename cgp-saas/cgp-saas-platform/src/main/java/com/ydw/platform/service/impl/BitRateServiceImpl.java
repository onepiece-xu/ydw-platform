package com.ydw.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.platform.model.db.BitRate;
import com.ydw.platform.dao.BitRateMapper;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.IBitRateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hea
 * @since 2020-10-07
 */
@Service
public class BitRateServiceImpl extends ServiceImpl<BitRateMapper, BitRate> implements IBitRateService {

    @Autowired
    private BitRateMapper bitRateMapper;

    @Override
    public ResultInfo getList(Integer type, Page buildPage) {
        QueryWrapper<BitRate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("valid",1);
        Page page = bitRateMapper.selectPage(buildPage, queryWrapper);
        return ResultInfo.success(page);
    }

    @Override
    public ResultInfo addBitRate(BitRate bitRate) {
        bitRate.setValid(true);
        bitRateMapper.insert(bitRate);
        return ResultInfo.success("添加成功！");
    }

    @Override
    public ResultInfo updateBitRate(BitRate bitRate) {
        bitRateMapper.updateById(bitRate);
        return ResultInfo.success("编辑成功！");
    }

    @Override
    public ResultInfo deleteBitRate(List<Integer> ids) {

        for (Integer id:ids) {
            BitRate bitRate = bitRateMapper.selectById(id);
            bitRate.setValid(true);
            bitRateMapper.updateById(bitRate);
        }
        return ResultInfo.success("删除成功！");
    }
}
