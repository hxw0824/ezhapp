/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 订单详情Entity
 * @author hcq
 * @version 2018-01-22
 */
public class OrderGoods extends DataEntity<OrderGoods> {
	
	private static final long serialVersionUID = 1L;
	private String orderId;		// order_id
	private String pSncode;		// p_sncode
	private String pNum;		// 购买数量
	private String pPrice;		// 单个价格
	private String status;		// status
	
	public OrderGoods() {
		super();
	}

	public OrderGoods(String id){
		super(id);
	}

	@Length(min=0, max=10, message="order_id长度必须介于 0 和 10 之间")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Length(min=0, max=32, message="p_sncode长度必须介于 0 和 32 之间")
	public String getPSncode() {
		return pSncode;
	}

	public void setPSncode(String pSncode) {
		this.pSncode = pSncode;
	}
	
	@Length(min=0, max=10, message="购买数量长度必须介于 0 和 10 之间")
	public String getPNum() {
		return pNum;
	}

	public void setPNum(String pNum) {
		this.pNum = pNum;
	}
	
	public String getPPrice() {
		return pPrice;
	}

	public void setPPrice(String pPrice) {
		this.pPrice = pPrice;
	}
	
	@Length(min=0, max=4, message="status长度必须介于 0 和 4 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}