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
import com.zlwh.yzh.admin.modules.user.entity.CUserCard;
import com.zlwh.yzh.admin.modules.user.service.CUserCardService;

/**
 * 单表生成Controller
 * @author liufei
 * @version 2016-05-26
 */
@Controller
@RequestMapping(value = "${adminPath}/basis/cUserCard")
public class CUserCardController extends BaseController {

	@Autowired
	private CUserCardService userCardService;
	
	@ModelAttribute
	public CUserCard get(@RequestParam(required=false) String id) {
		CUserCard entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userCardService.get(id);
		}
		if (entity == null){
			entity = new CUserCard();
		}
		return entity;
	}
	
	@RequiresPermissions("basis:cUserCard:view")
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute("cUserCard")CUserCard cUserCard, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CUserCard> page = userCardService.findPage(new Page<CUserCard>(request, response), cUserCard); 
		model.addAttribute("page", page);
		return "modules/basis/cUserCardList";
	}
	@RequestMapping(value = {"pay"})
	public String pay( HttpServletRequest request, HttpServletResponse response, Model model) {
		request.setAttribute("data","aaa");
		return "modules/basis/pay";
		
	}




	@RequiresPermissions("basis:cUserCard:edit")
	@RequestMapping(value = "delete")
	public String delete(CUserCard userCard, RedirectAttributes redirectAttributes) {
		userCardService.delete(userCard);
		addMessage(redirectAttributes, "删除单表成功");
		return "redirect:"+Global.getAdminPath()+"/basis/cUserCard/?repage";
	}
	
	
	
}