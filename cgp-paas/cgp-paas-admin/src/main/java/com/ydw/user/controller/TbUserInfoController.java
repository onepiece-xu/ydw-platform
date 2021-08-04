package com.ydw.user.controller;


import com.ydw.user.model.db.TbUserInfo;
import com.ydw.user.model.vo.CreateUserVO;
import com.ydw.user.model.vo.modifyPasswordVO;
import com.ydw.user.service.ITbUserInfoService;
import com.ydw.user.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author heao
 * @since 2020-04-13
 */
@RestController
@RequestMapping("/v1/userInfo")
public class TbUserInfoController extends BaseController {

    @Autowired
    private ITbUserInfoService iTbUserInfoService;


    /**
     *获取服务相关企业用户列表
     */
    @RequestMapping(value = "/serviceUserList", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo serviceUserList(){

        return  iTbUserInfoService.serviceUserList();
    }

    /**
     * 创建用
     * @param request
     * @param user
     * @return
     */
    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo createUser(HttpServletRequest request, @RequestBody CreateUserVO user) {

           return iTbUserInfoService.createUser(request, user);
    }

    /**
     * 修改用户信息
     * @param request
     * @param user
     * @return
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo updateUser(HttpServletRequest request, @RequestBody CreateUserVO user) {

        return iTbUserInfoService.updateUser(request, user);
    }

    /**
     * 重置密码
     * @param request
     * @param user
     * @return
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo resetPassword(HttpServletRequest request, @RequestBody TbUserInfo user) {

        return iTbUserInfoService.resetPassword(request, user);
    }

    /**
     * 删除用户
     * @param request
     * @param ids
     * @return
     */
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo deleteUser(HttpServletRequest request, @RequestBody List<String> ids) {

        return iTbUserInfoService.deleteUser(request, ids);
    }

    /**
     * 用户列表查询
     * @param request
     * @param
     * @param loginName
     * @param identification
     * @param type
     * @param
     * @param mobileNumber
     * @param telNumber
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/userList", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getUserList(HttpServletRequest request,
                                  @RequestParam(required = false) String enterpriseName,
                                  @RequestParam(required = false) String loginName,
                                  @RequestParam(required = false) String identification,
                                  @RequestParam(required = false) Integer type,
                                  @RequestParam(required = false) Integer schStatus,
                                  @RequestParam(required = false) String mobileNumber,
                                  @RequestParam(required = false) String telNumber,
                                  @RequestParam(required = false) String search,
                                  @RequestParam(required = false) Integer pageNum,
                                  @RequestParam(required = false) Integer pageSize) {
        return iTbUserInfoService.getUserList(request,enterpriseName,loginName,identification,type,schStatus
                ,mobileNumber,telNumber,search,buildPage());
    }

    /**
     * 用户详情
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/getUserInfo/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getUserInfo(HttpServletRequest request,@PathVariable String id){
        return iTbUserInfoService.getUserInfo(request,id);
    }

    /**
     * 启用/禁用
     * @param request
     * @param type
     * @param ids
     * @return
     */
    @RequestMapping(value = "/disableUser/{type}", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo disableUser(HttpServletRequest request ,@PathVariable Integer type,@RequestBody List<String> ids ) {

        return iTbUserInfoService.disableUser(request,type, ids);
    }

    /**
     * 修改密码
     * @param request
     * @param user
     * @return
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo updatePassword(HttpServletRequest request ,@RequestBody modifyPasswordVO user ) {

        return iTbUserInfoService.updatePassword(request, user);
    }
}

