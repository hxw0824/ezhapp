package com.zlwh.yzh.api.domain;

public class RegisterDomain {
	private String userName;
	private String tel;
	private String password;
	private String code;
	private String deviceType;
	private String imageUrl;
	
	public static final String DEVICETYPE_IPHONE = "0";
	public static final String DEVICETYPE_IPAD = "1";
	public static final String DEVICETYPE_ANDROID = "2";
	public static final String DEVICETYPE_ANDROID_PAD = "3";
	
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
}
