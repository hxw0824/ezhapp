/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.JedisUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.CClass;
import com.thinkgem.jeesite.modules.sys.entity.Card;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.CClassService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.RedisUtils;
import com.thinkgem.jeesite.modules.sys.utils.RoleIdUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.zlwh.yzh.admin.modules.user.entity.CUser;

/**
 * 用户Controller
 * @author ThinkGem
 * @version 2013-8-29
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/user")
public class UserController extends BaseController {

	@Autowired
	private SystemService systemService;
	
	@Autowired
	private CClassService cClassService;
	
	@ModelAttribute
	public User get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return systemService.getUser(id);
		}else{
			return new User();
		}
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"index"})
	public String index(User user, Model model) {
		return "modules/sys/userIndex";
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"list", ""})
	public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
        User currentUser = user.getCurrentUser();
        if(currentUser.getUserType().equals(RoleIdUtils.teacher)){
        	CClass cclass = cClassService.getByTeacherId(currentUser.getId());
        	 model.addAttribute("cclass", cclass);
        	 
        	 user.setOffice(currentUser.getOffice());
        	 user.setClassId(currentUser.getClassId());
        	 page.setList(systemService.findUserForTeacherByOfficeId(user));
        }
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("page", page);
		return "modules/sys/userList";
	}
	
	@ResponseBody
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"listData"})
	public Page<User> listData(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
		return page;
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "form")
	public String form(User user, Model model) {
		if (user.getCompany()==null || user.getCompany().getId()==null){
			user.setCompany(UserUtils.getUser().getCompany());
		}
		if (user.getOffice()==null || user.getOffice().getId()==null){
			user.setOffice(UserUtils.getUser().getOffice());
		}
		model.addAttribute("user", user);
		if (StringUtils.isBlank(user.getId())) {//添加时的角色列表展示
            model.addAttribute("allRoles", systemService.findAddRole());
        } else {//修改时的角色列表展示
            model.addAttribute("allRoles", systemService.findUpdateRole(user.getId()));
        }
		return "modules/sys/userForm";
	}

	
	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "checkOfficeAdmin",method=RequestMethod.POST)
	public User checkOfficeAdmin(String officeId, HttpServletRequest request, HttpServletResponse response) {
		User user = systemService.getOfficeAdmin(officeId);
		return user;
	}
	
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "save")
	public String save(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		User currentUser = user.getCurrentUser();
		
		// 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
		user.setCompany(new Office(request.getParameter("company.id")));
		user.setOffice(new Office(request.getParameter("office.id")));
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(user.getNewPassword())) {
			user.setPassword(SystemService.entryptPassword(user.getNewPassword()));
		}
		if (!beanValidator(model, user)){
			return form(user, model);
		}
		if (!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))){
			addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
			return form(user, model);
		}
		// 角色数据有效性验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		List<String> roleIdList = user.getRoleIdList();
		for (Role r : systemService.findAllRole()){
			if (roleIdList.contains(r.getId())){
				roleList.add(r);
			}
		}
		user.setUserToken(IdGen.uuid());
		user.setRoleList(roleList);
		if(roleList.get(0).getId().equals(RoleIdUtils.admin)){//幼儿园管理员
        	user.setUserType(RoleIdUtils.admin);
        }else if(roleList.get(0).getId().equals(RoleIdUtils.kindergarten)){
        	user.setUserType(RoleIdUtils.kindergarten);
        }else if(roleList.get(0).getId().equals(RoleIdUtils.teacher)){
        	user.setUserType(RoleIdUtils.teacher);
        }else if(roleList.get(0).getId().equals(RoleIdUtils.parent)){
        	user.setClassId(StringUtils.isBlank(user.getClassId()) ? currentUser.getClassId() : user.getClassId());
        	if(StringUtils.isBlank(user.getPeriodId())){
        		user.setPeriodId(currentUser.getPeriodId());
        	}
        	user.setUserType(RoleIdUtils.parent);
        }
		// 保存用户信息
		systemService.saveUser(user);
		
		// 清除当前用户缓存
		if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
			UserUtils.clearCache();
			//UserUtils.getCacheMap().clear();
		}
		addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}
	
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "delete")
	public String delete(User user, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		if (UserUtils.getUser().getId().equals(user.getId())){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
		}else if (User.isAdmin(user.getId())){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
		}else{
			systemService.deleteUser(user);
			addMessage(redirectAttributes, "删除用户成功");
			
			systemService.clearUserRedisByOfficeId(UserUtils.getUser().getOffice().getId());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}
	
	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(User user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<User> page = systemService.findUser(new Page<User>(request, response, -1), user);
    		new ExportExcel("用户数据", User.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }

	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<User> list = ei.getDataList(User.class);
			for (User user : list){
				try{
					if ("true".equals(checkLoginName("", user.getLoginName()))){
						if(!checkUserMobile(user.getMobile())){
							if(!checkCard(user.getCardId())){
								User currentUser = UserUtils.getUser();
								user.setCompany(currentUser.getCompany());
								user.setOffice(currentUser.getOffice());
								user.setUserToken(IdGen.uuid());
								List<String> roleIdList = Lists.newArrayList();
								if(currentUser.getUserType().equals(RoleIdUtils.kindergarten)){
									user.setUserType(RoleIdUtils.teacher);
									roleIdList = Lists.newArrayList();
									roleIdList.add(RoleIdUtils.teacher);
									user.setRoleIdList(roleIdList);
								}else if(currentUser.getUserType().equals(RoleIdUtils.teacher)){
									user.setClassId(currentUser.getClassId());
									user.setPeriodId(currentUser.getPeriodId());
									user.setUserType(RoleIdUtils.parent);
									roleIdList = Lists.newArrayList();
									roleIdList.add(RoleIdUtils.parent);
									user.setRoleIdList(roleIdList);
								}
								
								user.setPassword(SystemService.entryptPassword("123456"));
								// 角色数据有效性验证，过滤不在授权内的角色
								List<Role> roleList = Lists.newArrayList();
								for (Role r : systemService.findAllRole()){
									if (user.getRoleIdList().contains(r.getId())){
										roleList.add(r);
									}
								}
								user.setRoleList(roleList);
								
								BeanValidators.validateWithException(validator, user);
								systemService.saveUser(user);
								
								if(currentUser.getUserType().equals(RoleIdUtils.teacher)){
									if(isAllNum(user.getCardId())){
										User nowUser = UserUtils.getByLoginName(user.getLoginName());
										Card card = new Card();
										card.setId(user.getCardId());
										card.setOfficeId(nowUser.getOffice().getId());
										card.setUserId(nowUser.getId());
										card.setCreateDate(new Date());
										systemService.bindCard(card);
									}else{
										failureMsg.append("<br/>IC卡号 "+user.getCardId()+" 不符合要求（10位数字）; ");
										failureNum++;
									}
								}
								successNum++;
							}else{
								failureMsg.append("<br/>IC卡号 "+user.getCardId()+" 已绑定其他人; ");
								failureNum++;
							}
						}else{
							failureMsg.append("<br/>手机号 "+user.getMobile()+" 已存在; ");
							failureNum++;
						}
					}else{
						failureMsg.append("<br/>登录名 "+user.getLoginName()+" 已存在; ");
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
					ex.printStackTrace();
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
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }
	
	/**
	 * 下载导入用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据导入模板.xlsx";
    		List<User> list = Lists.newArrayList(); list.add(UserUtils.getUser());
    		new ExportExcel("用户数据", User.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }

	/**
	 * 验证登录名是否有效
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {
		if (loginName !=null && loginName.equals(oldLoginName)) {
			return "true";
		} else if (loginName !=null && systemService.getUserByLoginName(loginName) == null) {
			return "true";
		}
		return "false";
	}

	/**
	 * 用户信息显示及保存
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "info")
	public String info(User user, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getName())){
			if(Global.isDemoMode()){
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userInfo";
			}
			currentUser.setEmail(user.getEmail());
			currentUser.setPhone(user.getPhone());
			currentUser.setMobile(user.getMobile());
			currentUser.setRemarks(user.getRemarks());
			currentUser.setPhoto(user.getPhoto());
			systemService.updateUserInfo(currentUser);
			model.addAttribute("message", "保存用户信息成功");
		}
		model.addAttribute("user", currentUser);
		model.addAttribute("Global", new Global());
		return "modules/sys/userInfo";
	}

	/**
	 * 返回用户信息
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "infoData")
	public User infoData() {
		return UserUtils.getUser();
	}
	
	/**
	 * 修改个人用户密码
	 * @param oldPassword
	 * @param newPassword
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "modifyPwd")
	public String modifyPwd(String oldPassword, String newPassword, Model model) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)){
			if(Global.isDemoMode()){
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userModifyPwd";
			}
			if (SystemService.validatePassword(oldPassword, user.getPassword())){
				systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
				model.addAttribute("message", "修改密码成功");
			}else{
				model.addAttribute("message", "修改密码失败，旧密码错误");
			}
		}
		model.addAttribute("user", user);
		return "modules/sys/userModifyPwd";
	}
	
	@ResponseBody
	@RequestMapping(value = "initPwd")
	public String initPwd(User user) {
		user = UserUtils.get(user.getId()); 
		if(user != null){
			systemService.updatePasswordById(user.getId(), user.getLoginName(), "123456");
			return user.getLoginName();
		}
		return "error";
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String officeId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<User> list = systemService.findUserByOfficeId(officeId);
		for (int i=0; i<list.size(); i++){
			User e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", "u_"+e.getId());
			map.put("pId", officeId);
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeDataForClass")
	public List<Map<String, Object>> treeDataForClass(@RequestParam(required=false) String isAll,HttpServletResponse response) {
		String officeId = UserUtils.getUser().getOffice().getId();
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<User> list = systemService.findUserForClassByOfficeId(officeId,isAll);
		for (int i=0; i<list.size(); i++){
			User e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", "u_"+e.getId());
			map.put("pId", officeId);
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}
	
	@ResponseBody
	@RequestMapping(value = "treeDataForTeacher")
	public List<Map<String, Object>> treeDataForTeacher(HttpServletResponse response) {
		User user = UserUtils.getUser();
		String officeId = user.getOffice().getId();
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<User> list = systemService.findUserForTeacherByOfficeId(user);
		for (int i=0; i<list.size(); i++){
			User e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", "u_"+e.getId());
			map.put("pId", officeId);
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}
	
	@ResponseBody
	@RequestMapping(value = "treeDataForNoTeacher")
	public List<Map<String, Object>> treeDataForNoTeacher(HttpServletResponse response) {
		User user = UserUtils.getUser();
		String officeId = user.getOffice().getId();
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<User> list = systemService.findUserForNoTeacherByOfficeId(user);
		for (int i=0; i<list.size(); i++){
			User e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", "u_"+e.getLoginName());
			map.put("pId", officeId);
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}
	
	/**
	 * 验证手机号是否有效
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkMobile")
	public String checkMobile(String oldMobile, String mobile) {
		if (mobile !=null && mobile.equals(oldMobile)) {
			return "true";
		} else if (mobile !=null && !checkUserMobile(mobile)) {
			return "true";
		}
		return "false";
	}
	
	private boolean checkUserMobile(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			return true;
		}
		User user = systemService.getUserByMobile(mobile);
		return user != null;
	}
	
	private boolean checkCard(String cardId) {
		if (StringUtils.isBlank(cardId)) {
			return true;
		}
		Card card = systemService.getCard(cardId);
		return card != null;
	}
	
	private boolean isAllNum(String str){
	      return str.matches("[0-9]{10}");
	}
	
	@ResponseBody
	@RequestMapping(value = "bindCard")
	public Map<String,Object> bindCard(Card card) {
		Map<String,Object> map = Maps.newHashMap();
		Integer status = 0;
		Card card2 = systemService.getCard(card.getId());
		if(card2 == null){
			User user = UserUtils.get(card.getUserId());
			card.setOfficeId(user.getOffice().getId());
			card.setCreateDate(new Date());
			status = systemService.bindCard(card);
		}else{
			status = 2;
			map.put("user", systemService.getUser(card2.getUserId()));
		}
		map.put("status", status);
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "unbindCard")
	public Map<String,Object> unbindCard(String userId) {
		Map<String,Object> map = Maps.newHashMap();
		Integer status = systemService.deleteCard(userId);
		map.put("status", status);
		return map;
	}
    
//	@InitBinder
//	public void initBinder(WebDataBinder b) {
//		b.registerCustomEditor(List.class, "roleList", new PropertyEditorSupport(){
//			@Autowired
//			private SystemService systemService;
//			@Override
//			public void setAsText(String text) throws IllegalArgumentException {
//				String[] ids = StringUtils.split(text, ",");
//				List<Role> roles = new ArrayList<Role>();
//				for (String id : ids) {
//					Role role = systemService.getRole(Long.valueOf(id));
//					roles.add(role);
//				}
//				setValue(roles);
//			}
//			@Override
//			public String getAsText() {
//				return Collections3.extractToString((List) getValue(), "id", ",");
//			}
//		});
//	}
}
