/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.book.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.zlwh.yzh.admin.modules.book.entity.ResourceItem;

/**
 * 资源所属分类 5大领域/资源中心DAO接口
 * @author hxw
 * @version 2016-04-26
 */
@MyBatisDao
public interface ResourceItemDao extends CrudDao<ResourceItem> {

	/**
	 * 获取第一个数据用于展示
	 * @return
	 */
	ResourceItem getForTemplate();
	
	/**
	 * 根据三级编码和班次查找
	 * @param sysResourceItem
	 * @return
	 */
	List<ResourceItem> findByItem3CodeANDClassId(ResourceItem resourceItem);
	
	/**
	 * 根据二级编码和班次查找
	 * @param sysResourceItem
	 * @return
	 */
	List<ResourceItem> findByItem2CodeANDClassId(ResourceItem resourceItem);

//	List<ResourceItem> getResourceItemByItemIdByIndex(@Param("periodId")String periodId,@Param("itemId")String itemId,@Param("userId")String userId,@Param("index")String index);
//
//	List<ResourceItem> getResourceItemByItemId(String classid, String itemId);
//	
//	
//	List<ResourceItem> getSearchList(@Param("title")String title, @Param("userId")String userId, @Param("pageNo")int pageNo, @Param("pageSize")int pageSize);
//	
//	ResourceItem getSingleRes(@Param("id")String id, @Param("userId")String userId,@Param("periodId")String periodId);
	
	List<ResourceItem> getResourceByAny(@Param("userId")String userId,
			@Param("id")String id,
			@Param("periodId")String periodId,
			@Param("itemId")String itemId,
			@Param("index")String index,
			@Param("title")String title,
			@Param("pageNo")Integer pageNo,
			@Param("pageSize")Integer pageSize);
	Integer getSearchListCount(@Param("title")String title);
	
	Integer getResourceItemCount(@Param("periodId")String periodId,@Param("itemId")String itemId);
	
}