/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.CBaby;
import com.thinkgem.jeesite.modules.sys.dao.CBabyDao;

/**
 * sssService
 * @author sss
 * @version 2018-05-04
 */
@Service
@Transactional(readOnly = true)
public class CBabyService extends CrudService<CBabyDao, CBaby> {

	public CBaby get(String id) {
		return super.get(id);
	}
	
	public List<CBaby> findList(CBaby cBaby) {
		return super.findList(cBaby);
	}
	
	public Page<CBaby> findPage(Page<CBaby> page, CBaby cBaby) {
		return super.findPage(page, cBaby);
	}
	
	@Transactional(readOnly = false)
	public void save(CBaby cBaby) {
		super.save(cBaby);
	}
	
	@Transactional(readOnly = false)
	public void delete(CBaby cBaby) {
		super.delete(cBaby);
	}
	
}