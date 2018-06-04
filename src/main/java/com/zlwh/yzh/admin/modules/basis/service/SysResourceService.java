/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.basis.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.zlwh.yzh.admin.modules.basis.dao.SysResourceDao;
import com.zlwh.yzh.admin.modules.basis.entity.SysResource;

/**
 * 资源信息Service
 * @author hxw
 * @version 2016-04-26
 */
@Service
@Transactional(readOnly = true)
public class SysResourceService extends CrudService<SysResourceDao, SysResource> {
	private final String RESOURCE_CENTER_MUSIC_CODE = Global.getConfig("RESOURCE_CENTER_MUSIC_CODE");
	
	public SysResource getForTemplate(){
		return dao.getForTemplate();
	}
	
	public SysResource get(String id) {
		return super.get(id);
	}
	
	public List<SysResource> findList(SysResource sysResource) {
		return super.findList(sysResource);
	}
	
	public Page<SysResource> findPage(Page<SysResource> page, SysResource sysResource) {
		return super.findPage(page, sysResource);
	}
	
	@Transactional(readOnly = false)
	public void save(SysResource sysResource) {
		super.save(sysResource);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysResource sysResource) {
		super.delete(sysResource);
	}
	
	
	@Transactional(readOnly = false)
	public void update(SysResource sysResource) {
		dao.update(sysResource);
	}
	
	public SysResource getByResCode(String resCode){
		return dao.getByResCode(resCode);
	}
	
	public List<SysResource> getJAListByZYcodeANDClassId(String zyCode,String classId){
		return dao.getJAListByZYcodeANDClassId(zyCode, classId);
	}
	
	public List<SysResource> getZyListByJAcodeANDClassId(String jaCode,String classId){
		return dao.getZyListByJAcodeANDClassId(jaCode, classId);
	}
	
	public List<SysResource> findByItem2CodeANDClassId(String item1Code,String item2Code,String classId,String plbf){
		String resType = null;
		if(item2Code.equals(RESOURCE_CENTER_MUSIC_CODE)){
			return dao.findByItem2CodeANDClassIdMusic(item1Code,item2Code, classId,plbf,resType);
		}else{
			return dao.findByItem2CodeANDClassId(item1Code,item2Code, classId,plbf,resType);
		}
	}
	
	public List<SysResource> findByItem3CodeANDClassId(String item1Code,String item2Code,String item3Code,String classId,String plbf){
		String resType = null;
		if(item3Code.equals(RESOURCE_CENTER_MUSIC_CODE)){
			return dao.findByItem3CodeANDClassIdMusic(item1Code,item2Code,item3Code, classId,plbf,resType);
		}else{
			return dao.findByItem3CodeANDClassId(item1Code,item2Code,item3Code, classId,plbf,resType);
		}
	}
	
	public List<SysResource> findSpecial(String item1Code,String item2Code,String item3Code,String classId){
		return dao.findSpecial(item1Code,item2Code,item3Code, classId);
	}
	
	public List<SysResource> findAllSearchResourceNoLogin(String title){
		return dao.findAllSearchResourceNoLogin(title);
	}
	
	public List<SysResource> findAllSearchResourceLogin(String classStr,String title){
		return dao.findAllSearchResourceLogin(classStr,title);
	}
	
	public List<SysResource> findResourceByClassId(String classId){
		return dao.findResourceByClassId(classId);
	}

	public List<SysResource> findSearchResourceLogin(String classStr, String title) {
		// TODO Auto-generated method stub
		return dao.findSearchResourceLogin(classStr,title);
	}

	public List<SysResource> findSearchResourceNoLogin(String title) {
		// TODO Auto-generated method stub
		return dao.findSearchResourceNoLogin(title);
	}

	public void updateClickNum(SysResource resource) {
		// TODO Auto-generated method stub
		dao.updateClickNum( resource);
		
	}
	/**
	 * 
	*<pre>
	*<b>说明:</b> 增加视频点击数
	*<b>日期:</b> 2016年7月6日 上午10:29:16
	 */
	@Transactional(readOnly=false)
	public void addClicksNum(String resCode){
		dao.addClicksNum(resCode);
	}

	public Integer getClickNumByRescode(String resCode) {
		// TODO Auto-generated method stub
		return dao.getClickNumByRescode( resCode);
	}

	public List<SysResource> getRecentHot(String userId) {
		// TODO Auto-generated method stub
		return dao.getRecentHot(userId);
	}
}