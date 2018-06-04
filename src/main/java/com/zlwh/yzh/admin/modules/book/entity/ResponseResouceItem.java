package com.zlwh.yzh.admin.modules.book.entity;

public class ResponseResouceItem {
	private String id;		// 班次编号
	private String classId;		// 班次编号
	private String title;		// 班次编号
	private String className;		// 班次名称
	private String item1Code;		// 一级分类编号 关联meta_item_tree id
	private String item1Name;
	private String resCode;	
	private String resType;		// 资源类型 0教案 1资源
	private String fileName;		// 资源文件名称
	private String fileAuffix;		// 资源文件后缀
	private String fileUrl;		// 资源文件路径
	private String iconUrl;		// 资源缩略图路径
	private String thumbnailUrl;//背景大图
	private String isHot;		// 是否热点资源 1:是，0：否
	private String isCharge;		// 资源是否收费 Defalut 0; 1:免费，0：收费 默认为收费
	private String remark;
	private String isCollect;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getItem1Code() {
		return item1Code;
	}
	public void setItem1Code(String item1Code) {
		this.item1Code = item1Code;
	}
	public String getItem1Name() {
		return item1Name;
	}
	public void setItem1Name(String item1Name) {
		this.item1Name = item1Name;
	}
	public String getResCode() {
		return resCode;
	}
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	public String getResType() {
		return resType;
	}
	public void setResType(String resType) {
		this.resType = resType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileAuffix() {
		return fileAuffix;
	}
	public void setFileAuffix(String fileAuffix) {
		this.fileAuffix = fileAuffix;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	public String getIsHot() {
		return isHot;
	}
	public void setIsHot(String isHot) {
		this.isHot = isHot;
	}
	public String getIsCharge() {
		return isCharge;
	}
	public void setIsCharge(String isCharge) {
		this.isCharge = isCharge;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIsCollect() {
		return isCollect;
	}
	public void setIsCollect(String isCollect) {
		this.isCollect = isCollect;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}		

	
}
