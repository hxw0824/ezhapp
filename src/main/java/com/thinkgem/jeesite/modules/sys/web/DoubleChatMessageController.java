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
import com.thinkgem.jeesite.modules.sys.entity.DoubleChatMessage;
import com.thinkgem.jeesite.modules.sys.service.DoubleChatMessageService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * sssController
 * @author sss
 * @version 2018-05-04
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/doubleChatMessage")
public class DoubleChatMessageController extends BaseController {

	@Autowired
	private DoubleChatMessageService doubleChatMessageService;
	
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public DoubleChatMessage get(@RequestParam(required=false) String id) {
		DoubleChatMessage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = doubleChatMessageService.get(id);
		}
		if (entity == null){
			entity = new DoubleChatMessage();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:doubleChatMessage:view")
	@RequestMapping(value = {"list", ""})
	public String list(DoubleChatMessage doubleChatMessage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DoubleChatMessage> page = doubleChatMessageService.findPage(new Page<DoubleChatMessage>(request, response), doubleChatMessage); 
		model.addAttribute("page", page);
		
		if(StringUtils.isNotBlank(doubleChatMessage.getFrom())){
			doubleChatMessage.setUserName(systemService.getUserByLoginName(doubleChatMessage.getFrom()).getName());
		}
		model.addAttribute("doubleChatMessage", doubleChatMessage);
		return "modules/sys/doubleChatMessageList";
	}

	@RequiresPermissions("sys:doubleChatMessage:view")
	@RequestMapping(value = "form")
	public String form(DoubleChatMessage doubleChatMessage, Model model) {
		model.addAttribute("doubleChatMessage", doubleChatMessage);
		return "modules/sys/doubleChatMessageForm";
	}

	@RequiresPermissions("sys:doubleChatMessage:edit")
	@RequestMapping(value = "save")
	public String save(DoubleChatMessage doubleChatMessage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, doubleChatMessage)){
			return form(doubleChatMessage, model);
		}
		doubleChatMessageService.save(doubleChatMessage);
		addMessage(redirectAttributes, "保存通知（表现）成功");
		return "redirect:"+Global.getAdminPath()+"/sys/doubleChatMessage/?repage";
	}
	
	@RequiresPermissions("sys:doubleChatMessage:edit")
	@RequestMapping(value = "delete")
	public String delete(DoubleChatMessage doubleChatMessage, RedirectAttributes redirectAttributes) {
		doubleChatMessageService.delete(doubleChatMessage);
		addMessage(redirectAttributes, "删除通知（表现）成功");
		return "redirect:"+Global.getAdminPath()+"/sys/doubleChatMessage/?repage";
	}

}