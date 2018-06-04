/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 监控表Entity
 * @author hxw
 * @version 2018-04-02
 */
public class SysMonitor extends DataEntity<SysMonitor> {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private String deviceId;		// 设备ID
	private String officeId;		// 学校ID
	private String classId;		// 班级ID
	private String className;		// 班级ID
	private String ip;		// ip地址
	private String status;		// 状态 0：开启 1：关闭
	private String beginTime;		// 摄像头开启时间
	private String endTime;		// 摄像头关闭时间
	
	public static final String MONITOR_OPEN = "0";
	public static final String MONITOR_CLOSE = "1";
	
	public SysMonitor() {
		super();
	}

	public SysMonitor(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Length(min=0, max=64, message="设备ID长度必须介于 0 和 64 之间")
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	@Length(min=0, max=64, message="班级ID长度必须介于 0 和 64 之间")
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
	
	@Length(min=0, max=2000, message="ip地址长度必须介于 0 和 2000 之间")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}