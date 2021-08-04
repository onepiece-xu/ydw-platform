package com.ydw.game;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.ydw.admin.dao.UserMapper;
import com.ydw.admin.model.db.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
public class Test {
	
	@Autowired
    private UserMapper userMapper;
    @Ignore("not ready yet")
	@org.junit.Test
    public void testGetEntFileById(){
        for(int i = 100 ; i < 1100 ; i++){
        	System.out.println("11");
        	System.out.println(String.format(",", "aaa"));
//        	User user = new User();
//        	String j = i + "";
//        	user.setId(j);
//        	user.setLoginname(j);
//        	user.setPassword("123456");
//        	userMapper.insert(user);
        }
    }
    public static void main(String[] args) {
    	LocalDateTime parse = LocalDateTime.parse("2020-08-27T16:25:38.159");
    	System.out.println(parse);
	}
 
}
