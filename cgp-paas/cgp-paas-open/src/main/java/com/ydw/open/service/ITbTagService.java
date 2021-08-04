package com.ydw.open.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.open.model.db.TbTag;
import com.ydw.open.utils.ResultInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heao
 * @since 2020-06-29
 */
public interface ITbTagService extends IService<TbTag> {

//    ResultInfo add(HttpServletRequest request, TbTag body);
//
//    ResultInfo updateTag(HttpServletRequest request, TbTag body);
//
//    ResultInfo deleteTags(HttpServletRequest request, List<Integer> ids);
//
//    ResultInfo getTags(HttpServletRequest request, Integer pageNum, Integer pageSize, String search);
//
//    ResultInfo bindTags(HttpServletRequest request, String body);
//
//    ResultInfo getBindApps(HttpServletRequest request, Integer tagId, Integer pageNum, Integer pageSize);
//
//    ResultInfo getUnBindApps(HttpServletRequest request, Integer tagId, Integer pageNum, Integer pageSize);
//
//    ResultInfo getTagsByType(HttpServletRequest request);
    ResultInfo getTagList(HttpServletRequest request);

    ResultInfo getAppListByTag(HttpServletRequest request, String body);
}