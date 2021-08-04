package com.ydw.game.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import com.ydw.game.model.db.TbUserInfo;
import com.ydw.game.model.vo.TbRoleUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2019-09-24
 */
@Mapper
public interface TbUserInfoMapper extends BaseMapper<TbUserInfo> {
	 
	 int insertSelective(TbUserInfo record);
	 
	 int selectCountByMobileNumber(TbUserInfo record);
	 
	 TbUserInfo selectByMobileNumber(String name);
	 
	 TbUserInfo selectByMobileNumberFreeze(String name);
	 
	 int updateSelective(TbUserInfo record);
	 
	 int updateContact(TbUserInfo record);
	 
	 TbUserInfo selectByInfoId(String id);
	 
	 TbUserInfo selectByOpenId(String openId);
	 
	 String selectStatusByMobileNumber(String mobileNumber);
	 
	 Page<TbUserInfo> getUserList(@Param("nickname") String nickname,
								  @Param("mobileNumber") String mobileNumber, @Param("userLevel") Integer userLevel,
								  @Param("onlineStatus") Integer onlineStatus, @Param("accountStatus") Integer accountStatus,
								  @Param("registerTime") String registerTime);
	 
	 List<String> getAllIds();
	 
	 TbRoleUser getRoleIdByUserId(String id);
}
