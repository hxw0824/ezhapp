package com.zlwh.yzh.api.domain;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.CTemperature;

public class CTemperDomain extends DataEntity<CTemperature>{
	private static final long serialVersionUID = 1L;
	private String userId;
	private String temperVal;		// 温度
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTemperVal() {
		return temperVal;
	}
	public void setTemperVal(String temperVal) {
		this.temperVal = temperVal;
	}
}
