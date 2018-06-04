/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 班级表Entity
 * @author hxw
 * @version 2018-03-31
 */
public class SysWork extends DataEntity<SysWork> {
	
	private static final long serialVersionUID = 1L;
	private String deviceId;		// 学校id
	private String officeId;		// 学校名称
	private String images;		// 学段
	private String language;		// 入学日期 如：2018
	private String position;		// 教师id
	
	public SysWork() {
		super();
	}

	public SysWork(String id){
		super(id);
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	
}