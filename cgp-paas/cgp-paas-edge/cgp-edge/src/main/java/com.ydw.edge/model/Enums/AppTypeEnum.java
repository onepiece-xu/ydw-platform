package com.ydw.edge.model.Enums;

/**
 * app类型
 */
public enum AppTypeEnum {
    ARM(0),X86(1),GAME(2),COMPUTER(3),PHONE(4);

    public int type;

    private AppTypeEnum(int type){
        this.type = type;
    }
}
