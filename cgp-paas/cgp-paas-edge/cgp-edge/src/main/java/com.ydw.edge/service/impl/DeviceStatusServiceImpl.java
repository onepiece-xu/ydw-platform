package com.ydw.edge.service.impl;

import java.util.HashMap;

import com.ydw.edge.service.IYdwPaasService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ydw.edge.model.vo.DeviceStatus;
import com.ydw.edge.model.vo.ResultInfo;
import com.ydw.edge.service.IDeviceStatusService;

@Service
public class DeviceStatusServiceImpl implements IDeviceStatusService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IYdwPaasService ydwPaasService;

    /**********************/
    /*设备状态上报          */
    /**********************/
    @Override
    public ResultInfo updateStatus(DeviceStatus deviceStatus) {
        try {
        	logger.info("device {} macAddr {} report status {}" , deviceStatus.getDevid(), deviceStatus.getMacAddr(), deviceStatus.getStatus());
            ydwPaasService.reportDevStatus(deviceStatus.getDevid(), deviceStatus.getMacAddr(),deviceStatus.getStatus());
        }
        catch (Exception ex)
        {
            logger.error("report status error:" + ex.getMessage());
        }
        return ResultInfo.success();
    }

    /****************************
     * 设备异常退出上报
     * @return
     */
    @Override
    public ResultInfo abnormal (String errCode, String connectId){
        try {
            logger.info("connectId {} abnormal errCode {}" , connectId, errCode);
            ydwPaasService.abnormal(connectId,errCode);
        }
        catch (Exception ex)
        {
            logger.error("report abnormal error:" + ex.getMessage());
        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo normal(String connectId) {
        try {
            logger.info("connectId {} normal" , connectId);
            ydwPaasService.normal(connectId);
        }
        catch (Exception ex)
        {
            logger.error("report abnormal error:" + ex.getMessage());
        }
        return ResultInfo.success();
    }
}
