package com.ydw.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.platform.model.db.Enterprise;

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
