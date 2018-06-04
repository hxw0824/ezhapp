package com.zlwh.yzh.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.zlwh.yzh.admin.modules.basis.entity.Article;
import com.zlwh.yzh.admin.modules.basis.service.ArticleService;
import com.zlwh.yzh.admin.modules.basis.service.MessageService;
import com.zlwh.yzh.admin.modules.book.entity.Month;
import com.zlwh.yzh.admin.modules.book.entity.Course;
import com.zlwh.yzh.admin.modules.book.service.MonthService;
import com.zlwh.yzh.admin.modules.book.service.CourseService;


@Controller
@RequestMapping(value = "/api/H5/1.0/")
public class H5Controller extends BaseController {


	@Autowired
	ArticleService articleService;
	@Autowired
	MessageService messageService;
	@Autowired
	CourseService courseService;
	@Autowired
	MonthService bookService;
	
	/**
	 * 
	*<pre>
	*<b>说明:</b> 关于我们
	*<b>日期:</b> 2016年7月25日 下午1:39:14
	 */
	@RequestMapping(value = "aboutus")
	public String aboutus( Model model) {
		Article article = new Article();
		article.setType(Article.TYPE_GUANYU);
		article = articleService.get(article);
		model.addAttribute(article);
		return "mobile/modules/article/aboutus";
	}
	/**
	 * 
	*<pre>
	*<b>说明:</b> 版权声明
	*<b>日期:</b> 2016年7月25日 下午1:39:04
	 */
	@RequestMapping(value = "copyright")
	public String copyright( Model model) {
		Article article = new Article();
		article.setType(Article.TYPE_BANQUAN);
		article = articleService.get(article);
		model.addAttribute(article);
		return "mobile/modules/article/copyright";
	}
	
	
	/**
	 * 功能：提供App端服务协议黄页
	 * 
	 * @return view
	 * */
	@RequestMapping(value = "appservices")
	public String appServices() {
		return "mobile/modules/article/appservices";
	}
	
	/**
	 * 视频分享H5
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "share")
	public String share(HttpServletRequest request, Model model){
		String id = request.getParameter("id");
		if (StringUtils.isEmpty(id)) {
			return "mobile/modules/article/error";
		}
		Course course = new Course(id);
		Course courseDetail = courseService.detail(course);
		if (courseDetail == null) {
			return "mobile/modules/article/error";
		}
		Month book = bookService.get(courseDetail.getMonval());
		model.addAttribute("courseDetail", courseDetail);
		model.addAttribute("book", book);
		return "mobile/modules/article/H5Share";
	}
	

}