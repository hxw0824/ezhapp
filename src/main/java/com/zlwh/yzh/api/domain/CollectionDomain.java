package com.zlwh.yzh.api.domain;

import java.security.KeyStore.PrivateKeyEntry;
import java.util.List;

import com.thinkgem.jeesite.common.config.Global;


public class CollectionDomain {
	private String userToken;
	private String courseId;
	private String href;
	private int limit =Integer.valueOf(Global.getConfig("page.pageSize"));
	private int offset = 1;
	private long queryTime;
	private String userId;
	private List<String> courseList;
	

	public List<String> getCourseList() {
		return courseList;
	}
	public void setCourseList(List<String> courseList) {
		this.courseList = courseList;
	}
	public long getQueryTime() {
		return queryTime;
	}
	public void setQueryTime(long queryTime) {
		this.queryTime = queryTime;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	private String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public String getUserToken() {
		return userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}


}
