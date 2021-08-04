package com.ydw.platform.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import com.ydw.platform.dao.*;
import com.ydw.platform.model.db.App;
import com.ydw.platform.model.db.AppTag;
import com.ydw.platform.model.db.Tag;
import com.ydw.platform.model.db.TagType;
import com.ydw.platform.model.vo.*;
import com.ydw.platform.service.ITagService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author heao
 * @since 2020-06-29
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private AppTagMapper tbAppTagMapper;


    @Autowired
    private TagTypeMapper tbTagTypeMapper;

    @Value("${url.paasApi}")
    private String paasApi;

    @Value("${url.pics}")
    private String pics;

    @Autowired
    private AppMapper appMapper;

    @Autowired
    private AppPicturesMapper appPicturesMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 模拟登录
     *
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
//        Page buildPage = new Page();
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.eq("valid",1);
        List<Tag> tags = tagMapper.selectList(wrapper);
//        List<TagVO> tags = tagMapper.getAllTags();

     /*   List<Integer> Object = new ArrayList<>();
        List<Object> data = new ArrayList<>();
        for (TagVO tag : tags) {
            Integer tagType = tag.getTagType();
            if (!Object.contains(tagType)) {
                Object.add(tagType);
            }
        }

        for (Integer obj : Object) {
            HashMap<Object, Object> map = new HashMap<>();
            TagType tbTagType = tbTagTypeMapper.selectById(obj);
            map.put("tagTypeName", tbTagType.getTagTypeName());
            List<TagVO> list = new ArrayList<>();
            for (TagVO tagVO : tags) {
                if (obj == tagVO.getTagType()) {
                    list.add(tagVO);
                }
            }
            map.put("list", list);

            data.add(map);
        }
        */
        return ResultInfo.success(tags);
    }

    @Override
    public ResultInfo getAppListByTag(HttpServletRequest request, AppListByTagVO body,Page buildPage) {
        Integer platform = body.getPlatform();
        Integer free = body.getFree();
        Integer type = body.getType();
        String search = body.getSearch();
        List<String> tagNames = body.getTagNames();
        List<Integer> ids = new ArrayList<>();
        String sql=null;
        if(null!=tagNames){
            if(tagNames.size()>0){
                for(String one:tagNames){
                    QueryWrapper<Tag> wrapper = new QueryWrapper<>();
                    wrapper.eq("tag_name",one);
                    wrapper.eq("valid",1);
                    Tag tag = tagMapper.selectOne(wrapper);
                    ids.add(tag.getId());
                }
                //sql不为null  用标签搜索

            }
        }

        Page<AppVO> appList = appMapper.getAppList(platform, free, search,type,ids,ids.size(),buildPage);
        List<AppVO> records = appList.getRecords();
        for(AppVO vo:records){
            String tag = vo.getTag();
            List<String> list = new ArrayList<>();
            if(!StringUtils.isNullOrEmpty(tag)){
                String[] split = new String(tag).split("[\\,\\;]");


                for (String s:split){
                    list.add(s);
                }
            }

            vo.setList(list);
        }
        appList.setRecords(records);
        return  ResultInfo.success(appList);
    }


    @Override
    public ResultInfo add(HttpServletRequest request, Tag body) {
        body.setValid(true);
        tagMapper.insert(body);

        return ResultInfo.success("创建标签成功");
    }

    @Override
    public ResultInfo updateTag(HttpServletRequest request, Tag body) {
        tagMapper.updateById(body);
        return ResultInfo.success("编辑标签成功");
    }

    @Override
    public ResultInfo deleteTags(HttpServletRequest request, List<Integer> ids) {
        Tag tag = new Tag();
        for (Integer id : ids) {
            tag.setId(id);
            tag.setValid(false);
            tagMapper.updateById(tag);
        }
        return ResultInfo.success("删除标签成功");
    }

    @Override
    public ResultInfo getTags(HttpServletRequest request, Page b, String search) {

        Page<TagVO> tagTypeList = tagMapper.getTags(search, b);
//        PageInfo<TbTag> pageInfo = new PageInfo<>(tagTypeList);
        return ResultInfo.success(tagTypeList);
    }

    @Override
    public ResultInfo bindTags(HttpServletRequest request, String body) {

        JSONObject object = JSONObject.parseObject(body);
        String type = object.getString("type");
        Integer tagId = object.getInteger("tagId");
        JSONArray appIds = object.getJSONArray("appIds");
        AppTag tbAppTag = new AppTag();
        if (type.equals("bind")) {
            for (Object id : appIds) {
                AppTag tat = tbAppTagMapper.getAppTag(tagId, id.toString());
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
                AppTag tat = tbAppTagMapper.getAppTag(tagId, id.toString());
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
    public ResultInfo getBindApps(HttpServletRequest request, Integer tagId, Page buildPage) {
//        JSONObject object = JSONObject.parseObject(body);
//        Integer tagId = object.getInteger("tagId");

        Page<TagBindVO> tat = tbAppTagMapper.getAppTagByTagId(tagId, buildPage);
//        PageInfo<TbAppTag> pageInfo = new PageInfo<>(tat);
        return ResultInfo.success(tat);
    }

    @Override
    public ResultInfo getUnBindApps(HttpServletRequest request, Integer tagId, Page buildPage) {
//        JSONObject object = JSONObject.parseObject(body);
//        Integer tagId = object.getInteger("tagId");

        Page<TagBindVO> tat = tbAppTagMapper.getUnBindAppTagByTagId(tagId, buildPage);
//        PageInfo<TbAppTag> pageInfo = new PageInfo<>(tat);
        return ResultInfo.success(tat);
    }


    @Override
    public ResultInfo getTagsByType(HttpServletRequest request) {
        Page buildPage = new Page();
        Page<TagVO> tags = tagMapper.getTags(null, buildPage);

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
            TagType tbTagType = tbTagTypeMapper.selectById(obj);
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
    public ResultInfo AndroidTagList(HttpServletRequest request) {
        HashMap<Object, Object> map = new HashMap<>();
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tag_name", "PC游戏");
        queryWrapper.eq("valid", 1);
        Tag tag1 = tagMapper.selectOne(queryWrapper);
        QueryWrapper<Tag> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("tag_name", "手机游戏");
        queryWrapper2.eq("valid",1);
        Tag tag2 = tagMapper.selectOne(queryWrapper2);
        List<Tag> list1 = new ArrayList<>();
        if (null != tag1) {
            list1.add(tag1);
        }
        if (null != tag2) {
            list1.add(tag2);
        }
        map.put("platform", list1);
        //获取非pc游戏，手机游戏的标签
        List<Tag> otherTags = tagMapper.getOtherTags();
        map.put("others", otherTags);
        return ResultInfo.success(map);
    }

    @Override
    public ResultInfo getAppListByTags(HttpServletRequest request, AppListByTagVO body, Page buildPage) {
        Integer platform = body.getPlatform();
        Integer free = body.getFree();
        Integer type = body.getType();
        String search = body.getSearch();
        List<String> tagNames = body.getTagNames();
        List<Integer> ids = new ArrayList<>();

        if(null!=tagNames){
            if(tagNames.size()>0){
                for(String one:tagNames){
                    QueryWrapper<Tag> wrapper = new QueryWrapper<>();
                    wrapper.eq("tag_name",one);
                    wrapper.eq("valid",1);
                    Tag tag = tagMapper.selectOne(wrapper);
                    ids.add(tag.getId());
                }
            }
        }

        Page<AppListByTagsVO> appList = appMapper.getAppListByTags(platform, free, search,type,ids,ids.size(),buildPage);
        List<AppListByTagsVO> records = appList.getRecords();
        for(AppListByTagsVO vo:records){
            String tag = vo.getTag();
            List<String> list = new ArrayList<>();
            if(!StringUtils.isNullOrEmpty(tag)){
                String[] split = new String(tag).split("[\\,\\;]");


                for (String s:split){
                    list.add(s);
                }
            }

            vo.setList(list);
        }
        appList.setRecords(records);

        return  ResultInfo.success(appList);

    }

    @Override
    public ResultInfo getRecommendAppByTag(String appId) {
        List<Tag> tagByAppId = tagMapper.getTagByAppId(appId);
        List<GuessYouLikeVO> appList = new ArrayList<>();
        for (Tag t: tagByAppId){
            Integer id = t.getId();
            List<GuessYouLikeVO> topAppById = tagMapper.getTopAppById(id);
            for(GuessYouLikeVO vo:topAppById){
                if(!vo.getAppId().equals(appId)){
                    appList.add(vo);
                }
            }
        }
        List<GuessYouLikeVO> guessYouLikeVOS = null;
        try {
            guessYouLikeVOS = removeDuplicatePlan(appList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultInfo.success(guessYouLikeVOS);
    }
    private static List<GuessYouLikeVO> removeDuplicatePlan(List<GuessYouLikeVO> planList) {
        Set<GuessYouLikeVO> set = new TreeSet<GuessYouLikeVO>(new Comparator<GuessYouLikeVO>() {
            @Override
            public int compare(GuessYouLikeVO a, GuessYouLikeVO b) {
                // 字符串则按照asicc码升序排列
                return a.getAppId().compareTo(b.getAppId());
            }
        });

        set.addAll(planList);
        return new ArrayList<GuessYouLikeVO>(set);
    }
}
