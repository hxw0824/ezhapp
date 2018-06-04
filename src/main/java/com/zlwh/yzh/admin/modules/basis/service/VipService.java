/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.basis.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.zlwh.yzh.admin.modules.basis.entity.Vip;
import com.zlwh.yzh.admin.modules.basis.dao.VipDao;

/**
 * 单表生成Service
 * @author liufei
 * @version 2016-05-26
 */
@Service
@Transactional(readOnly = true)
public class VipService extends CrudService<VipDao, Vip> {

	public Vip get(String id) {
		return super.get(id);
	}
	
	public List<Vip> findList(Vip vip) {
		return super.findList(vip);
	}
	
	public Page<Vip> findPage(Page<Vip> page, Vip vip) {
		return super.findPage(page, vip);
	}
	
	@Transactional(readOnly = false)
	public void save(Vip vip) {
		super.save(vip);
	}
	
	@Transactional(readOnly = false)
	public void delete(Vip vip) {
		super.delete(vip);
	}
	public List<Vip> findAllList(Vip vip) {
		return dao.findAllList(vip);
	}
}