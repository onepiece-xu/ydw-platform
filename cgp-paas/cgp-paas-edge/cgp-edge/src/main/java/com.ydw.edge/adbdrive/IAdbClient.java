package com.ydw.edge.adbdrive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * adb客户端接口类
 * @author xulh
 *
 */
public interface IAdbClient {

	String getIp();
	
	Integer getPort();
	
	Lock getLock();
	
	Boolean connect();
	
	Boolean disconnect();
	
	Boolean installAPP(String path);
	
	Boolean uninstallAPP (String appPackageName);
	
	Boolean startApp (String appPackageName,String activityName);
	
	Boolean stopApp (String appPackageName);
	
	String[] getAppList ();

	AppDetailed getAppDetailed (String appPackageName);
	
	String getDevMacAddress();
	
	Boolean upLoadFile (String srcPath,String desPath);
	
	Boolean deleteFile (String filePath);
	
	Boolean reboot();
	
	Boolean isAppRun (String appPackageName,String activityName);
	
	DevDiskDetailed getDiskSize();
	
	Boolean initRAMDev();
	
	default Boolean lock() throws InterruptedException {
		Lock lock = getLock();
		Assert.notNull(lock, "实现类没有lock对象！");
		return lock.tryLock(1,TimeUnit.MINUTES);
	}

	default void unLock() {
		Lock lock = getLock();
		Assert.notNull(lock, "实现类没有lock对象！");
		lock.unlock();
	}

	default String runShell(String command) {
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(command);
			InputStream is = process.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line;
			while (null != (line = reader.readLine())) {
				sb.append(line).append("\n");
			}
			reader.close();
			is.close();
			return sb.toString().trim();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != process) {
				process.destroy();
			}
		}
		return null;
	}
	
	default Boolean runShell2(String command) {
		try {
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor(3, TimeUnit.SECONDS);
			process.destroy();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
