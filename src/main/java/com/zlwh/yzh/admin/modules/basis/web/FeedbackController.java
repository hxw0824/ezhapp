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
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.zlwh.yzh.admin.modules.basis.entity.Feedback;
import com.zlwh.yzh.admin.modules.basis.service.FeedbackService;

/**
 * 单表生成Controller
 * @author liufei
 * @version 2016-05-26
 */
@Controller
@RequestMapping(value = "${adminPath}/basis/feedback")
public class FeedbackController extends BaseController {

	@Autowired
	private FeedbackService feedbackService;
	
	@ModelAttribute
	public Feedback get(@RequestParam(required=false) String id) {
		Feedback entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = feedbackService.get(id);
		}
		if (entity == null){
			entity = new Feedback();
		}
		return entity;
	}
	
	@RequiresPermissions("basis:feedback:view")
	@RequestMapping(value = {"list", ""})
	public String list(Feedback feedback, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Feedback> page = feedbackService.findPage(new Page<Feedback>(request, response), feedback); 
		model.addAttribute("page", page);
		return "modules/basis/feedbackList";
	}

	@RequiresPermissions("basis:feedback:view")
	@RequestMapping(value = "form")
	public String form(Feedback feedback, Model model) {
		model.addAttribute("feedback", feedback);
		return "modules/basis/feedbackForm";
	}

	@RequiresPermissions("basis:feedback:edit")
	@RequestMapping(value = "save")
	public String save(Feedback feedback, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, feedback)){
			return form(feedback, model);
		}
		feedback.setStatus(Feedback.STATUS_DONE);
		feedback.setApproveUser(UserUtils.getUser());
		feedbackService.save(feedback);
		addMessage(redirectAttributes, "保存单表成功");
		return "redirect:"+Global.getAdminPath()+"/basis/feedback/?repage";
	}
	@RequiresPermissions("basis:feedback:edit")
	@RequestMapping(value = "approve")
	public String approve(Feedback feedback, Model model, RedirectAttributes redirectAttributes) {

		feedback.setStatus(Feedback.STATUS_DONE);
		feedback.setApproveUser(UserUtils.getUser());
		feedbackService.save(feedback);
		addMessage(redirectAttributes, "处理成功");
		return "redirect:"+Global.getAdminPath()+"/basis/feedback/?repage";
	}
	@RequiresPermissions("basis:feedback:edit")
	@RequestMapping(value = "delete")
	public String delete(Feedback feedback, RedirectAttributes redirectAttributes) {
		feedbackService.delete(feedback);
		addMessage(redirectAttributes, "删除单表成功");
		return "redirect:"+Global.getAdminPath()+"/basis/feedback/?repage";
	}

}