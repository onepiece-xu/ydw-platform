package com.ydw.platform.service;

public interface IPlayCardChargeService {

    boolean playCardChargeAble(String account);

    void startPlayCardCharge(String connectId, String account);

    void endPlayCardCharge(String connectId, String account);
}
