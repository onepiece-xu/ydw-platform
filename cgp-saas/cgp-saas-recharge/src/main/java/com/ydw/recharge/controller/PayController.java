package com.ydw.recharge.controller;

import com.ydw.recharge.model.enums.PayTypeEnum;
import com.ydw.recharge.model.vo.ResultInfo;
import com.ydw.recharge.service.IPayService;
import com.ydw.recharge.util.QRBarCodeUtil;
import com.ydw.recharge.util.wxpay.WXPayUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Map;

@RestController
@RequestMapping("/pay")
public class PayController extends BaseController{

    @Resource(name = "alipayService")
    private IPayService alipayService;

    @Resource(name = "wxpayService")
    private IPayService wxpayService;

    /**
     * 获取支付码
     * @param cardId
     */
    @GetMapping("/getPayCode")
    public void getAliPayCode(@RequestParam String cardId, @RequestParam int type, @RequestParam(required = false) String bagId){
        String userAccount = super.getAccount();
        String payUrl = null;
        File logo = null;
        try {
            if(type == PayTypeEnum.alipay.code){
                payUrl = alipayService.getPayUrl(cardId, userAccount, bagId);
                ClassPathResource resource = new ClassPathResource("pics/alipay.png");
                logo = resource.getFile();
            }else if(type == PayTypeEnum.wxpay.code){
                payUrl = wxpayService.getPayUrl(cardId, userAccount, bagId);
                ClassPathResource resource = new ClassPathResource("pics/wechat.png");
                logo = resource.getFile();
            }
            QRBarCodeUtil.createCodeToOutputStream(payUrl, logo, super.getResponse().getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 支付宝支付结果回调
     * @param map
     */
    @PostMapping("/alipayNotify")
    public void alipayNotify(@RequestParam Map<String,String> map){
        alipayService.payNotify(map);
    }

    /**
     * 微信支付结果回调
     */
    @RequestMapping("/wxpayNotify")
    public String wxpayNotify(){
        String xmlBack = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[报文为空]]></return_msg></xml> ";
        try {
            ServletInputStream inputStream = super.getRequest().getInputStream();
            // 将InputStream转换成String
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            Map<String, String> map = WXPayUtil.xmlToMap(sb.toString());
            xmlBack = wxpayService.payNotify(map);
        }catch (Exception e){

        }
        return xmlBack;
    }

    /**
     * 创建订单
     * @param cardId
     * @return
     */
    @GetMapping("/createOrder")
    public ResultInfo createOrder(@RequestParam String cardId,@RequestParam int type,@RequestParam(required = false) String bagId){
        String userAccount = super.getAccount();
        if(type == PayTypeEnum.alipay.code){
            return alipayService.createOrder(cardId, userAccount, bagId);
        }else if(type == PayTypeEnum.wxpay.code){
            return wxpayService.createOrder(cardId, userAccount, bagId);
        }
        return ResultInfo.fail("type类型错误！type=" + type);
    }
}
