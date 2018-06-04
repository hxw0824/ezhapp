/**   
* @Title: UserRecordDomain.java    
* @Package com.zlwh.yzh.api.domain    
* @Description: TODO(用一句话描述该文件做什么)    
* @author 刘斐   
* @date 2016年6月3日 下午1:53:03    
* @version V1.0   
*/
package com.zlwh.yzh.api.domain;

import com.thinkgem.jeesite.common.config.Global;

/**   
 * @ClassName: UserRecordDomain    
 * @Description: TODO(这里用一句话描述这个类的作用)    
 * @author 刘斐   
 * @date 2016年6月3日 下午1:53:03    
 *         
 */
public class UserRecordDomain {

	private String userToken;
	private String userId;
	
	private int offset = 1;
	
	private int limit = Integer.valueOf(Global.getConfig("page.pageSize"));
	
	private long queryTime;

	public long getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(long queryTime) {
		this.queryTime = queryTime;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	
	
}
