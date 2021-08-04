package com.ydw.user.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.user.dao.ClusterConfigMapper;
import com.ydw.user.model.db.ClusterConfig;
import com.ydw.user.service.IClusterConfigService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author heao
 * @since 2020-08-07
 */
@Service
public class ClusterConfigServiceImpl extends ServiceImpl<ClusterConfigMapper, ClusterConfig> implements IClusterConfigService {

}
