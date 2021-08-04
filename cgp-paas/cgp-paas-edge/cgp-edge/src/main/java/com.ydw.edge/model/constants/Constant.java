package com.ydw.edge.model.constants;

/**
 * 常量
 * @author huzh
 * @date 2020/5/9
 */
public class Constant {

    /**
     * 系统命令
     */
    public static final Integer COMMAND_SYSTEM_OPEN     = 0x0101;   ///开机
    public static final Integer COMMAND_SYSTEM_CLOSE    = 0x0102;   ///关机
    public static final Integer COMMAND_SYSTEM_REBOOT   = 0x0103;   ///重启
    public static final Integer COMMAND_SYSTEM_REBUILD  = 0x0104;   ///重置
    public static final Integer COMMAND_SYSTEM_SETID    = 0x0105;   ///设置 ID
    public static final Integer COMMAND_SYSTEM_GETID    = 0x0106;   ///设置 ID
    public static final Integer COMMAND_SYSTEM_INIT     = 0x0107;   ///初始化arm板信息
    public static final Integer COMMAND_STREAM_START    = 0x0201;   ///开启流服务
    public static final Integer COMMAND_STREAM_STOP     = 0x0202;   ///关闭流服务
    public static final Integer COMMAND_APP_START       = 0x0203;   ///开启应用
    public static final Integer COMMAND_APP_STOP        = 0x0204;   ///关闭应用

    public static final Integer COMMAND_SYSTEM_STATUS   = 0x0302;   ///系统状态
    public static final Integer COMMAND_STREAM_STATUS   = 0x0301;   ///流状态

    /**
     * 流参数名称定义
     */
    public static final String DEVICEID_PARAMETER       ="deviceid:" ;       ///应用编号
    public static final String APP_PARAMETER            ="app:" ;       ///应用编号
    public static final String APPID                    ="appId:" ;       ///游戏系统脚本
    public static final String USERID                   ="userId:" ;       ///游戏系统脚本
    public static final String SAVEPATH                 ="savePath:" ;       ///游戏系统脚本
    public static final String LUNCHER                  ="luncher:" ;       ///游戏系统脚本
    
    public static final String PATH_PARAMETER	        ="path:" ;       ///应用编号
    public static final String VIDEO_PARAMETER	        ="video:";       ///输出分辨率
    public static final String VIDEO_WIDTH_PARAMETER    ="width:";       ///输出分辨率宽
    public static final String VIDEO_HEIGTH_PARAMETER	="heigth:";       ///输出分辨率高

    public static final String BITRATE_PARAMETER        ="bitrate:";     ///码率，单位M
    public static final String FPS_PARAMETER            ="fps:";         ///帧率
    public static final String CODEC_PARAMETER          ="codec:";       ///0 --h264，1-h265
    public static final String TOKEN_PARAMETER          ="token:";       ///api认证token
    public static final String CONNECTID_PARAMETER      ="connectid:" ;       ///连接 id，用于连接认证
    public static final String WEBRTC_PARAMETER	        ="webrtc:"  ;   //0/1是否启动webrtc，默认不启动，不启动时，不传web rtc 参数
    public static final String SIGNALSERVER_PARAMETER	="signal:" ;      ///webrtc信令服务器参数
    public static final String TURNSERVER_PARAMETER 	="turn:"	;		///webrtc trun服务器参数
    public static final String TURNUSER_PARAMETER   	="username:";		///webrtc trun服务器用户名
    public static final String TURNPASSWORD_PARAMETER	="password:" ;		///webrtc trun服务器密码
    public static final String RETURN_PARAMETER         ="return:";      ///0 --成功，1-失败
    
    /**
     *编码格式
     */
    public static final Integer VIDEO_CODE_H264 = 0;     //h264
    public static final Integer VIDEO_CODE_H265 = 1;     //h265


    public static final Integer TYPE_SHORT_LENGTH = 2;    ///short 类型长度
    public static final Integer TYPE_INT_LENGTH   = 4;    ///int   类型长度

    public static final Integer LENTH_DEVICE_OPERATION = 20;   ///设备操作消息长度


    public  static final Integer DEVICE_ARCH_ARM   =  0;     ///arm设备
    public  static final Integer DEVICE_ARCH_X86   =  1;     ///x86 设备


    public  static final Integer DEVICE_STATUS_IDLE       = 1;  // 空闲
    public  static final Integer DEVICE_STATUS_USED       = 2;  // 使用
    public  static final Integer DEVICE_STATUS_ERROR      = 3;  // 错误
    public  static final Integer DEVICE_STATUS_INSTALLING = 4;  // 安装中
    public  static final Integer DEVICE_STATUS_REBOOTING  = 5;  // 重启中
    public  static final Integer DEVICE_STATUS_DELETED    = 6;  // 删除
    public  static final Integer DEVICE_STATUS_CLOSED     = 7;  //关机

    public  static final String  ARM_AGENT_PACKAGE        = "com.ydw.encoded";    ///arm设备代理程序包名
    public  static final String CONFIG_NAME = "EncodedConfig";   //配置文件名称
}
