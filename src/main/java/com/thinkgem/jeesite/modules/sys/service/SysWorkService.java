/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.CClass;
import com.thinkgem.jeesite.modules.sys.entity.SysWork;
import com.thinkgem.jeesite.modules.sys.dao.CClassDao;
import com.thinkgem.jeesite.modules.sys.dao.SysWorkDao;

/**
 * 班级表Service
 * @author hxw
 * @version 2018-03-31
 */
@Service
@Transactional(readOnly = true)
public class SysWorkService extends CrudService<SysWorkDao, SysWork> {

	public SysWork get(String id) {
		return super.get(id);
	}
	
	public List<SysWork> findList(SysWork cls) {
		return super.findList(cls);
	}
	
	public Page<SysWork> findPage(Page<SysWork> page, SysWork cls) {
		return super.findPage(page, cls);
	}
	
	@Transactional(readOnly = false)
	public void save(SysWork cls) {
		super.save(cls);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysWork cls) {
		super.delete(cls);
	}
	
	public List<SysWork> getByOfficeId(SysWork sysWork){
		return dao.getByOfficeId(sysWork);
	}
}