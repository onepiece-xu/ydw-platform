package com.ydw.platform.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.platform.model.db.AppComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.platform.model.vo.AppCommentVO;
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

    Page<AppCommentVO> getAppCommentList(@Param("appId")String appId, @Param("userId")String userId, Page page);

//    void addThumb(Integer id);
//
//    void cancelThumb(Integer id);
}
