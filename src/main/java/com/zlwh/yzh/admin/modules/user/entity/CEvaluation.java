/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.user.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 资源评价Entity
 * @author hxw
 * @version 2017-04-12
 */
public class CEvaluation extends DataEntity<CEvaluation> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	private String resCode;		// 资源ID
	private String score;		// 评价分数 1:非常差 2：差 3：一般 4：满意 5：非常满意
	
	public CEvaluation() {
		super();
	}

	public CEvaluation(String id){
		super(id);
	}

	@NotNull(message="用户ID不能为空")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=1, max=32, message="资源ID长度必须介于 1 和 32 之间")
	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	
	@Length(min=1, max=1, message="评价分数长度必须介于 1 和 1 之间")
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	
}