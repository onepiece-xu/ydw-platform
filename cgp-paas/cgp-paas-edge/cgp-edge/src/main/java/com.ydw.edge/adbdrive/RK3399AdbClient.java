package com.ydw.edge.adbdrive;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 一个arm板同时只能有一个adb客户端,并且同时只能执行一个命令
 * 
 * @author xulh
 *
 */
public class RK3399AdbClient implements IAdbClient{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private String ip;
	
	private int port = 5555;
	
	private ReentrantLock lock = new ReentrantLock();

	public RK3399AdbClient(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	
	private Boolean checkDeviceOnlineState () {
		logger.info("{}:{} start checkDeviceOnlineState ", ip, port);
		String string = runShell(AdbCommand.STRING_ADB_DEVICES);
		if (string == null || string.isEmpty()) {
			logger.info("{}:{} check null Device Online", ip, port);
			return false;
		} else if (string.contains(String.format(AdbCommand.STRING_ADB_DEVICE, ip, port))) {
			logger.info("{}:{} Device Online", ip, port);
			return true;
		} else if (string.contains(String.format(AdbCommand.STRING_ADB_OFFLINE, ip, port))) {
			logger.info("{}:{} Device Offline", ip, port);
			return false;
		}
		logger.info("end checkDeviceOnlineState ip 【{}:{}】 not in", ip, port);
		return false;
	}

	/**
	 * 连接
	 * @return
	 */
	public Boolean connect() {
		Boolean isConnect = false;
		if (checkDeviceOnlineState()) {
			logger.info("{}:{} connect started", ip, port);
			isConnect = true;
		} else {
			logger.info("{}:{} start connect ", ip, port);
			String string = String.format(AdbCommand.STRING_ADB_CONNECT, ip, port);
			logger.info("{}:{} connect command is {}", ip, port, string);
			string = runShell(string);
			logger.info("{}:{} connect command result is {}", ip, port, string);
			if (string == null || string.equals(String.format(AdbCommand.STRING_ADB_FAILED_TO_CONNECT, ip, port))) {
				logger.error("{}:{} connect fail", ip, port);
				isConnect = false;
			} else if (string.equals(String.format(AdbCommand.STRING_ADB_CONNECT_TO, ip, port))) {
				logger.info("{}:{} connect success" ,ip, port);
				isConnect = true;
			} else if (string.equals(String.format(AdbCommand.STRING_ADB_ALREADY_TO_CONNECT, ip, port))) {
				if (checkDeviceOnlineState()) {
					logger.info("{}:{} connect ing" , ip, port);
					isConnect = true;
				} else {
					logger.error("{}:{} connect offline", ip, port);
					disconnect();
					isConnect = false;
				}
			}
		}
		return isConnect;
	}

	/**
	 * 断开连接
	 * @return
	 */
	public Boolean disconnect() {
		logger.info("{}:{} start disconnect" ,ip, port);
		String string = String.format(AdbCommand.STRING_ADB_DISCONNECT, ip, port);
		string = runShell(string);
		if (string == null) {
			logger.info("{}:{} disconnect fail" ,ip,port);
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (String.format(AdbCommand.STRING_ADB_DISCONNECTED, ip, port).equals(string)) {
			logger.info("{}:{} disconnect success" ,ip, port);
		}
		logger.info("{}:{} end disconnect" ,ip,port);
		return false;
	}
	
	/**
	 * 安装应用
	 * @param path
	 * @return
	 */
	public Boolean installAPP (String path) {
		logger.info("{} start installAPP path {}" , ip, path);
		FutureTask<String> futureTask = new FutureTask<>(() -> runShell(
				String.format(AdbCommand.STRING_ADB_INSTALL, ip, path)));
		new Thread(futureTask).start();
		String string = null;
		try {
			string = futureTask.get(30, TimeUnit.MINUTES);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if (string == null) {
			logger.error("{} installAPP fail path {}" ,ip ,path);
			return false;
		}
		if (string.contains(AdbCommand.STRING_ADB_SUCCESS)) {
			logger.info("{} installAPP success path {}" ,ip ,path);
			return true;
		}
		logger.info("{} end installAPP path {} return false" , ip , path);
		return false;
	}
	
	/**
	 * 卸载应用
	 * @param appPackageName
	 * @return
	 */
	public Boolean uninstallAPP (String appPackageName) {
		logger.info("{} start uninstallAPP appPackageName {} ", ip, appPackageName);
		String string = String.format(AdbCommand.STRING_ADB_UNINSTALL, ip, appPackageName);
		string = runShell(string);
		if (string == null) {
			logger.error("{} uninstallAPP fail appPackageName {}" ,ip , appPackageName);
			return false;
		}
		if (string.contains(AdbCommand.STRING_ADB_SUCCESS)) {
			logger.info("{} end uninstallAPP success appPackageName {}" , ip , appPackageName);
			return true;
		}
		logger.info("{} end uninstallAPP appPackageName {} ", ip, appPackageName);
		return false;
	}
	
	/**
	 * 启动应用
	 * @param appPackageName
	 * @param activityName
	 * @return
	 */
	public Boolean startApp (String appPackageName,String activityName) {
		logger.info("{} start startApp appPackageName {}", ip , appPackageName);
		String string = String.format(AdbCommand.STRING_ADB_START_APP, ip, appPackageName,activityName);
		string = runShell(string);
		if (string == null) {
			logger.error("{} startApp fail appPackageName {}", ip , appPackageName);
			return false;
		}
		if (string.contains(AdbCommand.STRING_ADB_STARING)) {
			logger.info("{} startApp success appPackageName {}", ip , appPackageName);
			return true;
		}
		logger.info("{} end startApp return false appPackageName {}" , ip , appPackageName);
		return false;	
	}
	
	/**
	 * 关闭应用
	 * @param appPackageName
	 * @return
	 */
	public Boolean stopApp (String appPackageName) {
		logger.info("{} start stopApp appPackageName {}", ip , appPackageName);
		String string = String.format(AdbCommand.STRING_ADB_STOP_APP, ip, appPackageName);
		string = runShell(string);
		if (string == null) {
			logger.error("{} stopApp fail appPackageName {}", ip , appPackageName);
		}
		//暂时不清楚 TODO
//		clearAppData(appPackageName);
		return true;
	}
	
	/**
	 * 获取app列表
	 * @return
	 */
	public String[] getAppList () {
		logger.info("{} start getAppList" ,ip);
		String string = String.format(AdbCommand.STRING_ADB_LIST_PACKAGE, ip);
		string = runShell(string);
		if (string == null) {
			logger.error("{} getAppList fail", ip);
			return null;
		}
		String[] strings = string.split("\n");
		if (strings != null) {
			for (int i = 0; i < strings.length; i++) {
				strings[i] = strings[i].substring(8);
			}
			logger.info("{} getAppList Appsize {}", ip ,strings.length);
			return strings;
		}
		logger.info("{} end getAppList" ,ip);
		return null;
	}
	
	
	public AppDetailed getAppDetailed (String packageName) {
		logger.info("{} start getAppDetailed packageName {}" ,ip , packageName);
		String string = String.format(AdbCommand.STRING_ABD_DUMPSYS, ip,packageName);
		string = runShell(string);
		if (string == null) {
			logger.error("{} getAppDetailed fail" ,ip);
			return null;
		}
		String[] strings = string.split("\n");
		if (strings == null) {
			logger.error("{} getAppDetailed {} null " ,ip, packageName);
			return null;
		}
		for (int i = 0; i < strings.length; i++) {
			if (AdbCommand.STRING_ADB_ACTION_MAIN.equals(strings[i].trim())) {
				if (i + 1 <= strings.length - 1) {
					String[] tempStrings = strings[i + 1].trim().split(" ")[1].split("/");
					AppDetailed appDetailed = new AppDetailed();
					appDetailed.packageName = tempStrings[0];
					appDetailed.launcherActivityName = tempStrings[1];
					logger.info("{} getAppDetailed packageName {} success {}" , ip, packageName, tempStrings[1]);
					return appDetailed;
				}
			}
		}
		logger.info("{} end getAppDetailed packageName {} return null" , ip ,packageName);
		return null;
	} 
	
	public String getDevMacAddress () {
		logger.info("{} start getDevMacAddress ip" ,ip);
		String string = String.format(AdbCommand.STRING_ADB_GET_MAC, ip);
		string = runShell(string);
		if (string == null) {
			logger.error("{} getDevMacAddress fail" ,ip);
			return null;
		}
		string = string.replace(":", "").toUpperCase();
		logger.info("{} end getDevMacAddress mac:{}" ,ip , string);
		return string;
	}
	
	
	public Boolean upLoadFile (String srcPath,String desPath) {
		logger.info("{} start upLoadFile srcPath {} desPath {} ", ip ,srcPath , desPath);
		String string = String.format(AdbCommand.STRING_ADB_PUSH_FILE, ip,srcPath,desPath);
		string = runShell(string);
		if (string == null) {
			logger.error("{} upLoadFile srcPath {} desPath {} fail", ip,srcPath , desPath);
			return false;
		}
		if (string.contains(AdbCommand.STRING_ADB_ERROR)) {
			logger.error("{} upLoadFile srcPath {} desPath {} fail {}", ip,srcPath , desPath, string);
			return false;
		} else if (string.contains(AdbCommand.STRING_ADB_1_FILE_PUSHED)) {
			logger.info("{} upLoadFile STRING_ADB_1_FILE_PUSHED ip" ,ip );
			return true;
		}
		logger.info("{} end upLoadFile success");
		return false;
	}
	
	public Boolean deleteFile (String filePath) {
		logger.info("{} start deleteFile filePath {}" ,ip ,filePath);
		String string = String.format(AdbCommand.STRING_ADB_DELETE_FILE, ip, filePath);
		string = runShell(string);
		if (string == null) {
			logger.error("{} deleteFile {} fail", ip, filePath);
			return false;
		}
		if (string.isEmpty()) {
			logger.info("{} deleteFile {} true", ip, filePath);
			return true;
		}
		logger.info("{}  end deleteFile return false ip" , ip);
		return false;
	}
	
	public Boolean reboot () {
		logger.info("{} start reboot" ,ip);
		String string = String.format(AdbCommand.STRING_ADB_REBOOT, ip);
		Boolean flag = runShell2(string);
		logger.info("{} end reboot {}" ,ip , flag);
		return flag;
	}
	
	public Boolean isAppRun (String appPackageName,String activityName) {
		logger.info("{} start isAppRun appPackageName:{} activityName:{}" ,ip, appPackageName, activityName);
		String string = runShell(String.format(AdbCommand.STRING_ADB_DUMPSYS_RESUMED_ACTIVITY, ip));
		if (string == null) {
			logger.error("{} isAppRun fail appPackageName:{} activityName:{}" ,ip, appPackageName, activityName);
			return false;
		}
		if (string.contains(String.format(AdbCommand.STRING_ADB_PACKAGE_AND_ACTIVITY, appPackageName,activityName))) {
			logger.info("{} end isAppRun return true" ,ip);
			return true;
		}
		logger.info("{} end isAppRun return false" ,ip);
		return false;
	}
	
    public DevDiskDetailed getDiskSize () {
    	logger.info("{} start getDiskSize " ,ip);
		String string = runShell(String.format(AdbCommand.STRING_ADB_DF_STORAGE_EMULATED,ip));
		if (string == null) {
			logger.error("{} getDiskSize fail" ,ip);
			return null;
		}
		String[] dataStrings = string.split("\\s+");
		if (dataStrings == null || dataStrings.length != 6) {
			logger.error("{} getDiskSize dataStrings == null || dataStrings.length != 6" ,ip);
			return null;
		}
		DevDiskDetailed diskDetailed = new DevDiskDetailed();
		diskDetailed.totalSize = valueOf(dataStrings[1]);
		diskDetailed.usedSize = valueOf(dataStrings[2]);
		diskDetailed.availableSize = valueOf(dataStrings[3]);
		diskDetailed.usedRate = valueOf(dataStrings[4].replace("%", ""));
		logger.info("{} end getDiskSize {}" ,ip, string);
		return diskDetailed;
    }
    
    
    public Boolean clearAppData (String appPackageName) {
    	logger.info("{} start clearAppData appPackageName {}" ,ip , appPackageName);
    	String string = runShell(String.format(AdbCommand.STRING_ADB_PM_CLEAR, ip,appPackageName));
		if (string == null) {
			logger.info("{} end clearAppData fail", ip);
			return false;
		}
		if (string.equals(AdbCommand.STRING_ADB_SUCCESS)) {
			logger.info("{} end clearAppData success", ip);
			return true;
		}
		logger.info("{} end clearAppData fail {}", ip, string);
		return false;
    }
	
    private int valueOf (String numberString) {
    	try {
			return  Integer.valueOf(numberString);
		} catch (NumberFormatException e) {
			return -1;
		}
    }
	
	public synchronized Boolean initRAMDev () {
		String string = runShell(String.format(AdbCommand.STRING_ADB_REBOOT, ip));
		logger.info("{} initRAMDev1 ", ip, string);
		string = runShell(String.format(AdbCommand.STRING_ADB_REMOUNT, ip));
		logger.info("{} initRAMDev2 ", ip, string);
		string = runShell(String.format(AdbCommand.STRING_ADB_SHELL_RM_ANSERVERARGENT, ip));
		logger.info("{} initRAMDev3 ", ip, string);
		string = runShell(String.format(AdbCommand.STRING_ADB_UNINSTALL_WEIXIN, ip));
		logger.info("{} initRAMDev4 ", ip, string);
		string = runShell(String.format(AdbCommand.STRING_ADB_UNINSTALL_QQ, ip));
		logger.info("{} initRAMDev5 ", ip, string);
		return true;
	}

	@Override
	public String getIp() {
		return ip;
	}

	@Override
	public Integer getPort() {
		return port;
	}

	@Override
	public Lock getLock() {
		return lock;
	}
}
