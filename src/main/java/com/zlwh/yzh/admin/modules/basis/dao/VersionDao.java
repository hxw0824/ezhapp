/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.basis.dao;

import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.zlwh.yzh.admin.modules.basis.entity.Version;

/**
 * 单表生成DAO接口
 * @author liufei
 * @version 2016-05-26
 */
@MyBatisDao
public interface VersionDao extends CrudDao<Version> {
	
	/**
	 * 
	*<pre>
	*<b>说明:</b> 获取最新版本信息
	*<b>日期:</b> 2016年6月4日 上午10:17:47
	 */
	public Map<String, String> getVertion(Version version);
	
}