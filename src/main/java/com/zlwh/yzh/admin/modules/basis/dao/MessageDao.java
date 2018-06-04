/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.basis.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.zlwh.yzh.admin.modules.basis.entity.Message;

/**
 * 单表生成DAO接口
 * @author liufei
 * @version 2016-05-26
 */
@MyBatisDao
public interface MessageDao extends CrudDao<Message> {
	/**
	 * 
	*<pre>
	*<b>说明:</b> 
	*<b>日期:</b> 2016年6月3日 下午2:59:37
	 */
	public List<Map<String, Object>> getMessageList(Message message);
}