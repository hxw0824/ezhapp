/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.user.entity;

import java.util.Date;

import com.thinkgem.jeesite.modules.sys.entity.User;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 单表生成Entity
 * @author liufei
 * @version 2016-05-26
 */
public class CUserRecord extends DataEntity<CUserRecord> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	private String courseId;		// 课程ID
	private String periodId;//学段
	private int offset;//学段
	private int limit;//学段
	private long start;//学段
	private long end;//学段
	private Date searchDate;
	
	public CUserRecord() {
		super();
	}

	public CUserRecord(String id){
		super(id);
	}
	
	public String getPeriodId() {
		return periodId;
	}

	public void setPeriodId(String periodId) {
		this.periodId = periodId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}



	@Length(min=0, max=64, message="课程ID长度必须介于 0 和 64 之间")
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public Date getSearchDate() {
		return searchDate;
	}

	public void setSearchDate(Date searchDate) {
		this.searchDate = searchDate;
	}

	
}