/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.user.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.zlwh.yzh.admin.modules.book.entity.Course;
import com.zlwh.yzh.admin.modules.user.entity.CUserCollection;
import com.zlwh.yzh.admin.modules.user.entity.CUserRecord;

/**
 * 单表生成DAO接口
 * @author liufei
 * @version 2016-05-26
 */
@MyBatisDao
public interface CUserRecordDao extends CrudDao<CUserRecord> {
	
	/**
	 * 
	*<pre>
	*<b>说明:</b> 查询今天是否有学习记录
	*<b>日期:</b> 2016年6月3日 下午1:38:49
	 */
	public List<String> checkTodayRecord(CUserRecord cUserRecord);
	/**
	 * 
	*<pre>
	*<b>说明:</b> 获得我的学习记录列表
	*<b>日期:</b> 2016年6月3日 下午2:10:34
	 */
	public List<Course> getRecordList(CUserRecord cUserRecord);
	public Integer getRecordListCount(CUserRecord cUserRecord);
	/**
	 * 
	*<pre>清空学习记录
	*<b>说明:</b> 
	*<b>日期:</b> 2016年7月7日 上午11:10:18
	 */
	public void deleteAllRecord(CUserRecord cUserRecord);
	public List findList(CUserCollection collectionBean);
	
}