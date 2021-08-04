package com.ydw.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.model.db.UserInfo;
import com.ydw.admin.model.vo.*;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hea
 * @since 2020-10-30
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    IPage<DistributionUserVO> getDistributionUserList(Page buildPage, @Param("search")String search);

    DistributionUserVO getDistributionRecommender(@Param("userId")String userId);

    IPage<DistributionUserVO> getDistributionInferior(Page buildPage, @Param("userId")String userId);

    Page<OnlineUsersVO> getOnlineList(String search, Integer client, Page buildPage);

    Page<ChargeListVO> getChargeList(@Param("client")Integer client,@Param("search")String search, Page buildPage);

    Page<UserListVO> getNewUserList(@Param("search")String search, @Param("startTime")String startTime, @Param("endTime")String endTime, Page buildPage);

    String getNewUserRecharge(@Param("startTime")String startTime, @Param("endTime")String endTime);

    String getUserTotalRecharge(@Param("startTime")String startTime, @Param("endTime")String endTime);

    String getOldUserRecharge(@Param("startTime")String startTime, @Param("endTime")String endTime);

    String getUserActivity(@Param("startTime")String startTime, @Param("endTime")String endTime);

    Page<OnlineUsersVO> getOnlineListByEnterprise(@Param("search")String search, @Param("client")Integer client, @Param("list")List<String> channelIds, Page buildPage);

    Page<HashMap<String, Object>> getNewRegisterByEnterprise(@Param("startTime")LocalDateTime startTime, @Param("endTime")LocalDateTime endTime, @Param("list")List<String> channelIds, Page buildPage);

    String getTemplateByType(int i);

    List<String> getAllUser();

    List<IPCountVO> getOnlineListInfo();
}
