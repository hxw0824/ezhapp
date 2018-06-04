/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.CTemperature;
import com.thinkgem.jeesite.modules.sys.dao.CTemperatureDao;

/**
 * 体温表Service
 * @author hxw
 * @version 2018-04-03
 */
@Service
@Transactional(readOnly = true)
public class CTemperatureService extends CrudService<CTemperatureDao, CTemperature> {

	public CTemperature get(String id) {
		return super.get(id);
	}
	
	public List<CTemperature> findList(CTemperature cTemperature) {
		return super.findList(cTemperature);
	}
	
	public Page<CTemperature> findPage(Page<CTemperature> page, CTemperature cTemperature) {
		return super.findPage(page, cTemperature);
	}
	
	@Transactional(readOnly = false)
	public void save(CTemperature cTemperature) {
		super.save(cTemperature);
	}
	
	@Transactional(readOnly = false)
	public void delete(CTemperature cTemperature) {
		super.delete(cTemperature);
	}
	
	public List<CTemperature> findListByOfficeId(CTemperature cTemperature) {
		return dao.findListByOfficeId(cTemperature);
	}
}