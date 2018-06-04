/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.book.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.zlwh.yzh.admin.modules.basis.entity.Resource;
import com.zlwh.yzh.admin.modules.basis.entity.SysResource;

/**
 * 资源所属分类 5/资源中心Entity
 * @author hxw
 * @version 2016-04-26
 */
public class ResourceItem extends DataEntity<ResourceItem> {
	
	private static final long serialVersionUID = 1L;
	private String resCode;		// 关联资源编码
	private String classId;		// 班次编号
	private String className;		// 班次名称
	private String item1Code;		// 一级分类编号 关联meta_item_tree id
	private String item1Name;		// 一级分类名称 5大领域/资源中心
	private String item2Code;		// 二级分类编号
	private String item2Name;		// 二级分类名称
	private String item3Code;		// 三级分类编号
	private String item3Name;		// 三级分类名称
	private String sort;		// 排序
	private String clicknum;		// 排序
	private String star;		// 排序
	private String isStar;		// 是否评星  0:否 1：是
	private String isCollect;		// 是否收藏  0:否 1：是
	private SysResource sysresource;	//资源详情
	
	
	public String getStar() {
		return star;
	}

	public void setStar(String star) {
		this.star = star;
	}

	public String getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(String isCollect) {
		this.isCollect = isCollect;
	}

	
	public ResourceItem() {
		super();
	}

	public ResourceItem(String id){
		super(id);
	}

	@Length(min=1, max=32, message="关联资源编码长度必须介于 1 和 32 之间")
	@ExcelField(title = "编码", align = 2, sort = 2)
	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	
	@Length(min=0, max=32, message="班次编号长度必须介于 0 和 32 之间")
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
	
	@Length(min=0, max=32, message="班次名称长度必须介于 0 和 32 之间")
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	@Length(min=0, max=64, message="一级分类编号 关联meta_item_tree id长度必须介于 0 和 64 之间")
	public String getItem1Code() {
		return item1Code;
	}

	public void setItem1Code(String item1Code) {
		this.item1Code = item1Code;
	}
	
	@Length(min=0, max=100, message="一级分类名称 5大领域/资源中心长度必须介于 0 和 100 之间")
	public String getItem1Name() {
		return item1Name;
	}

	public void setItem1Name(String item1Name) {
		this.item1Name = item1Name;
	}
	
	@Length(min=0, max=64, message="二级分类编号长度必须介于 0 和 64 之间")
	public String getItem2Code() {
		return item2Code;
	}

	public void setItem2Code(String item2Code) {
		this.item2Code = item2Code;
	}
	
	@Length(min=0, max=100, message="二级分类名称长度必须介于 0 和 100 之间")
	public String getItem2Name() {
		return item2Name;
	}

	public void setItem2Name(String item2Name) {
		this.item2Name = item2Name;
	}
	
	@Length(min=0, max=64, message="三级分类编号长度必须介于 0 和 64 之间")
	public String getItem3Code() {
		return item3Code;
	}

	public void setItem3Code(String item3Code) {
		this.item3Code = item3Code;
	}
	
	@Length(min=0, max=100, message="三级分类名称长度必须介于 0 和 100 之间")
	public String getItem3Name() {
		return item3Name;
	}

	public void setItem3Name(String item3Name) {
		this.item3Name = item3Name;
	}
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public SysResource getSysresource() {
		return sysresource;
	}

	public void setSysresource(SysResource sysresource) {
		this.sysresource = sysresource;
	}

	public String getClicknum() {
		return clicknum;
	}

	public void setClicknum(String clicknum) {
		this.clicknum = clicknum;
	}

	public String getIsStar() {
		return isStar;
	}

	public void setIsStar(String isStar) {
		this.isStar = isStar;
	}


	
}