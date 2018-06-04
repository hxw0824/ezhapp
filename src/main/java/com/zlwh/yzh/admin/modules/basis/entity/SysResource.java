/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.basis.entity;

import org.apache.ibatis.annotations.Param;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 资源信息Entity
 * @author hxw
 * @version 2016-04-26
 */
public class SysResource extends DataEntity<SysResource> {
	
	private static final long serialVersionUID = 1L;
	private String uuid;		// uuid
	private String title;		// 资源标题
	private String tag;		// 标签，多标签多以&lsquo;,&rsquo;隔开
	private String resCode;		// 资源编码  编辑提供
	private String resType;		// 资源类型 0教案 1资源
	private String fileName;		// 资源文件名称
	private String fileAuffix;		// 资源文件后缀
	private String fileUrl;		// 资源文件路径
	private String iconUrl;		// 资源缩略图路径
	private String thumbnailUrl;		// 资源缩略图路径
	private String status;		// 资源有效状态 0：无效，1：有效 默认有效
	private String creater;		// 创建人
	private Date createTime;		// 创建时间
	private String isHot;		// 是否热点资源 1:是，0：否
	private String isCharge;		// 资源是否收费 Defalut 0; 1:免费，0：收费 默认为收费
	private String remark;		// 备注，说明性记录
	private String size;		// 文件大小（字节）
	private String sysLocal;   // 备课：标示是否本地资源
	private String level;   // 资源所在所有目录  以"\n"分隔
	private String clicknum;   // 资源所在所有目录  以"\n"分隔
	private String dayId;   // 资源所在所有目录  以"\n"分隔
	private String dayTitle;   // 资源所在所有目录  以"\n"分隔
	private String dayThumbnailUrl;   // 资源所在所有目录  以"\n"分隔
	private String classId;		// 班次编号
	private String metaCode;		// 班次编号
	
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getSysLocal() {
		return sysLocal;
	}

	public void setSysLocal(String sysLocal) {
		this.sysLocal = sysLocal;
	}

	public SysResource() {
		super();
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public SysResource(String id){
		super(id);
	}

	@Length(min=1, max=32, message="uuid长度必须介于 1 和 32 之间")
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	@Length(min=1, max=128, message="资源标题长度必须介于 1 和 128 之间")
	@ExcelField(title = "标题", align = 2, sort = 1)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=512, message="标签，多标签多以&lsquo;,&rsquo;隔开长度必须介于 0 和 512 之间")
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	@Length(min=1, max=32, message="资源编码  编辑提供长度必须介于 1 和 32 之间")
	@ExcelField(title = "编码", align = 2, sort = 2)
	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	
	@Length(min=1, max=2, message="资源类型 0教案 1资源长度必须介于 1 和 2 之间")
	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}
	
	@Length(min=1, max=128, message="资源文件名称长度必须介于 1 和 128 之间")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Length(min=1, max=5, message="资源文件后缀长度必须介于 1 和 5 之间")
	public String getFileAuffix() {
		return fileAuffix;
	}

	public void setFileAuffix(String fileAuffix) {
		this.fileAuffix = fileAuffix;
	}
	
	@Length(min=1, max=128, message="资源文件路径长度必须介于 1 和 128 之间")
	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
	@Length(min=0, max=128, message="资源缩略图路径长度必须介于 0 和 128 之间")
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	
	@Length(min=1, max=1, message="资源有效状态 0：无效，1：有效 默认有效长度必须介于 1 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=1, max=64, message="创建人长度必须介于 1 和 64 之间")
	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=1, max=1, message="是否热点资源 0:是，1：否长度必须介于 1 和 1 之间")
	public String getIsHot() {
		return isHot;
	}

	public void setIsHot(String isHot) {
		this.isHot = isHot;
	}
	
	@Length(min=1, max=1, message="资源是否收费 Defalut 0; 0:免费，1：收费 默认为收费长度必须介于 1 和 1 之间")
	public String getIsCharge() {
		return isCharge;
	}

	public void setIsCharge(String isCharge) {
		this.isCharge = isCharge;
	}
	
	@Length(min=0, max=2048, message="备注，说明性记录长度必须介于 0 和 2048 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getDayId() {
		return dayId;
	}

	public void setDayId(String dayId) {
		this.dayId = dayId;
	}

	public String getDayTitle() {
		return dayTitle;
	}

	public void setDayTitle(String dayTitle) {
		this.dayTitle = dayTitle;
	}

	public String getDayThumbnailUrl() {
		return dayThumbnailUrl;
	}

	public void setDayThumbnailUrl(String dayThumbnailUrl) {
		this.dayThumbnailUrl = dayThumbnailUrl;
	}

	public String getClicknum() {
		return clicknum;
	}

	public void setClicknum(String clicknum) {
		this.clicknum = clicknum;
	}

	public String getMetaCode() {
		return metaCode;
	}

	public void setMetaCode(String metaCode) {
		this.metaCode = metaCode;
	}
	
}