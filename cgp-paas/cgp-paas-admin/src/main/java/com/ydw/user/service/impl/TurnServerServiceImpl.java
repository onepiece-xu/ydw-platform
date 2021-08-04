package com.ydw.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.user.dao.ClusterTurnMapper;
import com.ydw.user.model.db.ClusterTurn;
import com.ydw.user.model.db.TurnServer;
import com.ydw.user.dao.TurnServerMapper;
import com.ydw.user.model.vo.ClusterBindVO;
import com.ydw.user.service.ITurnServerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.user.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author heao
 * @since 2020-08-22
 */
@Service
public class TurnServerServiceImpl extends ServiceImpl<TurnServerMapper, TurnServer> implements ITurnServerService {

    @Autowired
    private TurnServerMapper turnServerMapper;
    @Autowired
    private ClusterTurnMapper clusterTurnMapper;

    @Override
    public ResultInfo getSignalServerList(String body, Page buildPage) {
        QueryWrapper<TurnServer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("valid",1);
        Page page = turnServerMapper.selectPage(buildPage, queryWrapper);

        return ResultInfo.success(page);
    }

    @Override
    public ResultInfo addTurnServer(TurnServer turnServer) {
        turnServer.setValid(true);
        turnServerMapper.insert(turnServer);
        return ResultInfo.success("添加成功！");
    }

    @Override
    public ResultInfo updateTurnServer(TurnServer turnServer) {
        turnServerMapper.updateById(turnServer);
        return ResultInfo.success("编辑成功！");
    }

    @Override
    public ResultInfo delete(List<Integer> ids) {
        String  msg ="";
//        ClusterTurn clusterTurn = new ClusterTurn();

        for (Integer id : ids) {
//            clusterTurn.setTurnServer(id);
            List<ClusterTurn> byTurnServer = clusterTurnMapper.getByTurnServer(id);
            if(byTurnServer.size()>0){
                msg+=turnServerMapper.selectById(id).getServerName()+",";
            }else {
                TurnServer turnServer = turnServerMapper.selectById(id);
                turnServer.setValid(false);
                turnServerMapper.updateById(turnServer);
            }
        }
        if(!msg.equals("")){
            return ResultInfo.fail(msg.substring(0,msg.length()-1)+"删除失败，已被綁定！");
        }
        return ResultInfo.success("删除成功！");
    }

    @Override
    public ResultInfo bindList(Integer id, Page buildPage) {
//        QueryWrapper<ClusterTurn> wrapper = new QueryWrapper<>();
//        ClusterTurn clusterTurn = new ClusterTurn();
//        clusterTurn.setId(id);
//        Page<ClusterTurn> page = new Page<>();
//        Page<ClusterTurn> clusterTurnPage = clusterTurnMapper.selectPage(page, wrapper.setEntity(clusterTurn));
        Page<ClusterBindVO> list = clusterTurnMapper.bindList(id, buildPage);
        return ResultInfo.success(list);
    }

    @Override
    public ResultInfo bind(String body) {
        JSONObject jsonObject = JSON.parseObject(body);
        Integer turnServer = jsonObject.getInteger("turnServer");
        String type = jsonObject.getString("type");
        JSONArray clusterIds = jsonObject.getJSONArray("clusterIds");
//        String clusterId = jsonObject.getString("clusterIds");

        ClusterTurn clusterTurn = new ClusterTurn();

        for (Object id : clusterIds) {
            ClusterTurn byClusterId = clusterTurnMapper.getByClusterId(id.toString());
            if (type.equals("bind")) {
                //以前绑定过
                if (null != byClusterId) {
                    byClusterId.setValid(true);
                    byClusterId.setClusterId(id.toString());
                    byClusterId.setTurnServer(turnServer);
                    clusterTurnMapper.updateById(byClusterId);
                } else {
                    //未绑定过
                    clusterTurn.setClusterId(id.toString());
                    clusterTurn.setTurnServer(turnServer);
                    clusterTurn.setValid(true);
                    clusterTurnMapper.insert(clusterTurn);
                }

            } else {
                if(byClusterId!=null){
                    byClusterId.setValid(false);

                    clusterTurnMapper.updateById(byClusterId);
                }
            }

        }
        return ResultInfo.success("操作成功！");
    }

    @Override
    public ResultInfo unbindList(Integer id, Page buildPage) {
        Page<ClusterBindVO> list = clusterTurnMapper.unbindList(id, buildPage);
        return ResultInfo.success(list);
    }

    @Override
    public ResultInfo getInfo(Integer id) {
        TurnServer turnServer = turnServerMapper.selectById(id);

        return ResultInfo.success(turnServer);
    }

}
