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
import com.zlwh.yzh.admin.modules.basis.entity.Vip;
import com.zlwh.yzh.admin.modules.basis.service.VipService;

/**
 * 单表生成Controller
 * @author liufei
 * @version 2016-05-26
 */
@Controller
@RequestMapping(value = "${adminPath}/basis/vip")
public class VipController extends BaseController {

	@Autowired
	private VipService vipService;
	
	@ModelAttribute
	public Vip get(@RequestParam(required=false) String id) {
		Vip entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = vipService.get(id);
		}
		if (entity == null){
			entity = new Vip();
		}
		return entity;
	}
	
	@RequiresPermissions("basis:vip:view")
	@RequestMapping(value = {"list", ""})
	public String list(Vip vip, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Vip> page = vipService.findPage(new Page<Vip>(request, response), vip); 
		model.addAttribute("page", page);
		return "modules/basis/vipList";
	}

	@RequiresPermissions("basis:vip:view")
	@RequestMapping(value = "form")
	public String form(Vip vip, Model model) {
		model.addAttribute("vip", vip);
		return "modules/basis/vipForm";
	}

	@RequiresPermissions("basis:vip:edit")
	@RequestMapping(value = "save")
	public String save(Vip vip, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, vip)){
			return form(vip, model);
		}
		vipService.save(vip);
		addMessage(redirectAttributes, "保存单表成功");
		return "redirect:"+Global.getAdminPath()+"/basis/vip/?repage";
	}
	
	@RequiresPermissions("basis:vip:edit")
	@RequestMapping(value = "delete")
	public String delete(Vip vip, RedirectAttributes redirectAttributes) {
		vipService.delete(vip);
		addMessage(redirectAttributes, "删除单表成功");
		return "redirect:"+Global.getAdminPath()+"/basis/vip/?repage";
	}

}