package com.zlwh.yzh.api.domain;

import com.thinkgem.jeesite.common.persistence.Page;

public class BookDetailDomain {
	private String userId;
	private String userToken;
	private String monval;//月份
	private String recommendAge;
	private String sortType;// 0:默认 1：最热
	private String pageSize;
	private String pageNo;
	private Page<Object> page;
	
	private String periodId;
	
	private  String queryInfo;
	
	public String getQueryInfo() {
		return queryInfo;
	}
	public String getPeriodId() {
		return periodId;
	}
	public void setPeriodId(String periodId) {
		this.periodId = periodId;
	}
	public void setQueryInfo(String queryInfo) {
		this.queryInfo = queryInfo;
	}
	public String getUserToken() {
		return userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public String getMonval() {
		return monval;
	}
	public void setMonval(String monval) {
		this.monval = monval;
	}
	public String getRecommendAge() {
		return recommendAge;
	}
	public void setRecommendAge(String recommendAge) {
		this.recommendAge = recommendAge;
	}
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	public Page<Object> getPage() {
		return page;
	}
	public void setPage(Page<Object> page) {
		this.page = page;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
