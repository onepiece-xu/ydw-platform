package com.ydw.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import com.ydw.user.model.db.TbUserApprove;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-05-19
 */
public interface TbUserApproveMapper extends BaseMapper<TbUserApprove> {

    List<TbUserApprove> getAppsByIdentification(String identification);

    TbUserApprove getByIdentification(String identification);

    TbUserApprove getByLoginName(String loginName);

    Page<TbUserApprove> getUserApproveList(@Param("search")String search, @Param("schStatus")String schStatus, @Param("enterpriseName")String enterpriseName,Page  buildPage);


    TbUserApprove getUserInfo(@Param("id")String id);
}
