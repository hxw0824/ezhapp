/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.SysMonitor;

/**
 * 监控表DAO接口
 * @author hxw
 * @version 2018-04-02
 */
@MyBatisDao
public interface SysMonitorDao extends CrudDao<SysMonitor> {
	List<SysMonitor> findListByOffice(SysMonitor sysMonitor);
}