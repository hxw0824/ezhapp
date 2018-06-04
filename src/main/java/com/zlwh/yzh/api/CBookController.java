package com.zlwh.yzh.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beust.jcommander.internal.Maps;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.zlwh.yzh.admin.modules.book.entity.BookShelf;
import com.zlwh.yzh.admin.modules.book.entity.Month;
import com.zlwh.yzh.admin.modules.book.service.MonthService;
import com.zlwh.yzh.admin.modules.user.entity.CUser;
import com.zlwh.yzh.admin.modules.user.service.CUserService;
import com.zlwh.yzh.api.common.ReturnCode;
import com.zlwh.yzh.api.domain.BookDetailDomain;
import com.zlwh.yzh.api.domain.CUserDomain;

@Controller
@RequestMapping(value = "api/book/1.0/")
public class CBookController extends BaseController {
	
	@Autowired
	MonthService bookService;

	@Autowired
	CUserService cUserService;
	
	private CUser user=null;
	/**
	 * 
	*<pre>
	*<b>说明:</b> 书架
	*<b>日期:</b> 2016年6月28日 上午11:17:43
	 */
	@RequestMapping(value = "bookList")
	@ResponseBody
	public Map<String, Object> bookList(CUserDomain domain){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		if (!checkValid(domain.getUserId(),domain.getUserToken())) {
			resultMap.put("CODE", ReturnCode.PARAM_NOT_VALID.value());
			resultMap.put("MSG", "用户Token不合法");
			return resultMap;
		}
		Month book = new Month();
		book.setPeriodId(user.getPeriodId());
		try {
			List<Map<String, Object>> list1 = new ArrayList<Map<String,Object>>();
			List<BookShelf> bookShelfList = bookService.getBookShelfList();
			Map<String,Object> book_map = null;
			for (BookShelf bs : bookShelfList) {
				book_map = Maps.newHashMap();
				book.setShelfid(bs.getShelfId());
				List<Map<String, Object>> bookList = bookService.selectBookList(book);
				if(bookList.size() > 0){
					book_map.put("bookList", bookList);
					book_map.put("bookShelf", bs);
					list1.add(book_map);
				}
			}
			resultMap.put("DATA", list1);
			resultMap.put("CODE", ReturnCode.SUCCESS.value());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			resultMap.put("CODE", ReturnCode.ERROR.value());
			resultMap.put("MSG", "程序出现错误");
		}
		return resultMap;
	}
	/**
	 * 图书详情
	 * @param domain
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "detail")
	@ResponseBody
	public Map<String,Object> detail(BookDetailDomain domain){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if (!checkValid(domain.getUserId(),domain.getUserToken())) {
			resultMap.put("CODE", ReturnCode.PARAM_NOT_VALID.value());
			resultMap.put("MSG", "用户Token不合法");
			return resultMap;
		}
		try {
			Month book = bookService.get(domain.getMonval());
			resultMap.put("CODE", ReturnCode.SUCCESS.value());
			resultMap.put("DATA", book);
		} catch (Exception e) {
			logger.error(""+e);
			resultMap.put("CODE", ReturnCode.ERROR.value());
			resultMap.put("MSG", "程序出错");
		}
		return resultMap;
	}
	
	private boolean checkValid(String userId,String userToken) {
		if (StringUtils.isAnyBlank(userId,userToken)) {
			return false;
		}
		CUser userBean = new CUser();
		userBean.setUserToken(userToken);
		user = cUserService.get(userBean);
		return user != null ? (user.getId().equals(userId) && user.getStatus().equals(CUser.STATUS_UNLOCK)) : false;
	}
}