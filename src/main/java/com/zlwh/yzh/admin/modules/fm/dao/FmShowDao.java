/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.fm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.zlwh.yzh.admin.modules.fm.entity.FmShow;
import com.zlwh.yzh.admin.modules.user.entity.CEvaluation;
import com.zlwh.yzh.api.domain.SearchDomain;

/**
 * 家家秀DAO接口
 * @author h
 * @version 2018-01-10
 */
@MyBatisDao
public interface FmShowDao extends CrudDao<FmShow> {

	FmShow get(@Param("id")String id,@Param("userId")String userId);
	void changeAudit(FmShow fmShow);
	List<FmShow> getByIndex();
	List<FmShow> getAllUser();
	List<FmShow> getHomeShowIndexList();
	Integer getHomeShowCountByResCode(@Param("resCode")String resCode);
	List<FmShow> getHomeShowListByResCode(@Param("resCode")String resCode,@Param("pageNo")int pageNo,@Param("pageSize")int pageSize);
	
	//我的上传
	List<FmShow> getAuditList(SearchDomain domain);
	Integer getAuditListCount(SearchDomain domain);
	
	//大家都在看
	List<FmShow> getRecentHot();
	
	void addClick(@Param("id")String id);
}