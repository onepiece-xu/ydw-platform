package com.ydw.advert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ydw.advert.model.vo.Enterprise;
import com.ydw.advert.model.vo.ResultInfo;
import com.ydw.advert.service.ILoginService;

@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController extends BaseController{
	
	@Autowired
	private ILoginService loginService;

	@PostMapping("/login")
	public ResultInfo login(@RequestBody Enterprise enterprise){
		return loginService.login(enterprise);
	}
	
	@GetMapping("/refreshToken")
	public void refreshToken(){
		loginService.refreshToken(super.getToken());
	}
}
