package com.ydw.game.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ydw.game.dao.AppMapper;
import com.ydw.game.dao.TbAppTagMapper;
import com.ydw.game.dao.TbTagMapper;
import com.ydw.game.dao.TbTagTypeMapper;
import com.ydw.game.model.constant.Constant;
import com.ydw.game.model.db.App;
import com.ydw.game.model.db.TbAppTag;
import com.ydw.game.model.db.TbTag;

import com.ydw.game.model.db.TbTagType;
import com.ydw.game.model.vo.AppTagVO;
import com.ydw.game.model.vo.ResultInfo;
import com.ydw.game.model.vo.TagBindVO;
import com.ydw.game.model.vo.TagVO;
import com.ydw.game.redis.RedisUtil;
import com.ydw.game.service.ITbTagService;

import com.ydw.game.util.HttpUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author heao
 * @since 2020-06-29
 */
@Service
public class TbTagServiceImpl extends ServiceImpl<TbTagMapper, TbTag> implements ITbTagService {

    @Autowired
    private TbTagMapper tbTagMapper;

    @Autowired
    private TbAppTagMapper tbAppTagMapper;


    @Autowired
    private TbTagTypeMapper tbTagTypeMapper;

    @Value("${url.paasApi}")
    private String paasApi;

    @Autowired
    private AppMapper appMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 模拟登录
     * @return
     */
//    public String mockLogin(){
//        String token = null;
//        if((token = (String) redisUtil.get(Constant.STR_MOCK_LOGIN)) == null){
//            try {
//                Map<String,Object> params = new HashMap<>();
//                params.put("loginName", loginName);
//                params.put("password", password);
//                String doJsonPost = HttpUtil.doJsonPost(loginUrl, null, params);
//                JSONObject resultInfo = JSON.parseObject(doJsonPost);
//                JSONObject uservo = resultInfo.getJSONObject("data");
//                token = uservo.getString("token");
//                redisUtil.set(Constant.STR_MOCK_LOGIN, token, 360 * 10);
//            } catch (Exception e) {
//                logger.error("模拟登录失败！loginName:{},password:{}" ,loginName , password);
//            }
//
//        }
//        return token;
//    }

    @Override
    public ResultInfo getTagList(HttpServletRequest request) {
        Page buildPage=new Page();
        Page<TagVO> tags = tbTagMapper.getTags(null,buildPage);

        List<Integer> Object = new ArrayList<>();
        List<Object> data = new ArrayList<>();
        for (TagVO tag : tags.getRecords()) {
            Integer tagType = tag.getTagType();
            if (!Object.contains(tagType)) {
                Object.add(tagType);
            }
        }

        for (Integer obj : Object) {
            HashMap<Object, Object> map = new HashMap<>();
            TbTagType tbTagType = tbTagTypeMapper.selectById(obj);
            map.put("tagTypeName", tbTagType.getTagTypeName());
            List<TagVO> list = new ArrayList<>();
            for (TagVO tagVO : tags.getRecords()) {
                if (obj == tagVO.getTagType()) {
                    list.add(tagVO);
                }
            }
            map.put("list", list);

            data.add(map);
        }
        return ResultInfo.success(data);
    }

    @Override
    public ResultInfo getAppListByTag(HttpServletRequest request, String body) {

        JSONObject object = JSONObject.parseObject(body);
//        Object o = object.get("body");
        Integer pageNum = object.getIntValue("pageNum");
        Integer pageSize = object.getIntValue("pageSize");
        String  search = object.getString("search");
        List<Object> data =new ArrayList<>();
        JSONArray tagNames = object.getJSONArray("tagNames");
        List<Integer> tagIds = new ArrayList<>();
        List<String> apps = new ArrayList<>();
        if (null==tagNames) {
            //查全部游戏时
            List<AppTagVO> webApps = appMapper.getWebApps(search);
            for (AppTagVO app : webApps) {
                apps.add(app.getAppId());
            }
        }else{
            for (Object i:tagNames){
                Integer tagIdByName = tbTagMapper.getTagIdByName(i.toString());
                tagIds.add(tagIdByName);
            }
            apps = appMapper.getAppListByTag(tagIds, tagIds.size(),search);
            System.out.println(apps);
        }

        if(apps.size()>0){
            for (String id :apps) {
                App w = appMapper.selectById(id);
                List<TagVO> appTagNameByAppId = tbAppTagMapper.getAppTagNameByAppId(id);
                HashMap<Object, Object> map = new HashMap<>();
                map.put("description",w.getDescription());
                map.put("appId",w.getId());
                map.put("appName",w.getName());
                map.put("type",w.getType());


//转换为我们熟悉的日期格式
                SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd ");
                String fffsd=sd.format(w.getCreateTime());
                map.put("createTime",fffsd);
                List<String> ls =new ArrayList<>();
                for (TagVO v:appTagNameByAppId) {
                    ls.add(v.getTagName());
                }
                map.put("list",ls);
                data.add(map);
            }
        }

        List<Object> currentPageList = new ArrayList<>();

        if (data != null && data.size() > 0) {

            int currIdx = (pageNum > 1 ? (pageNum - 1) * pageSize : 0);

            for (int i = 0; i < pageSize && i < data.size() - currIdx; i++) {

                currentPageList.add(data.get(currIdx + i));

            }

        }

        return ResultInfo.success(data.size()+"",currentPageList);

    }



    @Override
    public ResultInfo add(HttpServletRequest request, TbTag body) {
        body.setValid(true);
        tbTagMapper.insert(body);

        return ResultInfo.success("创建标签成功");
    }

    @Override
    public ResultInfo updateTag(HttpServletRequest request, TbTag body) {
        tbTagMapper.updateById(body);
        return ResultInfo.success("编辑标签成功");
    }

    @Override
    public ResultInfo deleteTags(HttpServletRequest request, List<Integer> ids) {
        TbTag tag = new TbTag();
        for (Integer id : ids) {
            tag.setId(id);
            tag.setValid(false);
            tbTagMapper.updateById(tag);
        }
        return ResultInfo.success("删除标签成功");
    }

    @Override
    public ResultInfo getTags(HttpServletRequest request, Page b,String search) {

        Page<TagVO> tagTypeList = tbTagMapper.getTags(search,b);
//        PageInfo<TbTag> pageInfo = new PageInfo<>(tagTypeList);
        return ResultInfo.success(tagTypeList);
    }

    @Override
    public ResultInfo bindTags(HttpServletRequest request, String body) {

        JSONObject object = JSONObject.parseObject(body);
        String type = object.getString("type");
        Integer tagId = object.getInteger("tagId");
        JSONArray appIds = object.getJSONArray("appIds");
        TbAppTag tbAppTag = new TbAppTag();
        if (type.equals("bind")) {
            for (Object id : appIds) {
                TbAppTag tat = tbAppTagMapper.getAppTag(tagId, id.toString());
                if (null != tat) {
                    tat.setValid(true);
                    tbAppTagMapper.updateById(tat);
                } else {
                    tbAppTag.setAppId(id.toString());
                    tbAppTag.setTagId(tagId);
                    tbAppTag.setValid(true);
                    tbAppTagMapper.insert(tbAppTag);
                }
            }
            return ResultInfo.success("应用绑定成功！");
        } else {
            for (Object id : appIds) {
                TbAppTag tat = tbAppTagMapper.getAppTag(tagId, id.toString());
                if (null != tat) {
                    tat.setValid(false);
                    tbAppTagMapper.updateById(tat);
                } else {
                    tbAppTag.setAppId(id.toString());
                    tbAppTag.setTagId(tagId);
                    tbAppTag.setValid(false);
                    tbAppTagMapper.insert(tbAppTag);
                }
            }

            return ResultInfo.success("应用解绑成功！");
        }
    }

    @Override
    public ResultInfo getBindApps(HttpServletRequest request, Integer tagId,Page buildPage) {
//        JSONObject object = JSONObject.parseObject(body);
//        Integer tagId = object.getInteger("tagId");

        Page<TagBindVO> tat = tbAppTagMapper.getAppTagByTagId(tagId,buildPage);
//        PageInfo<TbAppTag> pageInfo = new PageInfo<>(tat);
        return ResultInfo.success(tat);
    }

    @Override
    public ResultInfo getUnBindApps(HttpServletRequest request,  Integer tagId,Page buildPage) {
//        JSONObject object = JSONObject.parseObject(body);
//        Integer tagId = object.getInteger("tagId");

        Page<TagBindVO> tat = tbAppTagMapper.getUnBindAppTagByTagId(tagId,buildPage);
//        PageInfo<TbAppTag> pageInfo = new PageInfo<>(tat);
        return ResultInfo.success(tat);
    }


    @Override
    public ResultInfo getTagsByType(HttpServletRequest request) {
        Page buildPage=new Page();
        Page<TagVO> tags = tbTagMapper.getTags(null,buildPage);

        List<Integer> Object = new ArrayList<>();
        List<Object> data = new ArrayList<>();
        for (TagVO tag : tags.getRecords()) {
            Integer tagType = tag.getTagType();
            if (!Object.contains(tagType)) {
                Object.add(tagType);
            }
        }

        for (Integer obj : Object) {
            HashMap<Object, Object> map = new HashMap<>();
            TbTagType tbTagType = tbTagTypeMapper.selectById(obj);
            map.put("tagTypeName", tbTagType.getTagTypeName());
            List<TagVO> list = new ArrayList<>();
            for (TagVO tagVO : tags.getRecords()) {
                if (obj == tagVO.getTagType()) {
                    list.add(tagVO);
                }
            }
            map.put("list", list);

            data.add(map);
        }
        return ResultInfo.success(data);
    }

}
