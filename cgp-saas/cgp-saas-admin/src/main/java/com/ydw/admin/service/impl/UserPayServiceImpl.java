package com.ydw.admin.service.impl;

import com.ydw.admin.model.db.UserPay;
import com.ydw.admin.dao.UserPayMapper;
import com.ydw.admin.service.IUserPayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户支付绑定 服务实现类
 * </p>
 *
 * @author xulh
 * @since 2021-01-04
 */
@Service
public class UserPayServiceImpl extends ServiceImpl<UserPayMapper, UserPay> implements IUserPayService {

}
