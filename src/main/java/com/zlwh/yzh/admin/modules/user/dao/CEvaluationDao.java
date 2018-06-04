/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.zlwh.yzh.admin.modules.user.entity.CEvaluation;

/**
 * 资源评价DAO接口
 * @author hxw
 * @version 2017-04-12
 */
@MyBatisDao
public interface CEvaluationDao extends CrudDao<CEvaluation> {
	
	/**
	 * 查询用户资源是否评价
	 * @param se
	 * @return
	 */
	CEvaluation getByEval(CEvaluation se);
	
	CEvaluation getByResCodeAndUid(CEvaluation cEvaluation);

	Integer getStarByResAndUser(String resCode, String userid);
}