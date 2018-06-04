/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.products.web;

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
import com.thinkgem.jeesite.modules.products.entity.Products;
import com.thinkgem.jeesite.modules.products.service.ProductsService;

/**
 * 商品_对应赎买的vip类型Controller
 * @author hcq
 * @version 2018-01-22
 */
@Controller
@RequestMapping(value = "${adminPath}/products/products")
public class ProductsController extends BaseController {

	@Autowired
	private ProductsService productsService;
	
	@ModelAttribute
	public Products get(@RequestParam(required=false) String id) {
		Products entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productsService.get(id);
		}
		if (entity == null){
			entity = new Products();
		}
		return entity;
	}
	
	@RequiresPermissions("products:products:view")
	@RequestMapping(value = {"list", ""})
	public String list(Products products, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Products> page = productsService.findPage(new Page<Products>(request, response), products); 
		model.addAttribute("page", page);
		return "modules/products/productsList";
	}

	@RequiresPermissions("products:products:view")
	@RequestMapping(value = "form")
	public String form(Products products, Model model) {
		model.addAttribute("products", products);
		return "modules/products/productsForm";
	}

	@RequiresPermissions("products:products:edit")
	@RequestMapping(value = "save")
	public String save(Products products, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, products)){
			return form(products, model);
		}
		productsService.save(products);
		addMessage(redirectAttributes, "保存保存商品成功成功");
		return "redirect:"+Global.getAdminPath()+"/products/products/?repage";
	}
	
	@RequiresPermissions("products:products:edit")
	@RequestMapping(value = "delete")
	public String delete(Products products, RedirectAttributes redirectAttributes) {
		productsService.delete(products);
		addMessage(redirectAttributes, "删除保存商品成功成功");
		return "redirect:"+Global.getAdminPath()+"/products/products/?repage";
	}

}