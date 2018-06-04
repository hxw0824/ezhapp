/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.CClass;

/**
 * 班级表DAO接口
 * @author hxw
 * @version 2018-03-31
 */
@MyBatisDao
public interface CClassDao extends CrudDao<CClass> {
	
	public List<CClass> getByOfficeId(CClass cclass);
	
	public List<CClass> getForTree(@Param("officeId")String officeId,@Param("isAll")String isAll);
	
	public CClass getByTeacherId(@Param("teacherId")String teacherId);
	
}