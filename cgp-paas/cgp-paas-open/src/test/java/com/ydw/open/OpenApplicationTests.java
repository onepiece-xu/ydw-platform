package com.ydw.open;

import java.util.ArrayList;
import java.util.List;

import com.ydw.open.model.vo.TrendVO;
import org.junit.Test;
//import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.ydw.open.dao.TbDeviceUsedMapper;
@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("com.ydw.open.dao")
public class OpenApplicationTests {

    @Autowired
    private TbDeviceUsedMapper tbDeviceUsedMapper;

    @Test
    public void get(){
    	List<String> appIds = new ArrayList<>();
    	appIds.add("6666671283120699174");
    	appIds.add("6666671282109871894");
    	List<String> list = new ArrayList<>();
    	list.add("2020-05-22");
		list.add("2020-05-23");
		list.add("2020-05-24");
		list.add("2020-05-25");
		list.add("2020-05-26");
		list.add("2020-05-27");
		list.add("2020-05-28");
		list.add("2020-05-29");
		list.add("2020-05-30");
		list.add("2020-05-31");
		list.add("2020-06-01");
    	List<TrendVO> appsConnectionCount = tbDeviceUsedMapper.getAppsConnectionCount("91110108MA01QB042H", appIds, list);
    	System.out.println(JSON.toJSONString(appsConnectionCount));
    }
}
