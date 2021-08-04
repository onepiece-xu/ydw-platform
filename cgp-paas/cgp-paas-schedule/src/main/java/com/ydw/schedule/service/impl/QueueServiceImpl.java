package com.ydw.schedule.service.impl;

import com.ydw.schedule.model.constant.RedisConstant;
import com.ydw.schedule.model.db.TbClusters;
import com.ydw.schedule.model.enums.ApplyStatusEnum;
import com.ydw.schedule.model.vo.*;
import com.ydw.schedule.service.*;
import com.ydw.schedule.util.RedisUtil;
import com.ydw.schedule.util.ThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 排队相关业务
 *
 * @author xulh
 * @since 2020-05-11
 */
@Service
public class QueueServiceImpl implements IQueueService {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private IScheduleService scheduleService;

	@Autowired
	private IConnectService connectService;

	@Autowired
    private ITbClustersService clustersService;

	@Autowired
	private IYdwResourceService ydwResourceService;

	@Autowired
	private ThreadPool threadPool;

	//排队超时时间（6小时）
	private long queueTimeout = 6 * 60 * 60;

	/**
	 * 入队
	 */
	@Override
	public QueueDetail enqueue(String account, List<String> clusterIds, String appId, String baseId) {
        //排队前先退出别的队
        dequeue(account);
        //全局排队
        globalEnqueue(account);
        //集群排队
        for (String clusterId : clusterIds){
            clusterEnqueue(account, clusterId);
        }
        return setQueueDetail(account, clusterIds, appId, baseId);
	}

    /**
     * 在集群中排队
     * @param account
     * @param clusterId
     */
	private void clusterEnqueue(String account, String clusterId){
        String key = MessageFormat.format(RedisConstant.QUE_ZSET_CLUSTER, clusterId);
        redisUtil.zSet(key, account, new Double(Instant.now().toEpochMilli()));
    }

    /**
     * 全局排队
     * @param account
     */
    private void globalEnqueue(String account){
        redisUtil.zSet(RedisConstant.QUE_ZSET_GLOBAL, account, new Double(Instant.now().toEpochMilli()));
    }

    /**
     * 关联用户排队详情
     * @param account
     * @param clusterIds
     * @param appId
     */
    private QueueDetail setQueueDetail(String account, List<String> clusterIds, String appId, String baseId){
        QueueDetail queueDetail = new QueueDetail(appId, clusterIds, baseId);
        redisUtil.hset(RedisConstant.QUE_MAP_ACCOUNT_APPLY, account, queueDetail);
        return queueDetail;
    }

    /**
     * 获取关联用户排队详情
     * @param account
     */
    private QueueDetail getQueueDetail(String account){
        return (QueueDetail)redisUtil.hget(RedisConstant.QUE_MAP_ACCOUNT_APPLY, account);
    }

	/**
	 * 出队
	 */
	@Override
	public boolean dequeue(String account) {
        Set<String> keys = redisUtil.getKeys(RedisConstant.QUE_ZSET_KEYS);
        for(String key : keys){
            redisUtil.zRemove(key, account);
        }
        removeQueueDetail(account);
        return globalDequeue(account);
	}

    /**
     * 集群队列出队
     * @param account
     * @param clusterId
     */
	private boolean clusterDequeue(String account, String clusterId){
        String key = MessageFormat.format(RedisConstant.QUE_ZSET_CLUSTER, clusterId);
        return redisUtil.zRemove(key, account) > 0;
    }

    /**
     * 全局队列出队
     * @param account
     */
    private boolean globalDequeue(String account){
        return redisUtil.zRemove(RedisConstant.QUE_ZSET_GLOBAL, account) > 0;
    }

    /**
     * 删除用户申请队列详情关系
     * @param account
     */
    private boolean removeQueueDetail(String account){
        return redisUtil.hdel(RedisConstant.QUE_MAP_ACCOUNT_APPLY, account) > 0;
    }

	/**
	 * 获取用户某一个集群排名
	 */
	@Override
	public long getClusterRank(String account, String clusterId){
	    String key = MessageFormat.format(RedisConstant.QUE_ZSET_CLUSTER, clusterId);
	    return getRank(key, account);
    }

    /**
     * 获取用户全局排名
     * @param account
     * @return
     */
    @Override
    public QueueVO getGlobalRank(String account) {
        QueueDetail queueDetail = getQueueDetail(account);
        long rank = getRank(RedisConstant.QUE_ZSET_GLOBAL, account);
        Double score = redisUtil.zScore(RedisConstant.QUE_ZSET_GLOBAL, account);
        if (queueDetail == null || rank == -1L || score == null){
            return null;
        }else{
            return new QueueVO(queueDetail, rank, score.longValue());
        }
    }

    private long getRank(String key, String account){
		long zRank = redisUtil.zRank(key, account, true);
		if(zRank != -1L){
			++zRank;
		}
		return zRank;
	}

	/**
	 * 用户是否能直接申请设备
	 * @param clusterIds
	 * @param appId
	 * @return
	 */
	@Override
	public List<String> ableApplyClusters(List<String> clusterIds, String appId) {
	    List<String> list = new ArrayList<>();
        for (String clusterId : clusterIds) {
            //只要有一个集群没人排队，就可以去碰碰运气
            String key = MessageFormat.format(RedisConstant.QUE_ZSET_CLUSTER, clusterId);
            if(!redisUtil.hasKey(key)){
                list.add(clusterId);
            }
        }
		return list;
	}

    /**
     * 消费所有排队队列
     */
    @Override
    public void consumeQueue() {
        Set<String> keys = redisUtil.getKeys(RedisConstant.QUE_ZSET_KEYS);
        for(String key : keys){
            threadPool.submit(() -> consumeQueue(key.split(":")[1]));
        }
    }

    /**
     * 消费某一个集群队列
     * @param clusterId 集群id
     */
    @Override
    public boolean consumeQueue(String clusterId) {
        long deviceCount = scheduleService.idleDeviceCount(clusterId);
        long queueCount = redisUtil.zCard(MessageFormat.format(RedisConstant.QUE_ZSET_CLUSTER, clusterId));
        logger.info("消费集群{}的请求队列时，此集群中空闲的设备数为{}，排队人数为{}", clusterId, deviceCount, queueCount);
        if (deviceCount > 0 && queueCount > 0){
            return consumeQueue(clusterId, deviceCount, queueCount);
        }else{
            return false;
        }
    }

    /**
     * 消费某一个集群队列
     * @param clusterId
     * @param deviceNum
     * @param queueNum
     */
    @Override
    public boolean consumeQueue(String clusterId, long deviceNum, long queueNum) {
        String lock = MessageFormat.format(RedisConstant.QUE_LOCK_CONSUME_CLUSTER, clusterId);
        if (redisUtil.lock(lock, clusterId)){
            try {
                logger.info("-------------开始消费集群{}的请求队列-------------", clusterId);
                long start = 0, end = start + deviceNum + (deviceNum >> 1) - 1;
                do{
                    //已经被消费的个数
                    AtomicInteger consumed = new AtomicInteger(0);
                    logger.info("获取集群{}的请求队列中{}-{}名来消费", clusterId, start, end);
                    Set<Object> accounts = redisUtil.zRangeObject(MessageFormat.format(RedisConstant.QUE_ZSET_CLUSTER, clusterId), start, end, true);
                    for (Object object : accounts) {
                        String account = (String)object;
                        Double score = redisUtil.zScore(RedisConstant.QUE_ZSET_GLOBAL, account);
                        long queuedTime = (System.currentTimeMillis() - score.longValue()) / 1000;
                        if(queuedTime > queueTimeout){
                            logger.info("用户{}排队时长超过{}小时，踢出队列", account ,queuedTime/60/60);
                            dequeue(account);
                            consumed.incrementAndGet();
                        }else {
                            QueueDetail queueDetail = getQueueDetail(account);
                            List<String> list = new ArrayList<>();
                            list.add(clusterId);
                            ApplyResultVO applyResultVO = connectService.applyFromAllIdle(account, list, queueDetail.getAppId(), queueDetail.getBaseId());
                            if (applyResultVO == null){
                                logger.info("用户{}未申请到资源，继续排队！", account);
                            }else{
                                if(applyResultVO.getStatus() == ApplyStatusEnum.GAIN.getStatus()){
                                    logger.info("用户{}刚刚申请到资源，开始后续操作",account);
                                    ConnectVO connectVO = applyResultVO.getConnectVO();
                                    consumed.incrementAndGet();
                                    boolean dequeue = dequeue(account);
                                    //判断用户这时是否已经退出排队
                                    if (dequeue){
                                        logger.info("用户{}申请到资源，并成功退出排队！开始通知上一层", account);
                                        connectVO.setQueuedTime(queuedTime);
                                        ydwResourceService.noticeResourceApplyed(connectVO);
                                    }else{
                                        logger.info("用户{}申请到资源，但退出排队失败！开始释放资源", account);
                                        //刚刚退出排队，取消连接
                                        connectService.releaseConnect(connectVO);
                                    }
                                }else{
                                    logger.info("用户{}已经其他线程消费，此线程不做处理",account);
                                }
                            }
                        }
                    }
                    logger.info("消费集群{}的请求队列{}-{}名结束，已消费{}个", clusterId, start, end, consumed);
                    deviceNum = scheduleService.idleDeviceCount(clusterId);
                    queueNum = redisUtil.zCard(MessageFormat.format(RedisConstant.QUE_ZSET_CLUSTER, clusterId));
                    logger.info("此时集群空闲设备数为{}，排队人数为{}", deviceNum, queueNum);
                    start = end + 1 - consumed.get();
                    end = start + deviceNum + (deviceNum >> 1) - 1;
                }while (deviceNum > 0 && queueNum > 0 && start < queueNum);
                String survivorPointKey = MessageFormat.format(RedisConstant.QUE_INT_CLUSTER_SURVIVORPOINT, clusterId);
                if (queueNum != 0L && deviceNum != 0L){
                    logger.info("此集群{}有空设备且有用户排队，设置幸存者指针为{}", clusterId, deviceNum - 1);
                    redisUtil.set(survivorPointKey, deviceNum - 1);
                }else{
                    logger.info("此集群{}无空设备或无用户排队，清空幸存者指针", clusterId);
                    redisUtil.del(survivorPointKey);
                }
            } catch (Exception e){
                logger.error(e.getMessage());
            } finally {
                redisUtil.unlock(lock, clusterId);
                logger.info("-------------本次消费集群{}的请求队列结束-------------", clusterId);
            }
            return true;
        }else {
            logger.info("此集群{}的正在被消费", clusterId);
            return false;
        }
    }

    @Override
    public long getSurvivorPoint(String clusterId){
        String survivorPointKey = MessageFormat.format(RedisConstant.QUE_INT_CLUSTER_SURVIVORPOINT, clusterId);
        Object o = redisUtil.get(survivorPointKey);
        logger.info("获取此集群{}的幸存者指针{}", clusterId, o);
        return o == null ? -1L : (int)o;
    }

    @Override
    public void decrSurvivorPoint(String clusterId){
        String survivorPointKey = MessageFormat.format(RedisConstant.QUE_INT_CLUSTER_SURVIVORPOINT, clusterId);
        redisUtil.decr(survivorPointKey, 1);
        logger.info("此集群{}的幸存者指针递减", clusterId);
    }

    @Override
    public ResultInfo getClustersQueueNum() {
        List<ClusterQueueStatistics> list = new ArrayList<>();
        Set<String> keys = redisUtil.getKeys(RedisConstant.QUE_ZSET_KEYS);
        for(String key : keys){
            ClusterQueueStatistics clusterQueueStatistics = new ClusterQueueStatistics();
            String clusterId = key.split(":")[1];
            clusterQueueStatistics.setClusterId(clusterId);
            TbClusters clusters = clustersService.getById(clusterId);
            clusterQueueStatistics.setClusterName(clusters.getName());
            long queueNum = redisUtil.zCard(key);
            clusterQueueStatistics.setQueueNum(queueNum);
            list.add(clusterQueueStatistics);
        }
        ClusterQueueStatistics clusterQueueStatistics = new ClusterQueueStatistics();
        clusterQueueStatistics.setClusterId("ALL");
        clusterQueueStatistics.setClusterName("全区服");
        clusterQueueStatistics.setQueueNum(redisUtil.zCard(RedisConstant.QUE_ZSET_GLOBAL));
        list.add(clusterQueueStatistics);
        return ResultInfo.success(list);
    }
}
