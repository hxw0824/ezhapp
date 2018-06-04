/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.products.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.products.entity.Products;

/**
 * 商品_对应赎买的vip类型DAO接口
 * @author hcq
 * @version 2018-01-22
 */
@MyBatisDao
public interface ProductsDao extends CrudDao<Products> {

	Products getProductsBySncode(String psncode);
	
}