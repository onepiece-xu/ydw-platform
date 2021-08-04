package com.ydw.user.service.impl;

//import com.github.pagehelper.Page;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.user.dao.TbTagMapper;
import com.ydw.user.model.db.TbTag;
import com.ydw.user.model.db.TbTagType;
import com.ydw.user.dao.TbTagTypeMapper;
import com.ydw.user.service.ITbTagTypeService;

import com.ydw.user.utils.ResultInfo;
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
public class TbTagTypeServiceImpl extends ServiceImpl<TbTagTypeMapper, TbTagType> implements ITbTagTypeService {
    @Autowired
    private  TbTagTypeMapper tbTagTypeMapper;
    @Autowired
    private TbTagMapper tbTagMapper;

    @Override
    public ResultInfo add(HttpServletRequest request, TbTagType body) {
        body.setValid(true);
        tbTagTypeMapper.insert(body);
        return ResultInfo.success("添加标签类型成功！");
    }

    @Override
    public ResultInfo updateTagType(HttpServletRequest request, TbTagType body) {

        tbTagTypeMapper.updateById(body);
        return ResultInfo.success("编辑标签类型成功！");
    }

    @Override
    public ResultInfo deleteTagType(HttpServletRequest request, List<Integer> ids) {
        TbTagType tbTagType = new TbTagType();
        String res="";
        for (Integer id:ids) {
            List<TbTag> byTagType = tbTagMapper.getByTagType(id);
             if(byTagType.size()>0){
                 TbTagType tagType = tbTagTypeMapper.selectById(id);
                    res+=tagType.getTagTypeName()+",";
             }else{


            tbTagType.setId(id);
            tbTagType.setValid(false);//置为删除
            tbTagTypeMapper.updateById(tbTagType);
             }
        }
        String msg ="["+res.substring(0, res.length() -1)+"] 删除失败，请确认标签类型是否已解绑标签！";
        if(res.equals("")){
            return ResultInfo.success("删除标签类型成功！");
        }
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
        Page<TbTagType> tagTypeList = tbTagTypeMapper.getTagTypeList(search,buildPage);
//        PageInfo<TbTagType> pageInfo = new PageInfo<>(tagTypeList);
        return ResultInfo.success(tagTypeList);
    }
}
