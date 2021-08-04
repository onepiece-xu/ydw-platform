package com.ydw.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.user.model.db.TbUserInfo;
import com.ydw.user.model.vo.AppVO;
import com.ydw.user.model.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-04-13
 */

public interface TbUserInfoMapper extends BaseMapper<TbUserInfo> {

    TbUserInfo  selectByInfoId(String id);

    int insertSelective(TbUserInfo record);

    int updateSelective(TbUserInfo record);

    Page<TbUserInfo> getUserList(@Param("enterpriseName")String userName,
                             @Param("loginName") String loginName,
                             @Param("identification")String identification,
                             @Param("type")Integer type,
                             @Param("schStatus") Integer schStatus,
                             @Param("mobileNumber") String mobileNumber,
                             @Param("telNumber") String telNumber,
                             @Param("search") String search, Page buildPage);

    List<TbUserInfo> getByLoginName(String loginName);

    List<AppVO> getAppsByIdentification(String identification);

    TbUserInfo getUserBySecretKeyAndIdentification( @Param("secretKey")String secretKey,  @Param("identification")String identification);

    TbUserInfo getByIdentification(@Param("identification")String identification);

    List<UserVO>  serviceUserList();
}
