/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zlwh.yzh.admin.modules.book.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.zlwh.yzh.admin.modules.book.entity.Course;
import com.zlwh.yzh.api.domain.BookDetailDomain;
import com.zlwh.yzh.api.domain.CourseDetailDomain;
import com.zlwh.yzh.admin.modules.book.dao.CourseDao;

/**
 * 单表生成Service
 * @author liufei
 * @version 2016-05-26
 */
@Service
@Transactional(readOnly = true)
public class CourseService extends CrudService<CourseDao, Course> {

	public Course get(String id) {
		return super.get(id);
	}
	
	public List<Course> findList(Course course) {
		return super.findList(course);
	}
	
	public Page<Course> findPage(Page<Course> page, Course course) {
		return super.findPage(page, course);
	}
	
	@Transactional(readOnly = false)
	public void save(Course course) {
		super.save(course);
	}
	
	@Transactional(readOnly = false)
	public void delete(Course course) {
		super.delete(course);
	}

	public Course detail(Course course) {
		return dao.detail(course);
	}
	
	
	/**
	 * 
	*<pre>
	*<b>说明:</b> 首页展示免费课程
	*<b>日期:</b> 2016年6月28日 上午9:36:27
	 */
	public Course getIndexCourse(Map<String, Object> map){
		return dao.getIndexCourse(map);
	}
	/**
	 * 
	*<pre>
	*<b>说明:</b> 获得课程列表
	*<b>日期:</b> 2016年6月28日 下午1:28:33
	 */
	public List<Course> getCourseList(BookDetailDomain domain){
		return dao.getCourseList(domain);
	}
	/**
	 * 
	*<pre>
	*<b>说明:</b> 增加视频点击数
	*<b>日期:</b> 2016年7月6日 上午10:29:16
	 */
	@Transactional(readOnly=false)
	public void addClicksNum(String courseId){
		dao.addClicksNum(courseId);
	}

	public List<Course> getCourseListByclassidAndMonth(String classid, String month,String userId,String index) {
		// TODO Auto-generated method stub
		return dao.getCourseListByclassidAndMonth(classid,month,userId,index);
	}

	public Course getByCourseCode(String courseId) {
		// TODO Auto-generated method stub
		return dao.getByCourseCode( courseId);
	}

	public List<Course> getCourseListByclassidAndMonthBySinglDay(String classid,int recommendSort, String userId ) {
		// TODO Auto-generated method stub
		return dao.getCourseListByclassidAndMonthBySinglDay( classid, recommendSort, userId);
	}
	
	public Course getSingleRes(String id, String userId,String classId) {
		return dao.getSingleRes( id,userId,classId);
	}
}