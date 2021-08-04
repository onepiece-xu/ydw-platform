package com.ydw.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.admin.dao.PostMessageMapper;
import com.ydw.admin.dao.UserInfoMapper;
import com.ydw.admin.model.db.PostMessage;
import com.ydw.admin.model.vo.PostMessageVO;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IPostMessageService;
import com.ydw.admin.util.SequenceGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hea
 * @since 2020-10-08
 */
@Service
public class PostMessageServiceImpl extends ServiceImpl<PostMessageMapper, PostMessage> implements IPostMessageService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PostMessageMapper postMessageMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;



    @Override
    public ResultInfo getMessages(String userId, Page buildPage) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        queryWrapper.select().orderByDesc("create_time");
        Page page = postMessageMapper.selectPage(buildPage, queryWrapper);
        List<PostMessage> records = page.getRecords();
        HashMap<Object, Object> map = new HashMap<>();
        int i = postMessageMapper.getUnreadCount(userId);

        if (i > 0) {
            map.put("isRead", false);
        } else {
            map.put("isRead", true);
        }
        map.put("record", records);
//        map.put("total", page.getTotal());
        map.put("number", i);
        return ResultInfo.success(map);
    }

    @Override
    public ResultInfo info(String id) {

        PostMessage p = postMessageMapper.selectById(id);
        p.setHasRead(true);
        PostMessageVO vo = new PostMessageVO();
        vo.setId(p.getId());
        String content = p.getContent();
        String title = p.getTitle();
        Boolean toAll = p.getToAll();
        String userid = p.getUserId();
        Boolean hasRead = p.getHasRead();
        Date createTime = p.getCreateTime();
        if (null != content) {
            vo.setContent(content);
        }
        if (null != title) {
            vo.setTitle(title);
        }
        if (null != toAll) {
            vo.setToAll(toAll);
        }
        if (null != userid) {
            vo.setUserId(userid);
        }
        if (null != createTime) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String format = formatter.format(p.getCreateTime());
            vo.setCreateTime(format);
        }
        if (null != hasRead) {
            vo.setHasRead(hasRead);
        }
        postMessageMapper.updateById(p);
        return ResultInfo.success(vo);
    }

    @Override
    public ResultInfo add(PostMessage postMessage) {
        postMessage.setId(SequenceGenerator.sequence());
        postMessage.setCreateTime(new Date());
        postMessage.setToAll(false);
        postMessage.setHasRead(false);
        //默认官方私信
        postMessage.setType(1);
        postMessageMapper.insert(postMessage);

        return ResultInfo.success("添加成功！");
    }

    @Override
    public ResultInfo addALL(PostMessage postMessage) {

        List<String> awardUsers = userInfoMapper.getAllUser();

//        List<String> list = new ArrayList<>();
//        list.add("6781143200662617063");
        String templateByType = userInfoMapper.getTemplateByType(12);
        for(String id:awardUsers){
            postMessage.setId(SequenceGenerator.sequence());
            postMessage.setContent(templateByType);
            postMessage.setUserId(id);
            postMessage.setCreateTime(new Date());
            postMessage.setToAll(false);
            postMessage.setHasRead(false);
            //默认官方私信
            postMessage.setType(1);
            postMessageMapper.insert(postMessage);
        }
        return ResultInfo.success();
    }


}
