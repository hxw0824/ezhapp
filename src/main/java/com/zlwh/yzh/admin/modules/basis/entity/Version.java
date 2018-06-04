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
public class Version extends DataEntity<Version> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 类型
	private String systemType;		// 系统类型
	private String resId;		// 文件ID
	private String versionNo;		// 版本号
	private String content;		// 描述
	
	public Version() {
		super();
	}

	public Version(String id){
		super(id);
	}

	@Length(min=0, max=1, message="类型长度必须介于 0 和 1 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=1, message="系统类型长度必须介于 0 和 1 之间")
	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}
	
	@Length(min=0, max=64, message="文件ID长度必须介于 0 和 64 之间")
	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}
	
	@Length(min=0, max=20, message="版本号长度必须介于 0 和 20 之间")
	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	
	@Length(min=0, max=500, message="描述长度必须介于 0 和 500 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}