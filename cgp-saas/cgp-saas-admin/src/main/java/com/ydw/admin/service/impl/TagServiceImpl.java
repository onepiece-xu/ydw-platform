package com.ydw.admin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.admin.dao.*;
import com.ydw.admin.model.db.App;
import com.ydw.admin.model.db.AppTag;
import com.ydw.admin.model.db.Tag;
import com.ydw.admin.model.db.TagType;
import com.ydw.admin.model.vo.*;
import com.ydw.admin.service.ITagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
    private AppTagMapper appTagMapper;


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
//        Page<TagVO> tags = tagMapper.getTags(null, buildPage);
        List<TagVO> tags = tagMapper.getAllTags();

        List<Integer> Object = new ArrayList<>();
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
        return ResultInfo.success(data);
    }

    @Override
    public ResultInfo getAppListByTag(HttpServletRequest request, String body) {

        JSONObject object = JSONObject.parseObject(body);
//        Object o = object.get("body");
        Integer pageNum = object.getIntValue("pageNum");
        Integer pageSize = object.getIntValue("pageSize");
        String search = object.getString("search");
        List<Object> data = new ArrayList<>();
        JSONArray tagNames = object.getJSONArray("tagNames");
        List<Integer> tagIds = new ArrayList<>();
        List<String> apps = new ArrayList<>();
        if (null == tagNames) {
            //查全部游戏时
            List<AppTagVO> webApps = appMapper.getWebApps(search);
            for (AppTagVO app : webApps) {
                apps.add(app.getAppId());
            }
        } else {
            for (Object i : tagNames) {
                Integer tagIdByName = tagMapper.getTagIdByName(i.toString());
                tagIds.add(tagIdByName);
            }
            apps = appMapper.getAppListByTag(tagIds, tagIds.size(), search);
            System.out.println(apps);
        }

        if (apps.size() > 0) {
            for (String id : apps) {
                App w = appMapper.selectById(id);
                List<TagVO> appTagNameByAppId = appTagMapper.getAppTagNameByAppId(id);
                HashMap<Object, Object> map = new HashMap<>();
                String appId = w.getId();
                map.put("appId", appId);
                AppPicDetailsVO appPicDetails = appPicturesMapper.getAppPicDetails(appId);
                if (null != appPicDetails) {
                    String bigPic = appPicDetails.getBigPic();
                    Integer type = appPicDetails.getType();
                    Date createTime = appPicDetails.getCreateTime();
                    String midPic = appPicDetails.getMidPic();
                    String smallPic = appPicDetails.getSmallPic();
                    String logoPic = appPicDetails.getLogoPic();

                    if (null != bigPic) {
                        map.put("type", type);
                    } else {
                        map.put("type", " ");
                    }
                    if (null != bigPic) {
                        map.put("bigPic", pics+bigPic);
                    } else {
                        map.put("bigPic", " ");
                    }

                    if (null != midPic) {
                        map.put("midPic", pics+midPic);
                    } else {
                        map.put("midPic", " ");
                    }
                    if (null != smallPic) {
                        map.put("smallPic", pics+smallPic);
                    } else {
                        map.put("smallPic", " ");
                    }
                    if (null != createTime) {
                        map.put("createTime", createTime);
                    } else {
                        map.put("createTime", " ");
                    }
                    if (null != logoPic) {
                        map.put("logoPic", pics+logoPic);
                    } else {
                        map.put("logoPic", " ");
                    }
                }
                map.put("description", w.getDescription());
                map.put("appId", appId);
                map.put("appName", w.getName());
                map.put("type", w.getType());


//转换为我们熟悉的日期格式
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd ");
                String fffsd = sd.format(w.getCreateTime());
                map.put("createTime", fffsd);
                List<String> ls = new ArrayList<>();
                for (TagVO v : appTagNameByAppId) {
                    ls.add(v.getTagName());
                }
                map.put("list", ls);
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

        return ResultInfo.success(data.size() + "", currentPageList);

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
            QueryWrapper<AppTag> wrapper = new QueryWrapper<>();
            wrapper.eq("tag_id",id);
            appTagMapper.delete(wrapper);

        }
//            List<TagBindVO> appTagByTagId = appTagMapper.getAppTagListByTagId(id);
//            Tag tagById = tagMapper.getTagById(id);
//            if(appTagByTagId.size()>0){
//                res+=tagById.getTagName()+",";
//            }else{
//                tag.setId(id);
//                tag.setValid(false);
//                tagMapper.updateById(tag);
//            }
//        }

//        if(!res.equals("")){
//            String msg ="标签["+res.substring(0, res.length() -1)+"] 删除失败，请确认标签是否已解绑应用！";
//            return ResultInfo.fail(msg);
//        }

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
                AppTag tat = appTagMapper.getAppTag(tagId, id.toString());
                if (null != tat) {
                    tat.setValid(true);
                    appTagMapper.updateById(tat);
                } else {
                    tbAppTag.setAppId(id.toString());
                    tbAppTag.setTagId(tagId);
                    tbAppTag.setValid(true);
                    appTagMapper.insert(tbAppTag);
                }
            }
            return ResultInfo.success("应用绑定成功！");
        } else {
            for (Object id : appIds) {
                AppTag tat = appTagMapper.getAppTag(tagId, id.toString());
                if (null != tat) {
//                    tat.setValid(false);
                    appTagMapper.deleteById(tat.getId());
                }
//                else {
//                    tbAppTag.setAppId(id.toString());
//                    tbAppTag.setTagId(tagId);
//                    tbAppTag.setValid(false);
//                    appTagMapper.insert(tbAppTag);
//                }
            }

            return ResultInfo.success("应用解绑成功！");
        }
    }

    @Override
    public ResultInfo getBindApps(HttpServletRequest request, Integer tagId, Page buildPage) {
//        JSONObject object = JSONObject.parseObject(body);
//        Integer tagId = object.getInteger("tagId");

        Page<TagBindVO> tat = appTagMapper.getAppTagByTagId(tagId, buildPage);
//        PageInfo<TbAppTag> pageInfo = new PageInfo<>(tat);
        return ResultInfo.success(tat);
    }

    @Override
    public ResultInfo getUnBindApps(HttpServletRequest request, Integer tagId, Page buildPage) {
//        JSONObject object = JSONObject.parseObject(body);
//        Integer tagId = object.getInteger("tagId");

        Page<TagBindVO> tat = appTagMapper.getUnBindAppTagByTagId(tagId, buildPage);
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
        Tag tag1 = tagMapper.selectOne(queryWrapper);
        QueryWrapper<Tag> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("tag_name", "手机游戏");
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

}
