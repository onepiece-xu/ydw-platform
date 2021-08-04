package com.saas.charge;

import com.ydw.charge.ChargeApplication;
import com.ydw.charge.service.ICardBagService;
import com.ydw.charge.service.IChargeService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ChargeApplication.class})
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
public class JunitTest {
	
	@Autowired
    private ICardBagService cardBagService;

    @Autowired
    private IChargeService chargeService;

    @org.junit.Test
    public void testConsumeCardBags(){
        cardBagService.consumeCardBags("6722101411999822629",1);
    }

    @org.junit.Test
    public void testStartCharge(){
        chargeService.startCharge("6722101411999822629","6725020851034774122");
    }
}
