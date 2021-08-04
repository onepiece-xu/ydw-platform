package com.ydw.game.service.impl;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.game.dao.TbAppTagMapper;
import com.ydw.game.dao.TbGameGroupMapper;
import com.ydw.game.model.db.TbAppTag;
import com.ydw.game.model.db.TbGameGroup;
import com.ydw.game.model.db.TbTag;
import com.ydw.game.model.vo.GameGroupAppVO;
import com.ydw.game.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.game.dao.AppMapper;
import com.ydw.game.model.db.App;
import com.ydw.game.model.vo.ResultInfo;
import com.ydw.game.service.IAppService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulh
 * @since 2020-08-05
 */
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements IAppService {
	@Value("${url.paasApi}")
	private String paasApi;
    @Autowired
    private YdwPaasTokenService ydwPaasTokenService;

    @Autowired
    private AppMapper appMapper;
    @Autowired
    private TbGameGroupMapper tbGameGroupMapper;
    @Autowired
    private TbAppTagMapper tbAppTagMapper;


    @Override
	public ResultInfo getAllApps() {
		List<App> list = list(new QueryWrapper<App>().orderByAsc("order_num"));
		return ResultInfo.success(list);
	}

	@Override
	public ResultInfo getSyncApps(String body) {

		String url= paasApi+"/cgp-paas-open/userAppsRelated/getOwnerAppList";
        Map<String,String> headers = new HashMap<>();
        String enterprisePaasToken = ydwPaasTokenService.getEnterprisePaasToken();
        headers.put("Authorization",enterprisePaasToken);
        Map<String,Object> params = new HashMap<>();
        JSONObject object = JSONObject.parseObject(body);
        String enterpriseId = object.getString("enterpriseId");

        params.put("enterpriseId",enterpriseId);

        String doGet = HttpUtil.doJsonPost(url, headers, params);
//
//        ResultInfo info = JSON.parseObject(JSON.parse(doGet).toString(), ResultInfo.class);
        JSONObject jsonObject = JSON.parseObject(doGet);

        JSONArray records = jsonObject.getJSONArray("data");
        //查到新 应用列表和 原应用列表
        List<App> newApps = JSONArray.parseArray(records.toJSONString(), App.class);
        QueryWrapper<App> wrapper = new QueryWrapper<>();
        List<App> oldAppList = appMapper.selectList(wrapper);

        //取ID对比
        List<String> newAppIds =new ArrayList<>();
        List<String> oldIds =new ArrayList<>();


        for(App newApp: newApps ) {
            newAppIds.add(newApp.getId());
        }
        for(App oldApp :oldAppList){
            oldIds.add(oldApp.getId());
        }
        //找到需要增加的应用 id和 需要删除的应用id
        Set set1= new HashSet(newAppIds);
        Set set2 =new HashSet(oldIds);
        set1.removeAll(set2);//set1 : 新增加的
        Set set3=new HashSet(newAppIds);
        set2.removeAll(set3);//set2 : 需要删除掉的

        List<String> add =new ArrayList<>(set1);

        List<String> del =new ArrayList<>(set2);


        //新加的应用插入应用表
        for(String id:add){
        for(App newApp: newApps ) {
            if(id.equals(newApp.getId())){
                    appMapper.insert(newApp);
                }
            }
        }
    // 需要删除的应用id
        for(String id:del){
           //删除应用和游戏组关联表
            List<TbGameGroup> game_id = tbGameGroupMapper.selectList(new QueryWrapper<TbGameGroup>().eq("game_id", id));
            for (int i = 0; i <game_id.size() ; i++) {
                tbGameGroupMapper.deleteById(game_id.get(i));
                System.out.println(game_id.get(i)+"----删除关联表应用组-----"+i);
            }

            //删标签应用关联表
            List<TbAppTag> app_id = tbAppTagMapper.selectList(new QueryWrapper<TbAppTag>().eq("app_id", id));
            for (int i = 0; i <app_id.size() ; i++) {
                tbAppTagMapper.deleteById(app_id.get(i));
                System.out.println(app_id.get(i)+"---删除关联表标签------"+i);
            }
            //删除应用表
            appMapper.deleteById(id);
            System.out.println("删除应用表-------------"+id);

        }
        // 更新处理相同的游戏id
        newAppIds.retainAll(oldIds);
        if(newAppIds.size()>0){
            for(String id:newAppIds){
                for (App a:newApps){
                    if(id.equals(a.getId())){
                        appMapper.updateById(a);
                    }
                }
            }

        }


        return ResultInfo.success("同步成功！");

	}

    @Override
    public ResultInfo getApps(String body, Page buildPage) {
        Page<App> list = appMapper.selectPage(buildPage,new QueryWrapper<App>());
        return ResultInfo.success(list);
    }

}
