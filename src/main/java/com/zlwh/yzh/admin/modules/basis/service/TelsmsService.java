/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.basis.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.zlwh.yzh.admin.modules.basis.entity.Telsms;
import com.zlwh.yzh.admin.modules.basis.dao.TelsmsDao;

/**
 * 单表生成Service
 * @author liufei
 * @version 2016-05-26
 */
@Service
@Transactional(readOnly = true)
public class TelsmsService extends CrudService<TelsmsDao, Telsms> {

	public Telsms get(String id) {
		return super.get(id);
	}
	
	public List<Telsms> findList(Telsms telsms) {
		return super.findList(telsms);
	}
	
	public Page<Telsms> findPage(Page<Telsms> page, Telsms telsms) {
		return super.findPage(page, telsms);
	}
	
	@Transactional(readOnly = false)
	public void save(Telsms telsms) {
		super.save(telsms);
	}
	
	@Transactional(readOnly = false)
	public void delete(Telsms telsms) {
		super.delete(telsms);
	}
	
}