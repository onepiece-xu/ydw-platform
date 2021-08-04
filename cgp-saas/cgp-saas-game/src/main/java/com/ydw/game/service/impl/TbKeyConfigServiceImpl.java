package com.ydw.game.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.game.model.constant.Constant;
import com.ydw.game.model.db.TbKeyConfig;
import com.ydw.game.model.vo.ResultInfo;
import com.ydw.game.redis.RedisUtil;
import com.ydw.game.service.ITbKeyConfigService;
import com.ydw.game.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author heao
 * @since 2020-07-27
 */
@Service
public class TbKeyConfigServiceImpl implements ITbKeyConfigService {

//    @Autowired
//    private TbKeyConfigMapper tbKeyConfigMapper;

    @Value("${url.paasApi}")
    private String paasApi;
    @Autowired
    private RedisUtil redisUtil;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private  YdwPaasTokenService ydwPaasTokenService;

    @Override
    public ResultInfo getConfigList(HttpServletRequest request, String body, Page buildPage) {
        JSONObject jsonObject = JSON.parseObject(body);
        String author = jsonObject.getString("author");
        String appId = jsonObject.getString("appId");
//        Page<TbKeyConfig> configList = tbKeyConfigMapper.getConfigList(author, appId, buildPage);
       String url=paasApi+"/cgp-paas-admin/keyConfig/getConfigList";
       String enterprisePaasToken = ydwPaasTokenService.getEnterprisePaasToken();
       Map<String,String> headers = new HashMap<>();
       Map<String,Object> params = new HashMap<>();
       headers.put("Authorization",enterprisePaasToken);
       params.put("appId", appId);
       params.put("author", author);
       String doJsonPost = HttpUtil.doJsonPost(url, headers, params);
       ResultInfo info = JSON.parseObject(JSON.parse(doJsonPost).toString(), ResultInfo.class);
       return info;
    }

    @Override
    public ResultInfo addConfig(HttpServletRequest request, TbKeyConfig tbKeyConfig) {
        tbKeyConfig.setValid(true);
//        tbKeyConfigMapper.insert(tbKeyConfig);
        String url =paasApi+"/cgp-paas-admin/keyConfig/addConfig";
        String enterprisePaasToken = ydwPaasTokenService.getEnterprisePaasToken();
        Map<String,String> headers = new HashMap<>();
        Map<String,Object> params = new HashMap<>();
        headers.put("Authorization",enterprisePaasToken);
        params.put("appId", tbKeyConfig.getAppId());
        params.put("author", tbKeyConfig.getAuthor());
        params.put("platform", tbKeyConfig.getPlatform());
        params.put("name", tbKeyConfig.getPlatform());
        params.put("config", tbKeyConfig.getConfig());
        String doJsonPost = HttpUtil.doJsonPost(url, headers, params);
        logger.info(tbKeyConfig.getName()+"，虚拟键位，创建成功！");
        ResultInfo info = JSON.parseObject(JSON.parse(doJsonPost).toString(), ResultInfo.class);
        return info;
    }

    @Override
    public ResultInfo updateConfig(HttpServletRequest request, TbKeyConfig tbKeyConfig) {
        String name = tbKeyConfig.getName();
        String url =paasApi+"/cgp-paas-admin/keyConfig/updateConfig";
        String enterprisePaasToken = ydwPaasTokenService.getEnterprisePaasToken();
        Map<String,String> headers = new HashMap<>();
        Map<String,Object> params = new HashMap<>();
        params.put("id",tbKeyConfig.getId());
        params.put("appId", tbKeyConfig.getAppId());
        params.put("author", tbKeyConfig.getAuthor());
        params.put("platform", tbKeyConfig.getPlatform());
        params.put("name", name);
        params.put("config", tbKeyConfig.getConfig());
        headers.put("Authorization",enterprisePaasToken);
//        TbKeyConfig configByName = tbKeyConfigMapper.getConfigByName(name);
//        if (null != configByName) {
//            logger.error(tbKeyConfig.getName()+"虚拟键位 名称被占用！");
//            return ResultInfo.fail("名称已被占用！请重新输入！");
//        }
//        tbKeyConfigMapper.updateById(tbKeyConfig);
        String doJsonPost = HttpUtil.doJsonPost(url, headers, params);
        ResultInfo info = JSON.parseObject(JSON.parse(doJsonPost).toString(), ResultInfo.class);
        return info;
    }

    @Override
    public ResultInfo deleteConfig(HttpServletRequest request, List<Integer> ids) {
        String url =paasApi+"/cgp-paas-admin/keyConfig/deleteConfig";
        String enterprisePaasToken = ydwPaasTokenService.getEnterprisePaasToken();
        Map<String,String> headers = new HashMap<>();
//        Map<String,Object> params = new HashMap<>();
        headers.put("Authorization",enterprisePaasToken);
        String doJsonPost = HttpUtil.doJsonPost(url, headers, ids);
//        params.put(ids);
//        TbKeyConfig tbKeyConfig = new TbKeyConfig();

//        for (Integer id : ids) {
//            tbKeyConfig.setId(id);
//            tbKeyConfig.setValid(false);
////            tbKeyConfigMapper.updateById(tbKeyConfig);
//            logger.error(tbKeyConfig.getName()+"，虚拟键位删除成功！");
//        }

        ResultInfo info = JSON.parseObject(JSON.parse(doJsonPost).toString(), ResultInfo.class);
        return info;
    }
}
