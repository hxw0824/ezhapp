package com.zlwh.yzh.api.domain;

public class LoginDomain {
	private String userName;
	private String password;
	private String deviceType;
	private String userToken;
	
	public static final String DEVICETYPE_WEB = "1";
	public static final String DEVICETYPE_IPHONE = "2";
	public static final String DEVICETYPE_ANDROID = "3";
	public static final String DEVICETYPE_IPAD = "4";
	public static final String DEVICETYPE_ANDROID_PAD = "5";
	
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserToken() {
		return userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	
}
