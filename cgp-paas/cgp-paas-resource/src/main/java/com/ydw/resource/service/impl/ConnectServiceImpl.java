package com.ydw.resource.service.impl;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ydw.resource.dao.*;
import com.ydw.resource.model.db.*;
import com.ydw.resource.model.enums.AppTypeEnum;
import com.ydw.resource.model.vo.*;
import com.ydw.resource.service.*;
import com.ydw.resource.task.PreparingScanTask;
import com.ydw.resource.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ydw.resource.model.constant.Constant;
import com.ydw.resource.model.enums.ApplyStatusEnum;
import com.ydw.resource.model.enums.ConnectStatus;
import com.ydw.resource.task.ThreadPool;

@Service
public class ConnectServiceImpl implements IConnectService{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IDeviceService deviceServiceImpl;

	@Autowired
	private AppStrategyMapper appStrategyMapper;

	@Autowired
	private UserAppsMapper userAppsMapper;

    @Autowired
    private UserServiceMapper userServiceMapper;

	@Autowired
	private ClustersMapper clustersMapper;

	@Autowired
	private DevicesMapper devicesMapper;

    @Autowired
    private ITaskService taskService;

	@Autowired
	private ThreadPool threadPool;

    @Autowired
    private YdwClusterServiceImpl ydwClusterService;

    @Autowired
    private IYdwScheduleService ydwScheduleServiceImpl;

    @Autowired
    private IDeviceUsedService deviceUsedService;

    @Autowired
    private PreparingScanTask preparingScanTask;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 申请资源
     */
    @Override
    public ResultInfo apply(ApplyParams param) {
        // 查看游戏和企业是否匹配
        AppVO appVO = userAppsMapper.getAppByIdentification(param.getEnterpriseId(), param.getAppId());
        if (appVO == null) {
            logger.error("此模式下【{}】此企业【{}】无此游戏【{}】！", param.getSaas(), param.getEnterpriseId(), param.getAppId());
            return ResultInfo.fail("无此应用！");
        }
        //调度设备申请连接
        ResultInfo scheduleResult = scheduleDevice(param.getClusterIds(), param.getAppId(), param.getBaseId(),param.getAccount(), param.getQueue());
        logger.info("申请连接结果：{}", JSON.toJSONString(scheduleResult));
        if (scheduleResult.getCode() == 200) {
            ApplyResultParams vo = JSON.parseObject(JSON.toJSONString(scheduleResult.getData()), ApplyResultParams.class);
            if (vo.getStatus() == ApplyStatusEnum.QUEUE.getStatus()){
                QueueVO queueVO = vo.getQueueVO();
                queueVO.setAppName(appVO.getAppName());
                queueVO.setQueuedTime((System.currentTimeMillis() - queueVO.getQueueTime()) / 1000);
                ResultInfo resultInfo = new ResultInfo();
                resultInfo.setCode(201);
                resultInfo.setData(queueVO);
                redisUtil.set(MessageFormat.format(Constant.STR_APPLYING_USERDETAIL, param.getAccount()),param,7 * 3600);
                return resultInfo;
            }
            ConnectVO connectVO = vo.getConnectVO();
            if (vo.getStatus() == ApplyStatusEnum.GAIN.getStatus()) {
                //上报设备使用中
                deviceServiceImpl.reportStatus(connectVO.getDeviceId(), Constant.DEVICE_STATUS_PREPARING);
                logger.info("开始放入准备资源队列{}", connectVO);
                //放入准备队列
                preparingScanTask.enqueue(connectVO.getAccount(), connectVO.getConnectId(), connectVO.getDeviceId());
            }
            DeviceInfo deviceInfo = devicesMapper.getDeviceInfo(connectVO.getDeviceId());
            ApplyResultVO applyResultVO = new ApplyResultVO(appVO.getAppName(), deviceInfo.getClusterId(), deviceInfo.getClusterName(), connectVO);
            return ResultInfo.success(applyResultVO);
        }
        return scheduleResult;
    }

    /**
	 * 调度设备
	 * @param
	 * @param appId
	 * @param baseId
	 * @param
	 * @return
	 */
	private ResultInfo scheduleDevice(List<String> clusterIds, String appId, String baseId, String account, boolean queue) {
		// 去调度模块调度设备
		HashMap<String, Object> schparams = new HashMap<>();
		schparams.put("clusterIds", clusterIds);
		schparams.put("appId", appId);
		schparams.put("baseId", baseId);
		schparams.put("account", account);
        schparams.put("queue", queue);
		return ydwScheduleServiceImpl.applyConnect(schparams);
	}

    /**
     * 通知给saas层
     * @param enterpriseId
     * @param saas
     * @param account
     * @param connectId
     * @param status
     */
    private void noticeSaas(String enterpriseId, int saas, String account, String connectId, int status, HashMap<String, Object> detail){
        logger.info("此连接{}准备回调，enterpriseId={}，saas={}，account={}，status={}，detail={}", connectId,
                enterpriseId, saas, account, status, detail);
        //先通知saas层
        QueryWrapper<UserService> qw = new QueryWrapper<>();
        qw.eq("identification",enterpriseId);
        qw.eq("service_id",saas);
        UserService userService = userServiceMapper.selectOne(qw);
        if (userService != null){
            threadPool.submit(() -> {
                HashMap<String, Object> params = new HashMap<>();
                params.put("connectId", connectId);
                params.put("status", status);
                params.put("account", account);
                if (detail != null){
                    String deviceId = (String)detail.get("deviceId");
                    String appId = (String)detail.get("appId");
                    ConnectInfo connectInfo = new ConnectInfo();
                    DeviceInfo deviceInfo = devicesMapper.getDeviceInfo(deviceId);
                    connectInfo.buildDeviceInfo(deviceInfo);
                    connectInfo.setAppId(appId);
                    UserApps userApps = userAppsMapper.selectById(appId);
                    connectInfo.setAppName(userApps.getName());
                    connectInfo.setConnectId(connectId);
                    Long queuedTime = (Long)detail.get("queuedTime");
                    connectInfo.setQueuedTime(queuedTime);
                    params.put("detail", connectInfo);
                }
                logger.info("saas回调url：{}", userService.getConnectCallbackUrl());
                logger.info("saas回调参数：{}", JSON.toJSONString(params));
                String s = HttpUtil.doJsonPost(userService.getConnectCallbackUrl(), params);
                logger.info("通知saas返回结果{}", s);
            });
        }else{
            logger.warn("此连接{}回调给saas平台时enterpriseId={}，saas={}没有找到回调域");
        }
    }

    /**
     * 排队申请到资源后调度模块回调
     * @param vo
     * @return
     */
    @Override
    public ResultInfo noticeResourceApplyed(ConnectVO vo) {
        String paramsKey = MessageFormat.format(Constant.STR_APPLYING_USERDETAIL, vo.getAccount());
        ApplyParams params = (ApplyParams)redisUtil.get(paramsKey);
        if (params == null){
            logger.error("排队申请到资源后回调时{}，获取不到用户申请参数！", vo);
        }else{
            redisUtil.del(paramsKey);
            //上报设备使用中
            deviceServiceImpl.reportStatus(vo.getDeviceId(), Constant.DEVICE_STATUS_PREPARING);
            logger.info("开始放入准备资源队列{}", vo);
            //放入准备队列
            preparingScanTask.enqueue(vo.getAccount(), vo.getConnectId(), vo.getDeviceId());
            HashMap<String, Object> detail = new HashMap<>();
            detail.put("deviceId", vo.getDeviceId());
            detail.put("appId", vo.getAppId());
            detail.put("queuedTime", vo.getQueuedTime());
            noticeSaas(params.getEnterpriseId(), params.getSaas(), params.getAccount(), vo.getConnectId(), ConnectStatus.APPLYCONNECT.getCode(), detail);
        }
        return ResultInfo.success(vo);
    }


    /**
     * 准备资源
     * @param param
     * @return
     */
    @Override
	public ResultInfo connect(ConnectParams param){
        ConnectVO connectVO = checkUserConnecting(param);
        if (connectVO == null){
            logger.info("请求参数用户关联的资源不匹配{}", param);
            return ResultInfo.fail("资源不存在！");
        }
        logger.info("开始移除准备资源队列{}", param);
        Long dequeue = preparingScanTask.dequeue(param.getAccount(), param.getConnectId(), param.getDeviceId());
        if (dequeue == null || dequeue == 0L){
            ConnectInfo deviceConnectInfo = getDeviceConnectInfo(connectVO);
            deviceConnectInfo.setNewConnect(false);
            return ResultInfo.success(deviceConnectInfo);
        }else{
            redisUtil.del(MessageFormat.format(Constant.STR_APPLYING_USERDETAIL, param.getAccount()));
            //上报设备使用中
            deviceServiceImpl.reportStatus(param.getDeviceId(), Constant.DEVICE_STATUS_USED);
            ConnectInfo connectInfo = new ConnectInfo();
            connectInfo.setConnectId(param.getConnectId());
            connectInfo.setConnectedTime(0L);
            //根据用户id和connectId生成密钥
            connectInfo.setToken(getTicket(param.getConnectId(),param.getAccount()));
            //设备详情
            DeviceInfo deviceInfo = devicesMapper.getDeviceInfo(param.getDeviceId());
            //app详情
            AppInfo appInfo = userAppsMapper.getUserAppInfo(param.getAppId(), deviceInfo.getClusterId());
            connectInfo.buildDeviceInfo(deviceInfo);
            connectInfo.buildAppInfo(appInfo);
            //查看资源分配有没有任务
            taskService.runPreResTask(deviceInfo);
            logger.info("开始打开流服务{}",JSON.toJSONString(connectInfo));
            ResultInfo resultInfo = openStream(deviceInfo, appInfo, param.getConnectId(), param.getAccount(), param.getClient(), param.getType());
            if (resultInfo.getCode() == 200){
                //打开流服务成功
                logger.info("打开流服务成功{}",JSON.toJSONString(connectInfo));
                WebrtcConfig data = (WebrtcConfig)resultInfo.getData();
                if (data != null){
                    connectInfo.buildConnectInfo(data);
                }
                //开始打开游戏
                logger.info("开始打开游戏{}",JSON.toJSONString(connectInfo));
                resultInfo = openApp(deviceInfo, appInfo, param.getAccount(), param.getRentalParams());
                if (resultInfo.getCode() == 200){
                    logger.info("打开游戏{}成功！",JSON.toJSONString(connectInfo));
                    resultInfo.setData(connectInfo);
                    //防止在打开游戏和流服务的时候已经释放资源，此时再计量就无法释放了
                    if (checkUserConnecting(param) != null){
                        //开始计量
                        DeviceUsed deviceUsed = new DeviceUsed(param.getConnectId(), param.getDeviceId(), param.getAppId(),
                                param.getEnterpriseId(), param.getAccount(),
                                param.getCustomIp(), LocalDateTime.now(), null, null,
                                param.getClient(), param.getType(), param.getSaas(), true);
                        deviceUsedService.save(deviceUsed);
                        return resultInfo;
                    }else{
                        logger.info("打开游戏{}之前用户已经放弃了此资源",JSON.toJSONString(connectInfo));
                    }
                }else{
                    logger.info("打开游戏{}失败！",JSON.toJSONString(connectInfo));
                }
            }
            logger.error("打开连接{}设备{}的游戏或者流服务失败，开始重启", param.getConnectId(), param.getDeviceId());
            deviceServiceImpl.reboot(new String[]{param.getDeviceId()});
            return ResultInfo.fail("初始化资源失败，请重新申请！");
        }
    }

    private ConnectVO checkUserConnecting(ConnectParams param){
        ConnectVO connectVO = null;
        ResultInfo userConnectStatus = ydwScheduleServiceImpl.getUserConnectStatus(param.getAccount());
        if(userConnectStatus.getData() != null){
            ApplyResultParams vo = JSON.parseObject(JSON.toJSONString(userConnectStatus.getData()), ApplyResultParams.class);
            if (vo.getStatus() == ApplyStatusEnum.GOT.getStatus()) {
                ConnectVO sconnectVO = vo.getConnectVO();
                if (sconnectVO.getDeviceId().equals(param.getDeviceId()) ||
                        sconnectVO.getConnectId().equals(param.getConnectId()) ||
                        sconnectVO.getAppId().equals(param.getAppId())){
                    connectVO = sconnectVO;
                }else{
                    logger.info("用户{}请求参数与内存不一致{}-{}", param.getAccount(), param, sconnectVO);
                }
            }else{
                logger.info("用户{}不是连接状态！{}", param.getAccount(), vo.getStatus());
            }
        }else{
            logger.info("用户{}没有连接状态！", param.getAccount());
        }
        return connectVO;
    }

    private String getTicket(String connectId, String account){
        return EncryptionUtil.MD5encode(connectId + account);
    }

    /**
     * 开启流服务
     * @param deviceInfo
     * @param appInfo
     * @param connectId
     * @param account
     * @param client
     * @param connectType
     * @return
     */
	private ResultInfo openStream(DeviceInfo deviceInfo,UserApps appInfo, String connectId, String account,
                                  int client, int connectType){
		logger.info("连接{},设备{},app{},客户端{},连接类型{}准备开启流服务", connectId,deviceInfo.getDeviceId(),
                appInfo.getId(),client,connectType);
		ResultInfo resultInfo = null;
		try {
			Integer strategyId = null;
            if (client == Constant.CLIENT_TYPE_MOBILE) {
                strategyId = appInfo.getStrategyId();
            } else {
                strategyId = appInfo.getPcStrategyId() == null ? appInfo.getStrategyId()
                        : appInfo.getPcStrategyId();
            }
			AppStrategy appStrategy = appStrategyMapper.selectById(strategyId);
			// 是否以webrtc的形式申请设备
			WebrtcConfig webRtcConfig = null;
			if (Constant.CONNECT_TYPE_WEBRTC.equals(connectType)) {
				webRtcConfig = clustersMapper.getClusterWebrtcConfig(deviceInfo.getClusterId());
			}
			String url = deviceInfo.getApiUrl() + Constant.URL_EDGE_STARTSTREAM;
			HashMap<String, Object> httpParams = new HashMap<>();
			httpParams.put("deviceId", deviceInfo.getDeviceId());
			httpParams.put("deviceType", deviceInfo.getDeviceType());
			httpParams.put("innerIp", deviceInfo.getInnerIp());
			httpParams.put("innerPort", deviceInfo.getInnerPort());
			httpParams.put("adbIp", deviceInfo.getAdbIp());
			httpParams.put("adbPort", deviceInfo.getAdbPort());
			httpParams.put("model", deviceInfo.getModel());

            httpParams.put("type", appInfo.getType() == 0 || appInfo.getType() == 1 ? AppTypeEnum.GAME.type : appInfo.getType());
			httpParams.put("screen", appInfo.getScreen());
			httpParams.put("fps", appStrategy.getFps());
			httpParams.put("codec", Constant.DEVICE_ENCODE_H265.equals(appStrategy.getEncode()) ? 1 : 0);
			httpParams.put("video", appStrategy.getVideo());
			httpParams.put("speed", appStrategy.getSpeed());
			httpParams.put("connectId", connectId);
			httpParams.put("token", getTicket(connectId,account));
			if (webRtcConfig != null) {
				httpParams.put("webRTC", true);
				httpParams.put("signalServer", webRtcConfig.getSignalServer());
				httpParams.put("turnServer", String.join(",", webRtcConfig.getStunUrl(), 
		            		webRtcConfig.getTurnTcpUrl(), webRtcConfig.getTurnUdpUrl()));
				httpParams.put("turnUser", webRtcConfig.getTurnUser());
				httpParams.put("turnPassword", webRtcConfig.getTurnPassword());
			}
            Map<String,String> headers = ydwClusterService.buildHeader(deviceInfo.getClusterId());
			logger.info("url[{}]",url);
			logger.info("headers[{}]",JSON.toJSONString(headers));
			logger.info("httpParams[{}]",JSON.toJSONString(httpParams));
			String result = HttpUtil.doJsonPost(url,headers, httpParams);
			logger.info("启动此连接{}的游戏流服务返回结果：{}", connectId,result);
            resultInfo = JSON.parseObject(result, ResultInfo.class);
			if(resultInfo.getCode() == 200){
                resultInfo.setData(webRtcConfig);
			}else{
                throw new RuntimeException();
            }
		} catch (Exception e) {
            logger.error("连接{},设备{},app{},客户端{},连接类型{}开启流服务失败！",connectId,deviceInfo.getDeviceId(),appInfo.getId(),client,connectType);
			logger.error(e.getMessage());
			resultInfo = ResultInfo.fail();
		}
		return resultInfo;
	}

    /**
     * 开启应用
     * @param deviceInfo
     * @param appInfo
     * @return
     */
    @Override
    public ResultInfo openApp(DeviceInfo deviceInfo, AppInfo appInfo, String userId, Object rentalParams){
        logger.info("连接准备开启设备{}的app服务{}", deviceInfo.getDeviceId(), appInfo.getId());
        ResultInfo resultInfo = null;
        try {
            String url = deviceInfo.getApiUrl() + Constant.URL_EDGE_STARTAPP;
            HashMap<String, Object> httpParams = new HashMap<>();
            httpParams.put("deviceId", deviceInfo.getDeviceId());
            httpParams.put("deviceType", deviceInfo.getDeviceType());
            httpParams.put("innerIp", deviceInfo.getInnerIp());
            httpParams.put("innerPort", deviceInfo.getInnerPort());
            httpParams.put("adbIp", deviceInfo.getAdbIp());
            httpParams.put("adbPort", deviceInfo.getAdbPort());
            httpParams.put("model", deviceInfo.getModel());

            httpParams.put("appId", appInfo.getId());
            httpParams.put("packageName", appInfo.getPackageName());
            httpParams.put("packageFileName", appInfo.getPackageFileName());
            httpParams.put("packageFilePath", appInfo.getPackageFilePath());
            httpParams.put("startShell", appInfo.getStartShell());
            httpParams.put("closeShell", appInfo.getCloseShell());
            httpParams.put("type", appInfo.getType() == 0 || appInfo.getType() == 1 ? AppTypeEnum.GAME.type : appInfo.getType());
            httpParams.put("savePath", appInfo.getSavePath());

            httpParams.put("userId", userId);
            httpParams.put("rentalParams", rentalParams);
            Map<String,String> headers = ydwClusterService.buildHeader(deviceInfo.getClusterId());
            logger.info("启动此设备{}的游戏{}请求参数：{}", deviceInfo.getDeviceId(),appInfo.getId(),httpParams.toString());
            String result = HttpUtil.doJsonPost(url, headers, httpParams);
            logger.info("启动此设备{}的游戏{}返回结果：{}", deviceInfo.getDeviceId(),appInfo.getId(),result);
            resultInfo = JSON.parseObject(result, ResultInfo.class);
            if(resultInfo.getCode() != 200){
                throw new RuntimeException();
            }
        } catch (Exception e) {
            logger.error("启动此设备{}的游戏{}失败！", deviceInfo.getDeviceId(),appInfo.getId());
            resultInfo = ResultInfo.fail();
        }
        return resultInfo;
    }

    @Override
    public ResultInfo openApp(AppOperateParams param) {
        logger.info("saas连接准备开启device {}的app服务{}", param.getDeviceId(), param.getAppId());
        DeviceInfo deviceInfo = devicesMapper.getDeviceInfo(param.getDeviceId());
        AppInfo userAppInfo = userAppsMapper.getUserAppInfo(param.getAppId(), deviceInfo.getClusterId());
        return openApp(deviceInfo, userAppInfo, param.getAccount(), param.getRentalParams());
    }

	@Override
	public ResultInfo reconnect(ReConnectVO vo){
	    logger.info("开始重连{}", JSON.toJSONString(vo));
        ResultInfo resultInfo = ydwScheduleServiceImpl.getUserConnectStatus(vo.getAccount());
        logger.info("获取用户连接结果{}",JSON.toJSONString(resultInfo));
        if(resultInfo.getData() != null){
            ApplyResultParams resultVO = JSON.parseObject(JSON.toJSONString(resultInfo.getData()), ApplyResultParams.class);
            if (resultVO.getStatus() == ApplyStatusEnum.GOT.getStatus()) {
                ConnectInfo connectInfo = new ConnectInfo();
                DeviceInfo deviceInfo = devicesMapper.getDeviceInfo(vo.getDeviceId());
                connectInfo.buildDeviceInfo(deviceInfo);
                AppInfo userApp = userAppsMapper.getUserAppInfo(vo.getAppId(), deviceInfo.getClusterId());
                connectInfo.buildAppInfo(userApp);
                DeviceUsed deviceUsed = deviceUsedService.getById(vo.getConnectId());
                if (deviceUsed == null){
                    connectInfo.setConnectId(vo.getConnectId());
                    connectInfo.setConnectedTime(0l);
                    connectInfo.setToken(getTicket(vo.getConnectId(),vo.getAccount()));
                    connectInfo.setType(vo.getType());
                    connectInfo.setClient(vo.getClient());
                }else{
                    connectInfo.buildConnectInfo(deviceUsed);
                }
                logger.info("开始关闭流服务{}",vo.getConnectId());
                resultInfo = closeStream(deviceInfo);
                logger.info("关闭流服务{}返回结果{}",vo.getConnectId(), JSON.toJSONString(resultInfo));
                for (int i = 1 ; i < 4 ; i++){
                    logger.info("第{}次开始打开流服务{}", i, vo.getConnectId());
                    resultInfo = openStream(deviceInfo, userApp, vo.getConnectId(),vo.getAccount(),vo.getClient(),vo.getType());
                    logger.info("打开流服务{}返回结果{}",vo.getConnectId(), JSON.toJSONString(resultInfo));
                    if (resultInfo.getCode() == 200){
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (userApp.getType() == AppTypeEnum.ARM.type ||
                        userApp.getType() == AppTypeEnum.PHONE.type){
                    logger.info("手机端需要重新（假）打开游戏");
                    openApp(deviceInfo,userApp,vo.getAccount(), null);
                }
                deviceUsedService.updateConnectMethod(vo.getConnectId(), vo.getClient(), vo.getType());
                if(vo.getType() == 1){
                    WebrtcConfig webRtcConfig = clustersMapper.getClusterWebrtcConfig(deviceInfo.getClusterId());
                    connectInfo.buildConnectInfo(webRtcConfig);
                }
                resultInfo.setCode(200);
                resultInfo.setData(connectInfo);
                return resultInfo;
            }
        }
        resultInfo = ResultInfo.fail();
        return resultInfo;
    }
	
	@Override
	public void forceRelease(String deviceId) {
		try {
			/// 查询当前设备的connectId
			ResultInfo connectByDevice = ydwScheduleServiceImpl.getConnectByDevice(deviceId);
			logger.info("根据设备id【{}】，获取连接id【{}】",deviceId,connectByDevice.getData());
			if (connectByDevice.getCode() == 200){
                Map map = (Map)connectByDevice.getData();
                String connectId = (String)map.get("connectId");
                String account = (String)map.get("account");
                logger.info("Force release account {} device {} connectId {}", account, deviceId, connectId);
                ConnectVO vo = new ConnectVO();
                vo.setConnectId(connectId);
                vo.setDeviceId(deviceId);
                vo.setAccount(account);
                release(vo);
            }else{
			    deviceServiceImpl.reboot(new String[]{deviceId});
            }
		} catch (Exception ex) {
			logger.error("释放报错{}",ex.getMessage());
		}
	}

    @Override
    public ResultInfo normal(String connectId) {
        logger.info("此连接connectId={}开始连接上报", connectId);
        DeviceUsed deviceUsed = deviceUsedService.getById(connectId);
        if(deviceUsed == null){
            return ResultInfo.fail();
        }
        noticeSaas(deviceUsed.getEnterpriseId(), deviceUsed.getSaas(), deviceUsed.getCustomId(),
                connectId, ConnectStatus.CONNECTING.getCode(), null);
        return ResultInfo.success();
    }

	@Override
	public ResultInfo getConnectDetail(String connectId) {
		DeviceUsed deviceUsed = deviceUsedService.getById(connectId);
		DeviceInfo deviceInfo = devicesMapper.getDeviceInfo(deviceUsed.getDeviceId());
		UserApps appInfo = userAppsMapper.selectById(deviceUsed.getAppId());
		ConnectInfo devInfo = new ConnectInfo();
        devInfo.buildDeviceInfo(deviceInfo);
        devInfo.buildAppInfo(appInfo);
        WebrtcConfig webRtcConfig = null;
        if(deviceUsed != null && deviceUsed.getType() == Constant.CONNECT_TYPE_WEBRTC){
            webRtcConfig = clustersMapper.getClusterWebrtcConfig(deviceInfo.getClusterId());
        }
        devInfo.buildConnectInfo(deviceUsed, webRtcConfig);
		return ResultInfo.success(devInfo);
	}

	/**
	 * 下机
	 */
	@Override
	public ResultInfo release(ConnectVO vo) {
		DeviceUsed deviceUsed = deviceUsedService.getById(vo.getConnectId());
        logger.info("释放连接时，获取connectId={},account={},deviceId={}的计量数据【{}】",
                vo.getConnectId(),vo.getAccount(),vo.getDeviceId(),deviceUsed);
		if (deviceUsed != null){
		    if (deviceUsed.getDeviceId().equals(vo.getDeviceId())
                    && deviceUsed.getCustomId().equals(vo.getAccount())
                    && deviceUsed.getEndTime() == null
                    && deviceUsed.getTotalTime() == null){
		        HashMap<String, Object> detail = new HashMap<>();
		        detail.put("deviceId", deviceUsed.getDeviceId());
		        detail.put("appId", deviceUsed.getAppId());
                ResultInfo releaseConnect = releaseConnect(deviceUsed.getDeviceId(), deviceUsed.getId());
                if(releaseConnect.getCode() == 200){
                    logger.info("此内存连接{}释放成功！", JSON.toJSONString(vo));
                }else{
                    logger.error("此内存连接{}释放失败！", JSON.toJSONString(vo));
                }
                boolean ended = deviceUsedService.endUse(vo.getConnectId());
                if(ended){
                    logger.info("此连接{}停止计量成功！", JSON.toJSONString(vo));
                }else{
                    logger.error("此连接{}停止计量失败！", JSON.toJSONString(vo));
                }
                noticeSaas(deviceUsed.getEnterpriseId(), deviceUsed.getSaas(), deviceUsed.getCustomId(), vo.getConnectId(),
                        ConnectStatus.OUTCONNECT.getCode(), null);
                if (StringUtil.isBlank(vo.getAppId())){
                    vo.setAppId(deviceUsed.getAppId());
                }
                threadPool.submit(() -> {
                    recoveryRes(vo.getDeviceId(),vo.getAppId(), vo.getAccount());
                });
            }else{
		        logger.info("此计量不能结束！尝试释放此内存中连接！connectId-{}，deviceId-{}", vo.getConnectId(), vo.getDeviceId());
                return releaseConnect(vo.getDeviceId(), vo.getConnectId());
            }
        }else{
            ResultInfo resultInfo = releaseConnect(vo.getDeviceId(), vo.getConnectId());
            if (resultInfo.getCode() == 200){
                deviceServiceImpl.reboot(new String[]{vo.getDeviceId()});
            }
            return resultInfo;
        }
        return ResultInfo.success();
	}
	
	/**
	 * 释放连接
	 * @param
	 * @param
	 * @return
	 */
	private ResultInfo releaseConnect(String deviceId, String connectId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("connectId", connectId);
        params.put("deviceId", deviceId);
        logger.info("释放设备【{}】连接【{}】", deviceId, connectId);
        return ydwScheduleServiceImpl.releaseConnect(params);
	}
	
	/**
	 * 回收资源
	 * @return
	 */
	private ResultInfo recoveryRes(String deviceId, String appId, String userId) {
        ResultInfo resultInfo = ResultInfo.fail();
        DeviceInfo deviceInfo = devicesMapper.getDeviceInfo(deviceId);
        AppInfo appInfo = userAppsMapper.getUserAppInfo(appId, deviceInfo.getClusterId());
        logger.info("开始释放资源！deviceId={},appId={}",deviceId,appId);
        try {
            //关闭游戏
            resultInfo = closeApp(deviceInfo, appInfo, userId);
            logger.info("关闭设备{}游戏{}返回结果--{}",deviceId,appId,JSON.toJSONString(resultInfo));
            if (resultInfo.getCode() == 200){
                logger.info("关闭设备{}的游戏{}成功！", deviceId, appId);
            }else {
                logger.info("关闭设备{}的游戏{}失败！", deviceId, appId);
            }
            //arm需要关闭流服务
            if (deviceInfo.getDeviceType() == 0){
                //关闭流服务
                resultInfo = closeStream(deviceInfo);
                logger.info("关闭设备{}流服务返回结果--{}",deviceId, JSON.toJSONString(resultInfo));
                if(resultInfo != null && resultInfo.getCode() == 200){
                    logger.info("关闭设备{}的流服务成功！", deviceId);
                    if (resultInfo.getData() != null) {
                        //修改磁盘使用情况
                        DeviceSizeVO devSize = JSON.parseObject(JSON.toJSONString(resultInfo.getData()), DeviceSizeVO.class);
                        devSize.setId(deviceId);
                        deviceServiceImpl.updateDiskSize(devSize);
                    }
                }else{
                    logger.info("关闭设备{}的流服务失败！", deviceId);
                }
            }

        } finally {
            if (resultInfo != null && resultInfo.getCode() != 200){
                logger.info("关闭设备{}的流服务或游戏失败！直接重启！", deviceId);
                deviceServiceImpl.reboot(new String[]{deviceId});
            }else{
                if(deviceInfo.getDeviceType() == 1){
                    deviceServiceImpl.reboot(new String[]{deviceId});
                }else{
                    deviceServiceImpl.reportStatus(deviceId, Constant.DEVICE_STATUS_IDLE);
                }
            }
        }
		return resultInfo;
	}

    /**
     * 关闭流服务
     * @param deviceInfo
     * @return
     */
	private ResultInfo closeStream(DeviceInfo deviceInfo){
        Map<String,String> headers = ydwClusterService.buildHeader(deviceInfo.getClusterId());
		String url = deviceInfo.getApiUrl() + Constant.URL_EDGE_STOPSTREAM;
		HashMap<String, Object> params = new HashMap<>();
        params.put("deviceId", deviceInfo.getDeviceId());
        params.put("deviceType", deviceInfo.getDeviceType());
        params.put("innerIp", deviceInfo.getInnerIp());
        params.put("innerPort", deviceInfo.getInnerPort());
        params.put("adbIp", deviceInfo.getAdbIp());
        params.put("adbPort", deviceInfo.getAdbPort());
        params.put("model", deviceInfo.getModel());
        String result = HttpUtil.doJsonPost(url, headers, params);
		return JSON.parseObject(result, ResultInfo.class);
	}

    /**
     * 关闭游戏
     * @param deviceInfo
     * @param appInfo
     * @return
     */
    @Override
    public ResultInfo closeApp(DeviceInfo deviceInfo, AppInfo appInfo, String userId){
        Map<String,String> headers = ydwClusterService.buildHeader(deviceInfo.getClusterId());
        String url = deviceInfo.getApiUrl() + Constant.URL_EDGE_STOPAPP;
        HashMap<String, Object> params = new HashMap<>();
        params.put("deviceId", deviceInfo.getDeviceId());
        params.put("deviceType", deviceInfo.getDeviceType());
        params.put("innerIp", deviceInfo.getInnerIp());
        params.put("innerPort", deviceInfo.getInnerPort());
        params.put("adbIp", deviceInfo.getAdbIp());
        params.put("adbPort", deviceInfo.getAdbPort());
        params.put("model", deviceInfo.getModel());
        params.put("type", appInfo.getType() == 0 || appInfo.getType() == 1 ? AppTypeEnum.GAME.type : appInfo.getType());
        params.put("appId", appInfo.getId());
        params.put("packageName", appInfo.getPackageName());
        params.put("packageFileName", appInfo.getPackageFileName());
        params.put("packageFilePath", appInfo.getPackageFilePath());
        params.put("startShell", appInfo.getStartShell());
        params.put("closeShell", appInfo.getCloseShell());
        params.put("savePath", appInfo.getSavePath());
        params.put("userId", userId);
        String result = HttpUtil.doJsonPost(url, headers, params);
        return JSON.parseObject(result, ResultInfo.class);
    }

    @Override
    public ResultInfo closeApp(AppOperateParams param) {
        DeviceInfo deviceInfo = devicesMapper.getDeviceInfo(param.getDeviceId());
        AppInfo userAppInfo = userAppsMapper.getUserAppInfo(param.getAppId(), deviceInfo.getClusterId());
        return closeApp(deviceInfo, userAppInfo, param.getAccount());
    }

	/**
	 * 连接异常上报
	 */
	@Override
	public ResultInfo abnormal(String errCode, String connectId) {
	    logger.info("此连接connectId={}开始异常上报errCode={}", connectId, errCode);
		DeviceUsed deviceUsed = deviceUsedService.getById(connectId);
		if(deviceUsed == null){
			return ResultInfo.fail();
		}
        noticeSaas(deviceUsed.getEnterpriseId(), deviceUsed.getSaas(), deviceUsed.getCustomId(), connectId,
                "0".equals(errCode) ? ConnectStatus.CONNECTERROR.getCode() : ConnectStatus.CONTROLRROR.getCode(), null);
        return ResultInfo.success();
    }

	private ConnectInfo getDeviceConnectInfo(ConnectVO vo) {
        ConnectInfo connectInfo = new ConnectInfo();
        DeviceInfo deviceInfo = devicesMapper.getDeviceInfo(vo.getDeviceId());
        connectInfo.buildDeviceInfo(deviceInfo);
        UserApps appInfo = userAppsMapper.selectById(vo.getAppId());
        connectInfo.buildAppInfo(appInfo);
        DeviceUsed deviceUsed = deviceUsedService.getById(vo.getConnectId());
        int type = 0;
        if (deviceUsed == null){
            connectInfo.setConnectId(vo.getConnectId());
            connectInfo.setConnectedTime((System.currentTimeMillis() - vo.getConnectTime()) / 1000);
            connectInfo.setToken(getTicket(vo.getConnectId(),vo.getAccount()));
        }else{
            connectInfo.buildConnectInfo(deviceUsed);
            type = deviceUsed.getType();
        }
        if(type == 1){
            WebrtcConfig webRtcConfig = clustersMapper.getClusterWebrtcConfig(deviceInfo.getClusterId());
            connectInfo.buildConnectInfo(webRtcConfig);
        }
		return connectInfo;
	}

    /**
     * 无设备：200
     * 排队中：201
     * 锁定中：202
     * 已连接：203
     * @param account
     * @return
     */
	@Override
	public ResultInfo getUserConnectStatus(String account) {
		ResultInfo resultInfo = ydwScheduleServiceImpl.getUserConnectStatus(account);
		if(resultInfo.getData() != null){
			ApplyResultParams vo = JSON.parseObject(JSON.toJSONString(resultInfo.getData()), ApplyResultParams.class);
			if (vo.getStatus() == ApplyStatusEnum.GOT.getStatus()) {
				ConnectInfo connectInfo = getDeviceConnectInfo(vo.getConnectVO());
				if (connectInfo.getNewConnect()){
                    resultInfo.setCode(202);
                }else{
				    resultInfo.setCode(203);
                }
				resultInfo.setData(connectInfo);
			}else if (vo.getStatus() == ApplyStatusEnum.QUEUE.getStatus()){
				QueueVO queueVO = vo.getQueueVO();
				UserApps userApp = userAppsMapper.selectById(queueVO.getAppId());
                queueVO.setAppName(userApp.getName());
				resultInfo.setCode(201);
				resultInfo.setData(queueVO);
			}
		}
		return resultInfo;
	}

    @Override
    public ResultInfo getRank(String account) {
        ResultInfo result = ydwScheduleServiceImpl.getUserRank(account);
        if (result.getCode() == 200 && result.getData() != null){
            QueueVO queueVO = JSON.parseObject(JSON.toJSONString(result.getData()), QueueVO.class);
            UserApps userApp = userAppsMapper.selectById(queueVO.getAppId());
            queueVO.setAppName(userApp.getName());
            queueVO.setQueuedTime((System.currentTimeMillis() - queueVO.getQueueTime()) / 1000);
            result.setData(queueVO);
        }
        return result;
    }

    @Override
    public ResultInfo cancelConnect(ConnectParams param) {
        Long dequeue = preparingScanTask.dequeue(param.getAccount(), param.getConnectId(), param.getDeviceId());
        if (dequeue == null || dequeue == 0L){
            logger.error("{}退出准备队列失败！{}", param, dequeue);
            return ResultInfo.fail();
        }
        logger.info("{}退出准备队列成功，开始释放内存资源！", param);
        ResultInfo resultInfo = releaseConnect(param.getDeviceId(), param.getConnectId());
        if (resultInfo.getCode() == 200){
            deviceServiceImpl.reportStatus(param.getDeviceId(), Constant.DEVICE_STATUS_IDLE);
        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo queueOut(String account) {
        redisUtil.del(MessageFormat.format(Constant.STR_APPLYING_USERDETAIL, account));
        return ydwScheduleServiceImpl.queueOut(account);
    }
}
