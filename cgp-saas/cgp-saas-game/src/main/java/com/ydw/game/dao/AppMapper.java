package com.ydw.game.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.game.model.db.App;
import com.ydw.game.model.vo.AppTagVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2020-08-05
 */
public interface AppMapper extends BaseMapper<App> {

    List<AppTagVO> getWebApps(@Param("search") String search);

    List<String> getAppListByTag(@Param("list") List<Integer> tagIds,@Param("size") int size,@Param("search") String search);
}
