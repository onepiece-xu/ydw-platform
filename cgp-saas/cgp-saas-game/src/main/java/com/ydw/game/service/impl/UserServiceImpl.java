package com.ydw.game.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.game.model.db.User;
import com.ydw.game.dao.UserMapper;
import com.ydw.game.model.vo.ResultInfo;
import com.ydw.game.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
