package com.ydw.recharge;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ydw.recharge.model.db.Recharge;
import com.ydw.recharge.service.ICardBagService;
import com.ydw.recharge.service.IRechargeCardService;
import com.ydw.recharge.service.impl.AliPayService;
import com.ydw.recharge.service.impl.RechargeServiceImpl;
import com.ydw.recharge.service.impl.WXPayService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ReChargeApplication.class})
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
public class Test {

	@Autowired
    RechargeServiceImpl rechargeService;

	@Autowired
	AliPayService aliPayService;

	@Autowired
	WXPayService wxPayService;

	@Autowired
	IRechargeCardService rechargeCardService;

	@Autowired
	ICardBagService cardBagService;

	@org.junit.Test
	public void testUpdate() {
		UpdateWrapper<Recharge> updateWrapper = new UpdateWrapper<>();
		String sql = "cost = cost + " + 1 + ", final_cost = final_cost + " + 2;
		updateWrapper.setSql(sql);
		updateWrapper.eq("id", "6716637904732664811");
		rechargeService.update(updateWrapper);
	}

	@org.junit.Test
	public void comboCardTest(){
		cardBagService.createCardBag("abc", "31");
	}

	@org.junit.Test
	public void createOrder() {
		wxPayService.createOrder("4","abc", null);
	}

	@org.junit.Test
	public void getPayUrl() {
		wxPayService.getPayUrl("4","abc", null);
	}

	public static void main(String[] args) {
		BigDecimal bigDecimal1=new BigDecimal("1.65");
		BigDecimal bigDecimal2=new BigDecimal("0.65");
		bigDecimal1=bigDecimal1.multiply(bigDecimal2).setScale(2,BigDecimal.ROUND_HALF_DOWN);
		System.out.println(bigDecimal1);
	}
}