package com.zlwh.yzh.api;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.zlwh.yzh.admin.modules.basis.entity.Card;
import com.zlwh.yzh.admin.modules.basis.service.CardService;
import com.zlwh.yzh.admin.modules.book.entity.Month;
import com.zlwh.yzh.admin.modules.book.entity.Course;
import com.zlwh.yzh.admin.modules.book.service.MonthService;
import com.zlwh.yzh.admin.modules.book.service.CourseService;
import com.zlwh.yzh.admin.modules.user.entity.CUser;
import com.zlwh.yzh.admin.modules.user.entity.CUserCard;
import com.zlwh.yzh.admin.modules.user.entity.CUserCollection;
import com.zlwh.yzh.admin.modules.user.service.CUserCardService;
import com.zlwh.yzh.admin.modules.user.service.CUserCollectionService;
import com.zlwh.yzh.admin.modules.user.service.CUserService;
import com.zlwh.yzh.api.common.ResultUtil;
import com.zlwh.yzh.api.common.ReturnCode;
import com.zlwh.yzh.api.common.ToolUtil;
import com.zlwh.yzh.api.domain.BookDetailDomain;
import com.zlwh.yzh.api.domain.CardDomain;
import com.zlwh.yzh.api.domain.CourseDetailDomain;
import com.zlwh.yzh.api.domain.base.Result;

/**
 * 课程相关
 * @author hcq
 *@date 2018年1月10日
 * @version 1.0
 */
@RestController
@RequestMapping(value = "api/course/1.0/")
public class CCourseController extends BaseController {
	
	@Autowired
	CourseService courseService;
	@Autowired
	CUserService userService;
	@Autowired
	CUserCollectionService userCollectionService;

	@Autowired
	MonthService bookService;
	@Autowired
	CardService cardService;
	@Autowired
	CUserCardService cUserCardService;
	
	
	private CUser user = null;

	
	/**
	 *获取全年所有月份或对应月份的课程
	 * @param domain
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public Result  list(BookDetailDomain domain) {
		if (checkUserToken(domain.getUserToken())) {
			return  ResultUtil.error(ReturnCode.TOKEN_NOT_VALID);
		}
		if (checkBook(domain.getMonval())&&StringUtils.isEmpty(domain.getQueryInfo())) {
			return ResultUtil.error(ReturnCode.COURCES_MON_VAL_NOT_VALID);
		}
		
		try {
			Map<String,Object> dataMap = new HashMap<String,Object>();
			Page<Object> page = new Page<Object>();
			if (StringUtils.isNotEmpty(domain.getPageNo())) {
				page.setPageNo(NumberUtils.toInt(domain.getPageNo()));
				page.setPageSize(NumberUtils.toInt(domain.getPageSize()));
				domain.setPage(page);
			}
			if(user!=null){
				domain.setPeriodId(user.getPeriodId());
			}else {
				domain.setPeriodId("0");
			}
			List<Course> detail = courseService.getCourseList(domain);
			dataMap.put("list", detail);
			if (StringUtils.isNotEmpty(domain.getPageNo())) {
				dataMap.put("totalSize", page.getCount());
				int totalPage = ToolUtil.getPageSize((int)page.getCount(), NumberUtils.toInt(domain.getPageSize()));
				dataMap.put("totolPage", totalPage);
			}
			return ResultUtil.success(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.error(ReturnCode.ERROR);
		}
		
	}
	
	/**
	 * 课程详情
	 * @param domain
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "detail")
	@ResponseBody
	public Result detail(CourseDetailDomain domain) {
		if (checkUserToken(domain.getUserToken())) {
			return ResultUtil.error(ReturnCode.TOKEN_NOT_VALID);
		}
		String courseCode =domain.getCourseCode();
		String courseId = domain.getCourseId();
		if (courseId!=null&&checkCourse(domain.getCourseId())) {
			return ResultUtil.error(ReturnCode.COURCES_NOT_FOUND);
		}
		if(StringUtils.isEmpty(courseCode)&&StringUtils.isEmpty(courseId)){
			return ResultUtil.error(ReturnCode.PARAM_NOT_VALID);
		}
		try {
			Course course = new Course();
			course.setResCode(courseCode);
			course.setId(domain.getCourseId());
			Course courseDetail = courseService.detail(course);
			if (StringUtils.isNoneEmpty(domain.getUserToken())) {
				CUserCollection userCollectionBean = new CUserCollection();
				userCollectionBean.setUserId(user.getId());
				userCollectionBean.setCourseId(domain.getCourseId());
				List<CUserCollection> list = userCollectionService.findList(userCollectionBean);
				if (list != null && list.size() > 0) {
					courseDetail.setIsCollection("Y");
				}else{
					courseDetail.setIsCollection("N");;
				}
			}
			//进行权限控制
			//判断图书是否免费
			if("Y".equals(courseDetail.getIsCharge())){
				courseDetail.setCan_play("Y");
			}else{
				if(user==null){
					courseDetail.setCan_play("N");
				}else {
					//进行权限控制查看用户是否已购买图书
					CUserCard cUserCard = new CUserCard();
					cUserCard.setUserId(user.getId());
					cUserCard.setPeriodId(courseDetail.getPeriodId());
					int count = cUserCardService.selectUserCard(cUserCard);
					if(count > 0){
						courseDetail.setCan_play("Y");
					}else {
						courseDetail.setCan_play("N");
					}
				}
			}
			return ResultUtil.success(courseDetail);
		} catch (Exception e) {
			logger.error(""+e);
			e.printStackTrace();
			return ResultUtil.error(ReturnCode.ERROR);
		}
		
	}
	
	/**
	 * 授权卡绑定
	 * @param domain
	 * @return
	 */
	@RequestMapping("authorizationCode")
	@ResponseBody
	public Result authorizationCode(CardDomain domain){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(checkNewUserToken(domain.getUserToken())){
			return ResultUtil.error(ReturnCode.TOKEN_NOT_VALID);
		}
		if(StringUtils.isEmpty(domain.getPwd())){
			return ResultUtil.error(ReturnCode.CARD_PW_NOT_NULL);
		} 
		//根据授权卡密码查询授权卡信息
		Card card = cardService.get(domain.getPwd());
		if (card==null) {
			return ResultUtil.error(ReturnCode.CARD_NOT_EXIST);
		}
		//查询授权卡使用次数
		int count = cUserCardService.getCardCount(card.getId());
		if(count>1){
			return ResultUtil.error(ReturnCode.CARD_USED);
		}
		//检查授权卡学段与用户学段是否相符
		if(!card.getPeriodId().equals(user.getPeriodId())){
			return ResultUtil.error(ReturnCode.CARD_CLASS_NOT_EXIST);
		}
		//进行权限控制查看用户是否已购买图书
		CUserCard cUserCard = new CUserCard();
		cUserCard.setUserId(user.getId());
		cUserCard.setPeriodId(card.getPeriodId());
		int cardCount = cUserCardService.selectUserCard(cUserCard);
		if(cardCount>0){
			return ResultUtil.error(ReturnCode.USER_OWNED);
		}
		//进行授权卡绑定
		cUserCard.setCardId(card.getId());
		try {
			int ret = cUserCardService.saveReturn(cUserCard);
			//修改授权卡使用次数
			card.setUseNum(card.getUseNum()+1);
			ret = cardService.saveReturn(card);
			if (ret<1) {
				 return ResultUtil.error(ReturnCode.CARD_BOND_ERROR);
			}
			return ResultUtil.error(ReturnCode.CARD_BOND_SUCCES);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(""+e);
			return ResultUtil.error(ReturnCode.CARD_BOND_ERROR);
		}
	}
	
	/**
	 * 
	*<pre>
	*<b>说明:</b> 扫码看视频
	*<b>日期:</b> 2016年7月14日 上午11:45:16
	 */
	@RequestMapping(value = "/courseDetail/{viewCode}")
	public ModelAndView downloadApp(@PathVariable String viewCode,HttpServletRequest request)
			throws ParseException, UnsupportedEncodingException {
		
		String vCode=viewCode;
		ModelAndView view = new ModelAndView("/downloadApp/downloadApp");
		return view;
	}
	private boolean checkBook(String bookId) {
		if (StringUtils.isEmpty(bookId)) {
			return true;
		}
		Month book = bookService.get(bookId);
		return book == null;
	}
	
	private boolean checkUserToken(String userToken) {
		if (StringUtils.isEmpty(userToken)) {
			user=null;
			return false;
		}
		CUser userBean = new CUser();
		userBean.setUserToken(userToken);
		user = userService.get(userBean);
		return user == null;
	}
	
	private boolean checkCourse(String courseId) {
		Course course = courseService.get(courseId);
		return course == null;
	}
	private boolean checkNewUserToken(String userToken) {
		if (StringUtils.isEmpty(userToken)) {
			return true;
		}
		CUser userBean = new CUser();
		userBean.setUserToken(userToken);
		user = userService.get(userBean);
		return user == null;
	}
}