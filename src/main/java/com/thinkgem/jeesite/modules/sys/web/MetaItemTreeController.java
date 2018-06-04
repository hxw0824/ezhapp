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
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.MetaItemTree;
import com.thinkgem.jeesite.modules.sys.service.MetaItemTreeService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 栏目分类表Controller
 * @author hxw
 * @version 2016-04-25
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/metaItemTree")
public class MetaItemTreeController extends BaseController {

	@Autowired
	private MetaItemTreeService metaItemTreeService;
	
	@ModelAttribute("metaItemTree")
	public MetaItemTree get(@RequestParam(required=false) String id) {
		MetaItemTree entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = metaItemTreeService.get(id);
		}
		if (entity == null){
			entity = new MetaItemTree();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:metaItemTree:view")
	@RequestMapping(value = {"list", ""})
	public String list(MetaItemTree metaItemTree, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<MetaItemTree> list = metaItemTreeService.findList(metaItemTree); 
		model.addAttribute("list", list);
		return "modules/sys/metaItemTreeList";
	}

	@RequiresPermissions("sys:metaItemTree:view")
	@RequestMapping(value = "form")
	public String form(MetaItemTree metaItemTree, Model model) {
		if (metaItemTree.getParent()!=null && StringUtils.isNotBlank(metaItemTree.getParent().getId())){
			metaItemTree.setParent(metaItemTreeService.get(metaItemTree.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(metaItemTree.getId())){
				MetaItemTree metaItemTreeChild = new MetaItemTree();
				metaItemTreeChild.setParent(new MetaItemTree(metaItemTree.getParent().getId()));
				List<MetaItemTree> list = metaItemTreeService.findChild(metaItemTree); 
				if (list.size() > 0){
					metaItemTree.setSort(list.get(list.size()-1).getSort());
					metaItemTree.setCode(list.get(list.size()-1).getCode());
					if (metaItemTree.getSort() != null){
						metaItemTree.setSort(metaItemTree.getSort() + 30);
					}
					if (metaItemTree.getSort() != null){
						Integer code = Integer.parseInt(metaItemTree.getCode()) + 10;
						metaItemTree.setCode(code.toString());
					}
				}
			}
		}
		if (metaItemTree.getSort() == null){
			metaItemTree.setSort(30);
		}
		model.addAttribute("user", UserUtils.getUser());
		model.addAttribute("metaItemTree", metaItemTree);
		return "modules/sys/metaItemTreeForm";
	}

	@RequiresPermissions("sys:metaItemTree:edit")
	@RequestMapping(value = "save")
	public String save(MetaItemTree metaItemTree, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, metaItemTree)){
			return form(metaItemTree, model);
		}
		metaItemTreeService.save(metaItemTree);
		addMessage(redirectAttributes, "保存栏目成功");
		return "redirect:"+Global.getAdminPath()+"/sys/metaItemTree/?repage";
	}
	
	@RequiresPermissions("sys:metaItemTree:edit")
	@RequestMapping(value = "delete")
	public String delete(MetaItemTree metaItemTree, RedirectAttributes redirectAttributes) {
		metaItemTreeService.delete(metaItemTree);
		addMessage(redirectAttributes, "删除栏目成功");
		return "redirect:"+Global.getAdminPath()+"/sys/metaItemTree/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<MetaItemTree> list = metaItemTreeService.findList(new MetaItemTree());
		for (int i=0; i<list.size(); i++){
			MetaItemTree e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				map.put("code", e.getCode());
				map.put("grade", e.getGrade());
				map.put("parent", e.getParent());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	@ResponseBody
	@RequestMapping(value = "allTreeData")
	public List<Map<String, Object>> allTreeData(HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<MetaItemTree> list = metaItemTreeService.findList(new MetaItemTree());
		for (MetaItemTree mit : list){
			Map<String, Object> map = Maps.newHashMap();
			MetaItemTree e = metaItemTreeService.get(mit.getParent().getId());
			map.put("name", mit.getName());
			map.put("code", mit.getCode());
			map.put("grade", mit.getGrade());
			if(e != null){
				map.put("parentName", e.getName());
				map.put("parentCode", e.getCode());
			}else{
				map.put("parentName", "");
				map.put("parentCode", "");
			}
			mapList.add(map);
		}
		return mapList;
	}
}