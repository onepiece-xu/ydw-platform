package com.ydw.game.service;

import com.ydw.game.model.db.Clusters;
import com.ydw.game.model.vo.ResultInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2020-08-04
 */
public interface IClustersService extends IService<Clusters> {

	ResultInfo getRegions();

}
