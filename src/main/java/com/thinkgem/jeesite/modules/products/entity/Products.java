/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.products.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 商品_对应赎买的vip类型Entity
 * @author hcq
 * @version 2018-01-22
 */
public class Products extends DataEntity<Products> {
	
	private static final long serialVersionUID = 1L;
	private String psncode;		// 商品编码（唯一）
	private String days;		// 商品编码（唯一）
	private String salePrice;		// 销售价格
	private String originalPrice;		// 原价
	private String pname;		// 商品名称
	private String status;		// 上下架 1：上架 0：下
	
	public Products() {
		super();
	}

	public Products(String id){
		super(id);
	}

	@Length(min=1, max=32, message="商品编码（唯一）长度必须介于 1 和 32 之间")
	public String getPsncode() {
		return psncode;
	}

	public void setPsncode(String psncode) {
		this.psncode = psncode;
	}
	
	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	@Length(min=1, max=7, message="销售价格长度必须介于 1 和 7 之间")
	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
	
	@Length(min=1, max=7, message="原价长度必须介于 1 和 7 之间")
	public String getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}
	
	@Length(min=1, max=32, message="商品名称长度必须介于 1 和 32 之间")
	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}
	
	@Length(min=1, max=2, message="上下架 1：上架 0：下长度必须介于 1 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}