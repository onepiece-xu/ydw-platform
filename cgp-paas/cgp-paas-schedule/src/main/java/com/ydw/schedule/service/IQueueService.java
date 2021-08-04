package com.ydw.schedule.service;

import com.ydw.schedule.model.vo.QueueDetail;
import com.ydw.schedule.model.vo.QueueVO;
import com.ydw.schedule.model.vo.ResultInfo;

import java.util.List;

/**
 * 排队相关业务
 *
 * @author xulh
 * @since 2020-05-11
 */
public interface IQueueService {

	/**
	 * 用户排队, 全局排名
	 * @param account
	 * @param clusterIds
	 * @param appId
	 * @return
	 */
	QueueDetail enqueue(String account, List<String> clusterIds, String appId, String baseId);

	/**
	 * 退出排队
	 * @param account
	 * @return
	 */
	boolean dequeue(String account);

	/**
	 * 获取用户在集群中的排名
	 * @param account
	 * @return
	 */
	long getClusterRank(String account, String clusterId);

    /**
     * 获取用户全局排名
     * @param account
     * @return
     */
	QueueVO getGlobalRank(String account);

	/**
	 * 是否能够直接申请资源的集群
	 * @param clusterIds
	 * @param appId
	 * @return
	 */
	List<String> ableApplyClusters(List<String> clusterIds, String appId);

	/**
	 * 消费所有排队队列
	 */
	void consumeQueue();

	/**
	 * 消费某一个集群队列
	 * @param clusterId
	 */
	boolean consumeQueue(String clusterId);

    /**
     * 消费某个集群队列
     * @param clusterId
     * @param deviceNum
     * @param queueNum
     */
	boolean consumeQueue(String clusterId, long deviceNum, long queueNum);

	/**
	 * 获取幸存指针
	 * @param clusterId
	 * @return
	 */
	long getSurvivorPoint(String clusterId);

	void decrSurvivorPoint(String clusterId);

	/**
	 * 获取所有集群的排队信息
	 * @return
	 */
	ResultInfo getClustersQueueNum();
}
