package com.ydw.platform.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.platform.dao.MessageTemplateMapper;
import com.ydw.platform.dao.PostMessageMapper;
import com.ydw.platform.model.db.MessageTemplate;
import com.ydw.platform.model.db.PostMessage;
import com.ydw.platform.model.vo.Msg;
import com.ydw.platform.model.vo.PostMessageVO;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.model.vo.UnreadCountVO;
import com.ydw.platform.service.IPostMessageService;
import com.ydw.platform.util.SequenceGenerator;
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
    private MessageTemplateMapper  messageTemplateMapper;

    @Autowired
    private FeginClientService feginClientService;


    @Override
    public ResultInfo getMessages(String userId, Integer type,Page buildPage) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        if(null!=type){
            queryWrapper.eq("type",type);
        }
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
        map.put("records", records);
        map.put("total", page.getTotal());
        map.put("number",records.size());
        map.put("current", page.getCurrent());
        map.put("pages", page.getPages());
        map.put("size", page.getSize());
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
    public ResultInfo add(Map<String, Object> map) {
        String userId = (String)map.get("userId");
        String templateId = (String)map.get("templateId");
        Object obj = map.get("type");
        int type = Integer.parseInt(obj.toString());
        Map data = (Map)map.get("data");
//        logger.info(data.toString());

        MessageTemplate messageTemplate = messageTemplateMapper.selectById(templateId);

        String content = messageTemplate.getContent();
        Boolean toAll = messageTemplate.getToAll();
        String title = messageTemplate.getTitle();
        PostMessage postMessage = new PostMessage();
        String sequenceId = SequenceGenerator.sequence();
        postMessage.setId(sequenceId);
        postMessage.setUserId(userId);
        postMessage.setHasRead(false);
        postMessage.setToAll(toAll);
        postMessage.setTitle(title);
        postMessage.setType(type);
        postMessage.setCreateTime(new Date());
        if(null!=data&&templateId.equals("1")){
            JSONObject object = JSON.parseObject(data.toString());
            String cardName = object.getString("cardName");
            content= content.replaceFirst("card", "【" + cardName + "】");
        }

        if(null!=data&&templateId.equals("2")){
            LocalDateTime startTime = (LocalDateTime)data.get("startTime");
            LocalDateTime endTime =(LocalDateTime) data.get("endTime");
            Object usedTime = data.get("usedTime");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String t1 = formatter.format(startTime);
            String t2 = formatter.format(endTime);

            content= content.replaceFirst("t1", t1);
            content= content.replaceFirst("t2", t2);
            long time = Long.valueOf(String.valueOf(usedTime));

            int hours = (int) Math.floor(time / 60);
            long minute = time % 60;
            System.out.printf("%d:%02d", hours, minute);
            if(hours<=0){
                content= content.replaceFirst("t3", minute+"分钟");
            }else{
                content= content.replaceFirst("t3", hours+"小时"+minute+"分钟");
            }
        }

        if(null!=data&&templateId.equals("5")){
            JSONObject object = JSON.parseObject(data.toString());
            String date = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(object.getDate("date"));
            String amount = object.getBigDecimal("amount").toString();
            content= MessageFormat.format(content,date,amount);
        }

        if(null!=data&&templateId.equals("6")){
            JSONObject object = JSON.parseObject(data.toString());
            String date = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(object.getDate("date"));
            String amount = object.getBigDecimal("amount").toString();
            content= MessageFormat.format(content,date,amount);
        }

        if(null!=data&&templateId.equals("7")){
            JSONObject object = JSON.parseObject(data.toString());
            Date date1 = object.getDate("date");
            logger.info(date1.toString());
            String date = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(object.getDate("date"));
            String amount = object.getBigDecimal("amount").toString();
            content= MessageFormat.format(content,date,amount);
        }

        if(null!=data&&templateId.equals("8")){
            JSONObject object = JSON.parseObject(data.toString());
            String date = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(object.getDate("date"));
            String amount = object.getBigDecimal("amount").toString();
            String payAccount = object.getString("payAccount");
            content= MessageFormat.format(content,date,amount,payAccount);
        }

        if(null!=data&&templateId.equals("9")){
            JSONObject object = JSON.parseObject(data.toString());
            String date = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(object.getDate("date"));
            String amount = object.getBigDecimal("amount").toString();
            String errorCode = object.getString("errorCode");
            content= MessageFormat.format(content,date,amount,errorCode);
        }

        postMessage.setContent(content);
        postMessageMapper.insert(postMessage);
        Msg msg = new Msg();
        List<String> userOnlineTokens = feginClientService.getUserOnlineTokens(userId);
        msg.setReceivers(userOnlineTokens);
        msg.setType("mail");
        HashMap<String, Object> msgData = new HashMap<>();
        msgData.put("msg","有新的站内信到啦！");
        msg.setData(JSON.toJSONString(msgData));
        feginClientService.sendMsg(msg);
        return ResultInfo.success("添加成功！");
    }

    @Override
    public ResultInfo getUnreadCountByUserId(String userId) {
        UnreadCountVO unreadCount = postMessageMapper.getUnreadCountByUserId(userId);
        return ResultInfo.success(unreadCount);
    }

    @Override
    public ResultInfo allRead(String userId,Integer type) {
        QueryWrapper<PostMessage> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.eq("type",type);
        wrapper.eq("has_read",false);
        List<PostMessage> postMessages = postMessageMapper.selectList(wrapper);
        for(PostMessage pm:postMessages){
            pm.setHasRead(true);
            postMessageMapper.updateById(pm);
        }
        return ResultInfo.success();
    }

}
