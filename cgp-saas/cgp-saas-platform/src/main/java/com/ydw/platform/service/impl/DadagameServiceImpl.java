package com.ydw.platform.service.impl;

import com.alibaba.fastjson.JSON;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.redis.RedisUtil;
import com.ydw.platform.service.IConnectService;
import com.ydw.platform.service.IDadagameService;
import com.ydw.platform.service.IResourceService;
import com.ydw.platform.util.HttpUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

/**
 * 游戏管家处理类
 */
@Service
public class DadagameServiceImpl implements IDadagameService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${rental.dadagame.channel}")
    private String channel;

    @Value("${rental.dadagame.scretKey}")
    private String scretKey;

    @Value("${rental.dadagame.url.borrowUrl}")
    private String borrowUrl;

    @Value("${rental.dadagame.url.returnUrl}")
    private String returnUrl;

    public final static String MAP_STARTGAMENO_CONNECTID = "map_startGameNo_connectId";

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IResourceService resourceService;

    private void buildSign(SortedMap<String, Object> params){
        String sign = getSign(params);
        params.put("sign", sign);
    }

    private String buildUrl(String url, Map<String, Object> params){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (sb.length() == 0){
                sb.append("?");
            }else{
                sb.append("&");
            }
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
        }
        sb.insert(0, url);
        return sb.toString();
    }

    /**
     * 游戏管家的签名处理流程：
     * 将请求参数中除 sign 参数外的参数按照参数名排序,按键名=键值加上”&”号拼接得到字
     * 符串加上”key=密钥”后 MD5 并转换为大写字符串.例如,密钥为 mykey,请求参数为
     * mac=a，uid=b,channel=d,gameId=e,timestamp=c 则按参数名排序后并根据=拼接
     * 参数对后的字符串为
     * channel=d&gameId=e&mac=a&timestamp=c&uid=b&key=mykey
     * 则签名
     * sign=strtoupper(md5(‘channel=d&gameId=e&mac=a&timestamp=c&uid=
     * b&key=mykey’))=4819B0038562DDA9E6731D5816E0944C
     * 注：null 不参与签名，空串参与签名
     * @param params
     */
    private String getSign(SortedMap<String, Object> params){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (entry.getKey().equals("sign")){
                continue;
            }
            if (sb.length() != 0){
                sb.append("&");
            }
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
        }
        sb.append("&").append("key").append("=").append(scretKey);
        return DigestUtils.md5Hex(sb.toString()).toUpperCase();
    }

    @Override
    public boolean checkSign(SortedMap<String, Object> params) {
        String sign = getSign(params);
        return Objects.equals(sign, params.get("sign"));
    }

    /**
     * 借号
     * @param account
     * @param appId
     * @return
     */
    @Override
    public ResultInfo borrowNum(String account, String appId) {
        SortedMap<String, Object> params = new TreeMap<>();
        params.put("uid", account);
        params.put("channel", channel);
        params.put("gameId", appId);
        params.put("timestamp", Instant.now().getEpochSecond());
        buildSign(params);
        String url = buildUrl(borrowUrl, params);
        String s = HttpUtil.doJsonPost(url, params);
        logger.info("取号返回结果：{}",s);
        return JSON.parseObject(s, ResultInfo.class);
    }

    /**
     * 还号
     * @param startGameNo
     * @return
     */
    @Override
    public ResultInfo returnNum(String startGameNo) {
        SortedMap<String, Object> params = new TreeMap<>();
        params.put("startGameNo", startGameNo);
        params.put("channel", channel);
        params.put("timestamp", Instant.now().getEpochSecond());
        buildSign(params);
        String url = buildUrl(returnUrl, params);
        String s = HttpUtil.doJsonPost(url, params);
        logger.info("还号返回结果：{}",s);
        delStartGameNoToConnectId(startGameNo);
        return JSON.parseObject(s, ResultInfo.class);
    }

    @Override
    public void returnNumNotice(String startGameNo) {
        String connectId = getConnectIdByStartGameNo(startGameNo);
        resourceService.release(connectId);
    }

    @Override
    public void saveStartGameNoToConnectId(String startGameNo, String connectId){
        Map<Object, Object> params = new HashMap<>();
        params.put(startGameNo, connectId);
        redisUtil.hmset(MAP_STARTGAMENO_CONNECTID, params);
    }

    private void delStartGameNoToConnectId(String startGameNo){
        redisUtil.hdel(MAP_STARTGAMENO_CONNECTID, startGameNo);
    }

    private String getConnectIdByStartGameNo(String startGameNo){
        return (String)redisUtil.hget(MAP_STARTGAMENO_CONNECTID, startGameNo);
    }

    public static void main(String[] args) throws Exception{
        DadagameServiceImpl dadagameService = new DadagameServiceImpl();
        dadagameService.channel = "ydw-c";
        dadagameService.scretKey = "fd302a0094e8c12cb0deed37b4584cec";
        dadagameService.borrowUrl = "http://api.test.dadagame.com/anon/channel/start/game";
        dadagameService.returnUrl = "http://api.test.dadagame.com/anon/channel/stop/game";

        ResultInfo resultInfo = dadagameService.borrowNum("abcde", "2005031");
        Object data = resultInfo.getData();
        String startGameNo = JSON.parseObject(JSON.toJSONString(data)).getString("startGameNo");
        dadagameService.logger.info("游戏账号：{}", startGameNo);
//        Thread.sleep(2000);
//        dadagameService.returnNum(startGameNo);
    }
}
