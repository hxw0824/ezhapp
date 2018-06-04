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
import com.zlwh.yzh.admin.modules.basis.entity.Telsms;
import com.zlwh.yzh.admin.modules.basis.service.TelsmsService;

/**
 * 单表生成Controller
 * @author liufei
 * @version 2016-05-26
 */
@Controller
@RequestMapping(value = "${adminPath}/basis/telsms")
public class TelsmsController extends BaseController {

	@Autowired
	private TelsmsService telsmsService;
	
	@ModelAttribute
	public Telsms get(@RequestParam(required=false) String id) {
		Telsms entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = telsmsService.get(id);
		}
		if (entity == null){
			entity = new Telsms();
		}
		return entity;
	}
	
	@RequiresPermissions("basis:telsms:view")
	@RequestMapping(value = {"list", ""})
	public String list(Telsms telsms, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Telsms> page = telsmsService.findPage(new Page<Telsms>(request, response), telsms); 
		model.addAttribute("page", page);
		return "modules/basis/telsmsList";
	}

	@RequiresPermissions("basis:telsms:view")
	@RequestMapping(value = "form")
	public String form(Telsms telsms, Model model) {
		model.addAttribute("telsms", telsms);
		return "modules/basis/telsmsForm";
	}

	@RequiresPermissions("basis:telsms:edit")
	@RequestMapping(value = "save")
	public String save(Telsms telsms, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, telsms)){
			return form(telsms, model);
		}
		telsmsService.save(telsms);
		addMessage(redirectAttributes, "保存单表成功");
		return "redirect:"+Global.getAdminPath()+"/basis/telsms/?repage";
	}
	
	@RequiresPermissions("basis:telsms:edit")
	@RequestMapping(value = "delete")
	public String delete(Telsms telsms, RedirectAttributes redirectAttributes) {
		telsmsService.delete(telsms);
		addMessage(redirectAttributes, "删除单表成功");
		return "redirect:"+Global.getAdminPath()+"/basis/telsms/?repage";
	}

}