/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.basis.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.zlwh.yzh.admin.modules.basis.entity.CardBatch;

/**
 * 授权卡批次DAO接口
 * @author liufei
 * @version 2016-07-20
 */
@MyBatisDao
public interface CardBatchDao extends CrudDao<CardBatch> {
	/**
	 * 
	*<pre>
	*<b>说明:</b> 获取当前最大批次号
	*<b>日期:</b> 2016年7月20日 上午11:39:34
	 */
	public Long getMaxBatchCodeByPeriod(String periodId);
}