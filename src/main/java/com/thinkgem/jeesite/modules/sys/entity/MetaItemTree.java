/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.TreeEntity;

/**
 * 栏目分类表Entity
 * @author hxw
 * @version 2016-04-25
 */
public class MetaItemTree extends TreeEntity<MetaItemTree> {
	
	private static final long serialVersionUID = 1L;
	private MetaItemTree parent;		// 父级编号
	private String parentIds;		// 所有父级编号
	private String name;		// 名称
	private String code;		// 编码
	private String grade;		// 等级
	private String sonName;		// 名称
	private String sonCode;		// 编码
	public String getSonName() {
		return sonName;
	}

	public void setSonName(String sonName) {
		this.sonName = sonName;
	}

	public String getSonCode() {
		return sonCode;
	}

	public void setSonCode(String sonCode) {
		this.sonCode = sonCode;
	}

	public MetaItemTree() {
		super();
	}

	public MetaItemTree(String id){
		super(id);
	}

	@JsonBackReference
	@NotNull(message="父级编号不能为空")
	public MetaItemTree getParent() {
		return parent;
	}

	public void setParent(MetaItemTree parent) {
		this.parent = parent;
	}
	
	@Length(min=1, max=2000, message="所有父级编号长度必须介于 1 和 2000 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@Length(min=1, max=100, message="名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Length(min=1, max=4, message="等级长度必须介于 1 和 4 之间")
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}