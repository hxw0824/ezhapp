/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.fm.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.zlwh.yzh.admin.modules.fm.entity.FmShow;
import com.zlwh.yzh.admin.modules.fm.dao.FmShowDao;
import com.zlwh.yzh.admin.modules.user.entity.CEvaluation;
import com.zlwh.yzh.api.domain.SearchDomain;

/**
 * 家家秀Service
 * @author h
 * @version 2018-01-10
 */
@Service
@Transactional(readOnly = true)
public class FmShowService extends CrudService<FmShowDao, FmShow> {

	public FmShow get(String id,String userId) {
		return dao.get(id,userId);
	}
	
	public List<FmShow> findList(FmShow fmShow) {
		return super.findList(fmShow);
	}
	
	public Page<FmShow> findPage(Page<FmShow> page, FmShow fmShow) {
		return super.findPage(page, fmShow);
	}
	
	@Transactional(readOnly = false)
	public void save(FmShow fmShow) {
		super.save(fmShow);
	}
	
	@Transactional(readOnly = false)
	public void delete(FmShow fmShow) {
		super.delete(fmShow);
	}
	
	@Transactional(readOnly = false)
	public void changeAudit(FmShow fmShow) {
		dao.changeAudit(fmShow);
	}

	public List<FmShow> getByIndex() {
		// TODO Auto-generated method stub
		return dao.getByIndex();
	}
	/**
	 * 获取家家秀首页数据
	 * @return
	 */
	public List<FmShow> getHomeShowIndexList() {
		return dao.getHomeShowIndexList();
	}
	
	/**
	 * 获取家家秀三级目录
	 * @param resCode
	 * @param pageNo	页码
	 * @param pageSize	页数
	 * @return
	 */
	public List<FmShow> getHomeShowListByResCode(String resCode,int pageNo,int pageSize) {
		return dao.getHomeShowListByResCode(resCode,pageNo,pageSize);
	}
	public Integer getHomeShowCountByResCode(String resCode) {
		return dao.getHomeShowCountByResCode(resCode);
	}
	/**
	 * 获取所有上传资源的用户
	 * @return
	 */
	public List<FmShow> getAllUser() {
		return dao.getAllUser();
	}
	
	/**
	 * 审核结果
	 * @return
	 */
	public List<FmShow> getAuditList(SearchDomain domain) {
		Integer pageNo = (domain.getOffset() - 1) * domain.getLimit();
		domain.setOffset(pageNo);
		return dao.getAuditList(domain);
	}
	public Integer getAuditListCount(SearchDomain domain) {
		return dao.getAuditListCount(domain);
	}

	/**
	 * 大家都在看
	 * @return
	 */
	public List<FmShow> getRecentHot() {
		return dao.getRecentHot();
	}
	
	/**
	 * 增加浏览数
	 * @param 
	 */
	@Transactional(readOnly = false)
	public void addClick(String id) {
		dao.addClick(id);
	}
}