package com.zlwh.yzh.api;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.Aes;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.zlwh.yzh.admin.modules.basis.entity.Telsms;
import com.zlwh.yzh.admin.modules.basis.service.TelsmsService;
import com.zlwh.yzh.admin.modules.user.entity.CSession;
import com.zlwh.yzh.admin.modules.user.entity.CUser;
import com.zlwh.yzh.admin.modules.user.service.CSessionService;
import com.zlwh.yzh.admin.modules.user.service.CUserService;
import com.zlwh.yzh.api.common.ClienEnum;
import com.zlwh.yzh.api.common.ResultUtil;
import com.zlwh.yzh.api.common.SMSUtil;
import com.zlwh.yzh.api.common.ReturnCode;
import com.zlwh.yzh.api.common.TelUtil;
import com.zlwh.yzh.api.domain.CUserDomain;
import com.zlwh.yzh.api.domain.CheckCodeDomain;
import com.zlwh.yzh.api.domain.LoginDomain;
import com.zlwh.yzh.api.domain.RegisterDomain;
import com.zlwh.yzh.api.domain.base.Result;

/**
 * 注册登录
 * @author hcq
 *@date 2018年1月6日
 * @version 1.0
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "v1.0/users/")
public class CLoginController extends BaseController {
	
	@Autowired
	CUserService userService;
	@Autowired
	TelsmsService telsmsService;
	@Autowired
	CSessionService sessionService;
	
	private CUser user = null;
	public static final Long EXPIRE = 60 * 1000L;
	public static final String TELNUM = "139,138,137,136,135,134,147,188,187,184,183,182,178,159,158,157,152,151,150,186,185,176,145,156,155,132,131,130,189,181,180,177,153,133,189,133,173";
	
	/**
	 * 发送验证码
	 * @param string  type =0注册  1--修改密码
	 * c端短信签名f19063a9ed418280742da4fc794a222c
	 * @return
	 * @throws Exception 
	 */
	@CrossOrigin(origins = "*")
	@RequestMapping(value="sendSMS/{_client}",method = RequestMethod.POST)
	@ResponseBody
	public Result sendSMS(String mobile, String signKey,String type,@PathVariable String _client) throws Exception {
		if (checkExpire(mobile)){
			return ResultUtil.error(ReturnCode.SEND_VERIFYCODE_NOT_VALID);
		}
		if (checkMobile(mobile)) {
			return ResultUtil.error(ReturnCode.PHONE_NOT_VALID);
		}
		if (checkSignKey(mobile, signKey)) {
//			resultMap.put("CODE", ReturnCode.PARAM_NOT_VALID.value());
//			resultMap.put("MSG", "SignKey不合法");
			return ResultUtil.error(ReturnCode.SNKEY_PARAM_NOT_VALID);
		}
		if(StringUtils.isEmpty(type)){
			return ResultUtil.error(ReturnCode.TYPE_PARAM_NOT_VALID);
		}
		//注册时验证手机号是否已注册
		if("0".equals(type)){
			if (checkUserTel(mobile)) {
				return ResultUtil.error(ReturnCode.USER_EXIST);
			}
		}else if ("1".equals(type)) {
			if (!checkUserTel(mobile)){
				return ResultUtil.error(ReturnCode.USER_NOT_FOUND);
			}
		}else {
			return ResultUtil.error(ReturnCode.TYPE_PARAM_NOT_VALID);
		}
		try {
			String code = String.valueOf((int) (Math.random() * 10000));
			String zero = "";
			for (int i = 4; i > code.length(); i--) {
				zero = "0" + zero;
			}
			code = zero + code;
			if (TelUtil.sendSMS(mobile, code,type,new SMSUtil(ClienEnum.EZH_FAMILY))) {
				Telsms telsmsBean = new Telsms();
				telsmsBean.setPhone(mobile);
				telsmsBean.setCode(code);
				telsmsBean.setType(type);
				telsmsService.save(telsmsBean);
				return ResultUtil.success();
			}else{
				return ResultUtil.error(ReturnCode.SEND_VERIFYCODE_ERROR);
			}
		} catch (Exception e) {
			logger.error(""+e);
			return ResultUtil.error(ReturnCode.ERROR);
		}
	}

	/**
	 * 注册
	 * @param domain
	 *  @RequestParam("key") String key,
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="register/{_client}",method = RequestMethod.POST)
	@ResponseBody
	public Result register(RegisterDomain domain,@PathVariable String _client) {
		if (checkMobile(domain.getTel())) {
			return ResultUtil.error(ReturnCode.PHONE_NOT_VALID);
		}
		if (checkUserName(domain.getUserName())) {
			return ResultUtil.error(ReturnCode.USER_EXIST);
		}
		if (checkPassword(domain.getPassword())) {
			return ResultUtil.error(ReturnCode.SIMPLE_PASSWORD);
		}
		if (checkCode(domain.getTel(), domain.getCode(),"0")) {
			return ResultUtil.error(ReturnCode.VERIFYCODE_NOT_CORRECT);
		}
		if (checkDeviceType(domain.getDeviceType())) {
			return ResultUtil.error(ReturnCode.DEVICE_PARAM_NOT_VALID);
		}
//		if (StringUtils.isEmpty(domain.getPeriodId())) {
//			return ResultUtil.error(ReturnCode.PERIOD_ERROR);
//		}
		try {
			String userToken = IdGen.uuid();
			CUser userBean = new CUser();
			userBean.setLoginName(domain.getUserName());
			userBean.setPwd(new Md5Hash(domain.getPassword()).toHex());
			userBean.setUserToken(userToken);
			userBean.setDeviceType(_client);
			userBean.setTel(domain.getTel());
			String defaultNickName = domain.getUserName()+"家长";
			userBean.setNickName(defaultNickName);
			userBean.setImageUrl(domain.getImageUrl()==null?Global.getConfig("defaultImage"):domain.getImageUrl());
			userBean.setStatus(CUser.STATUS_UNLOCK);
//			userBean.setPeriodId(domain.getPeriodId());
			userService.save(userBean);
			
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("userToken", userToken);
			return ResultUtil.success(dataMap);
		} catch (Exception e) {
			logger.error(""+e);
			return ResultUtil.error(ReturnCode.ERROR);
		}
	}
	/**
	 * 登录
	 * @param domain
	 * @return
	 */
	@RequestMapping(value="login",method = RequestMethod.POST)
	public Result login(LoginDomain domain, HttpServletRequest request) {
		if (checkDeviceType(domain.getDeviceType())) {
			return ResultUtil.error(ReturnCode.DEVICE_PARAM_NOT_VALID);
		}
//		if(checkUserToken(domain.getUserToken())){
//			return ResultUtil.error(ReturnCode.TOKEN_NOT_VALID);
//		}
		try {
			CUser userBean = new CUser();
			userBean.setLoginName(Aes.aesDecrypt(domain.getUserName(), Global.getConfig("securitKey")));
			userBean.setPwd(new Md5Hash(Aes.aesDecrypt(domain.getPassword(), Global.getConfig("securitKey"))).toHex());
//			userBean.setLoginName(domain.getUserName());
//			userBean.setPwd(new Md5Hash(domain.getPassword()).toHex());
			List<CUser> userList = userService.findList(userBean);
			if (userList == null || userList.size() == 0) {
				return ResultUtil.error(ReturnCode.USERNAME_PASSWORD_NOT_CORRECT);
			}
			String status= userList.get(0).getStatus();
			if("1".equals(status)){
				return ResultUtil.error(ReturnCode.USER_LOCK);
			}
			String sessionId = IdGen.uuid();
			
			saveSession(userList.get(0).getId(),sessionId,domain.getDeviceType(),request.getRemoteAddr());
			
			int multiLoginNum = Integer.parseInt(Global.getConfig("multi_login_num"));
			if (multiLoginNum != 0) {
				kickUser(userList.get(0).getId(),multiLoginNum);
			}
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("sid", sessionId);
			dataMap.put("userToken", userList.get(0).getUserToken());
			dataMap.put("userLoginName", userList.get(0).getLoginName());
			dataMap.put("userId", userList.get(0).getId());
			dataMap.put("periodId", userList.get(0).getPeriodId());
			dataMap.put("nickName",userList.get(0).getNickName());
			dataMap.put("imageUrl", userList.get(0).getImageUrl());
			return ResultUtil.success(dataMap);
		} catch (Exception e) {
			logger.error(""+e);
			return ResultUtil.error(ReturnCode.ERROR);
		}
	}
	
	private void saveSession(String userId, String sessionId, String deviceType, String ip) {
		CSession sessionBean = new CSession();
		sessionBean.setUserId(userId);
		sessionBean.setSessionId(sessionId);
		sessionBean.setDeviceType(deviceType);
		sessionBean.setLoginIp(ip);
		sessionBean.setLoginTime(new Date());
		sessionBean.setStatus(CSession.STATUS_LOGIN);
		sessionService.save(sessionBean);
	}

	private void kickUser(String id, int multiLoginNum) {
		CSession sessionBean = new CSession();
		sessionBean.setUserId(id);
		sessionBean.setStatus(CSession.STATUS_LOGIN);
		List<CSession> sessionList = sessionService.findList(sessionBean);
		if (sessionList.size() > multiLoginNum) {
			for (int i = multiLoginNum; i < sessionList.size(); i++) {
				CSession s = sessionList.get(i);
				s.setStatus(CSession.STATUS_KICKOUT);
				sessionService.save(s);
			}
		}
	}

	
	/**
	 * 登出
	 * @param userToken
	 * @return
	 */
	@RequestMapping(value="logout",method = RequestMethod.POST)
//	@ResponseBody
	public Result logout(CUserDomain domain) {
		if(checkUserToken(domain.getUserToken())){
			return ResultUtil.error(ReturnCode.TOKEN_NOT_VALID);
		}
		try {
			CUser userBean = new CUser();
			userBean.setUserToken(domain.getUserToken());
			List<CUser> userList = userService.findList(userBean);
			if (userList != null && userList.size() > 0 ) {
				CSession s = new CSession();
				s.setSessionId(domain.getSid());
				s.setUserId(userList.get(0).getId());
				List<CSession> sList = sessionService.findList(s);
				if (sList != null && sList.size() > 0) {
					s = sList.get(0);
					s.setStatus(CSession.STATUS_LOGOUT);
					s.setLogoutTime(new Date());
					sessionService.save(s);
				}
			}
			return ResultUtil.success();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(""+e);
			return ResultUtil.error(ReturnCode.ERROR);
		}
	}

	
	
	/**
	 * 修改密码
	 * @param domain
	 * @return
	 */
	@RequestMapping(value="modifyPassword")
	public Result modifyPassword(String userToken, String password){
		if (checkUserToken(userToken)) {
			return ResultUtil.error(ReturnCode.TOKEN_NOT_VALID);
		}
		if (checkPassword(password)) {
			return ResultUtil.error(ReturnCode.SIMPLE_PASSWORD);
		}
		try {
			user.setPwd(new Md5Hash(password).toHex());
			userService.save(user);
			return ResultUtil.success();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.error(ReturnCode.ERROR);
		}
	}
	/**
	 * 重置密码前获取用户基本信息
	 * @param domain
	 * @return
	 */
	@RequestMapping(value="getUser",method = RequestMethod.GET)
	public Result getUser(@RequestParam(value="loginName", required=false)String  loginName){
		if (!checkUserName(loginName)){
			return ResultUtil.error(ReturnCode.USER_NOT_FOUND);
		}
//		if (checkPassword(password)) {
//			return ResultUtil.error(ReturnCode.SIMPLE_PASSWORD);
//		}
//		if (checkCode(phone, code,"1")) {
//			return ResultUtil.error(ReturnCode.VERIFYCODE_NOT_CORRECT);
//		}
		try {
			CUser userBean = new CUser();
			userBean.setLoginName(loginName);
			List<CUser> list = userService.findList(userBean);
			userBean = list.get(0);
			return ResultUtil.success(userBean);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.error(ReturnCode.ERROR);
		}
	}
	@RequestMapping(value="checkCode",method = RequestMethod.POST)
	public Result checkCode(CheckCodeDomain CheckCodeDomain){
		if (checkCode(CheckCodeDomain.getPhone(), CheckCodeDomain.getCode(),CheckCodeDomain.getType())) {
			return ResultUtil.error(ReturnCode.VERIFYCODE_NOT_CORRECT);
		}
		return ResultUtil.success();
	}
	/**
	 * 重置密码
	 * @param domain
	 * @return
	 */
	@RequestMapping(value="resetPassword",method = RequestMethod.POST)
	@ResponseBody
	public Result resetPassword(String phone, String code, String password){
		if (!checkUserTel(phone)){
			return ResultUtil.error(ReturnCode.USER_NOT_FOUND);
		}
		if (checkPassword(password)) {
			return ResultUtil.error(ReturnCode.SIMPLE_PASSWORD);
		}
//		if (checkCode(phone, code,"1")) {
//			return ResultUtil.error(ReturnCode.VERIFYCODE_NOT_CORRECT);
//		}
		try {
			CUser userBean = new CUser();
			userBean.setTel(phone);
			List<CUser> list = userService.findList(userBean);
			userBean = list.get(0);
			userBean.setPwd(new Md5Hash(password).toHex());
			userService.save(userBean);
			return ResultUtil.success();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.error(ReturnCode.ERROR);
		}
	}
	
	private boolean checkThirdpartyId(String thirdpartyId) {
		if (StringUtils.isEmpty(thirdpartyId)) {
			return true;
		}
		return false;
	}
	
	private boolean checkSignKey(String mobile, String signKey) throws Exception {
		if (StringUtils.isEmpty(signKey)) {
			return true;
		}
//		if (!new Md5Hash(mobile,Global.getConfig("securitKey")).toHex().equalsIgnoreCase(signKey)) {
//			return true;
//		}
		if (!Aes.aesEncrypt(mobile, Global.getConfig("securitKey")).equals(signKey)) {
			return true;
		}
		
		return false;
	}
	
	private boolean checkDeviceType(String deviceType) {
		if (StringUtils.isBlank(deviceType)
				|| StringUtils.inString(deviceType, RegisterDomain.DEVICETYPE_IPHONE, RegisterDomain.DEVICETYPE_IPAD,
						RegisterDomain.DEVICETYPE_ANDROID, RegisterDomain.DEVICETYPE_ANDROID_PAD)) {
			return false;
		}
		return true;
	}
	
	private boolean checkPassword(String password) {
		if (StringUtils.isBlank(password)) {
			return true;
		}
		return false;
	}
	
	private boolean checkCode(String mobile, String code,String type) {
		if (StringUtils.isAnyBlank(mobile,code)) {
			return true;
		}
		Telsms telsmsBean = new Telsms();
		telsmsBean.setPhone(mobile);
		telsmsBean.setCode(String.valueOf(code));
		telsmsBean.setType(type);
		telsmsBean.setDelFlag(Telsms.DEL_FLAG_NORMAL);
		List<Telsms> list = telsmsService.findList(telsmsBean);
		if (list == null || list.size() == 0) {
			return true;
		}
		telsmsService.delete(list.get(0));
		return false;
	}

	private boolean checkUserName(String userName) {
		if (StringUtils.isBlank(userName)) {
			return true;
		}
		CUser userBean = new CUser();
		userBean.setLoginName(userName);
		List<CUser> list = userService.findList(userBean);
		if (list.size() > 0) {
			return true;
		}
		return false;
	}
	private boolean checkUserTel(String userName) {
		if (StringUtils.isBlank(userName)) {
			return true;
		}
		CUser userBean = new CUser();
		userBean.setTel(userName);
		List<CUser> list = userService.findList(userBean);
		if (list.size() > 0) {
			return true;
		}
		return false;
	}
	
	private boolean checkExpire(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			return true;
		}
		Telsms telsmsBean = new Telsms();
		telsmsBean.setPhone(mobile);
		List<Telsms> list = telsmsService.findList(telsmsBean);
		if (list != null && list.size() > 0) {
			Long lastSendDate = list.get(0).getCreateDate().getTime();
			if (new Date().getTime() - lastSendDate < EXPIRE) {
				return true;
			}
		}
		return false;
	}

	private boolean checkMobile(String mobile) {
		if (StringUtils.isBlank(mobile) || mobile.length() != 11 || TELNUM.indexOf(mobile.substring(0, 3)) == -1) {
			return true;
		}
		return false;
	}
	private boolean checkUserToken(String userToken) {
		if (StringUtils.isBlank(userToken)) {
			return true;
		}
		CUser userBean = new CUser();
		userBean.setUserToken(userToken);
		user = userService.get(userBean);
		return user == null;
	}
	
	public static void main(String[] args) throws Exception {
//		String code = String.valueOf((int) (Math.random() * 10000));
//		String zero = "";
//		for (int i = 4; i > code.length(); i--) {
//			zero = "0" + zero;e47b6b61204e1e2223b816b038497448 4F9E0D21332658265934330362557827
//		}28d0972ee804398e5149b3deaa3127da 28d0972ee804398e5149b3deaa3127da c4948bdb1249630b26b758f7c25ba164
//		code = zero + code;f19063a9ed418280742da4fc794a222c 
//		System.out.println(code);
		System.out.println(Aes.aesDecrypt("I+wZ4KnlWqnQukHGyQEwRQ==", Global.getConfig("securitKey")));
//		System.out.println(new Md5Hash("123456",Global.getConfig("securitKey")).toHex());
	}
}