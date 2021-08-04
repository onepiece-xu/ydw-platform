package com.ydw.authentication.controller;


import com.ydw.authentication.model.constant.Constant;
import com.ydw.authentication.model.db.UserApprove;
import com.ydw.authentication.model.db.UserInfo;
import com.ydw.authentication.model.vo.LoginVO;
import com.ydw.authentication.service.ILoginService;
import com.ydw.authentication.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private ILoginService loginServiceImpl;

	/**
	 * 给saas平台登录
	 * @param loginVO
	 * @throws
	 * @author xulh 2020/10/14 17:23
	 **/
	@PostMapping("/saasLogin")
	public ResultInfo saasLogin(@RequestBody LoginVO loginVO){
		return loginServiceImpl.saasLogin(loginVO);
	}

    /**
     * 检查token并获取用户id
     * @param token
     * @return
     */
	@GetMapping("/checkToken")
	public ResultInfo checkToken(@RequestParam String token){
		return loginServiceImpl.checkToken(token);
	}

    /**
     * 获取用户详情
     * @param
     * @return
     */
	@GetMapping("/getUserInfo")
	public ResultInfo getUserInfo(@RequestParam(value = "token") String token){
		return loginServiceImpl.getUserInfo(token);
	}
	/**
	 * 获取用户getIdentification
	 * @param
	 * @return
	 */
	@GetMapping(value = "/getIdentification")
	public  ResultInfo getIdentification(@RequestParam(value = "token") String token){
		return loginServiceImpl.getIdentification(token);
	}


	/**
	 * 开放平台登录
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/openLogin", method = RequestMethod.POST)
	@ResponseBody
	public ResultInfo openLogin(HttpServletRequest request, HttpServletResponse response, @RequestBody UserApprove user) {
		return loginServiceImpl.openLogin(request,response, user);
	}

	/**
	 * 管理后台登录
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/backStageLogin", method = RequestMethod.POST)
	@ResponseBody
	public ResultInfo backStageLogin(HttpServletRequest request, HttpServletResponse response, @RequestBody UserInfo user) {
		return loginServiceImpl.backStageLogin(request, response,user);
	}

	@PostMapping("/logout")
	public ResultInfo logout(HttpServletRequest request){
		String token = request.getHeader(Constant.PAAS_AUTHORIZATION_HEADER_NAME);
		return loginServiceImpl.logout(token);
	}

    /**
     * 边缘节点登录获取token
     * @param clusterId
     * @return
     */
    @PostMapping("/clusterLogin")
    public ResultInfo clusterLogin(@RequestParam String clusterId){
        return loginServiceImpl.clusterLogin(clusterId);
    }
}
