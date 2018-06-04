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
public class Vip extends DataEntity<Vip> {
	
	private static final long serialVersionUID = 1L;
	private String period;		// 时长
	private String unit;		// 0:天1:周2:月3:季度4:年5:永久
	private String title;		// 标题
	private String content;		// 内容
	private String oldPrice;		// 原价
	private String realPrice;		// 现价
	private String imageId;		// 图片ID
	private String imageUrl;		// 图片url
	private String status;		// 状态
	
	public Vip() {
		super();
	}

	public Vip(String id){
		super(id);
	}

	@Length(min=0, max=10, message="时长长度必须介于 0 和 10 之间")
	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
	
	@Length(min=0, max=1, message="0:天1:周2:月3:季度4:年5:永久长度必须介于 0 和 1 之间")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Length(min=0, max=255, message="标题长度必须介于 0 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=255, message="内容长度必须介于 0 和 255 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=15, message="原价长度必须介于 0 和 15 之间")
	public String getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(String oldPrice) {
		this.oldPrice = oldPrice;
	}
	
	@Length(min=0, max=15, message="现价长度必须介于 0 和 15 之间")
	public String getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(String realPrice) {
		this.realPrice = realPrice;
	}
	
	@Length(min=0, max=64, message="图片ID长度必须介于 0 和 64 之间")
	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	
	@Length(min=0, max=500, message="图片url长度必须介于 0 和 500 之间")
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	@Length(min=0, max=1, message="状态长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}