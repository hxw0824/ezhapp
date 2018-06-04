package com.zlwh.yzh.admin.modules.sys.entity;

import com.zlwh.yzh.admin.modules.book.entity.Course;
import com.zlwh.yzh.admin.modules.book.entity.Month;
import com.zlwh.yzh.admin.modules.fm.entity.FmShow;

public class IndexEntity {
	private String classid;
	private Course course;
	private Month month;
	private FmShow fmShow;
	public String getClassid() {
		return classid;
	}
	public void setClassid(String classid) {
		this.classid = classid;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public Month getMonth() {
		return month;
	}
	public void setMonth(Month month) {
		this.month = month;
	}
	public FmShow getFmShow() {
		return fmShow;
	}
	public void setFmShow(FmShow fmShow) {
		this.fmShow = fmShow;
	}
	
	

}
