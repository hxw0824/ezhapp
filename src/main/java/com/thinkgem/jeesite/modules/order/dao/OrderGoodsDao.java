/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.order.entity.OrderGoods;

/**
 * 订单详情DAO接口
 * @author hcq
 * @version 2018-01-22
 */
@MyBatisDao
public interface OrderGoodsDao extends CrudDao<OrderGoods> {
	
}