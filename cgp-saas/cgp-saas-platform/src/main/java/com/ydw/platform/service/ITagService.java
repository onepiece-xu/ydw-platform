package com.ydw.platform.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.platform.model.db.Tag;
import com.ydw.platform.model.vo.AppListByTagVO;
import com.ydw.platform.model.vo.ResultInfo;

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
public interface ITagService extends IService<Tag> {
    ResultInfo getTagList(HttpServletRequest request);

    ResultInfo getAppListByTag(HttpServletRequest request, AppListByTagVO body,Page buildPage);

    ResultInfo add(HttpServletRequest request, Tag body);

    ResultInfo updateTag(HttpServletRequest request, Tag body);

    ResultInfo deleteTags(HttpServletRequest request, List<Integer> ids);

    ResultInfo getTags(HttpServletRequest request, Page b, String search);

    ResultInfo bindTags(HttpServletRequest request, String body);

    ResultInfo getBindApps(HttpServletRequest request, Integer tagId, Page b);

    ResultInfo getUnBindApps(HttpServletRequest request, Integer tagId,Page b);

    ResultInfo getTagsByType(HttpServletRequest request);

    ResultInfo AndroidTagList(HttpServletRequest request);

    ResultInfo getAppListByTags(HttpServletRequest request, AppListByTagVO body, Page buildPage);

    ResultInfo getRecommendAppByTag(String appId);
}
