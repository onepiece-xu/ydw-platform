package com.ydw.game.service.impl;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.game.dao.ConnectMapper;
import com.ydw.game.model.constant.Constant;
import com.ydw.game.model.db.Connect;
import com.ydw.game.model.vo.ConnectVO;
import com.ydw.game.model.vo.ResultInfo;
import com.ydw.game.redis.RedisUtil;
import com.ydw.game.service.IConnectService;
import com.ydw.game.service.IResourceService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulh
 * @since 2020-08-04
 */
@Service
public class ConnectServiceImpl extends ServiceImpl<ConnectMapper, Connect> implements IConnectService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${url.schedulejob}")
	private String schedulejobUrl;
	
	@Value("${url.game}")
	private String gameUrl;
	
	@Value("${timeout}")
	private Long timeout;
	
	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	@Lazy
	private IResourceService resourceServiceImpl;

	@Override
	public void abnormalNotice(String connectId) {
		Connect connect = getById(connectId);
		String userSessionKey = MessageFormat.format(Constant.SET_WS_ONLINE, connect.getUserId());
		if(!redisUtil.hasKey(userSessionKey)){
			//确实已经不在线了
			ConnectVO vo = new ConnectVO();
			vo.setAccount(connect.getUserId());
			vo.setConnectId(connect.getId());
			vo.setDeviceId(connect.getDeviceId());;
			resourceServiceImpl.release(vo);
		}
	}

	@Override
	public ResultInfo abnormalConnect(String connectId) {
//		Connect connect = getById(connectId);
//		if(connect.getAbnormalTime() == null){
//			logger.error("{}连接异常时间为空！" ,connectId);
//			return ResultInfo.fail("无异常连接时间！");
//		}
//		if(Duration.between(connect.getAbnormalTime(),LocalDateTime.now()).getSeconds() >= timeout){
//			ConnectVO vo = new ConnectVO();
//			vo.setConnectId(connect.getId());
//			vo.setAccount(connect.getUserId());
//			vo.setDeviceId(connect.getDeviceId());
//			return ResultInfo.success();
////			return resourceServiceImpl.release(vo);
//		}else{
//			logger.error("{}连接异常时间{}，未到回收时间！" ,connectId, connect.getAbnormalTime().toString());
//			return ResultInfo.fail();
//		}
		return null;
	}

}
