/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.basis.web;

import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.zlwh.yzh.admin.modules.basis.entity.Article;
import com.zlwh.yzh.admin.modules.basis.service.ArticleService;


/**
 * 版权声明Controller
 * @author yuanjifeng
 * @version 2016-06-27
 */
@Controller
@RequestMapping(value = "${adminPath}/basis/copyRight")
public class CopyRightController extends BaseController {

	@Autowired
	private ArticleService articleService;
	
	@ModelAttribute
	public Article get(@RequestParam(required=false) String id) {
		Article entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = articleService.get(id);
		}
		if (entity == null){
			entity = new Article();
		}
		return entity;
	}

	@RequiresPermissions("basis:copyRight:edit")
	@RequestMapping(value = "form")
	public String form(Article article, Model model) {
		article.setType("2");
		List<Article> articleList = articleService.findList(article);
		if (articleList != null && articleList.size() > 0) {
			model.addAttribute("article", articleList.get(0));
		}else{
			model.addAttribute("article", new Article());
		}
		return "modules/basis/copyRight";
	}

	@RequiresPermissions("basis:copyRight:edit")
	@RequestMapping(value = "save")
	public String save(Article article, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, article)){
			return form(article, model);
		}
		article.setContent(StringEscapeUtils.unescapeHtml4(article.getContent()));
		articleService.save(article);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/basis/copyRight/form?repage";
	}

}