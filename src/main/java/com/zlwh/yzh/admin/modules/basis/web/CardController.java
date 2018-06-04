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
import com.zlwh.yzh.admin.modules.basis.entity.CardBatch;
import com.zlwh.yzh.admin.modules.basis.entity.Card;
import com.zlwh.yzh.admin.modules.basis.service.CardBatchService;
import com.zlwh.yzh.admin.modules.basis.service.CardService;

/**
 * 单表生成Controller
 * @author liufei
 * @version 2016-05-26
 */
@Controller
@RequestMapping(value = "${adminPath}/basis/card")
public class CardController extends BaseController {

	@Autowired
	private CardService cardService;
	@Autowired
	private CardBatchService cardbatchService;
	
	@ModelAttribute
	public Card get(@RequestParam(required=false) String id) {
		Card entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cardService.get(id);
		}
		if (entity == null){
			entity = new Card();
		}
		return entity;
	}
	
	@RequiresPermissions("basis:card:view")
	@RequestMapping(value = {"list", ""})
	public String list(Card card, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Card> page = cardService.findPage(new Page<Card>(request, response), card); 
		model.addAttribute("page", page);
		return "modules/basis/cardList";
	}




	@RequiresPermissions("basis:card:edit")
	@RequestMapping(value = "delete")
	public String delete(Card card, RedirectAttributes redirectAttributes) {
		cardService.delete(card);
		addMessage(redirectAttributes, "删除单表成功");
		return "redirect:"+Global.getAdminPath()+"/basis/card/?repage";
	}
	
	/**
	 * 
	*<pre>
	*<b>说明:</b> 进入生成授权卡页面
	*<b>日期:</b> 2016年7月20日 上午10:03:48
	 */
	@RequiresPermissions("basis:card:view")
	@RequestMapping(value = "form")
	public String form(CardBatch card, Model model) {
		model.addAttribute("card", card);
		return "modules/basis/addCardForm";
	}

	@RequiresPermissions("basis:card:edit")
	@RequestMapping(value = "save")
	public String save(CardBatch cardBatch, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cardBatch)){
			return form(cardBatch, model);
		}
		cardService.batchCreateSysCard(cardBatch);
		addMessage(redirectAttributes, "保存单表成功");
		return "redirect:"+Global.getAdminPath()+"/basis/card/?repage";
	}
	
}