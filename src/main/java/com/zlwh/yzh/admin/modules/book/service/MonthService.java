/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.book.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.zlwh.yzh.admin.modules.book.dao.MonthDao;
import com.zlwh.yzh.admin.modules.book.entity.BookShelf;
import com.zlwh.yzh.admin.modules.book.entity.Month;
import com.zlwh.yzh.admin.modules.user.entity.CUser;
import com.zlwh.yzh.admin.modules.user.entity.CUserCollection;
import com.zlwh.yzh.admin.modules.user.service.CUserCollectionService;
import com.zlwh.yzh.admin.modules.user.service.CUserService;
import com.zlwh.yzh.api.domain.BookDetailDomain;
import com.zlwh.yzh.api.domain.CollectionDomain;

/**
 * 单表生成Service
 * @author liufei
 * @version 2016-05-26
 */
@Service
@Transactional(readOnly = true)
public class MonthService extends CrudService<MonthDao, Month> {

	@Autowired
	CUserService userService;
	@Autowired
	CUserCollectionService userCollectionService;
	
	public Month get(String id) {
		return super.get(id);
	}
	
	public List<Month> findList(Month book) {
		return super.findList(book);
	}
	
	public Page<Month> findPage(Page<Month> page, Month book) {
		return super.findPage(page, book);
	}
	
	@Transactional(readOnly = false)
	public void save(Month book) {
		super.save(book);
	}
	
	@Transactional(readOnly = false)
	public void delete(Month book) {
		super.delete(book);
	}

	/**
	 * 
	*<pre>
	*<b>说明:</b> 获取图书列表
	*<b>日期:</b> 2016年7月7日 下午3:05:13
	 */
	public List<Map<String,Object>> selectBookList(Month book){
		return dao.selectMonthList(book);
	}
	public List<Map<String,Object>> selectBookList2(Month book){
		return dao.selectMonthList2(book);
	}
	
	
	public List<BookShelf> getBookShelfList(){
		return dao.getBookShelfList();
	}
}