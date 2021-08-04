package com.ydw.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.user.dao.ClusterConfigMapper;
import com.ydw.user.dao.TbWebrtcConfigMapper;


import com.ydw.user.model.db.ClusterConfig;
import com.ydw.user.model.db.TbWebrtcConfig;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.user.model.vo.WebRtcConfigVO;
import com.ydw.user.service.IWebrtcConfigService;
import com.ydw.user.utils.PasswordUtil;
import com.ydw.user.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author heao
 * @since 2020-08-07
 */
@Service
public class WebrtcConfigServiceImpl extends ServiceImpl<TbWebrtcConfigMapper, TbWebrtcConfig> implements IWebrtcConfigService {
    @Autowired
    private TbWebrtcConfigMapper webrtcConfigMapper;
    @Autowired
    private ClusterConfigMapper clusterConfigMapper;

    @Override
    public ResultInfo getConfigList(String body, Page buildPage) {
        QueryWrapper<WebRtcConfigVO> wrapper = new QueryWrapper<>();
        Page page = webrtcConfigMapper.getList(buildPage, wrapper);
        return ResultInfo.success(page);
    }

    @Override
    public ResultInfo addConfig(TbWebrtcConfig webrtcConfig) {
        webrtcConfig.setValid(true);
        webrtcConfig.setTurnPassword(PasswordUtil.md5(webrtcConfig.getTurnPassword()));
        webrtcConfigMapper.insert(webrtcConfig);
        return ResultInfo.success("添加成功！");
    }

    @Override
    public ResultInfo updateConfig(TbWebrtcConfig webrtcConfig) {
        webrtcConfig.setTurnPassword(PasswordUtil.md5(webrtcConfig.getTurnPassword()));
        webrtcConfigMapper.updateById(webrtcConfig);
        return ResultInfo.success("编辑成功！");
    }

    @Override
    public ResultInfo deleteConfig(List<Integer> ids) {
        String msg= "";
        for (Integer i:ids) {
            TbWebrtcConfig byId = webrtcConfigMapper.getById(i);
            List<ClusterConfig> byConfigId = clusterConfigMapper.getByConfigId(i);
            if(byConfigId.size()>0){
                msg+=byId.getSignalServer()+",";
            }else{
                byId.setValid(false);
                webrtcConfigMapper.updateById(byId);
            }
        }

        if(!msg.equals("")){
            return  ResultInfo.fail(msg.substring(0, msg.length() -1)+"，存在关联集群，删除失败！");
        }
        return ResultInfo.success("删除成功！");
    }
}
