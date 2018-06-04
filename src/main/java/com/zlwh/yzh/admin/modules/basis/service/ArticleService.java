/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.basis.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.zlwh.yzh.admin.modules.basis.entity.Article;
import com.zlwh.yzh.admin.modules.basis.dao.ArticleDao;

/**
 * 单表生成Service
 * @author liufei
 * @version 2016-05-26
 */
@Service
@Transactional(readOnly = true)
public class ArticleService extends CrudService<ArticleDao, Article> {

	public Article get(String id) {
		Article article = new Article(id);
		return super.get(article);
	}
	
	public List<Article> findList(Article article) {
		return super.findList(article);
	}
	
	public Page<Article> findPage(Page<Article> page, Article article) {
		return super.findPage(page, article);
	}
	
	@Transactional(readOnly = false)
	public void save(Article article) {
		super.save(article);
	}
	
	@Transactional(readOnly = false)
	public void delete(Article article) {
		super.delete(article);
	}
	
}