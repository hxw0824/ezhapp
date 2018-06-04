/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.CWork;

/**
 * 考勤表DAO接口
 * @author hxw
 * @version 2018-04-03
 */
@MyBatisDao
public interface CWorkDao extends CrudDao<CWork> {
	
	List<CWork> findListByOfficeId(CWork cWork);
	
	String getToDayClockNumByClassId(@Param("classId")String classId);
}