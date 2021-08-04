package com.ydw.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.user.model.db.TbTag;

import com.ydw.user.utils.ResultInfo;

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
public interface ITbTagService extends IService<TbTag> {

    ResultInfo add(HttpServletRequest request, TbTag body);

    ResultInfo updateTag(HttpServletRequest request, TbTag body);

    ResultInfo deleteTags(HttpServletRequest request, List<Integer> ids);

    ResultInfo getTags(HttpServletRequest request,Page buildPage, String search);

    ResultInfo bindTags(HttpServletRequest request, String body);

    ResultInfo getBindApps(HttpServletRequest request, Integer tagId,Page buildPage);

    ResultInfo getUnBindApps(HttpServletRequest request,  Integer tagId,Page buildPage);

    ResultInfo getTagsByType(HttpServletRequest request);
}
