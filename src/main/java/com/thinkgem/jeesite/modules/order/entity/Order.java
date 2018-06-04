/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.entity;

import org.hibernate.validator.constraints.Length;

import com.alipay.api.domain.Product;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.zlwh.yzh.admin.modules.user.entity.CUser;

/**
 * 订单及订单对应商品Entity
 * @author hcq
 * @version 2018-01-22
 */
public class Order extends DataEntity<Order> {
	
	private static final long serialVersionUID = 1L;
	private String orderNumber;		// 订单号，支付时生成
	private String orderTitle;		// 订单名称，支付时生成
	private String tradeno;		// 支付宝交易编号，支付成功时生成
	private String psncode;		// 商品编码
	private String userid;		// 下单人
	private String periodId;		// 班级
	private String salePrice;		// 成交价格
	private String receiptamount;		// 实际收到的款数
	private String buyerpayamount;		// 买家实际支付的金额
	private String sellerid;		// 卖家商户号
	private String isPay;		// 订单状态 1：已支付 0：未支付
	private String payTime;		// 完成支付的时间
	private String expireTime;		// 完成支付的时间
	private String timeoutexpress;		// 允许完成支付的时间
	private String status;		// 订单状态 1：正常 0 ：禁止操作 -1：删除
	private String payType;		// 支付方式	ALIPAY：支付宝 WXPAY：微信
	private com.thinkgem.jeesite.modules.products.entity.Products product;		// 订单状态 1：正常 0 ：禁止操作 -1：删除
	private CUser cuser;		// 订单状态 1：正常 0 ：禁止操作 -1：删除
	
	public Order() {
		super();
	}

	public Order(String id){
		super(id);
	}

	@Length(min=0, max=50, message="订单号，支付时生成长度必须介于 0 和 50 之间")
	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public String getOrderTitle() {
		return orderTitle;
	}

	public void setOrderTitle(String orderTitle) {
		this.orderTitle = orderTitle;
	}

	public String getTradeno() {
		return tradeno;
	}

	public void setTradeno(String tradeno) {
		this.tradeno = tradeno;
	}



	@Length(min=0, max=64, message="商品编码长度必须介于 0 和 64 之间")
	public String getPsncode() {
		return psncode;
	}

	public void setPsncode(String psncode) {
		this.psncode = psncode;
	}
	
	@Length(min=0, max=10, message="下单人长度必须介于 0 和 10 之间")
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
	
	@Length(min=0, max=4, message="订单状态 1：已支付 0：未支付长度必须介于 0 和 4 之间")
	public String getIsPay() {
		return isPay;
	}

	public void setIsPay(String isPay) {
		this.isPay = isPay;
	}
	
	@Length(min=0, max=10, message="完成支付的时间长度必须介于 0 和 10 之间")
	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	
	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	@Length(min=0, max=4, message="订单状态 1：正常 0 ：禁止操作 -1：删除长度必须介于 0 和 4 之间")
	public String getStatus() {
		return status;
	}

	public String getTimeoutexpress() {
		return timeoutexpress;
	}

	public void setTimeoutexpress(String timeoutexpress) {
		this.timeoutexpress = timeoutexpress;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReceiptamount() {
		return receiptamount;
	}

	public void setReceiptamount(String receiptamount) {
		this.receiptamount = receiptamount;
	}

	public String getBuyerpayamount() {
		return buyerpayamount;
	}

	public void setBuyerpayamount(String buyerpayamount) {
		this.buyerpayamount = buyerpayamount;
	}

	public String getSellerid() {
		return sellerid;
	}

	public void setSellerid(String sellerid) {
		this.sellerid = sellerid;
	}


	public com.thinkgem.jeesite.modules.products.entity.Products getProduct() {
		return product;
	}

	public void setProduct(com.thinkgem.jeesite.modules.products.entity.Products product) {
		this.product = product;
	}

	public CUser getCuser() {
		return cuser;
	}

	public void setCuser(CUser cuser) {
		this.cuser = cuser;
	}

	public String getPeriodId() {
		return periodId;
	}

	public void setPeriodId(String periodId) {
		this.periodId = periodId;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
}