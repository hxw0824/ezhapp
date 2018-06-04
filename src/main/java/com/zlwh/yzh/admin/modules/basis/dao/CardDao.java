/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.basis.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.zlwh.yzh.admin.modules.basis.entity.Card;

/**
 * 单表生成DAO接口
 * @author liufei
 * @version 2016-05-26
 */
@MyBatisDao
public interface CardDao extends CrudDao<Card> {
	/**
	 * 
	*<pre>
	*<b>说明:</b> 批量生成预配卡
	*<b>日期:</b> 2016年7月20日 下午1:10:47
	 */
	public int insertCardbyBatch(List<Card> cards);
}