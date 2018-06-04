/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import com.thinkgem.jeesite.modules.sys.entity.Office;
import org.hibernate.validator.constraints.Length;
import com.thinkgem.jeesite.modules.sys.entity.User;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * sssEntity
 * @author sss
 * @version 2018-05-04
 */
public class CBaby extends DataEntity<CBaby> {
	
	private static final long serialVersionUID = 1L;
	private String officeId;		// office_id
	private String classId;		// class_id
	private String userId;		// user_id
	private String text;		// text
	private String imgId;		// img_id
	private String userName;
	private String officeName;
	private String className;
	
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

	public CBaby() {
		super();
	}

	public CBaby(String id){
		super(id);
	}

	@Length(min=0, max=64, message="class_id长度必须介于 0 和 64 之间")
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
	
	@Length(min=0, max=2000, message="text长度必须介于 0 和 2000 之间")
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@Length(min=0, max=10000, message="img_id长度必须介于 0 和 10000 之间")
	public String getImgId() {
		return imgId;
	}

	public void setImgId(String imgId) {
		this.imgId = imgId;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	
}