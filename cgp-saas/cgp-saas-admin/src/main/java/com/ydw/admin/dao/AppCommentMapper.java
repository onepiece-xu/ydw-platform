package com.ydw.admin.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.model.db.AppComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.admin.model.vo.AppCommentListVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2021-04-21
 */
public interface AppCommentMapper extends BaseMapper<AppComment> {

    Page<AppCommentListVO> getAppCommentList(@Param("search") String search,@Param("reported") Integer reported, Page buildPage);

}
