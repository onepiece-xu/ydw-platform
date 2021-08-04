package com.ydw.user.service.impl;

import com.ydw.user.model.db.AreaCluster;
import com.ydw.user.dao.AreaClusterMapper;
import com.ydw.user.service.IAreaClusterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author heao
 * @since 2020-09-04
 */
@Service
public class AreaClusterServiceImpl extends ServiceImpl<AreaClusterMapper, AreaCluster> implements IAreaClusterService {
        @Autowired
        private AreaClusterMapper areaClusterMapper;

}
