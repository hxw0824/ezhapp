/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.util.List;

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
import com.thinkgem.jeesite.modules.sys.entity.CClass;
import com.thinkgem.jeesite.modules.sys.entity.CTemperature;
import com.thinkgem.jeesite.modules.sys.entity.CWork;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.CClassService;
import com.thinkgem.jeesite.modules.sys.service.CWorkService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.RoleIdUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.zlwh.yzh.admin.modules.user.entity.CUser;
import com.zlwh.yzh.admin.modules.user.service.CUserService;

/**
 * 考勤表Controller
 * @author hxw
 * @version 2018-04-03
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/cWork")
public class CWorkController extends BaseController {

	@Autowired
	private CWorkService cWorkService;
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private CClassService cClassService;
	
	@ModelAttribute
	public CWork get(@RequestParam(required=false) String id) {
		CWork entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cWorkService.get(id);
		}
		if (entity == null){
			entity = new CWork();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:cWork:view")
	@RequestMapping(value = {"list", ""})
	public String list(CWork cWork, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Page<CWork> page = cWorkService.findPage(new Page<CWork>(request, response), cWork); 
//		model.addAttribute("page", page);
		User user = UserUtils.getUser();
		CClass cclass = cClassService.getByTeacherId(user.getId());
		Page<CWork> page = new Page<CWork>(request, response);
		cWork.setPage(page);
		cWork.setOfficeId(user.getOffice().getId());
		if(user.getUserType().equals(RoleIdUtils.teacher)){
			cWork.setClassId(cclass != null ? cclass.getId() : "");
			String clockNum = cWorkService.getToDayClockNumByClassId(cclass != null ? cclass.getId() : "");

			List<User> userList = systemService.findUserForTeacherByOfficeId(user);
			model.addAttribute("clockNum", clockNum);
			model.addAttribute("allNum", userList.size());
		}
		List<CWork> list = cWorkService.findListByOfficeId(cWork);
		page.setList(list);
		if(StringUtils.isNotBlank(cWork.getClassId())){
			cWork.setClassName(cClassService.get(cWork.getClassId()).getName());
		}
		if(StringUtils.isNotBlank(cWork.getUserId())){
			cWork.setUserName(systemService.getUser(cWork.getUserId()).getName());
		}
		model.addAttribute("cclass", cclass);
		model.addAttribute("curUser", user);
		model.addAttribute("page", page);
		model.addAttribute("cWork", cWork);
		return "modules/sys/cWorkList";
	}

	@RequiresPermissions("sys:cWork:view")
	@RequestMapping(value = "form")
	public String form(CWork cWork, Model model) {
		model.addAttribute("cWork", cWork);
		return "modules/sys/cWorkForm";
	}

	@RequiresPermissions("sys:cWork:edit")
	@RequestMapping(value = "save")
	public String save(CWork cWork, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cWork)){
			return form(cWork, model);
		}
		cWorkService.save(cWork);
		addMessage(redirectAttributes, "保存考勤成功");
		return "redirect:"+Global.getAdminPath()+"/sys/cWork/?repage";
	}
	
	@RequiresPermissions("sys:cWork:edit")
	@RequestMapping(value = "delete")
	public String delete(CWork cWork, RedirectAttributes redirectAttributes) {
		cWorkService.delete(cWork);
		addMessage(redirectAttributes, "删除考勤成功");
		return "redirect:"+Global.getAdminPath()+"/sys/cWork/?repage";
	}

}