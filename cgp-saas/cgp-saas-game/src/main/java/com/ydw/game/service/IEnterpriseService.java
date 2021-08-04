package com.ydw.game.service;

import com.ydw.game.model.db.Enterprise;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.game.model.vo.ResultInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2020-08-04
 */
public interface IEnterpriseService extends IService<Enterprise> {

    String getEnterpriseId();
}
