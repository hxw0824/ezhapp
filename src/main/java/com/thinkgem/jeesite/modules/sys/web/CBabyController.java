/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

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
import com.thinkgem.jeesite.modules.sys.entity.CBaby;
import com.thinkgem.jeesite.modules.sys.entity.CClass;
import com.thinkgem.jeesite.modules.sys.service.CBabyService;
import com.thinkgem.jeesite.modules.sys.service.CClassService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * sssController
 * @author sss
 * @version 2018-05-04
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/cBaby")
public class CBabyController extends BaseController {

	@Autowired
	private CBabyService cBabyService;
	
	@Autowired
	private CClassService cClassService;
	
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public CBaby get(@RequestParam(required=false) String id) {
		CBaby entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cBabyService.get(id);
		}
		if (entity == null){
			entity = new CBaby();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:cBaby:view")
	@RequestMapping(value = {"list", ""})
	public String list(CBaby cBaby, HttpServletRequest request, HttpServletResponse response, Model model) {
		cBaby.setOfficeId(UserUtils.getUser().getOffice().getId());
		Page<CBaby> page = cBabyService.findPage(new Page<CBaby>(request, response), cBaby);
		model.addAttribute("page", page);
		
		if(StringUtils.isNotBlank(cBaby.getClassId())){
			cBaby.setClassName(cClassService.get(cBaby.getClassId()).getName());
		}
		if(StringUtils.isNotBlank(cBaby.getUserId())){
			cBaby.setUserName(UserUtils.get(cBaby.getUserId()).getName());
		}
		model.addAttribute("cBaby", cBaby);
		return "modules/sys/cBabyList";
	}

	@RequiresPermissions("sys:cBaby:view")
	@RequestMapping(value = "form")
	public String form(CBaby cBaby, Model model) {
		model.addAttribute("cBaby", cBaby);
		return "modules/sys/cBabyForm";
	}

	@RequiresPermissions("sys:cBaby:edit")
	@RequestMapping(value = "save")
	public String save(CBaby cBaby, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cBaby)){
			return form(cBaby, model);
		}
		cBabyService.save(cBaby);
		addMessage(redirectAttributes, "保存宝贝圈成功");
		return "redirect:"+Global.getAdminPath()+"/sys/cBaby/?repage";
	}
	
	@RequiresPermissions("sys:cBaby:edit")
	@RequestMapping(value = "delete")
	public String delete(CBaby cBaby, RedirectAttributes redirectAttributes) {
		cBabyService.delete(cBaby);
		addMessage(redirectAttributes, "删除宝贝圈成功");
		return "redirect:"+Global.getAdminPath()+"/sys/cBaby/?repage";
	}

}