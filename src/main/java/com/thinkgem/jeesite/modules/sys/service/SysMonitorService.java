/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.SysMonitor;
import com.thinkgem.jeesite.modules.sys.dao.SysMonitorDao;

/**
 * 监控表Service
 * @author hxw
 * @version 2018-04-02
 */
@Service
@Transactional(readOnly = true)
public class SysMonitorService extends CrudService<SysMonitorDao, SysMonitor> {

	public SysMonitor get(String id) {
		return super.get(id);
	}
	
	public List<SysMonitor> findList(SysMonitor sysMonitor) {
		return super.findList(sysMonitor);
	}
	
	public Page<SysMonitor> findPage(Page<SysMonitor> page, SysMonitor sysMonitor) {
		return super.findPage(page, sysMonitor);
	}
	
	@Transactional(readOnly = false)
	public void save(SysMonitor sysMonitor) {
		super.save(sysMonitor);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysMonitor sysMonitor) {
		super.delete(sysMonitor);
	}
	
	public List<SysMonitor> findListByOffice(SysMonitor sysMonitor){
		return dao.findListByOffice(sysMonitor);
	}
}