/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.zlwh.yzh.admin.modules.user.entity.CUserCollection;
import com.zlwh.yzh.admin.modules.book.entity.Course;
import com.zlwh.yzh.admin.modules.user.dao.CUserCollectionDao;

/**
 * 单表生成Service
 * @author liufei
 * @version 2016-05-26
 */
@Service
@Transactional(readOnly = true)
public class CUserCollectionService extends CrudService<CUserCollectionDao, CUserCollection> {

	public CUserCollection get(String id) {
		return super.get(id);
	}
	
	public List<CUserCollection> findList(CUserCollection cUserCollection) {
		return super.findList(cUserCollection);
	}
	
	public Page<CUserCollection> findPage(Page<CUserCollection> page, CUserCollection cUserCollection) {
		return super.findPage(page, cUserCollection);
	}
	
	@Transactional(readOnly = false)
	public void save(CUserCollection cUserCollection) {
		super.save(cUserCollection);
	}
	
	@Transactional(readOnly = false)
	public void delete(CUserCollection cUserCollection) {
		super.delete(cUserCollection);
	}
	/**
	 * 
	*<pre>
	*<b>说明:</b> 获取我的收藏信息
	*<b>日期:</b> 2016年7月7日 上午10:46:40
	 */
	public List<Course> getUserCollectionList(CUserCollection collectionBean){
		return dao.getUserCollectionList(collectionBean);
	}
	
	public Integer getCollectionCount(String userId){
		return dao.getCollectionCount(userId);
	}
	
}