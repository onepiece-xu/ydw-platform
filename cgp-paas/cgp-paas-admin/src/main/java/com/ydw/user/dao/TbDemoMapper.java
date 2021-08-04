package com.ydw.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.ydw.user.model.db.TbDemo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-06-09
 */
public interface TbDemoMapper extends BaseMapper<TbDemo> {

    List<TbDemo> getByOrder(@Param("orderNumber") Integer order);

    List<TbDemo> getAll();
}
