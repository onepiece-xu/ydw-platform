package com.ydw.user.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ydw.user.dao.TbDemoMapper;
import com.ydw.user.dao.TbUserAppsMapper;
import com.ydw.user.model.db.TbDemo;
import com.ydw.user.model.vo.AppVO;
import com.ydw.user.model.vo.DemoAppVO;
import com.ydw.user.model.vo.DemoApps;
import com.ydw.user.service.ITbDemoService;
import com.ydw.user.utils.ResultInfo;
import com.ydw.user.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author heao
 * @since 2020-06-09
 */
@Service
public class TbDemoServiceImpl extends ServiceImpl<TbDemoMapper, TbDemo> implements ITbDemoService {

    @Autowired
    private TbUserAppsMapper tbUserAppsMapper;
    @Autowired
    private TbDemoMapper  tbDemoMapper;


    @Override
    public ResultInfo getAppsList(HttpServletRequest request) {
        List<DemoApps> apps = tbUserAppsMapper.getApps();
        return ResultInfo.success(apps);
    }

    @Override
    public ResultInfo commit(HttpServletRequest request, String body) {
        JSONObject object = JSONObject.parseObject(body);

        JSONArray apps = object.getJSONArray("apps");
        TbDemo demo = new TbDemo();
        List<TbDemo> ls =tbDemoMapper.getAll();
        for (TbDemo td:ls ) {
            tbDemoMapper.deleteById(td.getId());
        }
        for (int i = 0; i < apps.size(); i++) {
            if (apps.getJSONObject(i).get("appId").equals("")) {
                continue;
            } else {
                String appId = apps.getJSONObject(i).get("appId").toString();
                Integer order = (Integer) apps.getJSONObject(i).get("order");
                Integer type = (Integer) apps.getJSONObject(i).get("type");
                demo.setAppId(appId);
                demo.setOrderNumber(order);
                demo.setType(type);
                demo.setValid(true);
                tbDemoMapper.insert(demo);
            }
        }

//        for (int i = 0; i < apps.size(); i++) {
//            if (apps.getJSONObject(i).get("appId") == null) {
//                continue;
//            } else {
//                String appId = apps.getJSONObject(i).get("appId").toString();
//                Integer order = (Integer) apps.getJSONObject(i).get("order");
//                List<TbDemo> byOrder = tbDemoMapper.getByOrder(order);
//                Integer type = (Integer) apps.getJSONObject(i).get("type");
//                if (byOrder.size() > 0) {
//                    TbDemo tbDemo = byOrder.get(0);
//                    tbDemo.setAppId(appId);
//                    tbDemo.setOrderNumber(order);
//                    tbDemo.setType(type);
//                    tbDemoMapper.updateById(tbDemo);
//                } else {
//                    demo.setAppId(appId);
//                    demo.setOrderNumber(order);
//                    demo.setType(type);
//                    demo.setValid(true);
//                    tbDemoMapper.insert(demo);
//                }
//            }
//        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo getDemoAppsList(HttpServletRequest request) {
        List<DemoAppVO> demoAppsList = tbUserAppsMapper.getDemoAppsList(1);
        return ResultInfo.success(demoAppsList);
    }

    @Override
    public ResultInfo getDemoAppsListPc(HttpServletRequest request,TbDemo tbDemo) {
        Integer type = tbDemo.getType();
        List<DemoAppVO> demoAppsList = tbUserAppsMapper.getDemoAppsList(type);
        return ResultInfo.success(demoAppsList);
    }


}
