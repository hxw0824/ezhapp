/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.basis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.zlwh.yzh.admin.modules.basis.entity.Card;
import com.zlwh.yzh.admin.modules.basis.entity.CardBatch;
import com.zlwh.yzh.admin.modules.basis.dao.CardBatchDao;
import com.zlwh.yzh.admin.modules.basis.dao.CardDao;
import com.zlwh.yzh.admin.modules.user.dao.CUserCardDao;
import com.zlwh.yzh.admin.modules.user.entity.CUserCard;
import com.zlwh.yzh.api.common.ReturnCode;
import com.zlwh.yzh.api.domain.CardDomain;

/**
 * 单表生成Service
 * @author liufei
 * @version 2016-05-26
 */
@Service
@Transactional(readOnly = true)
public class CardService extends CrudService<CardDao, Card> {

	@Autowired
	private CardBatchDao cardBatchDao;
	public Card get(String id) {
		return super.get(id);
	}
	
	public List<Card> findList(Card card) {
		return super.findList(card);
	}
	
	public Page<Card> findPage(Page<Card> page, Card card) {
		return super.findPage(page, card);
	}
	
	@Transactional(readOnly = false)
	public void save(Card card) {
		super.save(card);
	}
	
	@Transactional(readOnly = false)
	public void delete(Card card) {
		super.delete(card);
	}
	/**
	 * 
	*<pre>
	*<b>说明:</b> 批量生成授权卡
	*<b>日期:</b> 2016年7月20日 下午1:23:43
	 */
	@Transactional(readOnly = false)
	public int batchCreateSysCard(CardBatch cardBatch){
		//创建批次号
		Long basecode = cardBatchDao.getMaxBatchCodeByPeriod(cardBatch.getPeriodId());
		if(basecode==null){
			basecode=getFirstBatchCode(cardBatch.getPeriodId());
		}else{
			basecode=basecode+1;
		}
		String batchCode=String.format("%06d", basecode);
		cardBatch.preInsert();
		cardBatch.setBatchCode(batchCode);
		//生成批次信息
		int ret = cardBatchDao.insert(cardBatch);
		if (ret<1) {
			return -1;
		}
		//批量生成授权卡
		int amount = Integer.parseInt(cardBatch.getAmount());
		List<Card> cards = new ArrayList<Card>();
		for(int i=1;i<=amount;i++){
			Card card = new Card();
			String cardNo = batchCode+String.format("%05d", i);
			String pwd = cardNo+String.format("%05d", new Random().nextInt(99999));
			card.preInsert();
			card.setCardNo(cardNo);
			card.setPwd(pwd);
			card.setPeriodId(cardBatch.getPeriodId());
			card.setBatchId(cardBatch.getId());
		
			cards.add(card);
			
		}
		ret = dao.insertCardbyBatch(cards);
		if (ret<1) {
			return -1;
		}
		return ret;
	} 
	public long getFirstBatchCode(String periodId){
		String batchCode = periodId+"00001";
		return Long.parseLong(batchCode);
	}
}