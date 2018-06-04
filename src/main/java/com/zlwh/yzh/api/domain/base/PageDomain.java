package com.zlwh.yzh.api.domain.base;

import com.thinkgem.jeesite.common.config.Global;

public class PageDomain {
	private String pageNo;
	private String pageSize = Global.getConfig("page.pageSize");
	private long pageTime; // 上拉分页查询时可传，防止分页数据错乱
	private long queryTime; // 刷新时可传，获得该时间之后的数据

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public long getPageTime() {
		return pageTime;
	}

	public void setPageTime(long pageTime) {
		this.pageTime = pageTime;
	}

	public long getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(long queryTime) {
		this.queryTime = queryTime;
	}

}
