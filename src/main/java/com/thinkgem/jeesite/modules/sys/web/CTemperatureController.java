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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.beust.jcommander.internal.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.CClass;
import com.thinkgem.jeesite.modules.sys.entity.CTemperature;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.CClassService;
import com.thinkgem.jeesite.modules.sys.service.CTemperatureService;
import com.thinkgem.jeesite.modules.sys.utils.RoleIdUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.zlwh.yzh.admin.modules.user.service.CUserService;

/**
 * 体温表Controller
 * @author hxw
 * @version 2018-04-03
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/cTemperature")
public class CTemperatureController extends BaseController {

	@Autowired
	private CTemperatureService cTemperatureService;
	
	@Autowired
	private CUserService cUserService;
	
	@Autowired
	private CClassService cClassService;
	
	@ModelAttribute
	public CTemperature get(@RequestParam(required=false) String id) {
		CTemperature entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cTemperatureService.get(id);
		}
		if (entity == null){
			entity = new CTemperature();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:cTemperature:view")
	@RequestMapping(value = {"list", ""})
	public String list(CTemperature cTemperature, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		CClass cclass = cClassService.getByTeacherId(user.getId());
		Page<CTemperature> page = new Page<CTemperature>(request, response);
		cTemperature.setPage(page);
		cTemperature.setOfficeId(UserUtils.getUser().getOffice().getId());
		if(user.getUserType().equals(RoleIdUtils.teacher)){
			cTemperature.setClassId(cclass != null ? cclass.getId() : "");
		}
		List<CTemperature> list = cTemperatureService.findListByOfficeId(cTemperature);
		page.setList(list);
		if(StringUtils.isNotBlank(cTemperature.getClassId())){
			cTemperature.setClassName(cClassService.get(cTemperature.getClassId()).getName());
		}
		if(StringUtils.isNotBlank(cTemperature.getUserId())){
			cTemperature.setUserName(cUserService.get(cTemperature.getUserId()).getLoginName());
		}
		model.addAttribute("cclass", cclass);
		model.addAttribute("curUser", user);
		model.addAttribute("page", page);
		model.addAttribute("cTemperature", cTemperature);
		return "modules/sys/cTemperatureList";
	}

	@RequiresPermissions("sys:cTemperature:view")
	@RequestMapping(value = "form")
	public String form(CTemperature cTemperature, Model model) {
		model.addAttribute("cTemperature", cTemperature);
		return "modules/sys/cTemperatureForm";
	}

	@RequiresPermissions("sys:cTemperature:edit")
	@RequestMapping(value = "save")
	public String save(CTemperature cTemperature, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cTemperature)){
			return form(cTemperature, model);
		}
		cTemperatureService.save(cTemperature);
		addMessage(redirectAttributes, "保存体温成功");
		return "redirect:"+Global.getAdminPath()+"/sys/cTemperature/?repage";
	}
	
	@RequiresPermissions("sys:cTemperature:edit")
	@RequestMapping(value = "delete")
	public String delete(CTemperature cTemperature, RedirectAttributes redirectAttributes) {
		cTemperatureService.delete(cTemperature);
		addMessage(redirectAttributes, "删除体温成功");
		return "redirect:"+Global.getAdminPath()+"/sys/cTemperature/?repage";
	}

}