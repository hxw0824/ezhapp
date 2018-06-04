/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.DoubleChatMessage;
import com.thinkgem.jeesite.modules.sys.dao.DoubleChatMessageDao;

/**
 * sssService
 * @author sss
 * @version 2018-05-04
 */
@Service
@Transactional(readOnly = true)
public class DoubleChatMessageService extends CrudService<DoubleChatMessageDao, DoubleChatMessage> {

	public DoubleChatMessage get(String id) {
		return super.get(id);
	}
	
	public List<DoubleChatMessage> findList(DoubleChatMessage doubleChatMessage) {
		return super.findList(doubleChatMessage);
	}
	
	public Page<DoubleChatMessage> findPage(Page<DoubleChatMessage> page, DoubleChatMessage doubleChatMessage) {
		return super.findPage(page, doubleChatMessage);
	}
	
	@Transactional(readOnly = false)
	public void save(DoubleChatMessage doubleChatMessage) {
		super.save(doubleChatMessage);
	}
	
	@Transactional(readOnly = false)
	public void delete(DoubleChatMessage doubleChatMessage) {
		super.delete(doubleChatMessage);
	}
	
}