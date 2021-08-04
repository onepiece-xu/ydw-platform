package com.ydw.open.controller;


import com.alibaba.fastjson.JSONObject;
import com.ydw.open.model.db.TbUserInfo;
import com.ydw.open.model.vo.modifyPasswordVO;
import com.ydw.open.service.ITbUserInfoService;
import com.ydw.open.utils.ResultInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
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
@RequestMapping("/userInfo")
public class TbUserInfoController extends BaseController {

    @Autowired
    private ITbUserInfoService iTbUserInfoService;

    /**
     * 创建用
     * @param request
     * @param user
     * @return
     */
    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo createUser(HttpServletRequest request, @RequestBody TbUserInfo user) {

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
    public ResultInfo updateUser(HttpServletRequest request, @RequestBody TbUserInfo user) {

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

    /**
     * 获取当前用户信息
     */
    @RequestMapping(value = "/getInfo", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getInfo(HttpServletRequest request) {

        return iTbUserInfoService.getInfo(request);
    }

    /**
     * 获取当新注册用户统计
     */
    @GetMapping(value = "/getNewRegister")
    public ResultInfo getNewRegister(@RequestParam(required = false) String startTime,
                                     @RequestParam(required = false) String endTime,
                                     @RequestParam(required = false) Integer pageNum,
                                     @RequestParam(required = false) Integer pageSize) {
        String identification = super.getIdentification();
        return iTbUserInfoService.getNewRegister(identification, startTime, endTime, pageNum, pageSize);
    }

    /**
     * 获取当新注册用户统计
     */
    @GetMapping(value = "/getNewRegisterCount")
    public ResultInfo getNewRegisterCount(@RequestParam(required = false) String startTime,
                                     @RequestParam(required = false) String endTime) {
        String identification = super.getIdentification();
        return iTbUserInfoService.getNewRegisterCount(identification, startTime, endTime);
    }

    /**
     * 获取当新注册用户统计
     */
    @GetMapping(value = "/getOnlineList")
    public ResultInfo getOnlineList(@RequestParam(required = false) String search,
                                    @RequestParam(required = false) Integer client,
                                    @RequestParam(required = false) Integer pageNum,
                                    @RequestParam(required = false) Integer pageSize) {
        String identification = super.getIdentification();
        return iTbUserInfoService.getOnlineList(identification, search, client, pageNum, pageSize);
    }

    /**
     * 获取用户充值
     */
    @GetMapping(value = "/getRechargeList")
    public ResultInfo getOnlineList(@RequestParam(required = false) String search,
                                    @RequestParam(required = false) Integer pageNum,
                                    @RequestParam(required = false) Integer pageSize) {
        String identification = super.getIdentification();
        return iTbUserInfoService.getRechargeList(identification, search, pageNum, pageSize);
    }

    /**
     * 获取用户充值统计
     */
    @GetMapping(value = "/getRechargeCount")
    public ResultInfo getRechargeCount(@RequestParam(required = false) String search) {
        String identification = super.getIdentification();
        return iTbUserInfoService.getRechargeCount(identification, search);
    }

    /**
     * 获取用户余额
     */
    @GetMapping(value = "/getUserBalance")
    public ResultInfo getUserBalance() {
        String identification = super.getIdentification();
        return iTbUserInfoService.getUserBalance(identification);
    }

    /**
     * 获取申请提现
     */
    @PostMapping(value = "/applyWithdraw")
    public ResultInfo applyWithdraw(@RequestBody JSONObject param) {
        String identification = super.getIdentification();
        BigDecimal amount = param.getBigDecimal("amount");
        return iTbUserInfoService.applyWithdraw(identification, amount);
    }

    /**
     * 获取用户提现记录
     */
    @GetMapping(value = "/getWithdrawRecord")
    public ResultInfo getWithdrawRecord(@RequestParam(required = false) Integer status,
                                        @RequestParam(required = false) String beginDate,
                                        @RequestParam(required = false) String endDate) {
        String identification = super.getIdentification();
        return iTbUserInfoService.getWithdrawRecord(identification, status, beginDate, endDate);
    }

    /**
     * 获取用户绑定支付宝账号
     */
    @GetMapping(value = "/getUserPay")
    public ResultInfo getUserPay() {
        String identification = super.getIdentification();
        return iTbUserInfoService.getUserPay(identification);
    }

    /**
     * 获取用户充值统计
     */
    @GetMapping(value = "/bindUserPay")
    public ResultInfo bindUserPay(@RequestParam String payAccount) {
        String identification = super.getIdentification();
        return iTbUserInfoService.bindUserPay(identification, payAccount);
    }
}

