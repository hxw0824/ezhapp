/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.book.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.zlwh.yzh.admin.modules.book.entity.BookShelf;
import com.zlwh.yzh.admin.modules.book.entity.Month;
import com.zlwh.yzh.api.domain.BookDetailDomain;

/**
 * 单表生成DAO接口
 * @author liufei
 * @version 2016-05-26
 */
@MyBatisDao
public interface MonthDao extends CrudDao<Month> {
	/**
	 * 
	*<pre>
	*<b>说明:</b> 获取图书列表
	*<b>日期:</b> 2016年7月7日 下午3:05:13
	 */
	public List<Map<String,Object>> selectMonthList(Month month);
	public List<Map<String,Object>> selectMonthList2(Month month);
	
	public List<BookShelf> getBookShelfList();
	
}