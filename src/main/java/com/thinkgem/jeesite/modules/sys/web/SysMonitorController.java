/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.text.SimpleDateFormat;
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
import com.thinkgem.jeesite.common.utils.JedisUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.CClass;
import com.thinkgem.jeesite.modules.sys.entity.SysMonitor;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.CClassService;
import com.thinkgem.jeesite.modules.sys.service.SysMonitorService;
import com.thinkgem.jeesite.modules.sys.utils.RedisUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 监控表Controller
 * @author hxw
 * @version 2018-04-02
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysMonitor")
public class SysMonitorController extends BaseController {

	@Autowired
	private SysMonitorService sysMonitorService;
	
	@Autowired
	private CClassService cClassService;
	
	@ModelAttribute
	public SysMonitor get(@RequestParam(required=false) String id) {
		SysMonitor entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysMonitorService.get(id);
		}
		if (entity == null){
			entity = new SysMonitor();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sysMonitor:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysMonitor sysMonitor, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Page<SysMonitor> page = sysMonitorService.findPage(new Page<SysMonitor>(request, response), sysMonitor); 
		sysMonitor.setOfficeId(UserUtils.getUser().getOffice().getId());
		Page<SysMonitor> page = new Page<SysMonitor>(request, response);
		sysMonitor.setPage(page);
		page.setList(sysMonitorService.findListByOffice(sysMonitor));
		model.addAttribute("page", page);
		if(StringUtils.isNotBlank(sysMonitor.getClassId())){
			sysMonitor.setClassName(cClassService.get(sysMonitor.getClassId()).getName());
		}
		model.addAttribute("sysMonitor", sysMonitor);
		return "modules/sys/sysMonitorList";
	}

	@RequiresPermissions("sys:sysMonitor:view")
	@RequestMapping(value = "form")
	public String form(SysMonitor sysMonitor, Model model) {
		model.addAttribute("sysMonitor", sysMonitor);
		return "modules/sys/sysMonitorForm";
	}

	@RequiresPermissions("sys:sysMonitor:edit")
	@RequestMapping(value = "save")
	public String save(SysMonitor sysMonitor,String changeStatus,Model model, RedirectAttributes redirectAttributes){
		if (!beanValidator(model, sysMonitor)){
			return form(sysMonitor, model);
		}
		User user = UserUtils.getUser();
		if(StringUtils.isBlank(sysMonitor.getId())){
			sysMonitor.setOfficeId(user.getOffice().getId());
			if(StringUtils.isBlank(sysMonitor.getClassId())){
				sysMonitor.setClassId("PUBLIC");
			}
		}
		
		if(StringUtils.isNotBlank(changeStatus)){
			if(sysMonitor.getStatus().equals(SysMonitor.MONITOR_CLOSE)){
				sysMonitor.setStatus(SysMonitor.MONITOR_OPEN);
			}else{
				sysMonitor.setStatus(SysMonitor.MONITOR_CLOSE);
			}
		}
		sysMonitorService.save(sysMonitor);
		
		JedisUtils.delObject(RedisUtils.OFFICE_CLASS_MONITOR_LIST + user.getOffice().getId() + "_" + RedisUtils.KINDERGARTEN);
		if(StringUtils.isNotBlank(sysMonitor.getClassId()) && !sysMonitor.getClassId().equals("PUBLIC")){
			JedisUtils.delObject(RedisUtils.OFFICE_CLASS_MONITOR_LIST + user.getOffice().getId() + "_" + sysMonitor.getClassId());
		}else{
			CClass cclass = new CClass();
			cclass.setOfficeId(user.getOffice().getId());
			List<CClass> cclassList = cClassService.getByOfficeId(cclass);
			if(cclassList != null && cclassList.size() > 0){
				for (CClass cClass2 : cclassList) {
					JedisUtils.delObject(RedisUtils.OFFICE_CLASS_MONITOR_LIST + user.getOffice().getId() + "_" + cClass2.getId());
				}
			}
		}
		addMessage(redirectAttributes, "保存监控表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysMonitor/?repage";
	}
	
	@RequiresPermissions("sys:sysMonitor:edit")
	@RequestMapping(value = "delete")
	public String delete(SysMonitor sysMonitor, RedirectAttributes redirectAttributes) {
		sysMonitor = sysMonitorService.get(sysMonitor.getId());
		sysMonitorService.delete(sysMonitor);
		
		User user = UserUtils.getUser();
		JedisUtils.delObject(RedisUtils.OFFICE_CLASS_MONITOR_LIST + user.getOffice().getId() + "_" + RedisUtils.KINDERGARTEN);
		JedisUtils.delObject(RedisUtils.OFFICE_CLASS_MONITOR_LIST + user.getOffice().getId() + "_" + sysMonitor.getClassId());
		
		addMessage(redirectAttributes, "删除监控表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysMonitor/?repage";
	}

}