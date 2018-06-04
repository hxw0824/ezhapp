/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.zlwh.yzh.admin.modules.user.dao.CEvaluationDao;
import com.zlwh.yzh.admin.modules.user.entity.CEvaluation;

/**
 * 资源评价Service
 * @author hxw
 * @version 2017-04-12
 */
@Service
@Transactional(readOnly = true)
public class CEvaluationService extends CrudService<CEvaluationDao, CEvaluation> {

	public CEvaluation get(String id) {
		return super.get(id);
	}
	
	public CEvaluation getByResCodeAndUid(CEvaluation cEvaluation) {
		return dao.getByResCodeAndUid(cEvaluation);
	}
	
	/**
	 * 查询用户资源是否评价
	 * @param se
	 * @return
	 */
	public CEvaluation getByEval(CEvaluation se){
		return dao.getByEval(se);
	}
	
	@Transactional(readOnly = false)
	public void save(CEvaluation sysEvaluation) {
		super.save(sysEvaluation);
	}
	
	@Transactional(readOnly = false)
	public void delete(CEvaluation sysEvaluation) {
		super.delete(sysEvaluation);
	}

	public Integer getStarByResAndUser(String resCode, String userid) {
		// TODO Auto-generated method stub
		return dao.getStarByResAndUser( resCode,  userid);
	}
	
}