package com.ydw.game.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.ydw.game.model.db.TbUserAuths;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2019-09-24
 */
public interface TbUserAuthsMapper extends BaseMapper<TbUserAuths> {
	
	 int insertSelective(TbUserAuths record);
	 
	 TbUserAuths selectByIdentifier(@Param("identifier") String identifier, @Param("identityType") Integer identityType);
}
