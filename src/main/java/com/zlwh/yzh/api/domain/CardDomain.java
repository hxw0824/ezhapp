/**   
* @Title: CardDomain.java    
* @Package com.zlwh.yzh.api.domain    
* @Description: TODO(用一句话描述该文件做什么)    
* @author 刘斐   
* @date 2016年6月29日 上午9:24:49    
* @version V1.0   
*/
package com.zlwh.yzh.api.domain;

/**   
 * @ClassName: CardDomain    
 * @Description: TODO(这里用一句话描述这个类的作用)    
 * @author 刘斐   
 * @date 2016年6月29日 上午9:24:49    
 *         
 */
public class CardDomain {

	private String userToken;
	private String pwd;//授权卡密码
	private String periodId;//学段
	private String cardId;
	
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getUserToken() {
		return userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getPeriodId() {
		return periodId;
	}
	public void setPeriodId(String periodId) {
		this.periodId = periodId;
	}
	
	
	
}
