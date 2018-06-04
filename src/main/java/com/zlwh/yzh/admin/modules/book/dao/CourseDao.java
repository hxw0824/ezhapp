/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.book.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.zlwh.yzh.admin.modules.book.entity.Course;
import com.zlwh.yzh.api.domain.BookDetailDomain;

/**
 * 单表生成DAO接口
 * @author liufei
 * @version 2016-05-26
 */
@MyBatisDao
public interface CourseDao extends CrudDao<Course> {

	public Course detail(Course course);
	/**
	 * 
	*<pre>
	*<b>说明:</b> 首页展示免费课程
	*<b>日期:</b> 2016年6月28日 上午9:36:27
	 */
	public Course getIndexCourse(Map<String, Object> map);
	/**
	 * 
	*<pre>
	*<b>说明:</b> 获得课程列表
	*<b>日期:</b> 2016年6月28日 下午1:28:33
	 */
	public List<Course> getCourseList(BookDetailDomain domain);
	/**
	 * 
	*<pre>
	*<b>说明:</b> 增加视频点击数
	*<b>日期:</b> 2016年7月6日 上午10:29:16
	 */
	public void addClicksNum(String courseId);
	/**
	 * 获取班级月份对应的课程
	 * @param classid
	 * @param month
	 * @return
	 */
	public List<Course> getCourseListByclassidAndMonth(@Param("periodId")String periodId, @Param("monVal")String monVal,@Param("userId")String userId,@Param("index")String index);
	public Course getByCourseCode(String courseId);
	public List<Course> getCourseListByclassidAndMonthBySinglDay(@Param("periodId")String periodId,@Param("sort")int sort,@Param("userId")String userId);
	
	/**
	 *获取单个资源
	 * @return
	 */
	public Course getSingleRes(@Param("id")String id, @Param("userId")String userId,@Param("periodId")String periodId);
}