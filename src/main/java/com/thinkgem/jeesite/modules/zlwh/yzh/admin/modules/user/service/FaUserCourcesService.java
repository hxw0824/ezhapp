/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zlwh.yzh.admin.modules.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.zlwh.yzh.admin.modules.user.entity.FaUserCources;
import com.thinkgem.jeesite.modules.zlwh.yzh.admin.modules.user.dao.FaUserCourcesDao;

/**
 * xxxService
 * @author xxx
 * @version 2018-01-06
 */
@Service
@Transactional(readOnly = true)
public class FaUserCourcesService extends CrudService<FaUserCourcesDao, FaUserCources> {

	public FaUserCources get(String id) {
		return super.get(id);
	}
	
	public List<FaUserCources> findList(FaUserCources faUserCources) {
		return super.findList(faUserCources);
	}
	
	public Page<FaUserCources> findPage(Page<FaUserCources> page, FaUserCources faUserCources) {
		return super.findPage(page, faUserCources);
	}
	
	@Transactional(readOnly = false)
	public void save(FaUserCources faUserCources) {
		super.save(faUserCources);
	}
	
	@Transactional(readOnly = false)
	public void delete(FaUserCources faUserCources) {
		super.delete(faUserCources);
	}
	
}