package com.ydw.open.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ydw.open.model.db.TbUserInfo;
import com.ydw.open.model.vo.AppVO;

import com.ydw.open.model.vo.UserVO;
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

    Page<UserVO> getUserList(@Param("enterpriseName") String userName,
                             @Param("loginName") String loginName,
                             @Param("identification") String identification,
                             @Param("type") Integer type,
                             @Param("schStatus") Integer schStatus,
                             @Param("mobileNumber") String mobileNumber,
                             @Param("telNumber") String telNumber,
                             @Param("search") String search, Page buildPage);

    TbUserInfo getByLoginName(String loginName);

    List<AppVO> getAppsByIdentification(String identification);



    TbUserInfo getUserBySecretKeyAndIdentification(@Param("secretKey") String secretKey, @Param("identification") String identification);

    /**
     * 根据用户id查用户信息
     * @param identification
     * @return
     */
    TbUserInfo getByIdentification(@Param("identification") String identification);

    /**
     * 企业用户登录之后查询相关用户列表
     * @param userName
     * @param loginName
     * @param identification
     * @param type
     * @param
     * @param mobileNumber
     * @param telNumber
     * @return
     */
    List<TbUserInfo> getList(@Param("enterpriseName") String userName, @Param("loginName") String loginName, @Param("identification") String identification
            , @Param("type") Integer type, @Param("schStatus") Integer schStatus,@Param("mobileNumber")  String mobileNumber,@Param("telNumber") String telNumber,Page buildPage);
}
