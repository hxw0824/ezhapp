/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.basis.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.zlwh.yzh.admin.modules.basis.dao.CardBatchDao;
import com.zlwh.yzh.admin.modules.basis.entity.CardBatch;

/**
 * 授权卡批次Service
 * @author liufei
 * @version 2016-07-20
 */
@Service
@Transactional(readOnly = true)
public class CardBatchService extends CrudService<CardBatchDao, CardBatch> {

	public CardBatch get(String id) {
		return super.get(id);
	}
	
	public List<CardBatch> findList(CardBatch CardBatch) {
		return super.findList(CardBatch);
	}
	
	public Page<CardBatch> findPage(Page<CardBatch> page, CardBatch CardBatch) {
		return super.findPage(page, CardBatch);
	}
	
	@Transactional(readOnly = false)
	public void save(CardBatch CardBatch) {
		super.save(CardBatch);
	}
	
	@Transactional(readOnly = false)
	public void delete(CardBatch CardBatch) {
		super.delete(CardBatch);
	}

	

}