package com.ydw.admin.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "cgp-saas-charge", path = "/cgp-saas-charge")
public interface IYdwChargeService {

    /**
     * 计费
     * @param connectId
     * @return
     */
    @GetMapping("/charge/startCharge")
    String startCharge(@RequestParam(value = "connectId") String connectId,
                       @RequestParam(value = "userId") String userId);

    /**
     * 结束计费
     * @param connectId
     * @return
     */
    @GetMapping("/charge/endCharge")
    String endCharge(@RequestParam(value = "connectId") String connectId);

    /**
     * 获取用户可用时长
     * @param userId
     * @return
     */
    @GetMapping("/cardBag/getUserUseableTime")
    String getUserUseableTime(@RequestParam(value = "userId") String userId);

    /**
     * 是否能计费
     * @param userId
     * @return
     */
    @GetMapping("/charge/chargeAble")
    String chargeAble(@RequestParam(value = "userId") String userId);
}
