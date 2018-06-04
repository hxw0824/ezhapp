/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.zlwh.yzh.admin.modules.user.entity.CUserCard;
import com.zlwh.yzh.admin.modules.user.dao.CUserCardDao;
import com.zlwh.yzh.api.domain.CardDomain;

/**
 * 单表生成Service
 * @author liufei
 * @version 2016-05-26
 */
@Service
@Transactional(readOnly = true)
public class CUserCardService extends CrudService<CUserCardDao, CUserCard> {

	public CUserCard get(String id) {
		return super.get(id);
	}
	
	public List<CUserCard> findList(CUserCard cUserCard) {
		return super.findList(cUserCard);
	}
	
	public Page<CUserCard> findPage(Page<CUserCard> page, CUserCard cUserCard) {
		return super.findPage(page, cUserCard);
	}
	
	@Transactional(readOnly = false)
	public void save(CUserCard cUserCard) {
		super.save(cUserCard);
	}
	
	@Transactional(readOnly = false)
	public void delete(CUserCard cUserCard) {
		super.delete(cUserCard);
	}
	/**
	 * 
	*<pre>
	*<b>说明:</b> 获取授权卡使用次数
	*<b>日期:</b> 2016年6月29日 上午9:41:56
	 */
	public int getCardCount(String cardId){
		return dao.getCardCount(cardId);
	}
	/**
	 * 
	*<pre>
	*<b>说明:</b> 查询用户是否使用过授权卡
	*<b>日期:</b> 2016年7月7日 下午3:37:09
	 */
	public int selectUserCard(CUserCard cUserCard){
		return dao.selectUserCard(cUserCard);
	}
	
}