package com.ydw.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.platform.dao.EventMapper;
import com.ydw.platform.model.db.Event;

import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.IEventService;

import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xulh
 * @since 2021-01-26
 */
@Service
public class EventServiceImpl extends ServiceImpl<EventMapper, Event> implements IEventService {

    @Autowired
    private EventMapper eventMapper;

    @Value("${ftp.address}")
    private String address;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.userName}")
    private String userName;

    @Value("${ftp.passWord}")
    private String passWord;

    @Value("${config.uploadPath}")
    private String uploadPath;
    @Value("${url.pics}")
    private String pics;



    @Override
    public ResultInfo eventsList(String search,Integer type,Integer platform,Page buildPage) {
        QueryWrapper<Event> wrapper = new QueryWrapper<>();
        wrapper.eq("valid", 1);
        wrapper.eq("status", 1);
        if(StringUtils.isNotEmpty(search)){
            wrapper.like("name",search);
        }
        if(null!=type){
            wrapper.eq("type",type);
        }
        if(null!=platform){
            wrapper.eq("platform",platform);
        }
        Event events = eventMapper.selectOne(wrapper);
        return ResultInfo.success(events);
    }


}
