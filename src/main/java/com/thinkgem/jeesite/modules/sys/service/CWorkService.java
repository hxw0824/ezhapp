/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.CWork;
import com.thinkgem.jeesite.modules.sys.dao.CWorkDao;

/**
 * 考勤表Service
 * @author hxw
 * @version 2018-04-03
 */
@Service
@Transactional(readOnly = true)
public class CWorkService extends CrudService<CWorkDao, CWork> {

	public CWork get(String id) {
		return super.get(id);
	}
	
	public List<CWork> findList(CWork cWork) {
		return super.findList(cWork);
	}
	
	public Page<CWork> findPage(Page<CWork> page, CWork cWork) {
		return super.findPage(page, cWork);
	}
	
	@Transactional(readOnly = false)
	public void save(CWork cWork) {
		super.save(cWork);
	}
	
	@Transactional(readOnly = false)
	public void delete(CWork cWork) {
		super.delete(cWork);
	}
	
	public List<CWork> findListByOfficeId(CWork cWork) {
		return dao.findListByOfficeId(cWork);
	}
	
	public String getToDayClockNumByClassId(String classId){
		return dao.getToDayClockNumByClassId(classId);
	}
}