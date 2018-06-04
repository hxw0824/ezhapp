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
public class Banner extends DataEntity<Banner> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// type
	private String title;		// title
	private String imageId;		// image_id
	private String imageUrl;		// image_url
	private String link;		// link
	private String sort;		// sort
	
	public Banner() {
		super();
	}

	public Banner(String id){
		super(id);
	}

	@Length(min=0, max=1, message="type长度必须介于 0 和 1 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=255, message="title长度必须介于 0 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=64, message="image_id长度必须介于 0 和 64 之间")
	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	
	@Length(min=0, max=500, message="image_url长度必须介于 0 和 500 之间")
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	@Length(min=0, max=500, message="link长度必须介于 0 和 500 之间")
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	@Length(min=0, max=5, message="sort长度必须介于 0 和 5 之间")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
}