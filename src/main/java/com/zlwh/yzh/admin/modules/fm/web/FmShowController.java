/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.fm.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.zlwh.yzh.admin.modules.fm.entity.FmShow;
import com.zlwh.yzh.admin.modules.fm.service.FmShowService;

/**
 * 家家秀Controller
 * @author h
 * @version 2018-01-10
 */
@Controller
@RequestMapping(value = "${adminPath}/fm/fmShow")
public class FmShowController extends BaseController {

	@Autowired
	private FmShowService fmShowService;
	
	@ModelAttribute
	public FmShow get(@RequestParam(required=false) String id) {
		FmShow entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fmShowService.get(id);
		}
		if (entity == null){
			entity = new FmShow();
		}
		return entity;
	}
	
	@RequiresPermissions("fm:fmShow:view")
	@RequestMapping(value = {"list", ""})
	public String list(FmShow fmShow, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FmShow> page = fmShowService.findPage(new Page<FmShow>(request, response), fmShow); 
		model.addAttribute("allItems", fmShowService.getHomeShowIndexList());
		model.addAttribute("allUserList", fmShowService.getAllUser());
		model.addAttribute("page", page);
		return "modules/fm/fmShowList";
	}

	@RequiresPermissions("fm:fmShow:view")
	@RequestMapping(value = "form")
	public String form(FmShow fmShow, Model model) {
		model.addAttribute("fmShow", fmShow);
		return "modules/fm/fmShowForm";
	}

	@RequiresPermissions("fm:fmShow:edit")
	@RequestMapping(value = "save")
	public String save(FmShow fmShow, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, fmShow)){
			return form(fmShow, model);
		}
		fmShowService.save(fmShow);
		addMessage(redirectAttributes, "保存家家秀成功");
		return "redirect:"+Global.getAdminPath()+"/fm/fmShow/?repage";
	}
	
	@RequiresPermissions("fm:fmShow:edit")
	@RequestMapping(value = "delete")
	public String delete(FmShow fmShow, RedirectAttributes redirectAttributes) {
		fmShowService.delete(fmShow);
		addMessage(redirectAttributes, "删除家家秀成功");
		return "redirect:"+Global.getAdminPath()+"/fm/fmShow/?repage";
	}
	
	@RequiresPermissions("fm:fmShow:edit")
	@RequestMapping(value = "changeAudit")
	public String changeAudit(FmShow fmShow, Model model,RedirectAttributes redirectAttributes) {
		fmShowService.changeAudit(fmShow);
		String message = fmShow.getAuditStatus().equals("0") ? "已通过审核" : "未通过审核";
		addMessage(redirectAttributes, "资源‘"+fmShow.getName()+"’"+message);
		return "redirect:"+Global.getAdminPath()+"/fm/fmShow/?repage";
	}
}