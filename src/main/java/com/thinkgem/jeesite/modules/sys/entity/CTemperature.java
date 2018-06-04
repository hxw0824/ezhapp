/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 体温表Entity
 * @author hxw
 * @version 2018-04-03
 */
public class CTemperature extends DataEntity<CTemperature> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 学生id
	private String userName;		// 学生名称
	private String classId;		// 班级id
	private String className;		// 班级名称
	private String officeId;		// 学校id
	private String temperVal;		// 温度
	private Date beginDate;		
	private Date endDate;
	
	public CTemperature() {
		super();
	}

	public CTemperature(String id){
		super(id);
	}

	@Length(min=0, max=64, message="班级id长度必须介于 0 和 64 之间")
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
	
	public String getTemperVal() {
		return temperVal;
	}

	public void setTemperVal(String temperVal) {
		this.temperVal = temperVal;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}