package com.ydw.user.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.user.dao.TbKeyConfigMapper;
import com.ydw.user.model.db.TbKeyConfig;
import com.ydw.user.model.vo.KeyCode;
import com.ydw.user.model.vo.KeyConfigVO;
import com.ydw.user.service.ITbKeyConfigService;
import com.ydw.user.utils.ResultInfo;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author heao
 * @since 2020-07-27
 */
@Service
public class TbKeyConfigServiceImpl extends ServiceImpl<TbKeyConfigMapper, TbKeyConfig> implements ITbKeyConfigService {

    @Autowired
    private TbKeyConfigMapper tbKeyConfigMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public ResultInfo getConfigList(HttpServletRequest request, String body, Page buildPage) {
        JSONObject jsonObject = JSON.parseObject(body);
        String author = jsonObject.getString("author");
        String appId = jsonObject.getString("appId");
        Integer platform = jsonObject.getInteger("platform");
        Page<KeyConfigVO> configList = tbKeyConfigMapper.getConfigList(author, appId,platform, buildPage);
        List<KeyConfigVO>  data= new ArrayList<>();
        List<KeyConfigVO> records = configList.getRecords();
        for (KeyConfigVO vo: records) {
            String config = vo.getConfig();
            config = StringEscapeUtils.unescapeJavaScript(config);
            if(null!=config){
                config=  config.replaceAll("\r", "");
            }
            vo.setConfig(config);
            data.add(vo);
        }
        if(data.size()<=0){
            return ResultInfo.success("","");
        }
        return ResultInfo.success(data.get(0));

    }

    @Override
    public ResultInfo addConfig(HttpServletRequest request, TbKeyConfig tbKeyConfig) {
        tbKeyConfig.setValid(true);
        tbKeyConfigMapper.insert(tbKeyConfig);
        logger.info(tbKeyConfig.getName()+"，虚拟键位，创建成功！");
        return ResultInfo.success("创建成功！");
    }

    @Override
    public ResultInfo updateConfig(HttpServletRequest request, TbKeyConfig tbKeyConfig) {
        String name = tbKeyConfig.getName();
        TbKeyConfig configByName = tbKeyConfigMapper.getConfigByName(name);
        if (null != configByName) {
            logger.error(tbKeyConfig.getName()+"虚拟键位 名称被占用！");
            return ResultInfo.fail("名称已被占用！请重新输入！");
        }
        tbKeyConfigMapper.updateById(tbKeyConfig);
        return ResultInfo.success("编辑成功！");
    }

    @Override
    public ResultInfo deleteConfig(HttpServletRequest request, List<Integer> ids) {
        TbKeyConfig tbKeyConfig = new TbKeyConfig();

        for (Integer id : ids) {
            tbKeyConfig.setId(id);
            tbKeyConfig.setValid(false);
            tbKeyConfigMapper.updateById(tbKeyConfig);
            logger.error(tbKeyConfig.getName()+"，虚拟键位删除成功！");
        }

        return ResultInfo.success("删除成功！");
    }
}
