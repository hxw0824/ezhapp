/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.zlwh.yzh.admin.modules.sys.entity.SysSequence;
import com.zlwh.yzh.admin.modules.sys.dao.SysSequenceDao;

/**
 * 单表生成Service
 * @author ThinkGem
 * @version 2016-06-07
 */
@Service
@Transactional(readOnly = true)
public class SysSequenceService extends CrudService<SysSequenceDao, SysSequence> {

	public static String KEY_ORDER = "order";

	public SysSequence get(String id) {
		return super.get(id);
	}
	
	public List<SysSequence> findList(SysSequence sysSequence) {
		return super.findList(sysSequence);
	}
	
	public Page<SysSequence> findPage(Page<SysSequence> page, SysSequence sysSequence) {
		return super.findPage(page, sysSequence);
	}
	
	@Transactional(readOnly = false)
	public void save(SysSequence sysSequence) {
		super.save(sysSequence);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysSequence sysSequence) {
		super.delete(sysSequence);
	}

	public String getNextVal(String name) {
		SysSequence sysSequence = new SysSequence();
		sysSequence.setName(name);
		return dao.getNextVal(sysSequence);
	}
	
}