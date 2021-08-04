package com.ydw.platform.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.platform.dao.*;
import com.ydw.platform.model.db.*;
import com.ydw.platform.model.vo.*;
import com.ydw.platform.service.IAppService;
import com.ydw.platform.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Value("${url.pics}")
    private String pics;

    @Autowired
    private FeginClientService feginClientService;

    @Autowired
    private AppMapper appMapper;
    @Autowired
    private GameGroupMapper tbGameGroupMapper;
    @Autowired
    private AppTagMapper tbAppTagMapper;
    @Autowired
    private AppPicturesMapper appPicturesMapper;
    @Autowired
    private AppPlatformMapper appPlatformMapper;


    @Override
	public ResultInfo getAllApps() {
		List<App> list = list(new QueryWrapper<App>().orderByAsc("order_num"));
		return ResultInfo.success(list);
	}

    @Override
    public ResultInfo getPCAppsForVirtualKey(Page buildPage) {
        Page page = page(buildPage, new QueryWrapper<App>().eq("valid",true).in("type",1,3));
        return ResultInfo.success(page);
    }

    @Override
    public ResultInfo getPlayList(String userId) {
        List<PlayListVO> data = appMapper.getPlayList(userId);
        for(PlayListVO vo:data){
            String tag = vo.getTag();
            if(StringUtils.isNotEmpty(tag)){
                List<String> list = new ArrayList<>();
                String[] split = new String(tag).split("[\\,\\;]");
                for (String s:split){
                    list.add(s);
                }
                vo.setTags(list);
            }
            if(null==tag){
                vo.setTags(Collections.emptyList());
            }
        }
        return ResultInfo.success(data);

    }

    @Override
	public ResultInfo getSyncApps(String body) {

		String url= paasApi+"/cgp-paas-open/userAppsRelated/getOwnerAppList";
        Map<String,String> headers = new HashMap<>();
        String enterprisePaasToken = feginClientService.getPaasToken();
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
            List<GameGroup> game_id = tbGameGroupMapper.selectList(new QueryWrapper<GameGroup>().eq("game_id", id));
            for (int i = 0; i <game_id.size() ; i++) {
                tbGameGroupMapper.deleteById(game_id.get(i));
                System.out.println(game_id.get(i)+"----删除关联表应用组-----"+i);
            }

            //删标签应用关联表
            List<AppTag> app_id = tbAppTagMapper.selectList(new QueryWrapper<AppTag>().eq("app_id", id));
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
        QueryWrapper<App> appQueryWrapper = new QueryWrapper<>();
        appQueryWrapper.eq("valid",1);
        Page<App> list = appMapper.selectPage(buildPage,appQueryWrapper);
        return ResultInfo.success(list);
    }

    /**
     * 获取最近玩过的历史游戏记录
     * @return
     */
    @Override
    public ResultInfo getRecordPlayList(String userId) {

        List<RecordListVO> data = appMapper.getRecordPlayList(userId);

        List<RecordListVO> datas = new ArrayList<>();

        for(RecordListVO vo:data){
            String appId = vo.getAppId();
            AppPicDetailsVO appPicDetails = appPicturesMapper.getAppPicDetails(appId);
            if(appPicDetails!=null){
                String bigPic = appPicDetails.getBigPic();
                String midPic = appPicDetails.getMidPic();
                String smallPic = appPicDetails.getSmallPic();
                String logoPic = appPicDetails.getLogoPic();
                Integer type = appPicDetails.getType();
                vo.setType(type);
                vo.setBigPic(bigPic);
                vo.setMidPic(midPic);
                vo.setSmallPic(smallPic);
                vo.setLogoPic(logoPic);
            }
            datas.add(vo);
        }
        return ResultInfo.success(datas);
    }

    @Override
    public ResultInfo getRecordPlayListAndroid(String userId) {

        List<RecordListVO> data = appMapper.getRecordPlayList(userId);

        List<RecordListVO> datas = new ArrayList<>();

        for(RecordListVO vo:data){
            String appId = vo.getAppId();
            //获取app标签列表
            List<TagVO> appTagNameByAppId = tbAppTagMapper.getAppTagNameByAppId(appId);
            //存标签list
            List<String> tags = new ArrayList<>();
            for(TagVO t:appTagNameByAppId){
                String tagName = t.getTagName();
                tags.add(tagName);
            }
            vo.setTags(tags);
            AppPicDetailsVO appPicDetails = appPicturesMapper.getAppPicDetails(appId);
            if(appPicDetails!=null){
                String bigPic = appPicDetails.getBigPic();
                String midPic = appPicDetails.getMidPic();
                String smallPic = appPicDetails.getSmallPic();
                String logoPic = appPicDetails.getLogoPic();
                Integer type = appPicDetails.getType();
                vo.setType(type);
                vo.setBigPic(bigPic);
                vo.setMidPic(midPic);
                vo.setSmallPic(smallPic);
                vo.setLogoPic(logoPic);
            }
            datas.add(vo);
        }
        return ResultInfo.success(datas);
    }

    @Override
    public ResultInfo enableApps(EnableApps vo) {
        Integer type = vo.getType();
        List<String> ids = vo.getIds();
        for(String id:ids ){
            App app = appMapper.selectById(id);
            if(type==1){
                app.setValid(true);
                appMapper.updateById(app);
            }else{
                app.setValid(false);
                appMapper.updateById(app);
            }
        }

        return ResultInfo.success("操作成功!");
    }

    @Override
    public ResultInfo getPlatformList() {
        QueryWrapper<AppPlatform> wrapper = new QueryWrapper<>();
        List<AppPlatform> appPlatforms = appPlatformMapper.selectList(wrapper);
        return ResultInfo.success(appPlatforms);
    }

    @Override
    public App getCloudComputer() {
        QueryWrapper<App> qw = new QueryWrapper<>();
        qw.eq("type",3);
        qw.eq("valid",true);
        return getOne(qw);
    }
    @Override
    public ResultInfo getAppInfo(String id) {
        QueryWrapper<App> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        queryWrapper.eq("valid",1);
        App app = appMapper.selectOne(queryWrapper);
        List<String> appTag= tbAppTagMapper.getTagsByAppId(id);
        AppDetailVO detailVO = new AppDetailVO();
        BeanUtils.copyProperties(app,detailVO);
        detailVO.setTags(appTag);
        return ResultInfo.success(detailVO);
    }
}
