/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.basis.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.zlwh.yzh.admin.modules.basis.entity.Banner;
import com.zlwh.yzh.admin.modules.basis.service.BannerService;

/**
 * 单表生成Controller
 * @author liufei
 * @version 2016-05-26
 */
@Controller
@RequestMapping(value = "${adminPath}/basis/banner")
public class BannerController extends BaseController {

	@Autowired
	private BannerService bannerService;
	
	@ModelAttribute
	public Banner get(@RequestParam(required=false) String id) {
		Banner entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bannerService.get(id);
		}
		if (entity == null){
			entity = new Banner();
		}
		return entity;
	}
	
	@RequiresPermissions("basis:banner:view")
	@RequestMapping(value = {"list", ""})
	public String list(Banner banner, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Banner> page = bannerService.findPage(new Page<Banner>(request, response), banner); 
		model.addAttribute("page", page);
		return "modules/basis/bannerList";
	}

	@RequiresPermissions("basis:banner:view")
	@RequestMapping(value = "form")
	public String form(Banner banner, Model model) {
		model.addAttribute("banner", banner);
		return "modules/basis/bannerForm";
	}

	@RequiresPermissions("basis:banner:edit")
	@RequestMapping(value = "save")
	public String save(Banner banner, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, banner)){
			return form(banner, model);
		}
		bannerService.save(banner);
		addMessage(redirectAttributes, "保存单表成功");
		return "redirect:"+Global.getAdminPath()+"/basis/banner/?repage";
	}
	
	@RequiresPermissions("basis:banner:edit")
	@RequestMapping(value = "delete")
	public String delete(Banner banner, RedirectAttributes redirectAttributes) {
		bannerService.delete(banner);
		addMessage(redirectAttributes, "删除单表成功");
		return "redirect:"+Global.getAdminPath()+"/basis/banner/?repage";
	}

}