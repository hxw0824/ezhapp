/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 考勤表Entity
 * @author hxw
 * @version 2018-04-03
 */
public class CWork extends DataEntity<CWork> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 学生id
	private String classId;		// 班级id
	private String officeId;		// 学校id
	private String macId;		// 学校id
	private Date signTime;		// 打卡时间
	private String picUrl;		// 学生名称
	private String picUrl1;		// 学生名称
	private Double signTemp;		// 学生名称
	private String signMode;		// 学生名称
	
	private String userName;		// 学生名称
	private String className;		// 班级名称
	private Date beginDate;		
	private Date endDate;
	
	public CWork() {
		super();
	}

	public CWork(String id){
		super(id);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getMacId() {
		return macId;
	}

	public void setMacId(String macId) {
		this.macId = macId;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getPicUrl1() {
		return picUrl1;
	}

	public void setPicUrl1(String picUrl1) {
		this.picUrl1 = picUrl1;
	}

	public Double getSignTemp() {
		return signTemp;
	}

	public void setSignTemp(Double signTemp) {
		this.signTemp = signTemp;
	}

	public String getSignMode() {
		return signMode;
	}

	public void setSignMode(String signMode) {
		this.signMode = signMode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
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