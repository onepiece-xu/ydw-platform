package com.ydw.recharge.model.enums;

public enum PayTypeEnum {

    alipay(1),wxpay(2);

    public int code;

    private PayTypeEnum(int code){
        this.code = code;
    }
}
