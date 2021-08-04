package com.ydw.recharge.model.enums;

public enum OrderStatusEnum {

    unpay(0),payed(1),cancelpay(2),payerror(3);

    public int code;

    OrderStatusEnum(int code){
        this.code = code;
    }
}
