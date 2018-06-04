/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.basis.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.zlwh.yzh.admin.modules.basis.entity.Resource;

/**
 * 单表生成DAO接口
 * @author liufei
 * @version 2016-05-31
 */
@MyBatisDao
public interface ResourceDao extends CrudDao<Resource> {

	Resource getByRescode(String rescode);
	
}