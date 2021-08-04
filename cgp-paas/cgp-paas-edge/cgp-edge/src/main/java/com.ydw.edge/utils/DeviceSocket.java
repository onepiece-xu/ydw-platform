package com.ydw.edge.utils;

import com.alibaba.fastjson.JSON;
import com.ydw.edge.model.constants.Constant;
import com.ydw.edge.model.vo.AppOperateParam;
import com.ydw.edge.model.vo.DeviceOperateParam;
import com.ydw.edge.model.vo.ScreenTransfer;
import com.ydw.edge.model.vo.StreamOperateParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Random;

public class DeviceSocket {

	private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * tcp的socket连接
     */
	private Socket sock;

    /**
     * 设备的基本操作信息参数
     */
	private DeviceOperateParam deviceOperateParam;

    /**
     * app的基本操作参数
     */
	private AppOperateParam appOperateParam;

    /**
     * 流服务的基本操作信息参数
     */
	private StreamOperateParam streamOperateParam;

	public DeviceSocket(DeviceOperateParam deviceOperateParam) {
		this.deviceOperateParam = deviceOperateParam;

	}

    public DeviceSocket(AppOperateParam appOperateParam) {
        this.deviceOperateParam = appOperateParam.getDeviceOperateParam();
        this.appOperateParam = appOperateParam;
    }

    public DeviceSocket(StreamOperateParam streamOperateParam) {
        this.deviceOperateParam = streamOperateParam.getDeviceOperateParam();
        this.streamOperateParam = streamOperateParam;
    }

    public DeviceSocket(DeviceOperateParam deviceOperateParam, AppOperateParam appOperateParam,
                        StreamOperateParam streamOperateParam) {
        this.deviceOperateParam = deviceOperateParam;
        this.appOperateParam = appOperateParam;
        this.streamOperateParam = streamOperateParam;
    }

	private boolean connect() {
		try {
			sock = new Socket();
			logger.info("connecting to {} port is {}" , deviceOperateParam.getInnerIp(), deviceOperateParam.getInnerPort());
            sock.connect(new InetSocketAddress(deviceOperateParam.getInnerIp(), deviceOperateParam.getInnerPort()));
			logger.info("connect to {} port is {} success" , deviceOperateParam.getInnerIp(), deviceOperateParam.getInnerPort());
		} catch (Exception se) {
			logger.error("connect to {} port is {} fail {}", deviceOperateParam.getInnerIp() ,deviceOperateParam.getInnerPort(), se.toString());
		}
		return sock.isConnected();
	}

	/// 输出命令参数数组
	private byte[] getCommandBytes(Integer cmd) {
	    int type = streamOperateParam == null ? (appOperateParam == null ? 2 : appOperateParam.getType()) : streamOperateParam.getType();
		byte[] byTypes = Utility.short2bytes((short)type);
		byte[] byCS = Utility.short2bytes((short)1);
		byte[] byCmd = Utility.int2bytes(cmd);
		byte[] byTask = Utility.int2bytes( new Random().nextInt());
		Long curTime = System.currentTimeMillis() / 1000;
		byte[] byTime = Utility.int2bytes(curTime.intValue());
        /// 拼参数
        StringBuilder args = new StringBuilder();

		//-------------------------------------------------设备参数-----------------------------------
        if (StringUtils.isNotBlank(deviceOperateParam.getDeviceId())) {
            args.append("|").append(Constant.DEVICEID_PARAMETER).append(deviceOperateParam.getDeviceId());
        }

		//-------------------------------------------------流服务参数-----------------------------------
        if (streamOperateParam != null){
            /// 视频参数，需要同时确定横 、竖屏
            if (streamOperateParam.getVideo() != null){
                args.append("|").append(Constant.VIDEO_PARAMETER).append(streamOperateParam.getVideo());
                ScreenTransfer sc = new ScreenTransfer(streamOperateParam.getVideo(), streamOperateParam.getScreen());
                args.append("|").append(Constant.VIDEO_WIDTH_PARAMETER).append(sc.getWidth());
                args.append("|").append(Constant.VIDEO_HEIGTH_PARAMETER).append(sc.getHeigth());
            }
            /// 视频参数，码率
            if (streamOperateParam.getSpeed() != null) {
                args.append("|").append(Constant.BITRATE_PARAMETER).append(streamOperateParam.getSpeed());
            }
            //  视频参数，帧率
            if (streamOperateParam.getFps() != null) {
                args.append("|").append(Constant.FPS_PARAMETER).append(streamOperateParam.getFps());
            }
            //   编码格式
            if (streamOperateParam.getCodec() != null) {
                args.append("|").append(Constant.CODEC_PARAMETER ).append(streamOperateParam.getCodec());
            }
            //   连接凭证
            if (StringUtils.isNotBlank(streamOperateParam.getToken())) {
                args.append("|").append(Constant.TOKEN_PARAMETER).append(streamOperateParam.getToken());
            }
            //   连接id
            if (StringUtils.isNotBlank(streamOperateParam.getConnectId())) {
                args.append("|").append(Constant.CONNECTID_PARAMETER).append(streamOperateParam.getConnectId());
            }
            //    webRtc相关参数
            if (streamOperateParam.getWebRTC()) {
                args.append("|").append(Constant.WEBRTC_PARAMETER).append(1);
                if (StringUtils.isNotBlank(streamOperateParam.getSignalServer())) {
                    args.append("|").append(Constant.SIGNALSERVER_PARAMETER).append(streamOperateParam.getSignalServer());
                }
                if (StringUtils.isNotBlank(streamOperateParam.getTurnServer())) {
                    args.append("|").append(Constant.TURNSERVER_PARAMETER).append(streamOperateParam.getTurnServer());
                }
                if (StringUtils.isNotBlank(streamOperateParam.getTurnUser())) {
                    args.append("|").append(Constant.TURNUSER_PARAMETER).append(streamOperateParam.getTurnUser());
                }
                if (StringUtils.isNotBlank(streamOperateParam.getTurnPassword())) {
                    args.append("|").append(Constant.TURNPASSWORD_PARAMETER).append(streamOperateParam.getTurnPassword());
                }
            }
        }

        //---------------------------------------------应用相关参数-----------------------------------
        if (appOperateParam != null){
			//    app的id
			if (StringUtils.isNotBlank(appOperateParam.getAppId())) {
				args.append("|").append(Constant.APPID).append(appOperateParam.getAppId());
			}
			//    用户id
			if (StringUtils.isNotBlank(appOperateParam.getUserId())) {
				args.append("|").append(Constant.USERID).append(appOperateParam.getUserId());
			}
			//    存档地址savePath
			if (StringUtils.isNotBlank(appOperateParam.getSavePath())) {
				args.append("|").append(Constant.SAVEPATH).append(appOperateParam.getSavePath());
			}
            //    app的文件名
            if (StringUtils.isNotBlank(appOperateParam.getPackageFileName())) {
                args.append("|").append(Constant.APP_PARAMETER).append(appOperateParam.getPackageFileName());
            }
            //    app路径
            if (StringUtils.isNotBlank(appOperateParam.getPackageFilePath())) {
                args.append("|").append(Constant.PATH_PARAMETER).append(appOperateParam.getPackageFilePath());
            }
            //    app的开启shell脚本
            if(StringUtils.isNotBlank(appOperateParam.getStartShell())){
                args.append("|").append(Constant.LUNCHER).append(appOperateParam.getStartShell());
            }
            //租号参数
			if (appOperateParam.getRentalParams() != null){
				Map<String, Object> rentalParams = (Map<String, Object>)JSON.parse(JSON.toJSONString(appOperateParam.getRentalParams()));
				for (Map.Entry<String, Object> entry : rentalParams.entrySet()) {
					args.append("|").append(entry.getKey()).append(":").append(entry.getValue());
				}
			}
        }
		logger.info("{} send type {} cmd {} args is {}", deviceOperateParam.getInnerIp() ,type, cmd , args);
		byte[] byParameter = args.toString().getBytes(Charset.forName("GBK"));

		Integer arglen = byParameter.length;
		byte[] byarglen = Utility.int2bytes(arglen);

		Integer len = Constant.LENTH_DEVICE_OPERATION + arglen;
		byte[] byResult = new byte[len];
		Integer pos = 0;
		System.arraycopy(byTypes, 0, byResult, pos, Constant.TYPE_SHORT_LENGTH);
		pos += Constant.TYPE_SHORT_LENGTH;

		System.arraycopy(byCS, 0, byResult, pos, Constant.TYPE_SHORT_LENGTH);
		pos += Constant.TYPE_SHORT_LENGTH;

		System.arraycopy(byCmd, 0, byResult, pos, Constant.TYPE_INT_LENGTH);
		pos += Constant.TYPE_INT_LENGTH;

		System.arraycopy(byTask, 0, byResult, pos, Constant.TYPE_INT_LENGTH);
		pos += Constant.TYPE_INT_LENGTH;

		System.arraycopy(byTime, 0, byResult, pos, Constant.TYPE_INT_LENGTH);
		pos += Constant.TYPE_INT_LENGTH;

		System.arraycopy(byarglen, 0, byResult, pos, Constant.TYPE_INT_LENGTH);
		pos += Constant.TYPE_INT_LENGTH;

		System.arraycopy(byParameter, 0, byResult, pos, arglen);

		return byResult;
	}

	/// 发送数据
	public boolean sendCommand(Integer command) {
	    for(int i = 0 ; i < 3 ; i++){
            if (connect()) {
                break;
            }else{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (!sock.isConnected()){
        	logger.info("发送command:{}时{}的socket连接失败", command, deviceOperateParam.getInnerIp());
        	return false;
		}
		try {
			byte[] bycmd = getCommandBytes(command);
			logger.info("sending to {} command {} ", deviceOperateParam.getInnerIp(), command);
			OutputStream outputStream = sock.getOutputStream();
			outputStream.write(bycmd);
			outputStream.flush();
			byte[] temp = new byte[1024];
			InputStream inputStream = sock.getInputStream();
			Integer len = inputStream.read(temp);
			logger.info("{} return byte length is {} !", deviceOperateParam.getInnerIp(), len);
			if(len == -1 || len == 0){
				logger.error("{} no msg return", deviceOperateParam.getInnerIp());
				return false;
			}
			byte[] data = new byte[len];
			System.arraycopy(temp, 0, data, 0, len);
			return isSuccess(data);
		} catch (Exception se) {
			logger.error("{} command {} fail {}" ,deviceOperateParam.getInnerIp(), command ,se.getMessage());
			return false;
		} finally {
            try {
				sock.shutdownOutput();
				logger.info("{} socket outputStream closed command {} ", deviceOperateParam.getInnerIp(), command);
				sock.shutdownInput();
				logger.info("{} socket inputStream closed command {} ", deviceOperateParam.getInnerIp(), command);
                sock.close();
                logger.info("{} socket closed command {} ", deviceOperateParam.getInnerIp(), command);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}

	/// 检查操作是否成功
	/// 按照协议数据把返回数据中
	private boolean isSuccess(byte[] data) throws UnsupportedEncodingException {
		Integer datalen = data.length;
		if (datalen <= Constant.LENTH_DEVICE_OPERATION) /// 消息太短，不是完整消息
		{
			logger.info("{} return msg is invalid , length {} too short!", deviceOperateParam.getInnerIp(), datalen);
			return false;
		}

		datalen = Constant.LENTH_DEVICE_OPERATION - Constant.TYPE_INT_LENGTH; /// 取参数长度

		byte[] byarglen = new byte[Constant.TYPE_INT_LENGTH];

		System.arraycopy(data, datalen, byarglen, 0, Constant.TYPE_INT_LENGTH);

		Integer arglen = Utility.bytes2int(byarglen);

		byte[] byarr = new byte[arglen];

		System.arraycopy(data, Constant.LENTH_DEVICE_OPERATION, byarr, 0, arglen);

		String appMsg = null;
		try {
			/// 取
			appMsg = new String(byarr, "GBK");
			logger.info("{} return msg is {}", deviceOperateParam.getInnerIp(), appMsg);
		} catch (Exception ex) {
			logger.error("{} return msg is {}", deviceOperateParam.getInnerIp(), ex.getMessage());
			return false;
		}

		String[] splitmsg = appMsg.split("\\|");

		boolean flag = false;

		for (int i = 0; i < splitmsg.length; i++) {
			if (splitmsg[i].indexOf(Constant.RETURN_PARAMETER) > -1) /// 找到参数
			{
				
				String[] res = splitmsg[i].split(":");
				if (res.length == 2) {

					int ret = Integer.parseInt(res[1]);
					if (ret == 0) {
						flag = true;
					}
				}
			}
			if (splitmsg[i].indexOf(Constant.DEVICEID_PARAMETER) > -1) /// 返回ID
			{
				String[] res = splitmsg[i].split(":");
				if (res.length == 2) {
                    deviceOperateParam.setDeviceId(res[1]);
				}
			}
		}

		return flag;
	}
}
