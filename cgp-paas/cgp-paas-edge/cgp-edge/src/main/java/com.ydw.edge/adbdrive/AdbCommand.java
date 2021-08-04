package com.ydw.edge.adbdrive;

public class AdbCommand {

	public static final String STRING_ADB_CONNECT = "adb connect %s:%s";
	public static final String STRING_ADB_DISCONNECT= "adb disconnect %s:%s";
	public static final String STRING_ADB_INSTALL = "adb -s %s install -r -g %s";
	public static final String STRING_ADB_UNINSTALL = "adb -s %s uninstall %s";
	public static final String STRING_ADB_START_APP = "adb -s %s shell am start %s/%s";
	public static final String STRING_ADB_STOP_APP = "adb -s %s shell am force-stop %s";
	public static final String STRING_ABD_DUMPSYS = "adb -s %s shell dumpsys package %s";
	public static final String STRING_ADB_LIST_PACKAGE = "adb -s %s shell pm list package";
	public static final String STRING_ADB_GET_MAC = "adb -s %s shell cat /sys/class/net/eth0/address";
	public static final String STRING_ADB_DEVICES = "adb devices";
	public static final String STRING_ADB_PUSH_FILE = "adb -s %s push %s %s";
	public static final String STRING_ADB_DELETE_FILE = "adb -s %s shell rm -r %s";
	public static final String STRING_ADB_PULL_FILE = "adb -s %s pull %s %s";
	public static final String STRING_ADB_REBOOT = "adb -s %s reboot";
	public static final String STRING_ADB_PM_CLEAR = "adb -s %s shell pm clear %s";
	
	public static final String STRING_ADB_ROOT = "adb -s %s root";
	public static final String STRING_ADB_REMOUNT = "adb -s %s remount";
	public static final String STRING_ADB_SHELL_RM_ANSERVERARGENT = "adb -s %s shell rm /system/bin/AnServerArgent";
	public static final String STRING_ADB_UNINSTALL_WEIXIN =  "adb -s %s uninstall com.tencent.mm";
	public static final String STRING_ADB_UNINSTALL_QQ =  "adb -s %s uninstall com.tencent.mobileqq";
	public static final String STRING_ADB_DUMPSYS_RESUMED_ACTIVITY = "adb -s %s shell dumpsys activity activities | grep mResumedActivity";
	public static final String STRING_ADB_DF_STORAGE_EMULATED = "adb -s %s shell df | grep /storage/emulated";
	public static final String MKT_STRING_ADB_DF_STORAGE_EMULATED = "adb -s %s shell df | grep /mnt/runtime/default/emulated";
	
	public static final String STRING_ADB_CONNECT_TO = "connected to %s:%s";
	public static final String STRING_ADB_DISCONNECTED_ALL = "disconnected everything";
	public static final String STRING_ADB_DISCONNECTED = "disconnected %s:%s";
	public static final String STRING_ADB_SUCCESS = "Success";
	public static final String STRING_ADB_STARING = "Starting:";
	
	public static final String STRING_ADB_FAILED_TO_CONNECT = "failed to connect to %s:%s";
	public static final String STRING_ADB_ALREADY_TO_CONNECT = "already connected to %s:%s";
	public static final String STRING_ADB_OFFLINE = "%s:%s	offline";
	public static final String STRING_ADB_DEVICE = "%s:%s	device";
	public static final String STRING_ADB_1_FILE_PUSHED = "1 file pushed";
	public static final String STRING_ADB_ERROR = "adb: error:";
	public static final String STRING_ADB_1_FILE_PULLED = "1 file pulled";
	
	public static final String STRING_ADB_ACTION_MAIN = "android.intent.action.MAIN:";
	public static final String STRING_ADB_PACKAGE_AND_ACTIVITY = "%s/%s";
}
