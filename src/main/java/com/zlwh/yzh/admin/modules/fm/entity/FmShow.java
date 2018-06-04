/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.fm.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.zlwh.yzh.admin.modules.user.entity.CUser;

/**
 * 家家秀Entity
 * @author h
 * @version 2018-01-10
 */
public class FmShow extends DataEntity<FmShow> {
	
	private static final long serialVersionUID = 1L;
	private String resCode;		// name
	private String name;		// name
	private String fileUrl;		// file_url
	private String resType;		// file_url
	private String iconUrl;		// 图标
	private String thumbUrl;		// 背景大图
	private String star;		// star
	private String uid;		// star
	private String auditStatus;		// 资源是否通过审核 0：审核通过 1：未审核 2：审核未通过
	private String auditReason;		// 资源审核未通过理由
	private int clickNum;		// 点击次数
	private String remark;		
	private String isStar; //是否评星 0：未评星 1 已评星		
	private CUser cUser;
	
	private Date beginDate;		// 开始日期
	private Date endDate;		// 结束日期
	
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public FmShow() {
		super();
	}

	public FmShow(String id){
		super(id);
	}

	@Length(min=0, max=64, message="name长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="file_url长度必须介于 0 和 64 之间")
	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
	@Length(min=0, max=64, message="图标长度必须介于 0 和 64 之间")
	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	
	@Length(min=0, max=64, message="背景大图长度必须介于 0 和 64 之间")
	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}
	
	@Length(min=0, max=64, message="star长度必须介于 0 和 64 之间")
	public String getStar() {
		return star;
	}

	public void setStar(String star) {
		this.star = star;
	}

	public CUser getcUser() {
		return cUser;
	}

	public void setcUser(CUser cUser) {
		this.cUser = cUser;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getAuditReason() {
		return auditReason;
	}

	public void setAuditReason(String auditReason) {
		this.auditReason = auditReason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public String getIsStar() {
		return isStar;
	}

	public void setIsStar(String isStar) {
		this.isStar = isStar;
	}

	public int getClickNum() {
		return clickNum;
	}

	public void setClickNum(int clickNum) {
		this.clickNum = clickNum;
	}
	
}