/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.user.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 单表生成Entity
 * @author liufei
 * @version 2016-05-26
 */
public class CUser extends DataEntity<CUser> {
	
	private static final long serialVersionUID = 1L;
	
	private String officeId;		// 学校id
	private String classId;		// 班级id
	private String userToken;		// user_token
	private String loginName;		// 登录名
	private String deviceType;      // 设备类型
	private String deviceId;		// 设备ID
	private String openId;		// 第三方ID
	private String nickName;		// 昵称
	private String imageId;		// 用户头像ID
	private String imageUrl;		// 用户头像url
	private String sex;		// 默认男性，0.男，1.女
	private String tel;		// 手机号码
	private String pwd;		// 密码
	private String level;		// dengji
	private String status;		// 启用?
	private String loginIp;
	private Date loginTime;
	private String periodId; //学段0-小班 1中班 2大班
	private String userType;
	private String addr;
	private String trueName;
	
	private String oldLoginName;// 原登录名
	private String newPassword;	// 新密码
	
	
	public String getOldLoginName() {
		return oldLoginName;
	}

	public void setOldLoginName(String oldLoginName) {
		this.oldLoginName = oldLoginName;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getPeriodId() {
		return periodId;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public void setPeriodId(String periodId) {
		this.periodId = periodId;
	}



	public static final String USER_TYPE_LOCAL = "0";
	public static final String USER_TYPE_THIRDPARTY = "1";
	
	public static final String STATUS_LOCK = "1";
	public static final String STATUS_UNLOCK = "0";
	
	public CUser() {
		super();
	}

	public CUser(String id){
		super(id);
	}



	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	@Length(min=0, max=64, message="user_token长度必须介于 0 和 64 之间")
	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	
	@Length(min=0, max=40, message="登录名长度必须介于 0 和 40 之间")
	@ExcelField(title="登录名", align=2, sort=10)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	@Length(min=0, max=40, message="设备ID长度必须介于 0 和 40 之间")
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	@Length(min=0, max=64, message="第三方ID长度必须介于 0 和 64 之间")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	

	
	@Length(min=0, max=50, message="昵称长度必须介于 0 和 50 之间")
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	

	
	@Length(min=0, max=64, message="用户头像ID长度必须介于 0 和 64 之间")
	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	
	@Length(min=0, max=255, message="用户头像url长度必须介于 0 和 255 之间")
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
	@Length(min=0, max=20, message="女长度必须介于 0 和 10 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=20, message="手机号码长度必须介于 0 和 20 之间")
	@ExcelField(title="手机号", align=2, sort=20)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	@Length(min=0, max=50, message="密码长度必须介于 0 和 50 之间")
	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	@Length(min=0, max=1, message="启用?长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}