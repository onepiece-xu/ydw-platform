package com.ydw.platform.service.impl;

import com.ydw.platform.service.IClientService;
import com.ydw.platform.util.FTPUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/11/2316:09
 */
@Service
public class ClientServiceImpl implements IClientService {

    @Value("${ftp.address}")
    private String address;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.userName}")
    private String userName;

    @Value("${ftp.passWord}")
    private String passWord;

    @Value("${ftp.upgradeFileHome}")
    private String upgradeFileHome;

    @Value("${ftp.upgradeFile}")
    private String upgradeFile;

    @Override
    public void getUpgradeFile(String channelId, HttpServletResponse response){
        FTPClient ftpClient = FTPUtil.connectFtpServer(address, port, userName, passWord);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String channelUpgradeFile = upgradeFileHome + "/" + channelId + "/" + upgradeFile;
        FTPUtil.downloadSingleFile(ftpClient, channelUpgradeFile , outputStream);
    }
}
