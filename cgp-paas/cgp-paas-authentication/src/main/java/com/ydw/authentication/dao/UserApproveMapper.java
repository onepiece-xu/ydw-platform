package com.ydw.authentication.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.authentication.model.db.UserApprove;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-05-19
 */
public interface UserApproveMapper extends BaseMapper<UserApprove> {

//    List<UserApprove> getAppsByIdentification(String identification);

    UserApprove getByIdentification(String identification);

    UserApprove getByLoginName(String loginName);

//    List<ServiceVO> getServices();

    Page<UserApprove> getUserApproveList(@Param("search") String search, @Param("schStatus") String schStatus, @Param("enterpriseName") String enterpriseName, Page buildPage);

    UserApprove getUserInfo(@Param("id") String id);
}
