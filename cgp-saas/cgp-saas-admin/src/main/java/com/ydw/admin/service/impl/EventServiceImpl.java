package com.ydw.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.model.db.Event;
import com.ydw.admin.dao.EventMapper;
import com.ydw.admin.model.vo.EventVO;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IEventService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.admin.util.FTPUtil;
import com.ydw.admin.util.SequenceGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
    public ResultInfo addEvent(HttpServletRequest request, Event event) {
        String uid = SequenceGenerator.sequence();
        event.setValid(true);
        event.setStatus(false);
        eventMapper.insert(event);
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        if (null != file) {
//            String path = "D:/fdream/file/" + file.getOriginalFilename();
//            // 上传
//            file.transferTo(new File(path));
            FTPClient ftpClient = FTPUtil.connectFtpServer(address, port, userName, passWord);
//            ftpClient.enterLocalPassiveMode();

            try {
                FTPUtil.uploadFiles(ftpClient, uploadPath + "event/event_" + uid + ".jpg", file.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        event.setPicUrl(pics + "game/event/event_" + uid + ".jpg");
        event.setDelPath("pic/game/event/event_"+uid+".jpg");
        eventMapper.updateById(event);
        return ResultInfo.success("创建成功！");
    }

    @Override
    public ResultInfo updateEvent(HttpServletRequest request, Event event) {
        Integer id = event.getId();
        String uid = SequenceGenerator.sequence();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        QueryWrapper<Event> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        wrapper.eq("valid",1);
        Event selectOne = eventMapper.selectOne(wrapper);
        String delPath = selectOne.getDelPath();
        if (null != file) {
            FTPClient ftpClient = FTPUtil.connectFtpServer(address, port, userName, passWord);
//            ftpClient.enterLocalPassiveMode();
            try {
                FTPUtil.uploadFiles(ftpClient, uploadPath + "event/event_" + uid + ".jpg", file.getInputStream());
                ftpClient.deleteFile(delPath);
                event.setPicUrl(pics + "game/event/event_" + uid + ".jpg");
                event.setDelPath("pics/game/event/event_"+uid+".jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        eventMapper.updateById(event);
        return ResultInfo.success("编辑成功！");
    }

    @Override
    public ResultInfo deleteEvent(List<Integer> ids) {
        for (Integer id : ids) {
            QueryWrapper<Event> wrapper = new QueryWrapper<>();
            wrapper.eq("id", id);
            Event event = eventMapper.selectOne(wrapper);
            event.setValid(false);
            eventMapper.updateById(event);
        }
        return ResultInfo.success("删除成功！");
    }

    @Override
    public ResultInfo eventsList(String search,Page buildPage) {
        QueryWrapper<Event> wrapper = new QueryWrapper<>();
        wrapper.eq("valid", 1);
        if(StringUtils.isNotEmpty(search)){
            wrapper.like("name",search);
        }
        wrapper.orderByDesc("id");
        Page page = eventMapper.selectPage(buildPage, wrapper);
        return ResultInfo.success(page);
    }

    @Override
    public ResultInfo enable(EventVO eventVO) {
        Integer type = eventVO.getType();
        Integer id = eventVO.getId();



        QueryWrapper<Event> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        Event event = eventMapper.selectOne(wrapper);
        if(0==type){
            event.setStatus(false);
            eventMapper.updateById(event);
            return ResultInfo.success();
        }

        Integer platform = event.getPlatform();
        Integer eventType = event.getType();
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",1);
        queryWrapper.eq("type",eventType);
        queryWrapper.eq("platform",platform);
        queryWrapper.eq("valid",1);
        Event selectOne = eventMapper.selectOne(queryWrapper);
        if(null!=selectOne&&type==1){
            return ResultInfo.fail("同一活动类型同一平台只允许启用一条记录!");
        }
        event.setStatus(true);
        eventMapper.updateById(event);
        return ResultInfo.success();
    }
}
