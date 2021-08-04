package com.ydw.recharge.service;

import com.ydw.recharge.model.vo.ResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@FeignClient(value = "cgp-saas-authentication", path = "/cgp-saas-authentication")
public interface IYdwAuthenticationService {

	//获取用户信息
	@GetMapping(value = "/login/getUserInfo")
	String getUserInfoByToken(@RequestParam(value = "token") String token);

    //获取用户在线token
    @GetMapping(value = "/login/getUserOnlineTokens")
    String getUserOnlineTokens(@RequestParam(value = "userId") String userId);

    //获取用户上级
    @GetMapping(value = "/user/getUserRecommender")
    ResultInfo getUserRecommender(@RequestParam(value = "userId") String userId);

	/**
	 * 用户提现成功
	 * @return
	 */
	@PostMapping("/user/userWithdrawSuccess")
	ResultInfo userWithdrawSuccess(@RequestBody HashMap<String, Object> param);

	/**
	 * 用户充值奖励
	 * @return
	 */
	@PostMapping("/user/userDistributionAward")
	ResultInfo userDistributionAward(@RequestBody HashMap<String, Object> param);
}
