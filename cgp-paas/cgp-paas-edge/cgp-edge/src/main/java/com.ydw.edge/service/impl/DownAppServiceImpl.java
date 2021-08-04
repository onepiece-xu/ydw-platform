package com.ydw.edge.service.impl;

import com.ydw.edge.service.IDownAppService;
import com.ydw.edge.utils.FTPUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/12/410:59
 */
@Service
public class DownAppServiceImpl implements IDownAppService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${ftp.address}")
    private String address;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.userName}")
    private String userName;

    @Value("${ftp.passWord}")
    private String passWord;

    @Autowired
    private YdwPaasService ydwPaasService;

    @Override
    public boolean downLoadApp(String appId, String remoteDirectory, String remoteFileName,
                               String localDirectory, String localFileName) {
        logger.info("开始下载远程文件夹{}下的文件{}到本地文件夹{}的文件{}", remoteDirectory, remoteFileName,
                localDirectory, localFileName);
        boolean flag = false;
        File file = new File(localDirectory + File.separator + localFileName);
        if (file.exists()){
            File tempFile = new File(file.getParent() + File.separator + "temp",localFileName);
            try {
                if (!tempFile.getParentFile().exists()){
                    tempFile.getParentFile().mkdirs();
                }
                tempFile.createNewFile();
                OutputStream outputStream = new FileOutputStream(tempFile);
                FTPClient ftpClient = FTPUtil.connectFtpServer(address, port, userName, passWord);
                logger.info("开始下载远程文件夹{}下的文件{}到本地文件夹{}的文件", remoteDirectory, remoteFileName,tempFile.getCanonicalPath());
                flag = FTPUtil.downloadSingleFile(ftpClient, FTPUtil.rectifyPath(remoteDirectory + "/" + remoteFileName), outputStream);
                new Thread(() -> FTPUtil.closeFTPConnect(ftpClient)).start();
                if (flag){
                    logger.error("start to move file");
                    Files.move(tempFile.toPath(),
                            file.toPath(),
                            StandardCopyOption.REPLACE_EXISTING);
                    logger.info("下载远程文件夹{}下的文件{}到本地文件夹{}的文件{}成功！", remoteDirectory, remoteFileName,
                            localDirectory, localFileName);
                    ydwPaasService.issuedApp(appId);
                }else{
                    logger.error("下载远程文件夹{}下的文件{}到本地文件夹{}的文件{}失败！", remoteDirectory, remoteFileName,
                            localDirectory, localFileName);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            tempFile.deleteOnExit();
        }else{
            try {
                File parentFile = file.getParentFile();
                if(!parentFile.exists()){
                    parentFile.mkdirs();
                }
                file.createNewFile();
                OutputStream outputStream = new FileOutputStream(file);
                FTPClient ftpClient = FTPUtil.connectFtpServer(address, port, userName, passWord);
                flag = FTPUtil.downloadSingleFile(ftpClient, FTPUtil.rectifyPath(remoteDirectory + "/" + remoteFileName), outputStream);
                FTPUtil.closeFTPConnect(ftpClient);
                if (flag){
                    logger.info("下载远程文件夹{}下的文件{}到本地文件夹{}的文件{}成功！", remoteDirectory, remoteFileName,
                            localDirectory, localFileName);
                    ydwPaasService.issuedApp(appId);
                }else{
                    logger.error("下载远程文件夹{}下的文件{}到本地文件夹{}的文件{}失败！", remoteDirectory, remoteFileName,
                            localDirectory, localFileName);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

}
