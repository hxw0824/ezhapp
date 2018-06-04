/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.basis.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.zlwh.yzh.admin.modules.basis.entity.Banner;
import com.zlwh.yzh.admin.modules.basis.dao.BannerDao;

/**
 * 单表生成Service
 * @author liufei
 * @version 2016-05-26
 */
@Service
@Transactional(readOnly = true)
public class BannerService extends CrudService<BannerDao, Banner> {

	public Banner get(String id) {
		return super.get(id);
	}
	
	public List<Banner> findList(Banner banner) {
		return super.findList(banner);
	}
	
	public Page<Banner> findPage(Page<Banner> page, Banner banner) {
		return super.findPage(page, banner);
	}
	
	@Transactional(readOnly = false)
	public void save(Banner banner) {
		super.save(banner);
	}
	
	@Transactional(readOnly = false)
	public void delete(Banner banner) {
		super.delete(banner);
	}
	
}