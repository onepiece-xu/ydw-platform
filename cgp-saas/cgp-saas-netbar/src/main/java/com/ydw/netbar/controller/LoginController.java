package com.ydw.netbar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ydw.netbar.model.db.User;
import com.ydw.netbar.model.vo.ResultInfo;
import com.ydw.netbar.service.ILoginService;

@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController extends BaseController{
	
	@Autowired
	private ILoginService loginService;

	@PostMapping("/localLogin")
	public ResultInfo localLogin(@RequestBody User user){
		return loginService.localLogin(user);
	}
	
	public ResultInfo register(){
		return null;
	}
	
	public ResultInfo openLogin(){
		return null;
	}
}
