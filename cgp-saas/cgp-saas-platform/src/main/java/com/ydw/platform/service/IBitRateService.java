package com.ydw.platform.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.platform.model.db.BitRate;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.platform.model.vo.ResultInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hea
 * @since 2020-10-07
 */
public interface IBitRateService extends IService<BitRate> {


    ResultInfo getList(Integer type, Page buildPage);

    ResultInfo addBitRate(BitRate bitRate);

    ResultInfo updateBitRate(BitRate bitRate);

    ResultInfo deleteBitRate(List<Integer> ids);
}
