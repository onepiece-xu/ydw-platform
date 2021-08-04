package com.ydw.authentication.service.impl;

import com.ydw.authentication.model.db.UserRelational;
import com.ydw.authentication.dao.UserRelationalMapper;
import com.ydw.authentication.service.IUserRelationalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户关系树 服务实现类
 * </p>
 *
 * @author xulh
 * @since 2020-12-21
 */
@Service
public class UserRelationalServiceImpl extends ServiceImpl<UserRelationalMapper, UserRelational> implements IUserRelationalService {

}
