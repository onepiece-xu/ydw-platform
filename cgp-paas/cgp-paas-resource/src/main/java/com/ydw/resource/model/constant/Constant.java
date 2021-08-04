package com.ydw.resource.model.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 常量
 *
 * @author xulh
 * @date 2018/9/3 16:03
 */
@Component
public class Constant {

    /**
     * 服务类型  TYPE
     */
    public static final Integer STREAM_TYPE_COMPUTER = 1;     ///云电脑
    public static final Integer STREAM_TYPE_GAME = 2;     ///云游戏

    public static final Integer DEVICE_STATUS_IDLE = 1;  // 空闲
    public static final Integer DEVICE_STATUS_USED = 2;  // 使用
    public static final Integer DEVICE_STATUS_ERROR = 3;  // 错误
    public static final Integer DEVICE_STATUS_INSTALLING = 4;  // 安装中
    public static final Integer DEVICE_STATUS_REBOOTING = 5;  // 重启中
    public static final Integer DEVICE_STATUS_PREPARING = 6;  // 准备中
    public static final Integer DEVICE_STATUS_CLOSED = 7;  //关机

    public static final String DEVICE_ENCODE_H264 = "264";   ///h264 编码
    public static final String DEVICE_ENCODE_H265 = "265";   ///h265 编码

    public static final Integer APP_INTALL_BATCH = 1;      ///批量安装
    public static final Integer APP_UNINTALL_BATCH = 2;      ///批量卸载
    public static final Integer APP_UNINTALL_SYNC = 3;      ///同步卸载，不上报设备状态

    //开启游戏以及流服务
    public static final String URL_EDGE_STARTSTREAM = "/cgp-edge/v1/device/startStream";
    //关闭游戏以及流服务
    public static final String URL_EDGE_STOPSTREAM = "/cgp-edge/v1/device/stopStream";
    //开启游戏以及流服务
    public static final String URL_EDGE_STARTAPP = "/cgp-edge/v1/device/startApp";
    //关闭游戏以及流服务
    public static final String URL_EDGE_STOPAPP = "/cgp-edge/v1/device/stopApp";
    //安装app
    public static final String URL_EDGE_INSTALLAPP = "/cgp-edge/v1/device/install";
    //卸载app
    public static final String URL_EDGE_UNINSTALLAPP = "/cgp-edge/v1/device/uninstall";
    //开启游戏以及流服务
    public static final String URL_EDGE_OPEN = "/cgp-edge/v1/device/open";
    //重启
    public static final String URL_EDGE_REBOOT = "/cgp-edge/v1/device/reboot";
    //重建
    public static final String URL_EDGE_REBUILD = "/cgp-edge/v1/device/rebuild";
    //关机
    public static final String URL_EDGE_SHUTDOWN = "/cgp-edge/v1/device/shutdown";
    //获取设备id
    public static final String URL_EDGE_SETID = "/cgp-edge/v1/device/setid";
    //设置设备id
    public static final String URL_EDGE_GETID = "/cgp-edge/v1/device/getid";
    //注册
    public static final String URL_EDGE_INIT = "/cgp-edge/v1/device/initDeviceInfo";
    //注册
    public static final String URL_EDGE_ISSUEDAPP = "/cgp-edge/v1/device/downLoadApp";


    public static final Integer CONNECT_TYPE_APK = 0;         ///客户端为apk类型
    public static final Integer CONNECT_TYPE_WEBRTC = 1;      ///客户端为webrtc类型

    public static final Integer  SAAS_TYPE_PLATFORM   = 0;        ///平台
    public static final Integer  SAAS_TYPE_ADVERT     = 1;        ///广告
    public static final Integer  SAAS_TYPE_GAME       = 2;        ///云游戏
    public static final Integer  SAAS_TYPE_NETBAR	  = 3;        ///云电脑

    public static final Integer CLIENT_TYPE_MOBILE = 0;             ///移动客户端
    public static final Integer CLIENT_TYPE_PC = 1;             ///pc 客户端

    //重启中的设备
    public static final String ZSET_REBOOTING = "zset_rebooting";

    //准备中的设备
    public static final String ZSET_PREPARING = "zset_preparing";

    /**
     * 请求头token
     */
    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    /**
     * 请求头identification
     */
    public static final String IDENTIFICATION_HEADER_NAME = "identification";

    /**
     * 请求头saas
     */
    public final static String SAAS_HEADER_NAME = "saas";

    /**
     * 申请中的用户详情
     */
    public final static String STR_APPLYING_USERDETAIL = "str_applying_userDetail_{0}";
}
