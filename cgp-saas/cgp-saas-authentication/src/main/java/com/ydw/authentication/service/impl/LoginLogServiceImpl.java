package com.ydw.authentication.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.authentication.dao.LoginLogMapper;
import com.ydw.authentication.model.db.LoginLog;
import com.ydw.authentication.service.ILoginLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulh
 * @since 2021-03-01
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {

}
