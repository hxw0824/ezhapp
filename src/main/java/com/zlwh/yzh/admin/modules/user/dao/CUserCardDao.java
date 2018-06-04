/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.zlwh.yzh.admin.modules.user.entity.CUserCard;

/**
 * 单表生成DAO接口
 * @author liufei
 * @version 2016-05-26
 */
@MyBatisDao
public interface CUserCardDao extends CrudDao<CUserCard> {

	/**
	 * 
	*<pre>
	*<b>说明:</b> 获取授权卡使用次数
	*<b>日期:</b> 2016年6月29日 上午9:41:56
	 */
	public int getCardCount(String cardId);
	/**
	 * 
	*<pre>
	*<b>说明:</b> 查询用户是否使用过授权卡
	*<b>日期:</b> 2016年7月7日 下午3:37:09
	 */
	public int selectUserCard(CUserCard cUserCard);
}