/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.basis.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 单表生成Entity
 * @author liufei
 * @version 2016-05-26
 */
public class Telsms extends DataEntity<Telsms> {
	
	private static final long serialVersionUID = 1L;
	private String type; //0：注册 1：找回密码
	private String phone;		// phone
	private String code;		// code
	private String userToken;		// user_token
	private String random;		// random
	
	public static final String TYPE_REGISTER = "0";
	public static final String TYPE_FORGET_PASSWORD = "1";
	
	public Telsms() {
		super();
	}

	public Telsms(String id){
		super(id);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Length(min=0, max=15, message="phone长度必须介于 0 和 15 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=10, message="code长度必须介于 0 和 10 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=40, message="user_token长度必须介于 0 和 40 之间")
	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	
	@Length(min=0, max=40, message="random长度必须介于 0 和 40 之间")
	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}
	
}