/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.book.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;


public class BookShelf  extends DataEntity<BookShelf>{
	
	private static final long serialVersionUID = 1L;
	private String shelfId;	//书架ID
	private String name;	// 书架名称
	
	public String getShelfId() {
		return shelfId;
	}
	public void setShelfId(String shelfId) {
		this.shelfId = shelfId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}