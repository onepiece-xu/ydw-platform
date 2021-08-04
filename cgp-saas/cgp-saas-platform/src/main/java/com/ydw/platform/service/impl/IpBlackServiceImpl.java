package com.ydw.platform.service.impl;

import com.ydw.platform.model.db.IpBlack;
import com.ydw.platform.dao.IpBlackMapper;
import com.ydw.platform.service.IIpBlackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
