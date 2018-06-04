package com.zlwh.yzh.api.domain;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.CWork;

public class CWorkDomain extends DataEntity<CWork>{
	private static final long serialVersionUID = 1L;
	private String userId;
	private long clockTime;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public long getClockTime() {
		return clockTime;
	}
	public void setClockTime(long clockTime) {
		this.clockTime = clockTime;
	}
}
