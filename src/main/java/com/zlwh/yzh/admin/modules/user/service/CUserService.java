/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.zlwh.yzh.admin.modules.user.dao.CUserDao;
import com.zlwh.yzh.admin.modules.user.entity.CUser;
import com.zlwh.yzh.admin.modules.user.entity.CUserCollection;
import com.zlwh.yzh.api.domain.CollectionDomain;

/**
 * 单表生成Service
 * @author liufei
 * @version 2016-05-26
 */
@Service
@Transactional(readOnly = true)
public class CUserService extends CrudService<CUserDao, CUser> {
	
	@Autowired
	CUserCollectionService userCollectionService;

	public CUser get(String id) {
		return super.get(new CUser(id));
	}
	
	public List<CUser> findList(CUser cUser) {
		return super.findList(cUser);
	}
	
	public Page<CUser> findPage(Page<CUser> page, CUser cUser) {
		return super.findPage(page, cUser);
	}
	
	@Transactional(readOnly = false)
	public void save(CUser cUser) {
		super.save(cUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(CUser cUser) {
		super.delete(cUser);
	}
	@Transactional(readOnly = false)
	public int collection(CollectionDomain domain) {
		CUserCollection collectionBean = new CUserCollection();
		collectionBean.setUserId(domain.getUserId());
		collectionBean.setCourseId(domain.getCourseId());
		collectionBean.setHref(domain.getHref());
		List<CUserCollection> list = userCollectionService.findList(collectionBean);
		if (list.size() > 0) {
			return 1;
		}
		userCollectionService.save(collectionBean);
		return 0;
	}
	@Transactional(readOnly = false)
	public int isCollection(String userid,String resCode) {
		CUserCollection collectionBean = new CUserCollection();
		collectionBean.setUserId(userid);
		collectionBean.setCourseId(resCode);
		List<CUserCollection> list = userCollectionService.findList(collectionBean);
		if (list.size() > 0) {
			return 1;
		}
		userCollectionService.save(collectionBean);
		return 0;
	}

	public int getInterestCount(String userId) {
		return dao.getInterestCount(userId);
	}
	
	public List<CUser> findListByOfficeId(CUser cUser){
		return dao.findListByOfficeId(cUser);
	}
}