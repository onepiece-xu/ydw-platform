package com.ydw.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.admin.dao.GameGroupInfoMapper;
import com.ydw.admin.dao.GameGroupMapper;
import com.ydw.admin.model.db.GameGroup;
import com.ydw.admin.model.db.GameGroupInfo;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IGameGroupInfoService;
import com.ydw.admin.util.FTPUtil;
import com.ydw.admin.util.SequenceGenerator;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.sound.midi.Sequence;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author heao
 * @since 2020-07-16
 */
@Service
public class GameGroupInfoServiceImpl extends ServiceImpl<GameGroupInfoMapper, GameGroupInfo> implements IGameGroupInfoService {

    @Autowired
    private GameGroupInfoMapper gamegroupInfoMapper;
    @Autowired
    private GameGroupMapper tbGameGroupMapper;

    @Value("${url.pics}")
    private String pics;

//    @Value("${url.customizeDomain}")
//    private String customizeDomain;

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
    @Override
    public ResultInfo getGameGroupList(HttpServletRequest request, Page b) {
//        if (null != pageNum && null != pageSize) {
//            PageHelper.startPage(pageNum, pageSize);
//        } else {
//            PageHelper.startPage(1, 10);
//        }
        Page<GameGroup> list = gamegroupInfoMapper.getGameGroupList(b);
//        PageInfo<TbGameGroup> pageInfo = new PageInfo<>(list);
        return ResultInfo.success(list);
    }

    @Override
    public ResultInfo addGameGroup(HttpServletRequest request, GameGroupInfo tbGamegroupInfo) {
        String sequence = SequenceGenerator.sequence();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        if(null!=file){

            FTPClient ftpClient = FTPUtil.connectFtpServer(address, port, userName, passWord);
            ftpClient.enterLocalPassiveMode();
            try {
                FTPUtil.uploadFiles(ftpClient, uploadPath + "group/" +sequence + ".jpg", file.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        tbGamegroupInfo.setGamegroupPic(pics+"game/group/" +sequence + ".jpg");
        tbGamegroupInfo.setValid(true);
        gamegroupInfoMapper.insert(tbGamegroupInfo);
          return ResultInfo.success("添加成功！");
    }

    @Override
    public ResultInfo updateGameGroup(HttpServletRequest request, GameGroupInfo tbGamegroupInfo) {

        Integer gamegroupId = tbGamegroupInfo.getGamegroupId();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        if(null!=file){

            FTPClient ftpClient = FTPUtil.connectFtpServer(address, port, userName, passWord);
            ftpClient.enterLocalPassiveMode();
            try {
                FTPUtil.uploadFiles(ftpClient, uploadPath + "group/" +gamegroupId + ".jpg", file.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        tbGamegroupInfo.setGamegroupPic(pics+"game/group/" +gamegroupId + ".jpg");
        gamegroupInfoMapper.updateById(tbGamegroupInfo);
        return ResultInfo.success("编辑成功！");
    }

    @Override
    public ResultInfo deleteGameGroup(HttpServletRequest request, List<Integer> ids) {
        GameGroupInfo info = new GameGroupInfo();
        String msg="";
        for (Integer i:ids) {
            List<GameGroup> infos = tbGameGroupMapper.getByGameGroupId(i);
                if(infos.size()>0){
                    info.setGamegroupId(i);
                    GameGroupInfo byId = gamegroupInfoMapper.selectById(info);
                    msg+=byId.getGamegroupName()+"游戏组下包含应用,删除失败,";
                }else{
                    info.setGamegroupId(i);
                    GameGroupInfo byId = gamegroupInfoMapper.selectById(info);
                    byId.setValid(false);
                    gamegroupInfoMapper.updateById(byId);
                }

        }
        if(!msg.equals("")){
            return  ResultInfo.fail(msg.substring(0, msg.length() -1));
        }

        return ResultInfo.success();
    }
}
