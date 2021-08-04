package com.ydw.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.user.model.db.TbKeyConfig;
import com.ydw.user.model.vo.KeyConfigVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-07-27
 */
public interface TbKeyConfigMapper extends BaseMapper<TbKeyConfig> {

    Page<KeyConfigVO> getConfigList(@Param("author") String author, @Param("appId") String appId,@Param("platform") Integer platform, Page buildPage);

    TbKeyConfig getConfigByName(@Param("name") String name);
}
