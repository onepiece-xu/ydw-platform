package com.ydw.recharge;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.factory.Factory.Payment;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;

public class Main {
    public static void main(String[] args) throws Exception {
        // 1. 设置参数（全局只需设置一次）
        Factory.setOptions(getOptions());
        try {
            // 2. 发起API调用（以创建当面付收款二维码为例）
            AlipayTradePrecreateResponse response = Payment.FaceToFace()
                    .preCreate("Apple iPhone11 128G", "223456789012334", "5799.00");
            // 3. 处理响应或异常
            if (ResponseChecker.success(response)) {
                System.out.println("调用成功");
            } else {
                System.err.println("调用失败，原因：" + response.msg + "，" + response.subMsg);
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static Config getOptions() {
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = "openapi.alipaydev.com";
        config.signType = "RSA2";

        config.appId = "2021000116661286";

        // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
        config.merchantPrivateKey = "MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQDPPDyD9xpNve/bdDiTIfmh"
        		+ "IGQQ+o5i/TBC+4i5JtaDNKOwY6SXriM4+wSd8w0bCPv2Ib7hNSKgW1tTMQlQypcayeWk3+mGVuOPIZZRqRZobCXv"
        		+ "S8HiQFhoTnn0T+ejZN8ym+XNI/g8TfI5I4IpENxKj7zkTYbzNa11wr9R2u0csIrLtIw9j1Syj6Ba0rVGHdNX4zSQF"
        		+ "1gBA2578mn5YVYkvJ2ZNiDDbO1941HoptJjV1a3iWlkpfSspU+Aqb+yTp2MrUwSVdSWfZRHPWoJm6W6XAkiMWGXh2"
        		+ "9VVs2aCrfRIg98ZJqkJLryJ/SZLfavcGumCx6uWSYR9lcLVRzCHYdVAgMBAAECggEBAKDl0MpY2ThQ5uCJLL+3mjwq"
        		+ "jObN0i0zDPyElNPEPrqh7REvXARUNI6x/hSwN5Ws9QtVigEKozbDl3VQ1j2+/XDUHpNTMdmWrplbZ31Bfd9XUGCpfg"
        		+ "AvGzTo8TDptRwcXlrJxbLvYXVYw17vnchKie7uCKdtNgCwxVtv9+ESzBP/6sXqLBet2dBdfBcsn0iew3NTvSjm6E1k"
        		+ "i2Uon/Q8VGcdf7sq8qKheXDI1qWzrJ86vEUtLUSrt1w3/3ISLhW7dVlMMHEuDpdW2tZ50J3Ykbd+HR9FpqpXZETy1er"
        		+ "LK9iQasSj0WszVHjEM8xSCDAw9Qwbbh+ZihXaY8nTFUJP7IUCgYEA+4tZyumyW8mgX67L62Fx6IQrudXNSPoPpdQMLb"
        		+ "0a8lTKbQNHS6dIaCmBql5KXPgGPWhM8bJulTH4HMAnRulYxntHZnSdfOEngVnsMemRHItScywSWNxa1IN2HR9nSqPBF"
        		+ "DmEp+oDWB6ASELGrhhFvIrv8DbJpwNJJ5REVtQZiR8CgYEA0uf2Z2GmGFah3HaXlTSxFsYOYpHmHScym91yHu4BAdL"
        		+ "Iu9/uxehtSn/Df4KKiEV7Ejx1K+HtQQA2JyJ894pMtSEJhCp4EiFtoP62Maba250hQmCBi1mTHcyQdAlkTCOXiFbkC"
        		+ "xAJ30G2iElkzPbF36f52Vb+31oAIC82Frpf/QsCgYEAq+Hms37I/2thCMC6Wta1LTrTXiK4KbulYZzpzX9AoIJL5/2rh"
        		+ "AiEs5hZH/9aNPjN2rwgh69zXMNOVOcAb7YXkVi/y0S4MJLxmbPGb7y4CZcx+b0NtGauMjS6SuAghqIq1xUOjmabbmU5"
        		+ "Jydp96BiVplhpvTpChR2snLf160K3eUCgYEAm6T+DgtdVUbRyu5mLYGmUwU05n9/j/I2ghpptvO0IJFa/HNlNf6ycTFa"
        		+ "qEZBUBHeonWljw37VmX+kOD90nzo4R8Rc80XsWGkExUGhhv6Feqe+epYj+s6C9rEnC22LA50LPTa9GMQtgc72tY3Ip/XJ"
        		+ "ZhEZaWzEEfOIfJVe25tmkMCgYEAzrpZyuVH1w0S801cmm8I3jbOO1i+BijOAJd2CcpXoso5eGYvfxlZkgI9aYh4mOX"
        		+ "dnkfY4SsFk5F0FTE3c7GsGbXNA6vCzIZAcIUiC1Q+tBnj4hS03DQRnmIoyg9H4tRjjX8KfvblDukAvlHqhQhzTJ4s+ge"
        		+ "jvtfUOcnLC4ZKtdY=";

        //注：证书文件路径支持设置为文件系统中的路径或CLASS_PATH中的路径，优先从文件系统中加载，加载失败后会继续尝试从CLASS_PATH中加载
//        config.merchantCertPath = "<-- 请填写您的应用公钥证书文件路径，例如：/foo/appCertPublicKey_2019051064521003.crt -->";
//        config.alipayCertPath = "<-- 请填写您的支付宝公钥证书文件路径，例如：/foo/alipayCertPublicKey_RSA2.crt -->";
//        config.alipayRootCertPath = "<-- 请填写您的支付宝根证书文件路径，例如：/foo/alipayRootCert.crt -->";

        //注：如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
         config.alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAy72MXYJsVZBXVPrxD5W09t+8YCUo+zq1xQa6YJmcNAGM+QdWzvzyVNPJvnTnlAHQb6KyvMusNybiC/OvsWZQ7biJywZCGwGfl3WLFLXLGzkRvLW7jtBRzAZbalAc9IcWz1hmOyoMYo2YA2dzssFmwJv7ziPZqyw+KmTScMz4S9WogTSyqzOw5HQypONgOChuMZklmLSo+dWP5S8zjDKbPAOvYd2QLfLp9mLvc3D6J0bM04xAviml1I699EMfDRV/ZmXPyHtXWW04PGEk3nk8qVYiDLYa8CT5zX5ypSHN31nHaS0iuzW1IUTUlEvsQ/iXpwmD8ehJVUJagNN+1eIrgwIDAQAB";

        //可设置异步通知接收服务地址（可选）
        config.notifyUrl = "http://826sap.natappfree.cc/cgp-saas-recharge/recharge/pay";

        //可设置AES密钥，调用AES加解密相关接口时需要（可选）
//        config.encryptKey = "<-- 请填写您的AES密钥，例如：aa4BtZ4tspm2wnXLb1ThQA== -->";

        return config;
    }
}