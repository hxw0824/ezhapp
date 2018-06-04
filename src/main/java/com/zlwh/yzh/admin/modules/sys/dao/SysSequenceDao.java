/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.zlwh.yzh.admin.modules.sys.entity.SysSequence;

/**
 * 单表生成DAO接口
 * @author ThinkGem
 * @version 2016-06-07
 */
@MyBatisDao
public interface SysSequenceDao extends CrudDao<SysSequence> {

	String getNextVal(SysSequence sysSequence);
	
}