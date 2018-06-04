/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.zlwh.yzh.admin.modules.user.entity.CUserCollection;
import com.zlwh.yzh.admin.modules.user.entity.CUserRecord;
import com.zlwh.yzh.admin.modules.book.entity.Course;
import com.zlwh.yzh.admin.modules.user.dao.CUserRecordDao;

/**
 * 单表生成Service
 * @author liufei
 * @version 2016-05-26
 */
@Service
@Transactional(readOnly = true)
public class CUserRecordService extends CrudService<CUserRecordDao, CUserRecord> {


	public CUserRecord get(String id) {
		return super.get(id);
	}
	
	
	/**
	 * 
	*<pre>
	*<b>说明:</b> 获得我的学习记录列表
	*<b>日期:</b> 2016年6月3日 下午2:10:34
	 */
	public List<Course> getRecordList(CUserRecord cUserRecord){
		return dao.getRecordList(cUserRecord);
	}
	public Integer getRecordListCount(CUserRecord cUserRecord){
		return dao.getRecordListCount(cUserRecord);
	}
	
	public List<CUserRecord> findList(CUserRecord cUserRecord) {
		return super.findList(cUserRecord);
	}
	
	public Page<CUserRecord> findPage(Page<CUserRecord> page, CUserRecord cUserRecord) {
		return super.findPage(page, cUserRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(CUserRecord cUserRecord) {
		super.save(cUserRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(CUserRecord cUserRecord) {
		super.delete(cUserRecord);
	}
	/**
	 * 
	*<pre>
	*<b>说明:</b> 查询今天是否有学习记录此课程
	*<b>日期:</b> 2016年6月3日 下午1:38:49
	 */
	public List<String> checkTodayRecord(CUserRecord cUserRecord){
		return dao.checkTodayRecord(cUserRecord);
	}
	/**
	 * 
	*<pre>清空学习记录
	*<b>说明:</b> 
	*<b>日期:</b> 2016年7月7日 上午11:10:18
	 */
	@Transactional(readOnly=false)
	public void deleteAllRecord(CUserRecord cUserRecord){
		dao.deleteAllRecord(cUserRecord);
	}


	public List findList(CUserCollection collectionBean) {
		// TODO Auto-generated method stub
		return dao.findList(collectionBean);
	}
}