/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.CClass;
import com.thinkgem.jeesite.modules.sys.dao.CClassDao;

/**
 * 班级表Service
 * @author hxw
 * @version 2018-03-31
 */
@Service
@Transactional(readOnly = true)
public class CClassService extends CrudService<CClassDao, CClass> {

	public CClass get(String id) {
		return super.get(id);
	}
	
	public List<CClass> findList(CClass cls) {
		return super.findList(cls);
	}
	
	public Page<CClass> findPage(Page<CClass> page, CClass cls) {
		return super.findPage(page, cls);
	}
	
	@Transactional(readOnly = false)
	public void save(CClass cls) {
		super.save(cls);
	}
	
	@Transactional(readOnly = false)
	public void delete(CClass cls) {
		super.delete(cls);
	}
	
	/**
	 * 查询幼儿园下的班级
	 */
	public List<CClass> getByOfficeId(CClass cclass) {
		return dao.getByOfficeId(cclass);
	}
	
	public List<CClass> getForTree(String officeId,String isAll) {
		return dao.getForTree(officeId,isAll);
	}
	
	
	public CClass getByTeacherId(String teacherId) {
		return dao.getByTeacherId(teacherId);
	}
}