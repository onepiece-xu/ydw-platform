package com.ydw.platform.service.impl;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.platform.dao.UserInfoMapper;
import com.ydw.platform.model.db.UserInfo;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.model.vo.UsageRecordVO;
import com.ydw.platform.service.IUserInfoService;

import com.ydw.platform.util.FTPUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hea
 * @since 2020-10-30
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${ftp.address}")
    private String address;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.userName}")
    private String userName;

    @Value("${ftp.passWord}")
    private String passWord;

    @Value("${config.logPath}")
    private String logPath;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public ResultInfo getUsageRecord(String userId, Page buildPage) {
        Page<UsageRecordVO> usageRecord = userInfoMapper.getUsageRecord(buildPage, userId);
        return ResultInfo.success(usageRecord);
    }

    @Override
    public ResultInfo sendLog(HttpServletRequest request,String userId) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");


        if (null != file) {
//                DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss"); //
//                String filename =  df.format(new Date());
            FTPClient ftpClient = FTPUtil.connectFtpServer(address, port, userName, passWord);
//            ftpClient.enterLocalPassiveMode();
            try {

                boolean flag1 = false;
                FTPFile[] ftpFileArr1 = ftpClient.listFiles(logPath+"1_"+ userId + ".zip");
                if (ftpFileArr1.length > 0) {
                    flag1 = true;
                }
                if(!flag1){
                    FTPUtil.uploadFiles(ftpClient, logPath  +"1_"+ userId + ".zip", file.getInputStream());
                    return ResultInfo.success();
                }

                boolean flag2 = false;
                FTPFile[] ftpFileArr2 = ftpClient.listFiles(logPath+"2_"+ userId + ".zip");
                if (ftpFileArr2.length > 0) {
                    flag2 = true;
                }
                if(!flag2){
                    FTPUtil.uploadFiles(ftpClient, logPath  +"2_"+ userId + ".zip", file.getInputStream());
                    return ResultInfo.success();
                }

                boolean flag3 = false;
                FTPFile[] ftpFileArr3 = ftpClient.listFiles(logPath+"3_"+ userId + ".zip");
                if (ftpFileArr3.length > 0) {
                    flag3 = true;
                }
                if(!flag3){
                    FTPUtil.uploadFiles(ftpClient, logPath  +"3_"+ userId + ".zip", file.getInputStream());
                    return ResultInfo.success();
                }

                boolean flag4 = false;
                FTPFile[] ftpFileArr4 = ftpClient.listFiles(logPath+"4_"+ userId + ".zip");
                if (ftpFileArr4.length > 0) {
                    flag4 = true;
                }
                if(!flag4){
                    FTPUtil.uploadFiles(ftpClient, logPath  +"4_"+ userId + ".zip", file.getInputStream());
                    return ResultInfo.success();
                }

                boolean flag5 = false;
                FTPFile[] ftpFileArr5 = ftpClient.listFiles(logPath+"5_"+ userId + ".zip");
                if (ftpFileArr5.length > 0) {
                    flag5 = true;
                }
                if(!flag5){
                    FTPUtil.uploadFiles(ftpClient, logPath  +"5_"+ userId + ".zip", file.getInputStream());
                    return ResultInfo.success();
                }
                if(true){
                    boolean flag = false;
                    try {

                        // 切换FTP目录
                        ftpClient.changeWorkingDirectory(logPath  +"1_"+ userId + ".zip");
                        ftpClient.dele(logPath  +"1_"+ userId + ".zip");
                        ftpClient.dele(logPath  +"2_"+ userId + ".zip");
                        ftpClient.dele(logPath  +"3_"+ userId + ".zip");
                        ftpClient.dele(logPath  +"4_"+ userId + ".zip");
                        ftpClient.dele(logPath  +"5_"+ userId + ".zip");
                        FTPUtil.uploadFiles(ftpClient, logPath  +"1_"+ userId + ".zip", file.getInputStream());
                        ftpClient.logout();
                        flag = true;
                        System.out.println("删除文件成功");
                    } catch (Exception e) {
                        System.out.println("删除文件失败");
                        e.printStackTrace();
                    } finally {
                        if (ftpClient.isConnected()) {
                            try {
                                ftpClient.disconnect();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
                return ResultInfo.fail("上传失败！");
            }

        }
        return ResultInfo.success();
    }
}