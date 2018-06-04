/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.basis.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.zlwh.yzh.admin.modules.basis.entity.Resource;
import com.zlwh.yzh.admin.modules.basis.dao.ResourceDao;
import com.zlwh.yzh.admin.modules.book.entity.Course;

/**
 * 单表生成Service
 * @author liufei
 * @version 2016-05-31
 */
@Service
@Transactional(readOnly = true)
public class ResourceService extends CrudService<ResourceDao, Resource> {

	public Resource get(String id) {
		return super.get(id);
	}
	
	public List<Resource> findList(Resource resource) {
		return super.findList(resource);
	}
	
	public Page<Resource> findPage(Page<Resource> page, Resource resource) {
		return super.findPage(page, resource);
	}
	
	@Transactional(readOnly = false)
	public void save(Resource resource) {
		super.save(resource);
	}
	
	@Transactional(readOnly = false)
	public void delete(Resource resource) {
		super.delete(resource);
	}

	public Resource getByRescode(String rescode) {
		// TODO Auto-generated method stub
		return dao.getByRescode(rescode);
	}
	
}