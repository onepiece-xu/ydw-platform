package com.ydw.game.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.game.model.db.TbTagType;
import com.ydw.game.model.vo.ResultInfo;



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
public interface ITbTagTypeService extends IService<TbTagType> {

    ResultInfo add(HttpServletRequest request, TbTagType body);

    ResultInfo updateTagType(HttpServletRequest request, TbTagType body);

    ResultInfo deleteTagType(HttpServletRequest request, List<Integer> ids);

    ResultInfo getTagTypeList(HttpServletRequest request, Page buildPage, String search);

    ResultInfo tagList(HttpServletRequest request, Page buildPage, String search);
}
