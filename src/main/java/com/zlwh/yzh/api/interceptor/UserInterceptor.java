package com.zlwh.yzh.api.interceptor;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.zlwh.yzh.admin.modules.user.entity.CSession;
import com.zlwh.yzh.admin.modules.user.entity.CUser;
import com.zlwh.yzh.admin.modules.user.service.CSessionService;
import com.zlwh.yzh.admin.modules.user.service.CUserService;
import com.zlwh.yzh.api.common.ResultUtil;
import com.zlwh.yzh.api.common.ReturnCode;
import com.zlwh.yzh.api.domain.base.Result;

/**
 * 登录状态校验拦截器
 * @author 袁继峰
 * @version 2016-6-6
 */
public class UserInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String userToken = request.getParameter("userToken");
		String sid = request.getParameter("sid");
		
		//权限区分功能的action
		String actionArr = "getIndex,getResouseItem,getEveryCourse,getSingelRes,getSingelCourse,searchList,getRecentHot";
		List<String> actionList = Arrays.asList(actionArr.split(","));
		
		if (StringUtils.isAnyEmpty(userToken, sid)) {
			return true;
		}
		CUserService userService = SpringContextHolder.getBean(CUserService.class);
		CUser userBean = new CUser();
		userBean.setUserToken(userToken);
		CUser user = userService.get(userBean);
		if (user != null) {
			CSessionService sessionService = SpringContextHolder.getBean(CSessionService.class);
			CSession session = new CSession();
			session.setUserId(user.getId());
			session.setSessionId(sid);
			List<CSession> sList = sessionService.findList(session);
			Result result = null;
			String status = "NOLOGIN";
			if (sList != null && sList.size() > 0) {
				status = sList.get(0).getStatus();
				if (CSession.STATUS_KICKOUT.equals(status)) {
					result = new Result(ReturnCode.USER_OTHER_LOGIN);
				}else if (CSession.STATUS_LOGOUT.equals(status)) {
					result = new Result(ReturnCode.USER_LOGIN_OUT);
				}else if (CSession.STATUS_EXPIRE.equals(status)) {
					result = new Result(ReturnCode.USER_LOGIN_FAILURE);
				}else if(CSession.STATUS_LOGIN.equals(status)){
					return true;
				}
			}else{
				result = new Result(ReturnCode.USER_LOGIN_FAILURE);
			}
			String requestURI = request.getRequestURI();
			String uri = requestURI.substring(requestURI.lastIndexOf("/") + 1);

			if(!CSession.STATUS_LOGIN.equals(status)){
				if(!actionList.contains(uri)){
					response.setHeader("Access-Control-Allow-Origin", "*");
					response.setContentType("text/html;charset=UTF-8");
					PrintWriter writer = response.getWriter();
					Map<String,Object> resultMap = new LinkedHashMap<String, Object>();
					resultMap.put("exception", result);
					writer.print(JsonMapper.toJsonString(ResultUtil.success(resultMap)));
					writer.flush();
					
					
					return false;
				}else{
					request.setAttribute("CLEAR_USER_PARAMES", result);
					return true;
				}
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
