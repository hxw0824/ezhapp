/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.book.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 此类做为月份课程的合集，当做一本书
 * 单表生成Entity
 * @author liufei
 * @version 2016-05-26
 */
public class Month extends DataEntity<Month> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 分类名称
	private String bookshelfName;		// 书架分类名称
	private String content;		// 分类描述
	private String thumbnailUrl;		// 缩略图URL
	private String sort;		// 排序
	private String periodId; //学段
	private String imageUrl;
	private String shelfId;
	private int courseCount;
	private int clicksNum;
	private String monthval;//月份
	
	public Month() {
		super();
	}

	public Month(String id){
		super(id);
	}

	
//	public int getShelfId() {
//		return shelfId;
//	}

	public void setShelfid(String shelfId) {
		this.shelfId = shelfId;
	}

	public String getBookshelfName() {
		return bookshelfName;
	}

	public void setBookshelfName(String bookshelfName) {
		this.bookshelfName = bookshelfName;
	}


	public String getMonthval() {
		return monthval;
	}

	public void setMonthval(String monthval) {
		this.monthval = monthval;
	}

	public int getCourseCount() {
		return courseCount;
	}

	public void setCourseCount(int courseCount) {
		this.courseCount = courseCount;
	}

	public int getClicksNum() {
		return clicksNum;
	}

	public void setClicksNum(int clicksNum) {
		this.clicksNum = clicksNum;
	}

	public String getPeriodId() {
		return periodId;
	}

	public void setPeriodId(String periodId) {
		this.periodId = periodId;
	}

	@Length(min=0, max=255, message="分类名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="分类描述长度必须介于 0 和 255 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Length(min=0, max=500, message="缩略图URL长度必须介于 0 和 500 之间")
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	@Length(min=0, max=5)
	public String getShelfId() {
		return shelfId;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	
	@Length(min=0, max=5, message="排序长度必须介于 0 和 5 之间")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
}