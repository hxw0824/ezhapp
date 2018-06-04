/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.book.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.zlwh.yzh.admin.modules.book.dao.ResourceItemDao;
import com.zlwh.yzh.admin.modules.book.entity.ResourceItem;

/**
 * 资源所属分类 5大领域/资源中心Service
 * @author hxw
 * @version 2016-04-26
 */
@Service
@Transactional(readOnly = true)
public class ResourceItemService extends CrudService<ResourceItemDao, ResourceItem> {

	public ResourceItem getForTemplate(){
		return dao.getForTemplate();
	}
	
	public ResourceItem get(String id) {
		return super.get(id);
	}
	
	public List<ResourceItem> findList(ResourceItem resourceItem) {
		return super.findList(resourceItem);
	}
	
	public Page<ResourceItem> findPage(Page<ResourceItem> page, ResourceItem resourceItem) {
		return super.findPage(page, resourceItem);
	}
	
	@Transactional(readOnly = false)
	public void save(ResourceItem resourceItem) {
		super.save(resourceItem);
	}
	
	@Transactional(readOnly = false)
	public void delete(ResourceItem resourceItem) {
		super.delete(resourceItem);
	}
	
	public List<ResourceItem> findByItem3CodeANDClassId(ResourceItem resourceItem){
		return dao.findByItem3CodeANDClassId(resourceItem);
	}
	
	public List<ResourceItem> findByItem2CodeANDClassId(ResourceItem resourceItem){
		return dao.findByItem2CodeANDClassId(resourceItem);
	}

	
	public Integer getSearchListCount(String title) {
		return dao.getSearchListCount(title);
	}
	
	public Integer getResourceItemCount(String classId,String itemId) {
		return dao.getResourceItemCount(classId,itemId);
	}
	
	/**
	 * 获取资源
	 * 根据id查找：userId,id
	 * 根据班级栏目查找：userId,periodId,itemId(index可选)
	 * 搜索：userId,title,pageNo,pageSize
	 * @param userId	用户id
	 * @param id	根据资源id获取
	 * @param periodId	班级id
	 * @param itemId	栏目id
	 * @param index		是否首页资源
	 * @param title		搜索关键字
	 * @param pageNo	搜索页码
	 * @param pageSize	搜索页数
	 * @return
	 */
	public List<ResourceItem> getResourceByAny(String userId,String id,String periodId,String itemId,String index,String title,Integer pageNo,Integer pageSize){
		return dao.getResourceByAny(userId,id,periodId,itemId,index,title,pageNo,pageSize);
	}
//	
//	/**
//	 * 获取单个资源
//	 * @param id
//	 * @param userId
//	 * @return
//	 */
//	public ResourceItem getSingleRes(String id, String userId,String periodId) {
//		return dao.getSingleRes( id,  userId,periodId);
//	}
//	/**
//	 * 获取搜索列表
//	 * @param classid
//	 * @param itemId
//	 * @return
//	 */
//	public List<ResourceItem> getSearchList(String title, String userId,int pageNo,int pageSize) {
//		return dao.getSearchList(title, userId,pageNo,pageSize);
//	}
//	
//	public List<ResourceItem> getResourceItemByItemIdByIndex(String classid,String itemId,String userId,String index) {
//		// TODO Auto-generated method stub
//		return dao.getResourceItemByItemIdByIndex(classid,itemId,userId,index);
//	}
//
//	public List<ResourceItem> getResourceItemByItemId(String classid, String itemId) {
//		// TODO Auto-generated method stub
//		return dao.getResourceItemByItemId( classid,  itemId);
//	}
	
	
}