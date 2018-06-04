/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.order.entity.Order;

/**
 * 订单及订单对应商品DAO接口
 * @author hcq
 * @version 2018-01-22
 */
@MyBatisDao
public interface OrderDao extends CrudDao<Order> {

	Order getByOrderNo(String out_trade_no);
	
	Order getByOrderNoAndUserId(@Param("orderNo")String out_trade_no);
	
}