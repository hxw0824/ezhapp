package com.zlwh.yzh.admin.modules.weixin;

public class PayAttach {
	private String orderId;	//商户订单号
	private String classId;	//班级
	private String psncode;	//商品编码
	
	public PayAttach(String orderId, String classId, String psncode) {
		this.orderId = orderId;
		this.classId = classId;
		this.psncode = psncode;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getPsncode() {
		return psncode;
	}
	public void setPsncode(String psncode) {
		this.psncode = psncode;
	}
}
