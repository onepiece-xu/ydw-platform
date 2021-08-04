package com.ydw.user.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
//import com.github.pagehelper.Page;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ydw.user.dao.TbAppTagMapper;
import com.ydw.user.dao.TbTagTypeMapper;
import com.ydw.user.model.db.TbAppTag;
import com.ydw.user.model.db.TbTag;
import com.ydw.user.dao.TbTagMapper;
import com.ydw.user.model.db.TbTagType;
import com.ydw.user.model.vo.TagBindVO;
import com.ydw.user.model.vo.TagVO;
import com.ydw.user.service.ITbTagService;

import com.ydw.user.utils.ResultInfo;
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


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ResultInfo add(HttpServletRequest request, TbTag body) {
        body.setValid(true);
        tbTagMapper.insert(body);
        logger.info("标签创建成功");
        return ResultInfo.success("创建标签成功");
    }

    @Override
    public ResultInfo updateTag(HttpServletRequest request, TbTag body) {
        tbTagMapper.updateById(body);
        return ResultInfo.success("编辑标签成功");
    }

    @Override
    public ResultInfo deleteTags(HttpServletRequest request, List<Integer> ids) {
        //删除标签时候判断是否能被删除
        String res="";
        TbTag tag = new TbTag();
        for (Integer id : ids) {
            Page<TagBindVO> appTagByTagId = tbAppTagMapper.getAppTagByTagId(id,new Page());
            TbTag tagById = tbTagMapper.getTagById(id);
            if(appTagByTagId.getSize()>0){
                    res+=tagById.getTagName()+",";
            }else{
                tag.setId(id);
                tag.setValid(false);
                tbTagMapper.updateById(tag);

            }
        }
        String msg ="标签["+res.substring(0, res.length() -1)+"] 删除失败，请确认标签是否已解绑应用！";
        if(res.equals("")){
            return ResultInfo.success("删除标签成功");
        }
        return ResultInfo.fail(msg);
    }

    @Override
    public ResultInfo getTags(HttpServletRequest request, Page buildPage,String search) {
//        if (null != pageNum && null != pageSize) {
//            PageHelper.startPage(pageNum, pageSize);
//        } else {
//            PageHelper.startPage(1, 10);
//        }
        Page<TagVO> tagTypeList = tbTagMapper.getTags(search,buildPage);
//        PageInfo<TagVO> pageInfo = new PageInfo<>(tagTypeList);

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
//        if (null != pageNum && null != pageSize) {
//            PageHelper.startPage(pageNum, pageSize);
//        } else {
//            PageHelper.startPage(1, 10);
//        }
        Page<TagBindVO> tat = tbAppTagMapper.getAppTagByTagId(tagId,buildPage);
//        PageInfo<TagBindVO> pageInfo = new PageInfo<>(tat);
        return ResultInfo.success(tat);
    }

    @Override
    public ResultInfo getUnBindApps(HttpServletRequest request,  Integer tagId,Page buildPage) {
//        JSONObject object = JSONObject.parseObject(body);
//        Integer tagId = object.getInteger("tagId");
//        if (null != pageNum && null != pageSize) {
//            PageHelper.startPage(pageNum, pageSize);
//        } else {
//            PageHelper.startPage(1, 10);
//        }
        Page<TbAppTag> tat = tbAppTagMapper.getUnBindAppTagByTagId(tagId,buildPage);
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
