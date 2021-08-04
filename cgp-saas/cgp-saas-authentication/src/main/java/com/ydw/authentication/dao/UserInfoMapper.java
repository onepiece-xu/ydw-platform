package com.ydw.authentication.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.authentication.model.db.UserInfo;
import com.ydw.authentication.model.vo.UserShareInfoVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2020-07-29
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    int insertSelective(UserInfo record);

    UserInfo selectByMobileNumber(String name);

    int updateSelective(UserInfo record);

    UserInfo selectByInfoId(String id);

    UserInfo selectByWXOpenId(String openId);

    UserInfo selectByQQOpenId(String openId);

    UserShareInfoVO getUserShareInfo(@Param("userId") String userId);

    UserInfo getUserRecommender(@Param("userId") String userId);

}
