package com.leaf.sqlquery.util;

import java.util.ArrayList;
import java.util.HashMap;


public class Settings {


	public static String getServerUrl() {
		return serverUrl;
	}

	public static void setServerUrl(String serverUrl) {
		Settings.serverUrl = serverUrl;
	}


	public static String getListFileUrl() {
		return serverUrl + listFileUrl;
	}

	public static String getDownloadFileUrl() {
		return serverUrl + downloadFileUrl;
	}



	public static void setMessage(String message) {
		Settings.message = message;
	}

	public static String getMessage() {
		return message;
	}

	public static void setBarHeight(int height){
		Settings.height = height;
	}
	public static int getBarHeight(){
		return height;
	}
	
	public static void setIfMessage(int ifMessage){
		Settings.ifMessage = ifMessage;
	}
	public static int getIfMessage(){
		return ifMessage;
	}
	
	public static void setSysMessage(String sysMessage){
		Settings.sysMessage = sysMessage;
	}
	public static String getSysMessage(){
		return sysMessage;
	}

	public static String getUserInfoUrl() {
		return serverUrl + userInfoUrl;
	}

	public static void setUserInfoUrl(String userInfoUrl) {
		Settings.userInfoUrl = userInfoUrl;
	}

	public static ArrayList<HashMap<String, String>> getSubsidys() {
		return subsidys;
	}

	public static void setSubsidys(ArrayList<HashMap<String, String>> subsidys) {
		Settings.subsidys = subsidys;
	}

	private static String serverUrl = "";
	private static String listFileUrl = "/queryFile.action";
	private static String downloadFileUrl = "/download.action";
	private static String userInfoUrl="/queryInfo.action";
	// 用这个变量传递临时状态
	private static String message;
	private static int height;
	
	private static ArrayList<HashMap<String, String>> subsidys;
	
	
	private static int ifMessage;
	private static String sysMessage;
//	private static Map upFilesStatus = new HashMap();
//	private static Map upFilesProgress = new HashMap();
}
