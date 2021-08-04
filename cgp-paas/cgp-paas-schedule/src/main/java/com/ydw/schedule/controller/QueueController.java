package com.ydw.schedule.controller;

import com.ydw.schedule.model.vo.QueueVO;
import com.ydw.schedule.model.vo.ResultInfo;
import com.ydw.schedule.service.IQueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/queue")
public class QueueController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IQueueService queueServiceImpl;
	
	/**
	 * 离队
	 * @return
	 */
	@GetMapping(value = "/queueOut")
	public ResultInfo queueOut(@RequestParam String account){
		queueServiceImpl.dequeue(account);
		return ResultInfo.success();
	}

	/**
	 * 获取用户排名
	 * @return
	 */
	@GetMapping(value = "/getUserRank")
	public ResultInfo getUserRank(@RequestParam String account){
		QueueVO globalRank = queueServiceImpl.getGlobalRank(account);
		if (globalRank == null){
			return new ResultInfo(201,"用户已不在队列！", -1);
		}
		return ResultInfo.success(globalRank);
	}

	/**
	 * 获取申请的排队人数
	 * @return
	 */
	@GetMapping(value = "/getClustersQueueNum")
	public ResultInfo getClustersQueueNum(){
		return queueServiceImpl.getClustersQueueNum();
	}
}
