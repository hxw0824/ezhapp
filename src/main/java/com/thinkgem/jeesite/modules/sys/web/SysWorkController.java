/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.CClass;
import com.thinkgem.jeesite.modules.sys.entity.SysWork;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.CClassService;
import com.thinkgem.jeesite.modules.sys.service.SysWorkService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 班级表Controller
 * @author hxw
 * @version 2018-03-31
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysWork")
public class SysWorkController extends BaseController {

	@Autowired
	private SysWorkService sysWorkService;
	
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public SysWork get(@RequestParam(required=false) String id) {
		SysWork entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysWorkService.get(id);
		}
		if (entity == null){
			entity = new SysWork();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sysWork:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysWork sysWork, HttpServletRequest request, HttpServletResponse response, Model model) {
		sysWork.setOfficeId(UserUtils.getUser().getOffice().getId());
		model.addAttribute("list", sysWorkService.getByOfficeId(sysWork));
		
		model.addAttribute("sysWork", sysWork);
		return "modules/sys/sysWorkList";
	}

	@RequiresPermissions("sys:sysWork:view")
	@RequestMapping(value = "form")
	public String form(SysWork sysWork, Model model) {
		model.addAttribute("sysWork", sysWork);
		return "modules/sys/sysWorkForm";
	}

	@RequiresPermissions("sys:sysWork:edit")
	@RequestMapping(value = "save")
	public String save(SysWork sysWork, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysWork)){
			return form(sysWork, model);
		}
		User user = UserUtils.getUser();
		if(user != null && user.getOffice() != null && !"".equals(user.getOffice().getId())){
			sysWork.setOfficeId(user.getOffice().getId());
		}
		sysWorkService.save(sysWork);
		addMessage(redirectAttributes, "保存打卡机成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysWork/?repage";
	}
	
	@RequiresPermissions("sys:sysWork:edit")
	@RequestMapping(value = "delete")
	public String delete(SysWork sysWork, RedirectAttributes redirectAttributes) {
		sysWorkService.delete(sysWork);
		addMessage(redirectAttributes, "删除打卡机成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysWork/?repage";
	}
}