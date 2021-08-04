package com.ydw.resource.dao;

import com.ydw.resource.model.db.UserApps;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.resource.model.vo.AppInfo;
import com.ydw.resource.model.vo.AppVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2020-07-30
 */
public interface UserAppsMapper extends BaseMapper<UserApps> {

    AppInfo getUserAppInfo(@Param("appId") String appId, @Param("clusterId") String clusterId);

    List<AppVO> getAppsByIdentification(@Param("identification") String identification);

    AppVO getAppByIdentification(@Param("identification") String identification,
                                 @Param("appId") String appId);
}
