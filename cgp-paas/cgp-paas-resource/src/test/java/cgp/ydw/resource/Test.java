package cgp.ydw.resource;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ydw.resource.service.impl.DeviceAppsServiceImpl;
import com.ydw.resource.utils.HttpUtil;
import com.ydw.resource.utils.RedisUtil;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;import com.baomidou.mybatisplus.core.toolkit.Sequence;
import com.ydw.resource.ResourceApplication;
import com.ydw.resource.dao.DeviceAppsMapper;
import com.ydw.resource.dao.DevicesMapper;
import com.ydw.resource.dao.UserAppsMapper;
import com.ydw.resource.model.db.DeviceApps;
import com.ydw.resource.model.db.Devices;
import com.ydw.resource.model.db.UserApps;
import com.ydw.resource.model.vo.DeviceInfo;
import com.ydw.resource.model.vo.StreamVO;
import com.ydw.resource.service.impl.ConnectServiceImpl;
import com.ydw.resource.service.impl.DeviceServiceImpl;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ResourceApplication.class})
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
public class Test {
	
	@Autowired
    private ConnectServiceImpl connectService;
	
	@Autowired
    private DeviceServiceImpl deviceService;

	@Autowired
	private DeviceAppsServiceImpl deviceAppsService;
	
	@Autowired
    private DevicesMapper devicesMapper; 
	
	@Autowired
    private DeviceAppsMapper appMapper;

	@Autowired
	RedisUtil redisUtil;
	
//    @Ignore("not ready yet")
	@org.junit.Test
    public void testGetEntFileById(){
//    	StreamVO vo = new StreamVO("6701043005703560817", "6669081243857164325", "6666671282365724442", "6ebedd5f7f134402e178a93ad886bfa3",
//				2, 1, 1);
//    	connectService.prepareRes(vo);
//    	connectService.openStream(vo);
    }

	@org.junit.Test
	public void testUninstall(){
		deviceService.disable(new String[]{"6669081243051857947","6669081243148326940"});
	}


	@org.junit.Test
    public void testEnable(){
		deviceAppsService.checkDownApps("6723432240988411815");
    }
	
	@org.junit.Test
    public void testInit(){
		deviceService.initDeviceInfo(new String[]{"6669081242284300306"});
    }
	
	@org.junit.Test
    public void test() throws Exception{
		QueryWrapper<Devices> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("status",6);
		queryWrapper.eq("valid", true);
		List<Devices> list = deviceService.list(queryWrapper);
		String[] strings = list.stream().map(Devices::getId).toArray(String[]::new);
		HashMap<String, String> headers = new HashMap<>();
		headers.put("Authorization","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZGVudGlmaWNhdGlvbiI6IjkxMTEwMTA4TUEwMVFCMDQyRzciLCJzZWNyZXRLZXkiOiIzNjdhYzY5MzMwZWE3YWU2NTc0MmEwMWFjYTAxNTJiOTk2NGEzMTMzMmVlM2Q2MzgzZjljYzA5MjY0YTQ5NjBmIiwidGltZSI6MTYyNDMyNjc1MzQ5MiwiZXhwIjoxNjI0MzMwMzUzLCJhY2NvdW50IjoiNjY2NDEwMjY1NDA4NDcwNzU0MCJ9.zsaWF2DnOljsjfTB7B6ZWxLCrB8baatlrYWJydZu19o");
		String s = HttpUtil.doJsonPost("https://api.yidianwan.cn/cgp-paas-resource/device/reboot", headers, strings);
		System.out.println(s);
		Thread.sleep(6000);
	}

	public static void main(String[] args) {
		String str = "{\"name\":\"z\",\"age\":12}";
		P p = JSON.parseObject(str, P.class);
		System.out.println(p.age);
		System.out.println(p.name);
	}

	static class P{
		String name;
		private int age;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}
	}
}
