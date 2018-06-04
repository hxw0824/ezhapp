/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.zlwh.yzh.admin.modules.user.web;

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
import com.thinkgem.jeesite.modules.zlwh.yzh.admin.modules.user.entity.FaUserCources;
import com.thinkgem.jeesite.modules.zlwh.yzh.admin.modules.user.service.FaUserCourcesService;

/**
 * xxxController
 * @author xxx
 * @version 2018-01-06
 */
@Controller
@RequestMapping(value = "${adminPath}/zlwh.yzh.admin.modules.user/faUserCources")
public class FaUserCourcesController extends BaseController {

	@Autowired
	private FaUserCourcesService faUserCourcesService;
	
	@ModelAttribute
	public FaUserCources get(@RequestParam(required=false) String id) {
		FaUserCources entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = faUserCourcesService.get(id);
		}
		if (entity == null){
			entity = new FaUserCources();
		}
		return entity;
	}
	
	@RequiresPermissions("zlwh.yzh.admin.modules.user:faUserCources:view")
	@RequestMapping(value = {"list", ""})
	public String list(FaUserCources faUserCources, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FaUserCources> page = faUserCourcesService.findPage(new Page<FaUserCources>(request, response), faUserCources); 
		model.addAttribute("page", page);
		return "modules/zlwh.yzh.admin.modules.user/faUserCourcesList";
	}

	@RequiresPermissions("zlwh.yzh.admin.modules.user:faUserCources:view")
	@RequestMapping(value = "form")
	public String form(FaUserCources faUserCources, Model model) {
		model.addAttribute("faUserCources", faUserCources);
		return "modules/zlwh.yzh.admin.modules.user/faUserCourcesForm";
	}

	@RequiresPermissions("zlwh.yzh.admin.modules.user:faUserCources:edit")
	@RequestMapping(value = "save")
	public String save(FaUserCources faUserCources, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, faUserCources)){
			return form(faUserCources, model);
		}
		faUserCourcesService.save(faUserCources);
		addMessage(redirectAttributes, "保存xxx成功");
		return "redirect:"+Global.getAdminPath()+"/zlwh.yzh.admin.modules.user/faUserCources/?repage";
	}
	
	@RequiresPermissions("zlwh.yzh.admin.modules.user:faUserCources:edit")
	@RequestMapping(value = "delete")
	public String delete(FaUserCources faUserCources, RedirectAttributes redirectAttributes) {
		faUserCourcesService.delete(faUserCources);
		addMessage(redirectAttributes, "删除xxx成功");
		return "redirect:"+Global.getAdminPath()+"/zlwh.yzh.admin.modules.user/faUserCources/?repage";
	}

}