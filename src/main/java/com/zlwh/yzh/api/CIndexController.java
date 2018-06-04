package com.zlwh.yzh.api;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.MetaItemTree;
import com.thinkgem.jeesite.modules.sys.service.MetaItemTreeService;
import com.zlwh.yzh.admin.modules.book.entity.Course;
import com.zlwh.yzh.admin.modules.book.entity.ResourceItem;
import com.zlwh.yzh.admin.modules.book.entity.ResponseResouceItem;
import com.zlwh.yzh.admin.modules.book.service.CourseService;
import com.zlwh.yzh.admin.modules.book.service.ResourceItemService;
import com.zlwh.yzh.admin.modules.fm.entity.FmShow;
import com.zlwh.yzh.admin.modules.fm.service.FmShowService;
import com.zlwh.yzh.admin.modules.user.entity.CUser;
import com.zlwh.yzh.admin.modules.user.service.CUserService;
import com.zlwh.yzh.api.common.ResultUtil;
import com.zlwh.yzh.api.common.ReturnCode;
import com.zlwh.yzh.api.common.ToolUtil;
import com.zlwh.yzh.api.domain.CUserDomain;
import com.zlwh.yzh.api.domain.CollectionDomain;
import com.zlwh.yzh.api.domain.base.Result;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/v1.0/index/")
public class CIndexController extends BaseController {
	
	//http://f.ezhihe.cn
	@Autowired
	CourseService courseService;
	@Autowired
	ResourceItemService resourceItemService;
	@Autowired
	FmShowService fmShowService;
	@Autowired
	CUserService userService;
	@Autowired
	MetaItemTreeService metaItemTreeService;
	//所有课程总数
	private static final long courseNum=22;
	private CUser user=null;
	/**
	 * 首页
	 * @return
	 */
	@RequestMapping(value="list")
	@ResponseBody
	public Map<String,Object> list(String periodId) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			//获取当天距离9月1日的间隔天数
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = simpleDateFormat.parse("2016-09-01");
			long days = DateUtils.pastDays(date);
			//除以所有课程总数取余，为了进行循环处理
			days = Math.abs(days) % courseNum;
			if(StringUtils.isEmpty(periodId)){
				periodId="0";
			}
			paramMap.put("periodId", periodId);
			paramMap.put("recommendSort", days+1);
			Course course = courseService.getIndexCourse(paramMap);
			if (course==null) {
				paramMap.put("recommendSort", "1");
				paramMap.put("periodId", periodId);
				course = courseService.getIndexCourse(paramMap);
			}
			resultMap.put("CODE", ReturnCode.SUCCESS.value());
			resultMap.put("MSG", "成功");
			resultMap.put("DATA", course);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("CODE", ReturnCode.ERROR.value());
			resultMap.put("MSG", "系统错误");
		}
		return resultMap;
	}
	
	/**
	 * (value="classid", required=false)
	 * @param classid
	 * @return
	 */
	@RequestMapping(value="getIndex",method = RequestMethod.POST)
	public Result getIndex(HttpServletRequest request,CUserDomain domain,@RequestParam(value="classid", required=false) String classid,
			@RequestParam(value="month", required=false) String month,
			@RequestParam(value="ItemId", required=false) String ItemId,
			@RequestParam(value="index", required=false) String index){
		Map<String,Object> resultMap = new LinkedHashMap<String, Object>();
		Object isClear = request.getAttribute("CLEAR_USER_PARAMES");
		if(isClear != null){
			domain.setUserId(null);
			domain.setUserToken(null);
			resultMap.put("exception", (Result)isClear);
		}
		
		if(domain.getUserToken()!=null && !"".equals(domain.getUserToken())){
			if (checkUserToken(domain.getUserToken())) {
				return ResultUtil.error(ReturnCode.TOKEN_NOT_VALID);
			}
		}
		
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		if(classid==null){
			classid = "0";
		}
		if(month==null){
			month = String.valueOf(cal.get(Calendar.MONTH )+1);
		}
		List<MetaItemTree> tagIndex = metaItemTreeService.findChildByIndex("d896d0c4ff254a10807ec0ad53cc60ee");
		List<MetaItemTree> tagsec = metaItemTreeService.findChildByIndex("7ad04bb9f1a149348f0ca671c1aa3c59");
		
		String userId = domain.getUserId() != null ? domain.getUserId() : null;
		List<Course> course = courseService.getCourseListByclassidAndMonth(classid,month,userId,index);//每日
		if(ItemId==null){
			ItemId = "00200101";
		}
		List<ResourceItem> resourceItems = resourceItemService.getResourceByAny(userId,null,classid,ItemId,index,null,null,null);//资源中心对应栏目
		List<ResponseResouceItem> responseResouceItem = new ArrayList<ResponseResouceItem>();
		for (ResourceItem resourceItem:resourceItems) {
			ResponseResouceItem r = new ResponseResouceItem();
			r.setId(resourceItem.getId());
			r.setClassId(resourceItem.getClassId());
			r.setClassName(resourceItem.getClassName());
			r.setTitle(resourceItem.getSysresource()==null?"null":resourceItem.getSysresource().getTitle());
			r.setFileName(resourceItem.getSysresource()==null?"null":resourceItem.getSysresource().getFileName());
			r.setFileUrl(resourceItem.getSysresource()==null?"null":resourceItem.getSysresource().getFileUrl());
			r.setIconUrl(resourceItem.getSysresource()==null?"null":resourceItem.getSysresource().getIconUrl());
			r.setThumbnailUrl(resourceItem.getSysresource()==null?"null":resourceItem.getSysresource().getThumbnailUrl());
			r.setResCode(resourceItem.getSysresource()==null?"null":resourceItem.getResCode());
//			r.setIsCollect(userService.isCollection(domain.getUserId(),resourceItem.getSysresource()==null?"null":resourceItem.getResCode())==1?"1":"0");
			r.setResType(resourceItem.getSysresource()==null?"null":resourceItem.getSysresource().getFileAuffix());
			r.setRemark(resourceItem.getSysresource()==null?"null":resourceItem.getSysresource().getRemark());
			r.setIsCharge(resourceItem.getSysresource()==null?"null":resourceItem.getSysresource().getIsCharge());
			responseResouceItem.add(r);
		}
		List<FmShow> fmShow = fmShowService.getByIndex();
		resultMap.put("tagIndex", tagIndex);
		resultMap.put("tagsec", tagsec);
		resultMap.put("resourceItem", responseResouceItem);
		resultMap.put("course", course);
		resultMap.put("fmShow", fmShow);
		return ResultUtil.success(resultMap);
	}
	
	@RequestMapping(value="getResouseItem",method = RequestMethod.POST)
	public Result getResouseItem(HttpServletRequest request,CollectionDomain domain,@RequestParam(value="classid", required=false) String classid,@RequestParam(value="ItemId", required=false) String ItemId,@RequestParam(value="index", required=false) String index){
		Map<String,Object> resultMap = new LinkedHashMap<String, Object>();
		Object isClear = request.getAttribute("CLEAR_USER_PARAMES");
		if(isClear != null){
			domain.setUserId(null);
			domain.setUserToken(null);
			resultMap.put("exception", (Result)isClear);
		}
		
		if(domain.getUserToken()!=null && !"".equals(domain.getUserToken())){
			if (checkUserToken(domain.getUserToken())) {
				return ResultUtil.error(ReturnCode.TOKEN_NOT_VALID);
			}
		}
		if(ItemId==null){
			ItemId = "00200101";
		}
		String userId = domain.getUserId() != null && !"".equals(domain.getUserId()) ? domain.getUserId() : null;
		
		List<ResourceItem> resourceItems = resourceItemService.getResourceByAny(userId,null,classid,ItemId,null,null,(domain.getOffset() - 1) * domain.getLimit(),domain.getLimit());//资源中心对应栏目
		List<ResponseResouceItem> responseResouceItem = new ArrayList<ResponseResouceItem>();
		for (ResourceItem resourceItem:resourceItems) {
			ResponseResouceItem r = new ResponseResouceItem();
			r.setId(resourceItem.getId());
			r.setClassId(resourceItem.getClassId());
			r.setClassName(resourceItem.getClassName());
			r.setTitle(resourceItem.getSysresource()==null?"null":resourceItem.getSysresource().getTitle());
			r.setFileName(resourceItem.getSysresource()==null?"null":resourceItem.getSysresource().getFileName());
			r.setFileUrl(resourceItem.getSysresource()==null?"null":resourceItem.getSysresource().getFileUrl());
			r.setIconUrl(resourceItem.getSysresource()==null?"null":resourceItem.getSysresource().getIconUrl());
			r.setThumbnailUrl(resourceItem.getSysresource()==null?"null":resourceItem.getSysresource().getThumbnailUrl());
			r.setResCode(resourceItem.getSysresource()==null?"null":resourceItem.getResCode());
//			r.setIsCollect(userService.isCollection(domain.getUserId(),resourceItem.getSysresource()==null?"null":resourceItem.getResCode())==1?"1":"0");
			r.setResType(resourceItem.getSysresource()==null?"null":resourceItem.getSysresource().getFileAuffix());
			r.setRemark(resourceItem.getSysresource()==null?"null":resourceItem.getSysresource().getRemark());
			r.setIsCharge(resourceItem.getSysresource()==null?"null":resourceItem.getSysresource().getIsCharge());
			responseResouceItem.add(r);
		}
		resultMap.put("resourceItem", responseResouceItem);
		int totalSize = resourceItemService.getResourceItemCount(classid,ItemId);
		int totalPage = ToolUtil.getPageSize(totalSize, domain.getLimit());
		resultMap.put("count", totalSize);
		resultMap.put("totalPage", totalPage);
		return ResultUtil.success(resultMap);
	}
	/**
	 * (value="classid", required=false)
	 * @param classid
	 * @return
	 */
	@RequestMapping(value="getEveryCourse",method = RequestMethod.POST)
	public Result getEveryCourse(HttpServletRequest request,CUserDomain domain,@RequestParam(value="classid", required=false) String classid,
									@RequestParam(value="month", required=true) String month){
		Map<String,Object> resultMap = new LinkedHashMap<String, Object>();
		Object isClear = request.getAttribute("CLEAR_USER_PARAMES");
		if(isClear != null){
			domain.setUserId(null);
			domain.setUserToken(null);
			resultMap.put("exception", (Result)isClear);
		}
		
		if(domain.getUserToken()!=null  && !"".equals(domain.getUserToken())){
			if (checkUserToken(domain.getUserToken())) {
				return ResultUtil.error(ReturnCode.TOKEN_NOT_VALID);
			}
		}
		if(classid==null){
			classid = "0";
		}
		
		String userId = domain.getUserId()!= null && !"".equals(domain.getUserId()) ? domain.getUserId() : null;
		List<Course> course = courseService.getCourseListByclassidAndMonth(classid,month,userId,null);//每日
		
		resultMap.put("course", course);
		return ResultUtil.success(resultMap);
	}

	/**
	 *一纸鹤扫码下载
	 * CommonController.java
	 * zhaoxin
	 * 下午3:25:19
	 * TODO
	 */
	@RequestMapping("downloadapp/yizhiheparent")
	public ModelAndView downloadAppYZH(HttpServletRequest request)
			throws ParseException, UnsupportedEncodingException {
		ModelAndView view = new ModelAndView("/downloadApp/downloadApp");
		return view;
	}
	private boolean checkUserToken(String userToken) {
		if (StringUtils.isEmpty(userToken)) {
			user= null;
			return false;
		}
		CUser userBean = new CUser();
		userBean.setUserToken(userToken);
		user = userService.get(userBean);
		return user == null;
	}
	/**
	 * 功能：提供对应设备的一纸鹤互动家园应用APP下载
	 * 
	 * @param request
	 * **/
	@RequestMapping("/downapp")
	public ModelAndView todownApp(HttpServletRequest request) {

		logger.info("提供对应设备的一纸鹤互动家园应用APP下载：" );
		ModelAndView view = new ModelAndView("/downloadApp/iosSafari");
		return view;
	}

}