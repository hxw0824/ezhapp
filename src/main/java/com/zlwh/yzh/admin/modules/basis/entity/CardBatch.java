/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.basis.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 授权卡批次Entity
 * @author liufei
 * @version 2016-07-20
 */
public class CardBatch extends DataEntity<CardBatch> {
	
	private static final long serialVersionUID = 1L;
	private String batchName;		// 批次名
	private String batchCode;		// 批次号
	private String amount;		// 授权卡数量
	private String periodId;  //学段
	public CardBatch() {
		super();
	}

	public CardBatch(String id){
		super(id);
	}

	public String getPeriodId() {
		return periodId;
	}

	public void setPeriodId(String periodId) {
		this.periodId = periodId;
	}

	@Length(min=0, max=200, message="批次名长度必须介于 0 和 200 之间")
	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	
	@Length(min=0, max=10, message="批次号长度必须介于 0 和 10 之间")
	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}
	
	@Length(min=0, max=10, message="授权卡数量长度必须介于 0 和 10 之间")
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
}