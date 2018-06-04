/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.MetaItemTree;

/**
 * 资源分类表DAO接口
 * @author hxw
 * @version 2016-04-25
 */
@MyBatisDao
public interface MetaItemTreeDao extends TreeDao<MetaItemTree> {
	
	void inser(MetaItemTree entity);
	 
	MetaItemTree getByCode(String code);
	
	/**
	* 根据级别查找
	* @param metaItemTree
	* @return
	*/
	List<MetaItemTree> findByGrade(String parebtId);
	  
	/**
	* 根据父级ID查找
	* @param metaItemTree
	* @return
	*/
	List<MetaItemTree> findByParentId(String parebtId);
	
	/**
	 * 查找子集
	 * @param metaItemTree
	 * @return
	 */
	List<MetaItemTree> findChild(MetaItemTree metaItemTree);

	List<MetaItemTree> findChildByIndex(String pid);
	
	
}