package com.ydw.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.model.db.VirtualkeyConfig;
import com.ydw.admin.model.vo.VirtualkeyConfigVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2021-01-13
 */
public interface VirtualkeyConfigMapper extends BaseMapper<VirtualkeyConfig> {

    Page<VirtualkeyConfigVO> getVirtualkeyConfigs(IPage page, @Param("search") String search);

}
