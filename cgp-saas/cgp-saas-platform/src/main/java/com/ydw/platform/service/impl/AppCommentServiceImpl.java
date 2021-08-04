package com.ydw.platform.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.platform.dao.UserLikedMapper;
import com.ydw.platform.model.db.AppComment;
import com.ydw.platform.dao.AppCommentMapper;
import com.ydw.platform.model.db.UserInfo;
import com.ydw.platform.model.db.UserLiked;
import com.ydw.platform.model.vo.AppCommentVO;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.IAppCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.platform.service.IYdwAuthenticationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulh
 * @since 2021-04-21
 */
@Service
public class AppCommentServiceImpl extends ServiceImpl<AppCommentMapper, AppComment> implements IAppCommentService {

    @Autowired
    private AppCommentMapper appCommentMapper;
    @Autowired
    private UserLikedMapper userLikedMapper;

    @Autowired
    private IYdwAuthenticationService iYdwAuthenticationService;
    @Override
    public ResultInfo addComment(HttpServletRequest request,AppComment appComment) {
        try {
            String token = request.getHeader("Authorization");
            String userInfoByToken = iYdwAuthenticationService.getUserInfoByToken(token);
            ResultInfo res = JSON.parseObject(userInfoByToken, ResultInfo.class);
            String jsonString = JSON.toJSONString(res.getData());
            UserInfo userInfo = JSONObject.parseObject(jsonString, UserInfo.class);
            String id = userInfo.getId();
            appComment.setOwnerId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        appComment.setCreateTime(new Date());
        appComment.setStatus(1);
        appComment.setThumb(0);
        appComment.setLiked(0);
        appComment.setReported(0);
        //评论发布状态为仅对个人可见
        appComment.setApproved(0);
        appCommentMapper.insert(appComment);
        return ResultInfo.success(appComment);
    }

    @Override
    public ResultInfo deleteComment(AppComment appComment) {
        Integer id = appComment.getId();
        AppComment comment = appCommentMapper.selectById(id);
        comment.setStatus(0);
        appCommentMapper.updateById(comment);
        return  ResultInfo.success();
    }

    @Override
    public ResultInfo reportComment(AppComment appComment) {
        Integer id = appComment.getId();
        AppComment comment = appCommentMapper.selectById(id);
        comment.setReported(1);
        appCommentMapper.updateById(comment);
        return  ResultInfo.success();
    }

    @Override
    public ResultInfo likeComment(HttpServletRequest request,AppComment appComment) {
        String  userId="";
        try {
            String token = request.getHeader("Authorization");
            String userInfoByToken = iYdwAuthenticationService.getUserInfoByToken(token);
            ResultInfo res = JSON.parseObject(userInfoByToken, ResultInfo.class);
            String jsonString = JSON.toJSONString(res.getData());
            UserInfo userInfo = JSONObject.parseObject(jsonString, UserInfo.class);
             userId = userInfo.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Integer status = appComment.getStatus();
        Integer commentId = appComment.getId();
        AppComment comment = appCommentMapper.selectById(commentId);
        if(null==comment){
           return ResultInfo.fail("评论不存在!");
        }
        if(null!=comment){
            Integer staus = comment.getStatus();
            if(staus==0){
                return ResultInfo.fail("评论已删除!");
            }
        }
       //1点赞 0取消点赞
       if(1==status){
           //不能反复点赞
           QueryWrapper<UserLiked> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id",userId);
            wrapper.eq("comment_id",commentId);
           UserLiked liked = userLikedMapper.selectOne(wrapper);
           if(null==liked){
               UserLiked userLiked = new UserLiked();
               userLiked.setCreateTime(new Date());
               userLiked.setUserId(userId);
               userLiked.setCommentId(commentId);
               userLikedMapper.insert(userLiked);
           }

        }
        if(0==status) {
            QueryWrapper<UserLiked> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", userId);
            wrapper.eq("comment_id", commentId);
            userLikedMapper.delete(wrapper);
        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo getCommentList(HttpServletRequest request,String appId, Page page) {
            //获取用户id
            String  userId="";
            try {
                String token = request.getHeader("Authorization");
                String userInfoByToken = iYdwAuthenticationService.getUserInfoByToken(token);
                ResultInfo res = JSON.parseObject(userInfoByToken, ResultInfo.class);
                String jsonString = JSON.toJSONString(res.getData());
                UserInfo userInfo = JSONObject.parseObject(jsonString, UserInfo.class);
                userId = userInfo.getId();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //查询当前应用下的评论

        Page<AppCommentVO> appCommentList = appCommentMapper.getAppCommentList(appId, userId,page);
        List<AppCommentVO> voList = appCommentList.getRecords();
        // 先查所有的评论id ，再把点赞表的点赞 和评论id归类，再set到每个评论里


            for(AppCommentVO ap:voList){
                Integer commentId = ap.getId();
                String image = ap.getImage();
                if(!StringUtils.isNotEmpty(image)){
                    ap.setImage("");
                }
                //查找所有的评论记录点赞数 并setcount
                QueryWrapper<UserLiked> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("comment_id",commentId);
                Integer count = userLikedMapper.selectCount(queryWrapper);
                ap.setThumb(count);
                //查找当前用户点赞过的评论 并set1
                queryWrapper.eq("user_id",userId);
                UserLiked userLiked = userLikedMapper.selectOne(queryWrapper);
                if(null==userLiked){
                    ap.setLiked(0);
                }
                if(null!=userLiked){
                    ap.setLiked(1);
                }
            }
            return ResultInfo.success(appCommentList);
    }
}
