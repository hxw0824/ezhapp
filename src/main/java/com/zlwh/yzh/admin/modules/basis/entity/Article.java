/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.basis.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 单表生成Entity
 * @author liufei
 * @version 2016-05-26
 */
	
	public class Article extends DataEntity<Article> {
		
		private static final long serialVersionUID = 1L;
		private String type;		// type
		private String title;		// title
		private String imageUrl;
		private String content;		// content
		private String subTitle;		// sub_title
		private String publishUserId;		// publish_user_id
		private Date publishTime;		// publish_time
		private Date pageTime;
		private Date queryTime;
		
		public static final String TYPE_TAIDU = "0";
		public static final String TYPE_GUANYU = "1";
		public static final String TYPE_BANQUAN = "2";
		public static final String TYPE_HUIYUANXIEYI = "3";
		
		public Article() {
			super();
		}

		public Article(String id){
			super(id);
		}

		public Date getPageTime() {
			return pageTime;
		}

		public void setPageTime(Date pageTime) {
			this.pageTime = pageTime;
		}

		public Date getQueryTime() {
			return queryTime;
		}

		public void setQueryTime(Date queryTime) {
			this.queryTime = queryTime;
		}

		public String getImageUrl() {
			return imageUrl;
		}

		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}

		@Length(min=0, max=1, message="type长度必须介于 0 和 1 之间")
		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
		
		@Length(min=0, max=255, message="title长度必须介于 0 和 255 之间")
		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
		
		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
		
		@Length(min=0, max=255, message="sub_title长度必须介于 0 和 255 之间")
		public String getSubTitle() {
			return subTitle;
		}

		public void setSubTitle(String subTitle) {
			this.subTitle = subTitle;
		}
		
		@Length(min=0, max=64, message="publish_user_id长度必须介于 0 和 64 之间")
		public String getPublishUserId() {
			return publishUserId;
		}

		public void setPublishUserId(String publishUserId) {
			this.publishUserId = publishUserId;
		}
		
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		public Date getPublishTime() {
			return publishTime;
		}

		public void setPublishTime(Date publishTime) {
			this.publishTime = publishTime;
		}
		
	}