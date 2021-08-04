package com.ydw.authentication;

import com.alibaba.fastjson.JSON;
import com.ydw.authentication.dao.UserInfoMapper;
import com.ydw.authentication.model.db.UserInfo;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AuthenticationApplication.class})
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
//@WebAppConfiguration
public class Test {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserInfoMapper userInfoMapper;

    @org.junit.Test
//    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void trancTest() throws Exception{
        String userId = "6724883686447504106";
        UserInfo userInfo = userInfoMapper.selectById(userId);
        logger.info(JSON.toJSONString(userInfo));
        userInfo = userInfoMapper.selectById(userId);
        logger.info(JSON.toJSONString(userInfo));
    }

}
