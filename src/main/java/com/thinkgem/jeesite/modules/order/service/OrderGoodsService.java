/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.order.entity.OrderGoods;
import com.thinkgem.jeesite.modules.order.dao.OrderGoodsDao;

/**
 * 订单详情Service
 * @author hcq
 * @version 2018-01-22
 */
@Service
@Transactional(readOnly = true)
public class OrderGoodsService extends CrudService<OrderGoodsDao, OrderGoods> {

	public OrderGoods get(String id) {
		return super.get(id);
	}
	
	public List<OrderGoods> findList(OrderGoods orderGoods) {
		return super.findList(orderGoods);
	}
	
	public Page<OrderGoods> findPage(Page<OrderGoods> page, OrderGoods orderGoods) {
		return super.findPage(page, orderGoods);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderGoods orderGoods) {
		super.save(orderGoods);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderGoods orderGoods) {
		super.delete(orderGoods);
	}
	
}