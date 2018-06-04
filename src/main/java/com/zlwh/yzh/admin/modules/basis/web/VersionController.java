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
import com.zlwh.yzh.admin.modules.basis.entity.Version;
import com.zlwh.yzh.admin.modules.basis.service.VersionService;

/**
 * 单表生成Controller
 * @author liufei
 * @version 2016-05-26
 */
@Controller
@RequestMapping(value = "${adminPath}/basis/version")
public class VersionController extends BaseController {

	@Autowired
	private VersionService versionService;
	
	@ModelAttribute
	public Version get(@RequestParam(required=false) String id) {
		Version entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = versionService.get(id);
		}
		if (entity == null){
			entity = new Version();
		}
		return entity;
	}
	
	@RequiresPermissions("basis:version:view")
	@RequestMapping(value = {"list", ""})
	public String list(Version version, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Version> page = versionService.findPage(new Page<Version>(request, response), version); 
		model.addAttribute("page", page);
		return "modules/basis/versionList";
	}

	@RequiresPermissions("basis:version:view")
	@RequestMapping(value = "form")
	public String form(Version version, Model model) {
		model.addAttribute("version", version);
		return "modules/basis/versionForm";
	}

	@RequiresPermissions("basis:version:edit")
	@RequestMapping(value = "save")
	public String save(Version version, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, version)){
			return form(version, model);
		}
		versionService.save(version);
		addMessage(redirectAttributes, "保存单表成功");
		return "redirect:"+Global.getAdminPath()+"/basis/version/?repage";
	}
	
	@RequiresPermissions("basis:version:edit")
	@RequestMapping(value = "delete")
	public String delete(Version version, RedirectAttributes redirectAttributes) {
		versionService.delete(version);
		addMessage(redirectAttributes, "删除单表成功");
		return "redirect:"+Global.getAdminPath()+"/basis/version/?repage";
	}

}