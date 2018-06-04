package com.zlwh.yzh.api.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.modules.order.entity.Order;

public class VipInfo {
	private String nickName;
	private String userid;
	private String userToken;
	private String level;
	private String curTime;
	private List <Order>vipInfo = new ArrayList<Order>();
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUserToken() {
		return userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public List<Order> getVipInfo() {
		return vipInfo;
	}
	public void setVipInfo(List<Order> vipInfo) {
		this.vipInfo = vipInfo;
	}
	public String getCurTime() {
		return curTime;
	}
	public void setCurTime(String curTime) {
		this.curTime = curTime;
	}
	
	

}
