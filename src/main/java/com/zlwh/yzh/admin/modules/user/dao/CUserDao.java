/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.user.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.zlwh.yzh.admin.modules.user.entity.CUser;

/**
 * 单表生成DAO接口
 * @author liufei
 * @version 2016-05-26
 */
@MyBatisDao
public interface CUserDao extends CrudDao<CUser> {

	int getInterestCount(String userId);

	List<CUser> findListByOfficeId(CUser cUser);
}