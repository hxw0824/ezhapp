/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.basis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.zlwh.yzh.admin.modules.basis.entity.SysResource;

/**
 * 资源信息DAO接口
 * @author hxw
 * @version 2016-04-26
 */
@MyBatisDao
public interface SysResourceDao extends CrudDao<SysResource> {
	
	SysResource getByResCode(String resCode);
	
	/**
	 * 获取第一个数据用于展示
	 * @return
	 */
	SysResource getForTemplate();
	
	/**
	 * 根据资源查询所有的教案      关联表
	 * @param zyCode	资源code
	 * @param classId	班次ID
	 * @return
	 */
	List<SysResource> getJAListByZYcodeANDClassId(@Param("zyCode")String zyCode,@Param("classId")String classId);
	
	/**
	 * 根据活动方案查询所有的资源      关联表
	 * @param jaCode 	活动方案code
	 * @param classId	班次ID
	 * @return
	 */
	List<SysResource> getZyListByJAcodeANDClassId(@Param("jaCode")String jaCode,@Param("classId")String classId);
	
	/**
	 * 未登录时资源总表
	 * @param item2Code	二级目录CODE
	 * @param classId	班次ID
	 * @return
	 */
	List<SysResource> findByItem2CodeANDClassId(@Param("item1Code")String item1Code,@Param("item2Code")String item2Code,@Param("classId")String classId,@Param("where")String where,@Param("resType")String resType);
	List<SysResource> findByItem2CodeANDClassIdMusic(@Param("item1Code")String item1Code,@Param("item2Code")String item2Code,@Param("classId")String classId,@Param("where")String where,@Param("resType")String resType);
	
	/**
	 * 未登录时资源总表
	 * @param item3Code	三级目录CODE
	 * @param classId	班次ID
	 * @return
	 */
	List<SysResource> findByItem3CodeANDClassId(@Param("item1Code")String item1Code,@Param("item2Code")String item2Code,@Param("item3Code")String item3Code,@Param("classId")String classId,@Param("where")String where,@Param("resType")String resType);
	List<SysResource> findByItem3CodeANDClassIdMusic(@Param("item1Code")String item1Code,@Param("item2Code")String item2Code,@Param("item3Code")String item3Code,@Param("classId")String classId,@Param("where")String where,@Param("resType")String resType);
	
	List<SysResource> findSpecial(@Param("item1Code")String item1Code,@Param("item2Code")String item2Code,@Param("item3Code")String item3Code,@Param("classId")String classId);
		
	/**
	 * 未登录查询资源 
	 * @param sysResource
	 * @return
	 */
	List<SysResource> findAllSearchResourceNoLogin(@Param("title")String title);
	
	/**
	 * 登录查询资源 
	 * @param sysResource
	 * @return
	 */
	List<SysResource> findAllSearchResourceLogin(@Param("classStr")String classStr,@Param("title")String title);
	
	/**
	 * 根据班次查询资源 
	 * @param classId
	 * @return
	 */
	List<SysResource> findResourceByClassId(@Param("classId")String classId);

	List<SysResource> findSearchResourceLogin(String classStr, String title);

	List<SysResource> findSearchResourceNoLogin(String title);

	void updateClickNum(SysResource resource);

	void addClicksNum(String resCode);

	Integer getClickNumByRescode(String resCode);

	List<SysResource> getRecentHot(@Param("userId")String userId);
}