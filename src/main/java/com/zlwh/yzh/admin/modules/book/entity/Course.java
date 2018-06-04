/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.book.entity;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.zlwh.yzh.admin.modules.basis.entity.SysResource;
import com.zlwh.yzh.api.common.EncryptUncrypt;
import com.zlwh.yzh.api.common.MonthEnum;
import com.zlwh.yzh.api.common.ReturnCode;

/**
 * 单表生成Entity
 * @author liufei
 * @version 2016-05-26
 */
public class Course extends DataEntity<Course> {
	
	private static final long serialVersionUID = 1L;
	private String monval;		// 图书对应月份
	private String monid;		// 图书对应月份
	private String courseName;		// 课程名称
	private String resCode;		// 课程编号 资源编号
	private String content;		// 课程内容
	private String thumbnailUrl;		// 背景大图URL
	private String iconUrl;		// 缩略图url
	private String videoPath;		// 视频URL
	private String sort;		// 排序
	
	private int clicksNum;
	private String courseId;
	private String isStar;		// 是否评星  0:否 1：是
	private String isCollect;		// 是否收藏  0:否 1：是
	private String isCollection;//是否收藏
	private String can_play;//是否有权限播放
	
	private String recommendSort;
	private String monthName;
	private String periodId;//banji 
	private String resType;
	private String tag;
	private String tagcode;
	private String isCharge;//是否免费
	
	private String href;//是否免费
	private String star;//是否免费
	private SysResource sysResource;//是否免费
	public String getStar() {
		return star;
	}

	public void setStar(String star) {
		this.star = star;
	}

	public String getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(String isCollect) {
		this.isCollect = isCollect;
	}

	public void setIsCharge(String isCharge) {
		this.isCharge = isCharge;
	}

	public Course() {
		super();
	}

	public Course(String id){
		super(id);
	}

	public String getIsCharge() {
		return isCharge;
	}

	public void setisCharge(String isCharge) {
		this.isCharge = isCharge;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public String getPeriodId() {
		return periodId;
	}

	public void setPeriodId(String periodId) {
		this.periodId = periodId;
	}

	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	public String getCan_play() {
		return can_play;
	}

	public void setCan_play(String can_play) {
		this.can_play = can_play;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	@Length(min=1, max=64, message="图书月份长度必须介于 1 和 64 之间")
	public String getMonval() {
		return monval;
	}

	public void setMonval(String monval) {
		this.monval = monval;
	}

	@Length(min=0, max=255, message="课程名称长度必须介于 0 和 255 之间")
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	@Length(min=0, max=255, message="课程编号长度必须介于 0 和 255 之间")
	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	
	
	
	@Length(min=0, max=500, message="课程内容长度必须介于 0 和 500 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	

	
	@Length(min=0, max=500, message="缩略图URL长度必须介于 0 和 500 之间")
	public String getThumbnailUrl() {
		if(StringUtils.isNotEmpty(thumbnailUrl)&&!thumbnailUrl.contains("http")){
			String url = Global.getConfig("staticFileUrl")+thumbnailUrl;
			return thumbnailUrl;
		}else{
			return thumbnailUrl;
		}
			

	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	@Length(min=0, max=500, message="视频URL长度必须介于 0 和 500 之间")
	public String getVideoPath() {
		if(StringUtils.isNotEmpty(videoPath)){
//			if(!videoPath.contains("http")){
//				videoPath = Global.getConfig("staticFileUrl")+videoPath;
//			}
			return videoPath;
			//加密
//			String encryptvideoPath = new String(EncryptUncrypt.encrypt(videoPath.getBytes()));
//			return encryptvideoPath;
		}else {
			return videoPath;
		}
	}
	
	@JsonIgnore
	public String getAbsVideoPath() {
		return Global.getConfig("staticFileUrl")+videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}
	
	@Length(min=0, max=5, message="排序长度必须介于 0 和 5 之间")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}


	public String getIconUrl() {
		if(StringUtils.isNotEmpty(iconUrl)&&!iconUrl.contains("http")){
			String url = Global.getConfig("staticFileUrl")+iconUrl;
			return iconUrl;
		}else{
			return iconUrl;
		}
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public int getClicksNum() {
		return clicksNum;
	}

	public void setClicksNum(int clicksNum) {
		this.clicksNum = clicksNum;
	}

	public String getRecommendSort() {
		return recommendSort;
	}

	public void setRecommendSort(String recommendSort) {
		this.recommendSort = recommendSort;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTagcode() {
		return tagcode;
	}

	public void setTagcode(String tagcode) {
		this.tagcode = tagcode;
	}

	public String getMonid() {
		return monid;
	}

	public void setMonid(String monid) {
		this.monid = monid;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public SysResource getSysResource() {
		return sysResource;
	}

	public void setSysResource(SysResource sysResource) {
		this.sysResource = sysResource;
	}

	public String getIsStar() {
		return isStar;
	}

	public void setIsStar(String isStar) {
		this.isStar = isStar;
	}

	public String getIsCollection() {
		return isCollection;
	}

	public void setIsCollection(String isCollection) {
		this.isCollection = isCollection;
	}

	
}