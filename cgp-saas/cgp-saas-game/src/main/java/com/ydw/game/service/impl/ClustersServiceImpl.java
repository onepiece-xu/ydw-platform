package com.ydw.game.service.impl;

import com.ydw.game.model.db.Clusters;
import com.ydw.game.model.vo.ResultInfo;
import com.ydw.game.dao.ClustersMapper;
import com.ydw.game.service.IClustersService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulh
 * @since 2020-08-04
 */
@Service
public class ClustersServiceImpl extends ServiceImpl<ClustersMapper, Clusters> implements IClustersService {

	@Override
	public ResultInfo getRegions() {
		List<Clusters> selectList = list(new QueryWrapper<>());
		return ResultInfo.success(selectList);
	}

}
