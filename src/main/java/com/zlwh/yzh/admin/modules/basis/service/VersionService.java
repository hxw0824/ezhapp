/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.basis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.zlwh.yzh.admin.modules.basis.entity.Version;
import com.zlwh.yzh.admin.modules.basis.dao.VersionDao;

/**
 * 单表生成Service
 * @author liufei
 * @version 2016-05-26
 */
@Service
@Transactional(readOnly = true)
public class VersionService extends CrudService<VersionDao, Version> {


	
	
	/**
	 * 
	*<pre>
	*<b>说明:</b> 获取最新版本信息
	*<b>日期:</b> 2016年6月4日 上午10:17:47
	 */
	public Map<String, String> getVertion(Version version){
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap = dao.getVertion(version);
		if(resultMap==null){
			resultMap = new HashMap<String, String>();
			resultMap.put("isNeedUpdate", "N");
			return resultMap;
		}
		String versionFromPhone= version.getVersionNo();
		String versionFromDB = resultMap.get("versionNo");
		 int result = versionFromPhone.compareTo(versionFromDB);
		 if(result>=0){//不更新
			 resultMap.put("isNeedUpdate", "N");
		 }else {
			 resultMap.put("isNeedUpdate", "Y");
		}
		return resultMap;
	}
	public Version get(String id) {
		return super.get(id);
	}
	
	public List<Version> findList(Version version) {
		return super.findList(version);
	}
	
	public Page<Version> findPage(Page<Version> page, Version version) {
		return super.findPage(page, version);
	}
	
	@Transactional(readOnly = false)
	public void save(Version version) {
		super.save(version);
	}
	
	@Transactional(readOnly = false)
	public void delete(Version version) {
		super.delete(version);
	}
	
}