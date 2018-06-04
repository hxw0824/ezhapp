/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.basis.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.zlwh.yzh.admin.modules.basis.entity.Feedback;
import com.zlwh.yzh.admin.modules.basis.dao.FeedbackDao;

/**
 * 单表生成Service
 * @author liufei
 * @version 2016-05-26
 */
@Service
@Transactional(readOnly = true)
public class FeedbackService extends CrudService<FeedbackDao, Feedback> {

	public Feedback get(String id) {
		return super.get(id);
	}
	
	public List<Feedback> findList(Feedback feedback) {
		return super.findList(feedback);
	}
	
	public Page<Feedback> findPage(Page<Feedback> page, Feedback feedback) {
		return super.findPage(page, feedback);
	}
	
	@Transactional(readOnly = false)
	public void save(Feedback feedback) {
		super.save(feedback);
	}
	
	@Transactional(readOnly = false)
	public void delete(Feedback feedback) {
		super.delete(feedback);
	}
	
}