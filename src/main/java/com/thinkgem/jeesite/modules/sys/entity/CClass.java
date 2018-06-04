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
public class CClass extends DataEntity<CClass> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 班级名称
	private String officeId;		// 学校id
	private String officeName;		// 学校名称
	private String period;		// 学段
	private String year;		// 入学日期 如：2018
	private String teacherId;		// 教师id
	private String teacherName;		// 教师名字
	private String monitorId;		// 监控id
	private String monitorName;		// 监控名称
	
	public CClass() {
		super();
	}

	public CClass(String id){
		super(id);
	}

	public String getMonitorId() {
		return monitorId;
	}

	public void setMonitorId(String monitorId) {
		this.monitorId = monitorId;
	}

	public String getMonitorName() {
		return monitorName;
	}

	public void setMonitorName(String monitorName) {
		this.monitorName = monitorName;
	}

	@Length(min=0, max=64, message="班级名称长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=10, message="学段长度必须介于 0 和 10 之间")
	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
	
	@Length(min=0, max=10, message="入学日期 如：2018长度必须介于 0 和 10 之间")
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	@Length(min=0, max=64, message="教师id长度必须介于 0 和 64 之间")
	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	
}