/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.CTemperature;

/**
 * 体温表DAO接口
 * @author hxw
 * @version 2018-04-03
 */
@MyBatisDao
public interface CTemperatureDao extends CrudDao<CTemperature> {
	
	List<CTemperature> findListByOfficeId(CTemperature cTemperature);
	
}