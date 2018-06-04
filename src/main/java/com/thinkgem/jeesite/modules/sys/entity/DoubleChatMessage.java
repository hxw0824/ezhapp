/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * sssEntity
 * @author sss
 * @version 2018-05-04
 */
public class DoubleChatMessage extends DataEntity<DoubleChatMessage> {
	
	private static final long serialVersionUID = 1L;
	private String roomId;		// 聊天会话id
	private String from;		// 发起人账号
	private String text;		// 文本内容
	private String audio;		// 语音内容
	private String image;		// 图文内容
	private String type;		// 聊天类型
	private Date createTime;		// 创建时间
	private String userName;		// 发起人账号
	
	public DoubleChatMessage() {
		super();
	}

	public DoubleChatMessage(String id){
		super(id);
	}

	@NotNull(message="聊天会话id不能为空")
	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	
	@Length(min=1, max=16, message="发起人账号长度必须介于 1 和 16 之间")
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
	
	@Length(min=0, max=256, message="文本内容长度必须介于 0 和 256 之间")
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@Length(min=0, max=256, message="语音内容长度必须介于 0 和 256 之间")
	public String getAudio() {
		return audio;
	}

	public void setAudio(String audio) {
		this.audio = audio;
	}
	
	@Length(min=0, max=256, message="图文内容长度必须介于 0 和 256 之间")
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	@Length(min=0, max=16, message="聊天类型长度必须介于 0 和 16 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}