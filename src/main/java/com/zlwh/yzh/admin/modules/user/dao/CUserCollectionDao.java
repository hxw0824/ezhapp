/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.zlwh.yzh.admin.modules.book.entity.Course;
import com.zlwh.yzh.admin.modules.user.entity.CUserCollection;

/**
 * 单表生成DAO接口
 * @author liufei
 * @version 2016-05-26
 */
@MyBatisDao
public interface CUserCollectionDao extends CrudDao<CUserCollection> {

	/**
	 * 
	*<pre>
	*<b>说明:</b> 获取我的收藏信息
	*<b>日期:</b> 2016年7月7日 上午10:46:40
	 */
	public List<Course> getUserCollectionList(CUserCollection collectionBean);
	
	public Integer getCollectionCount(@Param("userId")String userId);
}