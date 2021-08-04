package com.ydw.edge.utils;

import com.ydw.edge.adbdrive.AdbCommand;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.io.CopyStreamEvent;
import org.apache.commons.net.io.FromNetASCIIInputStream;
import org.apache.commons.net.io.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/11/2311:31
 */
public class FTPUtil extends FTPClient{

    static Logger logger = LoggerFactory.getLogger(FTPUtil.class);

    /**
     * 连接 FTP 服务器
     *
     * @param addr     FTP 服务器 IP 地址
     * @param port     FTP 服务器端口号
     * @param username 登录用户名
     * @param password 登录密码
     * @return
     * @throws Exception
     */
    public static FTPClient connectFtpServer(String addr, int port, String username, String password) {
        FTPClient ftpClient = new FTPClient();
        logger.info("开始连接ftp服务器{}:{},{}:{}",addr, port, username, password);
        try {
            /**设置文件传输的编码*/
            ftpClient.setControlEncoding("UTF-8");
//            ftpClient.setDefaultTimeout(10000);
//            ftpClient.setConnectTimeout(10000);
//            ftpClient.setControlKeepAliveTimeout(3000);
//            ftpClient.setControlKeepAliveReplyTimeout(1000);

            /**连接 FTP 服务器
             * 如果连接失败，则此时抛出异常，如ftp服务器服务关闭时，抛出异常：
             * java.net.ConnectException: Connection refused: connect*/
            logger.info("开始连接ftp服务器{}:{}",addr, port);
            ftpClient.connect(addr, port);
//            ftpClient.enterLocalPassiveMode();
            /**登录 FTP 服务器
             * 1）如果传入的账号为空，则使用匿名登录，此时账号使用 "Anonymous"，密码为空即可*/
            if (StringUtils.isBlank(username)) {
                ftpClient.login("Anonymous", "");
            } else {
                logger.info("开始登录ftp服务器{}:{}",username, password);
                ftpClient.login(username, password);
            }

            // 设置被动模式，开通一个端口来传输数据
            ftpClient.enterLocalPassiveMode();
            /** 设置传输的文件类型
             * BINARY_FILE_TYPE：二进制文件类型
             * ASCII_FILE_TYPE：ASCII传输方式，这是默认的方式
             * ....
             */
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

            /**
             * 确认应答状态码是否正确完成响应
             * 凡是 2开头的 isPositiveCompletion 都会返回 true，因为它底层判断是：
             * return (reply >= 200 && reply < 300);
             */
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                /**
                 * 如果 FTP 服务器响应错误 中断传输、断开连接
                 * abort：中断文件正在进行的文件传输，成功时返回 true,否则返回 false
                 * disconnect：断开与服务器的连接，并恢复默认参数值
                 */
                ftpClient.abort();
                ftpClient.disconnect();
            }

        } catch (IOException e) {
            e.printStackTrace();
            logger.error(">>>>>FTP服务器连接登录失败，请检查连接参数是否正确，或者网络是否通畅*********");
        }
        return ftpClient;
    }

    /**
     * 使用完毕，应该及时关闭连接
     * 终止 ftp 传输
     * 断开 ftp 连接
     *
     * @param ftpClient
     * @return
     */
    public static FTPClient closeFTPConnect(FTPClient ftpClient) {
        try {
            if (ftpClient != null && ftpClient.isConnected()) {
                ftpClient.abort();
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ftpClient;
    }

    /**
     * 下载 FTP 服务器上指定的单个文件，而且本地存放的文件相对部分路径 会与 FTP 服务器结构保持一致
     *
     * @param ftpClient              ：连接成功有效的 FTP客户端连接
     * @param relativeRemoteDirectory     ：文件在服务器所在的绝对路径，此方法强制路径使用右斜杠"/"，如 "/home/web/files/"
     * @param remoteFileName     ：文件名，比如ydw.apk
     * @param absoluteLocalDirectory ：本地存储文件的绝对路径，如 E:\gxg\ftpDownload
     * @param localFileName     ：文件名，比如ydw.apk
     * @return
     */
    public static void downloadSingleFile(FTPClient ftpClient, String relativeRemoteDirectory, String remoteFileName,
                                          String absoluteLocalDirectory, String localFileName) {
        /**如果 FTP 连接已经关闭，或者连接无效，则直接返回*/
        if (!ftpClient.isConnected() || !ftpClient.isAvailable()) {
            logger.error(">>>>>FTP服务器连接已经关闭或者连接无效*********");
            return;
        }
        if (StringUtils.isBlank(relativeRemoteDirectory) || StringUtils.isBlank(absoluteLocalDirectory)) {
            logger.error(">>>>>下载时遇到本地存储路径或者ftp服务器文件路径为空，放弃...*********");
            return;
        }
        if (StringUtils.isBlank(remoteFileName) || StringUtils.isBlank(localFileName)) {
            logger.error(">>>>>下载时遇到本地文件名或者ftp服务器文件名为空，放弃...*********");
            return;
        }
        try {
            /**没有对应路径时，FTPFile[] 大小为0，不会为null*/
            FTPFile[] ftpFiles = ftpClient.listFiles(rectifyPath(relativeRemoteDirectory + "/" + remoteFileName));
            if (ftpFiles.length > 1){
                logger.error(">>>>>下载时遇到ftp服务器文件路径为目录，放弃...*********");
                return;
            }
            /** ftpFile.getName():获取的是文件名称，如 123.mp4
             * 必须保证文件存放的父目录必须存在，否则 retrieveFile 保存文件时报错
             */
            File localFile = new File(absoluteLocalDirectory, localFileName);
            if (!localFile.getParentFile().exists()) {
                localFile.getParentFile().mkdirs();
            }
            OutputStream outputStream = new FileOutputStream(localFile);
            /**下载指定的 FTP 文件 到本地
             * 1)注意只能是文件，不能直接下载整个目录
             * 2)如果文件本地已经存在，默认会重新下载
             * 3)下载文件之前，ftpClient 工作目录必须是下载文件所在的目录
             * 4)下载成功返回 true，失败返回 false
             */
            ftpClient.retrieveFile(rectifyPath(relativeRemoteDirectory + "/" + remoteFileName), outputStream);
            outputStream.flush();
            outputStream.close();
            logger.info(">>>>>FTP服务器文件{}下载到{}完毕*********" ,remoteFileName, localFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载 FTP 服务器上指定的单个文件，而且本地存放的文件相对部分路径 会与 FTP 服务器结构保持一致
     *
     * @param ftpClient              ：连接成功有效的 FTP客户端连接
     * @param relativeRemoteDirectory     ：文件在服务器所在的绝对路径，此方法强制路径使用右斜杠"/"，如 "/home/web/files/"
     * @param remoteFileName     ：文件名，比如ydw.apk
     * @param absoluteLocalDirectory ：本地存储文件的绝对路径，如 E:\gxg\ftpDownload
     * @return
     */
    public static void downloadSingleFile(FTPClient ftpClient, String relativeRemoteDirectory, String remoteFileName,
                                          String absoluteLocalDirectory) {
        downloadSingleFile(ftpClient, relativeRemoteDirectory, remoteFileName, absoluteLocalDirectory, remoteFileName);
    }

    /**
     * 下载 FTP 服务器上指定的单个文件，而且本地存放的文件相对部分路径 会与 FTP 服务器结构保持一致
     *
     * @param ftpClient          ：连接成功有效的 FTP客户端连接
     * @param remoteFileName     ：文件名，比如ydw.apk
     * @param absoluteLocalDirectory ：本地存储文件的绝对路径，如 E:\gxg\ftpDownload
     * @return
     */
    public static void downloadSingleFile(FTPClient ftpClient, String remoteFileName,
                                          String absoluteLocalDirectory) {
        String fileName = remoteFileName.substring(remoteFileName.lastIndexOf("/")+1);
        String remoteFileDir = remoteFileName.replace(fileName, "");
        String localFileDir = remoteFileDir;
        if (!File.separator.equals("/")){
            //当前环境是非linux环境
            localFileDir = localFileDir.replace("/","\\");
        }
        downloadSingleFile(ftpClient, "./" + remoteFileDir, fileName, absoluteLocalDirectory + File.separator + localFileDir);
    }

    /**
     * 下载 FTP 服务器上指定的单个文件，而且本地存放的文件相对部分路径 会与 FTP 服务器结构保持一致
     *
     * @param ftpClient              ：连接成功有效的 FTP客户端连接
     * @param relativeRemotePath     ：ftpFile 文件在服务器所在的绝对路径，此方法强制路径使用右斜杠"\"，如 "\video\2018.mp4"
     * @return
     */
    public static boolean downloadSingleFile(FTPClient ftpClient, String relativeRemotePath,OutputStream outputStream) {
        boolean flag = false;
        /**如果 FTP 连接已经关闭，或者连接无效，则直接返回*/
        if (!ftpClient.isConnected() || !ftpClient.isAvailable()) {
            logger.error(">>>>>FTP服务器连接已经关闭或者连接无效*********");
            return flag;
        }
        if (StringUtils.isBlank(relativeRemotePath)) {
            logger.error(">>>>>下载时遇到ftp服务器文件路径为空，放弃...*********");
            return flag;
        }
        try {
            /**没有对应路径时，FTPFile[] 大小为0，不会为null*/
            FTPFile[] ftpFiles = ftpClient.listFiles(relativeRemotePath);
            if (ftpFiles.length > 1){
                logger.error(">>>>>下载时遇到ftp服务器文件路径为目录，放弃...*********");
                return flag;
            }
//            long size = ftpFiles[0].getSize();
//            InputStream inputStream = ftpClient.retrieveFileStream(relativeRemotePath);
//
//            logger.info("当前工作目录{}", ftpClient.printWorkingDirectory());
//            flag = ftpClient.retrieveFile(relativeRemotePath, outputStream);
//            logger.info(">>>>>FTP服务器文件{}下载结果{}*********", relativeRemotePath, flag);
//            outputStream.flush();
//            outputStream.close();
//            logger.info(">>>>>FTP服务器文件下载完毕*********");
            InputStream inputStream = ftpClient.retrieveFileStream(relativeRemotePath);
            byte[] bytesArray = new byte[65536];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(bytesArray)) != -1) {
                outputStream.write(bytesArray, 0, bytesRead);
            }
            logger.info("file Transfer complete.");
            outputStream.flush();
            logger.info("outputStream.flush.");
            outputStream.close();
            logger.info("outputStream.close.");
            inputStream.close();
            logger.info("inputStream.close.");
            flag = true;
//            FutureTask<Boolean> futureTask = new FutureTask<>(() -> {
//                boolean f = false;
//                try {
//                    inputStream.close();
//                    ftpClient.completePendingCommand();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                return f;
//            });
//            new Thread(futureTask).start();
//            try {
//                flag = futureTask.get(2, TimeUnit.SECONDS);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 遍历 FTP 服务器指定目录下的所有文件(包含子孙文件)
     *
     * @param ftpClient        ：连接成功有效的 FTP客户端连接
     * @param relativeRemotePath ：返回查询结果，其中为服务器目录下的文件相对路径，如：\1.png、\docs\overview-tree.html 等
     * @param absoluteLocalDirectory       ：查询的 FTP 服务器目录，如果文件，则视为无效，使用绝对路径，如"/"、"/video"、"\\"、"\\video"
     * @return
     */
    public static void downloadDirectory(FTPClient ftpClient, String relativeRemotePath , String absoluteLocalDirectory) {
        /**如果 FTP 连接已经关闭，或者连接无效，则直接返回*/
        if (!ftpClient.isConnected() || !ftpClient.isAvailable()) {
            logger.error(">>>>>FTP服务器连接已经关闭或者连接无效*********");
            return;
        }
        if (StringUtils.isBlank(absoluteLocalDirectory) || StringUtils.isBlank(relativeRemotePath)) {
            logger.error(">>>>>下载时遇到本地存储路径或者ftp服务器文件路径为空，放弃...*********");
            return;
        }
        try {
            /**没有对应路径时，FTPFile[] 大小为0，不会为null*/
            FTPFile[] ftpFiles = ftpClient.listFiles(relativeRemotePath);
            if (ftpFiles.length == 0){
                logger.error(">>>>>下载时遇到ftp服务器文件夹路径为目录，放弃...*********");
                return;
            }
            //是否要copy当前目录
            File parent = new File(absoluteLocalDirectory + File.separator +relativeRemotePath);
            if (!parent.exists()){
                parent.mkdirs();
            }
            for (FTPFile ftpFile : ftpFiles){
                if (ftpFile.isDirectory()){
                    File childFile = null;
                    if (parent != null){
                        childFile = new File(parent, ftpFile.getName());
                    }else{
                        childFile = new File(absoluteLocalDirectory, ftpFile.getName());
                    }
                    if (!childFile.exists()){
                        childFile.mkdir();
                    }
                    downloadDirectory(ftpClient, rectifyPath(relativeRemotePath + "/" + ftpFile.getName()), absoluteLocalDirectory);
                }else{
                    downloadSingleFile(ftpClient, rectifyPath(relativeRemotePath + "/" + ftpFile.getName()), absoluteLocalDirectory);
                }
            }
            logger.info(">>>>>FTP服务器文件夹{}下载完毕*********" ,relativeRemotePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传本地文件 或 目录 至 FTP 服务器----保持 FTP 服务器与本地 文件目录结构一致
     *
     * @param ftpClient  连接成功有效的 FTPClinet
     * @param uploadFile 待上传的文件 或 文件夹(此时会遍历逐个上传)
     * @throws Exception
     */
    public static void uploadFiles(FTPClient ftpClient, String relativeRemoteDirectory, File uploadFile) {
        /**如果 FTP 连接已经关闭，或者连接无效，则直接返回*/
        if (!ftpClient.isConnected() || !ftpClient.isAvailable()) {
            logger.error(">>>>>FTP服务器连接已经关闭或者连接无效*****放弃文件上传****");
            return;
        }
        if (uploadFile == null || !uploadFile.exists()) {
            logger.error(">>>>>待上传文件为空或者文件不存在*****放弃文件上传****");
            return;
        }
        try {
            if (StringUtils.isBlank(relativeRemoteDirectory)){
                relativeRemoteDirectory = ".";
            }
            if (uploadFile.isDirectory()) {
                /**如果被上传的是目录时
                 * makeDirectory：在 FTP 上创建目录(方法执行完，服务器就会创建好目录，如果目录本身已经存在，则不会再创建)
                 * 1）可以是相对路径，即不以"/"开头，相对的是 FTPClient 当前的工作路径，如 "video"、"视频" 等，会在当前工作目录进行新建目录
                 * 2）可以是绝对路径，即以"/"开头，与 FTPCLient 当前工作目录无关，如 "/images"、"/images/2018"
                 * 3）注意多级目录时，必须确保父目录存在，否则创建失败，
                 *      如 "video/201808"、"/images/2018" ，如果 父目录 video与images不存在，则创建失败
                 * */
                boolean b = ftpClient.makeDirectory(rectifyPath(relativeRemoteDirectory + "/" + uploadFile.getName()));
                System.out.println(b);
                File[] listFiles = uploadFile.listFiles();
                for (int i = 0; i < listFiles.length; i++) {
                    File loopFile = listFiles[i];
                    uploadFiles(ftpClient, rectifyPath(relativeRemoteDirectory + "/" + uploadFile.getName()), loopFile);
                }
            } else {
                /**如果被上传的是文件时*/
                FileInputStream input = new FileInputStream(uploadFile);
                /** storeFile:将本地文件上传到服务器
                 * 1）如果服务器已经存在此文件，则不会重新覆盖,即不会再重新上传
                 * 2）如果当前连接FTP服务器的用户没有写入的权限，则不会上传成功，但是也不会报错抛异常
                 * */
                ftpClient.storeFile(rectifyPath(relativeRemoteDirectory + "/" + uploadFile.getName()), input);
                input.close();
                logger.info(">>>>>文件上传成功****" + uploadFile.getPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除服务器的文件
     *
     * @param ftpClient   连接成功且有效的 FTP客户端
     * @param deleteFiles 待删除的文件或者目录，为目录时，会逐个删除，
     *                    路径必须是绝对路径，如 "/1.png"、"/video/3.mp4"、"/images/2018"
     *                    "/" 表示用户根目录,则删除所有内容
     */
    public static void deleteServerFiles(FTPClient ftpClient, String deleteFiles) {
        /**如果 FTP 连接已经关闭，或者连接无效，则直接返回*/
        if (!ftpClient.isConnected() || !ftpClient.isAvailable()) {
            logger.error(">>>>>FTP服务器连接已经关闭或者连接无效*****放弃文件上传****");
            return;
        }
        try {
            /** 尝试改变当前工作目录到 deleteFiles
             * 1）changeWorkingDirectory：变更FTPClient当前工作目录，变更成功返回true，否则失败返回false
             * 2）如果变更工作目录成功，则表示 deleteFiles 为服务器已经存在的目录
             * 3）否则变更失败，则认为 deleteFiles 是文件，是文件时则直接删除
             */
            boolean changeFlag = ftpClient.changeWorkingDirectory(deleteFiles);
            if (changeFlag) {
                /**当被删除的是目录时*/
                FTPFile[] ftpFiles = ftpClient.listFiles();
                for (FTPFile ftpFile : ftpFiles) {
                    System.out.println("----------------::::" + ftpClient.printWorkingDirectory());
                    if (ftpFile.isFile()) {
                        boolean deleteFlag = ftpClient.deleteFile(ftpFile.getName());
                        if (deleteFlag) {
                            logger.info(">>>>>删除服务器文件成功****" + ftpFile.getName());
                        } else {
                            logger.error(">>>>>删除服务器文件失败****" + ftpFile.getName());
                        }
                    } else {
                        /**printWorkingDirectory：获取 FTPClient 客户端当前工作目录
                         * 然后开始迭代删除子目录
                         */
                        String workingDirectory = ftpClient.printWorkingDirectory();
                        deleteServerFiles(ftpClient, workingDirectory + "/" + ftpFile.getName());
                    }
                }
                /**printWorkingDirectory：获取 FTPClient 客户端当前工作目录
                 * removeDirectory：删除FTP服务端的空目录，注意如果目录下存在子文件或者子目录，则删除失败
                 * 运行到这里表示目录下的内容已经删除完毕，此时再删除当前的为空的目录，同时将工作目录移动到上移层级
                 * */
                String workingDirectory = ftpClient.printWorkingDirectory();
                ftpClient.removeDirectory(workingDirectory);
                ftpClient.changeToParentDirectory();
            } else {
                /**deleteFile：删除FTP服务器上的文件
                 * 1）只用于删除文件而不是目录，删除成功时，返回 true
                 * 2）删除目录时无效,方法返回 false
                 * 3）待删除文件不存在时，删除失败，返回 false
                 * */
                boolean deleteFlag = ftpClient.deleteFile(deleteFiles);
                if (deleteFlag) {
                    logger.info(">>>>>删除服务器文件成功****" + deleteFiles);
                } else {
                    logger.error(">>>>>删除服务器文件失败****" + deleteFiles);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String rectifyPath(String path){
        while(path.contains("//")){
            path = path.replace("//", "/");
        }
        return path;
    }
}
