/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.user.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sun.org.mozilla.javascript.internal.ast.NewExpression;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.modules.sys.entity.CClass;
import com.thinkgem.jeesite.modules.sys.entity.CWork;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.CClassService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.RoleIdUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.zlwh.yzh.admin.modules.user.entity.CUser;
import com.zlwh.yzh.admin.modules.user.service.CUserService;

/**
 * 用户管理Controller
 * @author liufei
 * @version 2016-05-26
 */
@Controller
@RequestMapping(value = "${adminPath}/user/cUser")
public class CUserController2 extends BaseController {

	@Autowired
	private CUserService cUserService;
	
	@Autowired
	private CClassService cClassService;
	
	@Autowired
	private SystemService systemService;
	
	
	@ModelAttribute
	public CUser get(@RequestParam(required=false) String id) {
		CUser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cUserService.get(id);
		}
		if (entity == null){
			entity = new CUser();
		}
		return entity;
	}
	
	@RequiresPermissions("user:cUser:view")
	@RequestMapping(value = {"list", ""})
	public String list(CUser cUser, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Page<CUser> page = cUserService.findPage(new Page<CUser>(request, response), cUser);
		User user = UserUtils.getUser();
		CClass cclass = cClassService.getByTeacherId(user.getId());
		Page<CUser> page = new Page<CUser>(request, response);
		cUser.setPage(page);
		List<CUser> list = null;
		if(cclass != null){
			cUser.setOfficeId(user.getOffice().getId());
			cUser.setClassId(cclass.getId());
			list = cUserService.findListByOfficeId(cUser);
		}
		page.setList(list);
		model.addAttribute("page", page);
		model.addAttribute("cclass", cclass);
		model.addAttribute("cUser", cUser);
		return "modules/user/cUserList";
	}

	@RequiresPermissions("user:cUser:view")
	@RequestMapping(value = "form")
	public String form(CUser cUser, Model model) {
		model.addAttribute("cUser", cUser);
		return "modules/user/cUserForm";
	}

	@RequiresPermissions("user:cUser:edit")
	@RequestMapping(value = "lock")
	public String lock(CUser cUser, RedirectAttributes redirectAttributes, Model model) {
		cUser.setStatus(CUser.STATUS_LOCK);
		cUserService.save(cUser);
		addMessage(redirectAttributes, "操作成功");
		return "redirect:"+Global.getAdminPath()+"/user/cUser/?repage";
	}
	
	@RequiresPermissions("user:cUser:edit")
	@RequestMapping(value = "unlock")
	public String unlock(CUser cUser, RedirectAttributes redirectAttributes, Model model) {
		cUser.setStatus(CUser.STATUS_UNLOCK);
		cUserService.save(cUser);
		addMessage(redirectAttributes, "操作成功");
		return "redirect:"+Global.getAdminPath()+"/user/cUser/?repage";
	}
	
	@RequiresPermissions("user:cUser:edit")
	@RequestMapping(value = "initPwd")
	@ResponseBody
	public String initPwd(CUser cUser) {
		String pwd = new Md5Hash("123456").toHex();
		cUser.setPwd(pwd);
		cUserService.save(cUser);
		return pwd;
	}
	
	@RequiresPermissions("user:cUser:edit")
	@RequestMapping(value = "clearImage")
	@ResponseBody
	public String clearImage(CUser cUser){
		cUserService.save(cUser);
		return "ok";
	}
	public static void main(String[] args) {
		System.out.println(new Date().getTime());
	}
	@RequiresPermissions("user:cUser:edit")
	@RequestMapping(value = "save")
	public String save(CUser cUser, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cUser)){
			return form(cUser, model);
		}
		if(StringUtils.isBlank(cUser.getId())){
			User curUser = UserUtils.getUser();
			CClass cclass = cClassService.getByTeacherId(curUser.getId());
			cUser.setPeriodId(cclass.getPeriod());
			cUser.setClassId(cclass.getId());
			cUser.setUserToken(IdGen.uuid());
			cUser.setStatus(CUser.STATUS_UNLOCK);
			cUser.setNickName(cUser.getLoginName()+"家长");
			cUser.setDeviceType("0");
			cUser.setImageUrl(cUser.getImageUrl()==null?Global.getConfig("defaultImage"):cUser.getImageUrl());
			cUser.setOfficeId(cclass.getOfficeId());
			cUser.setPwd(new Md5Hash("123456").toHex());
		}
		if (StringUtils.isNotBlank(cUser.getNewPassword())) {
			cUser.setPwd(new Md5Hash(cUser.getNewPassword()).toHex());
		}
		cUserService.save(cUser);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/user/cUser/?repage";
	}
	
	@RequiresPermissions("user:cUser:edit")
	@RequestMapping(value = "delete")
	public String delete(CUser cUser, RedirectAttributes redirectAttributes) {
		cUserService.delete(cUser);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/user/cUser/?repage";
	}

	/**
	 * 导出家长数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("user:cUser:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(CUser cUser, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "家长数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            User user = UserUtils.getUser();
    		CClass cclass = cClassService.getByTeacherId(user.getId());
    		List<CUser> list = null;
    		if(cclass != null){
    			cUser.setOfficeId(user.getOffice().getId());
    			cUser.setClassId(cclass.getId());
    			cUser.setStatus(CUser.STATUS_UNLOCK);
    			list = cUserService.findListByOfficeId(cUser);
    		}
    		new ExportExcel("用户数据", CUser.class).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出家长失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/user/cUser/list?repage";
    }

	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("user:cUser:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/user/cUser/list?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CUser> list = ei.getDataList(CUser.class);
			
			User curUser = UserUtils.getUser();
			CClass cclass = cClassService.getByTeacherId(curUser.getId());
			
			for (CUser user : list){
				try{
					if(StringUtils.isNotBlank(user.getLoginName())){
						if ("true".equals(checkLoginName("", user.getLoginName()))){
							if ("true".equals(checkTel("", user.getTel()))){
								user.setPeriodId(cclass.getPeriod());
								user.setClassId(cclass.getId());
								user.setUserToken(IdGen.uuid());
								user.setStatus(CUser.STATUS_UNLOCK);
								user.setNickName(user.getLoginName()+"家长");
								user.setDeviceType("0");
								user.setImageUrl(user.getImageUrl()==null?Global.getConfig("defaultImage"):user.getImageUrl());
								user.setOfficeId(cclass.getOfficeId());
								user.setPwd(new Md5Hash("123456").toHex());
								BeanValidators.validateWithException(validator, user);
								cUserService.save(user);
								successNum++;
							}else{
								failureMsg.append("<br/>手机号 "+user.getTel()+" 已存在; ");
								failureNum++;
							}
						}else{
							failureMsg.append("<br/>登录名 "+user.getLoginName()+" 已存在; ");
							failureNum++;
						}
					}else{
						failureMsg.append("<br/>登录名 "+user.getLoginName()+" 不能为空; ");
						failureNum++;
					}
				}catch(ConstraintViolationException ex){
					failureMsg.append("<br/>登录名 "+user.getLoginName()+" 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("<br/>登录名 "+user.getLoginName()+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/user/cUser/list?repage";
    }
	
	/**
	 * 下载导入用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("user:cUser:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据导入模板.xlsx";
    		List<CUser> list = Lists.newArrayList(); 
    		list.add(cUserService.findList(new CUser()).get(0));
    		new ExportExcel("家长数据", CUser.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/user/cUser/list?repage";
    }
	
	/**
	 * 验证登录名是否有效
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("user:cUser:edit")
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {
		if (loginName !=null && loginName.equals(oldLoginName)) {
			return "true";
		} else if (loginName !=null && !checkUserName(loginName)) {
			return "true";
		}
		return "false";
	}
	
	/**
	 * 验证手机号是否有效
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("user:cUser:edit")
	@RequestMapping(value = "checkTel")
	public String checkTel(String oldTel, String tel) {
		if (tel !=null && tel.equals(oldTel)) {
			return "true";
		} else if (tel !=null && !checkUserTel(tel)) {
			return "true";
		}
		return "false";
	}
	
	private boolean checkUserName(String userName) {
		if (StringUtils.isBlank(userName)) {
			return true;
		}
		CUser userBean = new CUser();
		userBean.setLoginName(userName);
		List<CUser> list = cUserService.findList(userBean);
		if (list.size() > 0) {
			return true;
		}
		return false;
	}
	
	private boolean checkUserTel(String tel) {
		if (StringUtils.isBlank(tel)) {
			return true;
		}
		CUser userBean = new CUser();
		userBean.setTel(tel);
		List<CUser> list = cUserService.findList(userBean);
		if (list.size() > 0) {
			return true;
		}
		return false;
	}
	
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(HttpServletResponse response) {
		User user = UserUtils.getUser();
		CClass cclass = cClassService.getByTeacherId(user.getId());
		
		String officeId = user.getOffice().getId();
		List<Map<String, Object>> mapList = Lists.newArrayList();
		
		CUser cUser = new CUser();
		if(user.getUserType().equals(RoleIdUtils.teacher)){
			cUser.setClassId(cclass != null ? cclass.getId() : "");
		}
		cUser.setStatus(CUser.STATUS_UNLOCK);
		cUser.setOfficeId(officeId);
		List<CUser> list = cUserService.findListByOfficeId(cUser);
		for (int i=0; i<list.size(); i++){
			CUser e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("name", StringUtils.replace(e.getLoginName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}
}