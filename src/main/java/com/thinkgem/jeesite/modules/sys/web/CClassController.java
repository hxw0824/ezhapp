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
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.CClassService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 班级表Controller
 * @author hxw
 * @version 2018-03-31
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/cclass")
public class CClassController extends BaseController {

	@Autowired
	private CClassService cClassService;
	
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public CClass get(@RequestParam(required=false) String id) {
		CClass entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cClassService.get(id);
		}
		if (entity == null){
			entity = new CClass();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:class:view")
	@RequestMapping(value = {"list", ""})
	public String list(CClass cclass, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Page<CClass> page = cClassService.findPage(new Page<CClass>(request, response), cclass);
		cclass.setOfficeId(UserUtils.getUser().getOffice().getId());
		model.addAttribute("list", cClassService.getByOfficeId(cclass));
		User user = UserUtils.get(cclass.getTeacherId());
		if(user != null){
			cclass.setTeacherName(user.getName());
		}
		model.addAttribute("cclass", cclass);
		return "modules/sys/cclassList";
	}

	@RequiresPermissions("sys:class:view")
	@RequestMapping(value = "form")
	public String form(CClass cclass, Model model) {
		model.addAttribute("cclass", cclass);
		return "modules/sys/cclassForm";
	}

	@RequiresPermissions("sys:class:edit")
	@RequestMapping(value = "save")
	public String save(CClass cclass, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cclass)){
			return form(cclass, model);
		}
		User user = UserUtils.getUser();
		if(user != null && user.getOffice() != null && !"".equals(user.getOffice().getId())){
			cclass.setOfficeId(user.getOffice().getId());
		}
		cClassService.save(cclass);
		User user2 = systemService.getUser(cclass.getTeacherId());
		CClass cclass2 = cClassService.getByTeacherId(cclass.getTeacherId());
		user2.setClassId(cclass2.getId());
		user2.setPeriodId(cclass2.getPeriod());
		systemService.saveUser(user2);
		
		// 清除所有用户通讯录缓存
		systemService.clearUserRedisByOfficeId(user.getOffice().getId());
		addMessage(redirectAttributes, "保存班级表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/cclass/?repage";
	}
	
	@RequiresPermissions("sys:class:edit")
	@RequestMapping(value = "delete")
	public String delete(CClass cclass, RedirectAttributes redirectAttributes) {
		cClassService.delete(cclass);
		addMessage(redirectAttributes, "删除班级表成功");
		
		// 清除所有用户通讯录，班级列表缓存
		systemService.clearUserRedisByOfficeId(UserUtils.getUser().getOffice().getId());
		return "redirect:"+Global.getAdminPath()+"/sys/cclass/?repage";
	}
	
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String isAll,HttpServletResponse response) {
		String officeId = UserUtils.getUser().getOffice().getId();
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<CClass> list = cClassService.getForTree(officeId,isAll);
		for (int i=0; i<list.size(); i++){
			CClass e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}

}