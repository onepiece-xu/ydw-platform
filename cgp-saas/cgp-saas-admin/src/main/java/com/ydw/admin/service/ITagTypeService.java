package com.ydw.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.admin.model.db.TagType;
import com.ydw.admin.model.vo.ResultInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heao
 * @since 2020-06-29
 */
public interface ITagTypeService extends IService<TagType> {

    ResultInfo add(HttpServletRequest request, TagType body);

    ResultInfo updateTagType(HttpServletRequest request, TagType body);

    ResultInfo deleteTagType(HttpServletRequest request, List<Integer> ids);

    ResultInfo getTagTypeList(HttpServletRequest request, Page buildPage, String search);

    ResultInfo tagList(HttpServletRequest request, Page buildPage, String search);
}
