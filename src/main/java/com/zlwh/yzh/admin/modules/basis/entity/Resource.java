/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.basis.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 单表生成Entity
 * @author liufei
 * @version 2016-05-31
 */
public class Resource extends DataEntity<Resource> {
	
	private static final long serialVersionUID = 1L;
	private String fileType;		// file_type
	private String orgName;		// org_name
	private String name;		// name
	private String path;		// path
	private String url;		// url
	
	public Resource() {
		super();
	}

	public Resource(String id){
		super(id);
	}

	@Length(min=0, max=32, message="file_type长度必须介于 0 和 32 之间")
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	@Length(min=0, max=500, message="org_name长度必须介于 0 和 500 之间")
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	@Length(min=0, max=255, message="name长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=1000, message="path长度必须介于 0 和 1000 之间")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	@Length(min=0, max=1000, message="url长度必须介于 0 和 1000 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}