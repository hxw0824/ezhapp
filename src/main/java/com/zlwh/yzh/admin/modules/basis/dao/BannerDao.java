/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.basis.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.zlwh.yzh.admin.modules.basis.entity.Banner;

/**
 * 单表生成DAO接口
 * @author liufei
 * @version 2016-05-26
 */
@MyBatisDao
public interface BannerDao extends CrudDao<Banner> {
	
}