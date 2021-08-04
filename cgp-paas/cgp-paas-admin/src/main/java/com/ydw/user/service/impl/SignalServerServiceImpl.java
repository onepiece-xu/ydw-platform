package com.ydw.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.user.dao.ClusterSignalMapper;
import com.ydw.user.model.db.ClusterSignal;
import com.ydw.user.model.db.SignalServer;
import com.ydw.user.dao.SignalServerMapper;
import com.ydw.user.model.db.TurnServer;
import com.ydw.user.model.vo.ClusterBindVO;
import com.ydw.user.service.ISignalServerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.user.utils.ResultInfo;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author heao
 * @since 2020-08-22
 */
@Service
public class SignalServerServiceImpl extends ServiceImpl<SignalServerMapper, SignalServer> implements ISignalServerService {
    @Autowired
    private  SignalServerMapper signalServerMapper;
    @Autowired
    private ClusterSignalMapper clusterSignalMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ResultInfo getSignalServerList(String body, Page buildPage) {
        QueryWrapper<SignalServer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("valid",1);
        Page page = signalServerMapper.selectPage(buildPage, queryWrapper);
        return ResultInfo.success(page);
    }

    @Override
    public ResultInfo addSignalServer(SignalServer signalServer) {
        signalServer.setValid(true);
        signalServerMapper.insert(signalServer);
        logger.info(signalServer.getServerName()+"添加成功！--------");
        return ResultInfo.success("添加成功！");
    }

    @Override
    public ResultInfo updateSignalServer(SignalServer signalServer) {
        signalServerMapper.updateById(signalServer);
        logger.info(signalServer.getServerName()+"编辑成功！--------");
        return ResultInfo.success("编辑成功！");
    }

    @Override
    public ResultInfo delete(List<Integer> ids) {
//        ClusterSignal clusterSignal = new ClusterSignal();
    String msg="";
        for (Integer id: ids) {
            //T  刪除前判斷是否被使用
//            clusterSignal.setSignalServer(1);
            List<ClusterSignal> bySignalServer = clusterSignalMapper.getBySignalServer(id);
            if(bySignalServer.size()>0){
                msg+=signalServerMapper.selectById(id).getServerName()+",";
            }else{
                SignalServer signalServer = signalServerMapper.selectById(id);
                signalServer.setValid(false);
                signalServerMapper.updateById(signalServer);
            }
        }
        if(!msg.equals("")){
            logger.error(msg.substring(0,msg.length()-1)+"删除失败，已被綁定！");
            return  ResultInfo.fail(msg.substring(0,msg.length()-1)+"删除失败，已被綁定！");
        }
        return ResultInfo.success("删除成功！");
    }

    @Override
    public ResultInfo bindList(Integer id,Page page) {
//        QueryWrapper<ClusterSignal> wrapper = new QueryWrapper<>();
//        ClusterSignal cs = new ClusterSignal();
//        cs.setId(id);
//        Page<ClusterSignal> page = new Page<>();
//        Page list = clusterSignalMapper.selectPage(page,wrapper.setEntity(cs));

        Page<ClusterBindVO> list = clusterSignalMapper.bindList(id, page);
        return ResultInfo.success(list);
    }

    @Override
    public ResultInfo bind(String body) {
        JSONObject jsonObject = JSON.parseObject(body);
        Integer signalServer = jsonObject.getInteger("signalServer");
        JSONArray clusterIds = jsonObject.getJSONArray("clusterIds");
        String type = jsonObject.getString("type");
        QueryWrapper<ClusterSignal> wrapper= new QueryWrapper<>();
        ClusterSignal clusterSignal = new ClusterSignal();
        for (Object id: clusterIds ) {
            ClusterSignal selectOne = clusterSignalMapper.getByClusterId(id.toString());
            if(type.equals("bind")){
                if(null!=selectOne){
                   selectOne.setValid(true);
                   selectOne.setClusterId(id.toString());
                   selectOne.setSignalServer(signalServer);
                   clusterSignalMapper.updateById(selectOne);
                }else{
                    clusterSignal.setValid(true);
                    clusterSignal.setSignalServer(signalServer);
                    clusterSignal.setClusterId(id.toString());
                    clusterSignalMapper.insert(clusterSignal);
                }
            }else{
                //解绑
                if(selectOne!=null){
                    selectOne.setValid(false);
                    clusterSignalMapper.updateById(selectOne);
                }


            }
        }


        return ResultInfo.success("操作成功！");
    }

    @Override
    public ResultInfo unbindList(Integer id, Page buildPage) {
        Page<ClusterBindVO> list = clusterSignalMapper.unbindList(id, buildPage);
        return ResultInfo.success(list);
    }

    @Override
    public ResultInfo getInfo(Integer id) {

        SignalServer signalServer = signalServerMapper.selectById(id);
        return ResultInfo.success(signalServer);
    }
}
