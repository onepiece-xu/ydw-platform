package com.ydw.platform.service.impl;

//import com.github.pagehelper.Page;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.platform.dao.TagMapper;

import com.ydw.platform.dao.TagTypeMapper;
import com.ydw.platform.model.db.Tag;

import com.ydw.platform.model.db.TagType;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.ITagTypeService;

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
 * @since 2020-06-29
 */
@Service
public class TagTypeServiceImpl extends ServiceImpl<TagTypeMapper, TagType> implements ITagTypeService {
    @Autowired
    private  TagTypeMapper tbTagTypeMapper;
    @Autowired
    private TagMapper tbTagMapper;

    @Override
    public ResultInfo add(HttpServletRequest request, TagType body) {
        body.setValid(true);
        tbTagTypeMapper.insert(body);
        return ResultInfo.success("添加标签类型成功！");
    }

    @Override
    public ResultInfo updateTagType(HttpServletRequest request, TagType body) {

        tbTagTypeMapper.updateById(body);
        return ResultInfo.success("编辑标签类型成功！");
    }

    @Override
    public ResultInfo deleteTagType(HttpServletRequest request, List<Integer> ids) {
        TagType tbTagType = new TagType();
        String res="";
        for (Integer id:ids) {
            List<Tag> byTagType = tbTagMapper.getByTagType(id);
             if(byTagType.size()>0){
                 TagType tagType = tbTagTypeMapper.selectById(id);
                    res+=tagType.getTagTypeName()+",";
             }else{


            tbTagType.setId(id);
            tbTagType.setValid(false);//置为删除
            tbTagTypeMapper.updateById(tbTagType);
             }
        }

        if(res.equals("")){
            return ResultInfo.success("删除标签类型成功！");
        }
        String msg ="["+res.substring(0, res.length() -1)+"] 删除失败，请确认标签类型是否已解绑标签！";
        return ResultInfo.fail(msg);

    }

    @Override
    public ResultInfo getTagTypeList(HttpServletRequest request, Page buildPage,String search) {
//        if (null != pageNum && null != pageSize) {
////            PageHelper.startPage(pageNum, pageSize);
//        } else {
//            Page<TbTagType> tagTypeList = tbTagTypeMapper.getTagTypeList(search);
//            return ResultInfo.success(tagTypeList);
//        }
        Page<TagType> tagTypeList = tbTagTypeMapper.getTagTypeList(search,buildPage);
//        PageInfo<TbTagType> pageInfo = new PageInfo<>(tagTypeList);
        return ResultInfo.success(tagTypeList);
    }

    @Override
    public ResultInfo tagList(HttpServletRequest request, Page buildPage, String search) {
       List<TagType> tagTypeList = tbTagTypeMapper.tagList(search,buildPage);
        return ResultInfo.success(tagTypeList);
    }
}
