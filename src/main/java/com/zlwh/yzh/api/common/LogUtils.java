/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.api.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.thinkgem.jeesite.common.utils.Exceptions;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.zlwh.yzh.admin.modules.sys.dao.CLogDao;
import com.zlwh.yzh.admin.modules.sys.entity.CLog;

/**
 * 日志工具类
 * @author ThinkGem
 * @version 2014-11-7
 */
public class LogUtils {
	
	private static CLogDao logDao = SpringContextHolder.getBean(CLogDao.class);
	private static Map<String,LogType> uriLogTypeMapping = new HashMap<String,LogType>();
	static{
		uriLogTypeMapping.put("/hands/api/user/1.0/login", LogType.LOGIN);
		uriLogTypeMapping.put("/hands/api/user/1.0/logout", LogType.LOGOUT);
		uriLogTypeMapping.put("/hands/api/user/1.0/modifyPhone", LogType.REBINDMOBILE);
		uriLogTypeMapping.put("/hands/api/user/1.0/register", LogType.REGISTER);
		uriLogTypeMapping.put("/hands/api/user/1.0/resetPassword", LogType.RESETPWD);
		uriLogTypeMapping.put("/hands/api/user/1.0/sendSMS", LogType.TELSMS);
		uriLogTypeMapping.put("/hands/api/hands/1.0/upload", LogType.UPLOADWORK);
	}
	
	/**
	 * 保存日志
	 */
	public static void saveLog(HttpServletRequest request, Object handler, Exception ex){
			CLog log = new CLog();
			LogType logType = uriLogTypeMapping.get(request.getRequestURI());
			if (logType != null) {
				log.setTitle(logType.getRemark());
				log.setType(String.valueOf(logType.value()));
			}
			log.setUserToken(request.getParameter("userToken"));
			log.setRemoteAddr(StringUtils.getRemoteAddr(request));
			log.setUserAgent(request.getHeader("user-agent"));
			log.setRequestUri(request.getRequestURI());
			log.setParams(request.getParameterMap());
			log.setMethod(request.getMethod());
			log.setProvince("");
			log.setCity("");
			log.setLng("");
			log.setLat("");
			log.setCreateTime(new Date());
			// 异步保存日志
			new SaveLogThread(log, handler, ex).start();
	}

	/**
	 * 保存日志线程
	 */
	public static class SaveLogThread extends Thread{
		
		private CLog log;
		private Object handler;
		private Exception ex;
		
		public SaveLogThread(CLog log, Object handler, Exception ex){
			super(SaveLogThread.class.getSimpleName());
			this.log = log;
			this.handler = handler;
			this.ex = ex;
		}
		
		@Override
		public void run() {
			// 如果有异常，设置异常信息
			log.setException(Exceptions.getStackTraceAsString(ex));
			// 如果无标题并无异常日志，则不保存信息
			if (StringUtils.isBlank(log.getTitle()) && StringUtils.isBlank(log.getType())){
				return;
			}
			// 保存日志信息
			log.preInsert();
			logDao.insert(log);
		}
	}

}
