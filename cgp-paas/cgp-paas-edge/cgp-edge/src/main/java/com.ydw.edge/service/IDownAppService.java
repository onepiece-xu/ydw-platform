package com.ydw.edge.service;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/12/410:37
 */
public interface IDownAppService {

    boolean downLoadApp(String appId, String remoteDirectory, String remoteFileName, String localDirectory, String localFileName);
}
