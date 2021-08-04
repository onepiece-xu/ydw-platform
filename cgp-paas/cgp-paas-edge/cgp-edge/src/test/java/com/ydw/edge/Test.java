package com.ydw.edge;

import com.ydw.edge.service.impl.ArmDeviceServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EdgeApplication.class})
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
public class Test {
	
	@Autowired
	ArmDeviceServiceImpl armDeviceServiceImpl;

    public static void main(String[] args) {
        String str = "  ";
        System.out.println(StringUtils.isBlank(str));
    }
}
