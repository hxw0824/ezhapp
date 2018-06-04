/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.user.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 单表生成Entity
 * @author liufei
 * @version 2016-05-30
 */
public class CSession extends DataEntity<CSession> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	private String sessionId;		// session_id
	private String deviceType;		// 0:iphone 1:ipad 2:android 3:android pad
	private String loginIp;
	private Date loginTime;		// login_time
	private Date logoutTime;		// logout_time
	private String status;		// 0:登录1:注销2:踢出3:超时
	
	public static final String STATUS_LOGIN = "0";
	public static final String STATUS_LOGOUT = "1";
	public static final String STATUS_KICKOUT = "2";
	public static final String STATUS_EXPIRE = "3";
	
	public CSession() {
		super();
	}

	public CSession(String id){
		super(id);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@Length(min=0, max=64, message="session_id长度必须介于 0 和 64 之间")
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	@Length(min=0, max=1, message="0:iphone1:ipad2:android长度必须介于 0 和 1 之间")
	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}
	
	@Length(min=0, max=1, message="0:登录1:注销2:踢出3:超时长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}