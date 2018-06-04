/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.products.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.products.entity.Products;
import com.thinkgem.jeesite.modules.products.dao.ProductsDao;

/**
 * 商品_对应赎买的vip类型Service
 * @author hcq
 * @version 2018-01-22
 */
@Service
@Transactional(readOnly = true)
public class ProductsService extends CrudService<ProductsDao, Products> {

	public Products get(String id) {
		return super.get(id);
	}
	
	public List<Products> findList(Products products) {
		return super.findList(products);
	}
	
	public Page<Products> findPage(Page<Products> page, Products products) {
		return super.findPage(page, products);
	}
	
	@Transactional(readOnly = false)
	public void save(Products products) {
		super.save(products);
	}
	
	@Transactional(readOnly = false)
	public void delete(Products products) {
		super.delete(products);
	}

	public Products getProductsBySncode(String psncode) {
		// TODO Auto-generated method stub
		return dao.getProductsBySncode(psncode);
	}
	
}