package com.zlwh.yzh.api;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.hx.jfts.JFTSClient;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.products.entity.Products;
import com.thinkgem.jeesite.modules.products.service.ProductsService;
import com.thinkgem.jeesite.modules.sys.entity.MetaItemTree;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.MetaItemTreeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.zlwh.yzh.admin.modules.basis.entity.Feedback;
import com.zlwh.yzh.admin.modules.basis.entity.Resource;
import com.zlwh.yzh.admin.modules.basis.entity.SysResource;
import com.zlwh.yzh.admin.modules.basis.entity.Version;
import com.zlwh.yzh.admin.modules.basis.service.FeedbackService;
import com.zlwh.yzh.admin.modules.basis.service.ResourceService;
import com.zlwh.yzh.admin.modules.basis.service.SysResourceService;
import com.zlwh.yzh.admin.modules.basis.service.VersionService;
import com.zlwh.yzh.admin.modules.book.entity.Course;
import com.zlwh.yzh.admin.modules.book.entity.ResourceItem;
import com.zlwh.yzh.admin.modules.book.service.MonthService;
import com.zlwh.yzh.admin.modules.book.service.CourseService;
import com.zlwh.yzh.admin.modules.book.service.ResourceItemService;
import com.zlwh.yzh.admin.modules.fm.entity.FmShow;
import com.zlwh.yzh.admin.modules.fm.service.FmShowService;
import com.zlwh.yzh.admin.modules.user.entity.CEvaluation;
import com.zlwh.yzh.admin.modules.user.entity.CUser;
import com.zlwh.yzh.admin.modules.user.entity.CUserCollection;
import com.zlwh.yzh.admin.modules.user.entity.CUserRecord;
import com.zlwh.yzh.admin.modules.user.service.CUserCollectionService;
import com.zlwh.yzh.admin.modules.user.service.CEvaluationService;
import com.zlwh.yzh.admin.modules.user.service.CUserRecordService;
import com.zlwh.yzh.admin.modules.user.service.CUserService;
import com.zlwh.yzh.api.common.ResultUtil;
import com.zlwh.yzh.api.common.ReturnCode;
import com.zlwh.yzh.api.common.ToolUtil;
import com.zlwh.yzh.api.domain.CEvaluationDomain;
import com.zlwh.yzh.api.domain.CUserDomain;
import com.zlwh.yzh.api.domain.CUserRecordDomain;
import com.zlwh.yzh.api.domain.CollectionDomain;
import com.zlwh.yzh.api.domain.CourseDomain;
import com.zlwh.yzh.api.domain.FeedbackDomain;
import com.zlwh.yzh.api.domain.SearchDomain;
import com.zlwh.yzh.api.domain.UserRecordDomain;
import com.zlwh.yzh.api.domain.base.Result;

/**
 * 
 * @author hcq
 *@date 2018年1月6日
 * @version 1.0
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "v1.0/users/")
public class CUserController extends BaseController {
	public static final String TELNUM = "139,138,137,136,135,134,147,188,187,184,183,182,178,159,158,157,152,151,150,186,185,176,145,156,155,132,131,130,189,181,180,177,153,133,189,133";
	public static final String HOME_SHOW_FILE_TYPE = "mp3,mp4,jpg,png,jpeg,gif";
	public static final long HOME_SHOW_FILE_MAX_SIZE = Global.getUploadSize();
	
	@Autowired
	CUserService userService;
	@Autowired
	CourseService courseService;
	@Autowired
	CUserCollectionService userCollectionService;
	@Autowired
	CEvaluationService CEvaluationService;
	@Autowired
	CUserRecordService userRecordService;
	@Autowired
	MonthService bookService;
	@Autowired
	SysResourceService sysResourceService;
	@Autowired
	FeedbackService feedbackService;
	@Autowired
	VersionService versionService;
	@Autowired
	ProductsService productsService;
	@Autowired
	FmShowService fmShowService;
	@Autowired
	MetaItemTreeService metaItemTreeService;
	@Autowired
	ResourceItemService resourceItemService;
	private CUser user = null;
	
	

	/**
	 * 查询用户信息
	 * @param domain
	 * @return
	 */
	@RequestMapping(value="userInfo")
	@ResponseBody
	public Result userInfo(CUserDomain domain){
		if (checkUserToken(domain.getUserToken())) {
			return ResultUtil.error(ReturnCode.PARAM_NOT_VALID);
		}
		try {
			CUser userInfo= userService.get(domain.getUserId());
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("imageUrl", userInfo.getImageUrl());//头像
			dataMap.put("nickName", userInfo.getNickName());//昵称
			dataMap.put("loginName", userInfo.getLoginName());//用户名
			dataMap.put("periodId", userInfo.getPeriodId());//学段 0-小班1中班2大班
			return ResultUtil.success(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.error(ReturnCode.ERROR);
		}
	}
	/**
	 * 修改用户信息
	 * @param request
	 * @param domain
	 * @return
	 */
	@RequestMapping(value="updateUserInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result updateUserInfo( CUserDomain domain,HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (checkUserToken(domain.getUserToken())) {
			return ResultUtil.error(ReturnCode.PARAM_NOT_VALID);
		}
		
		CUser cUser = new CUser();
		cUser.setId(domain.getUserId());
		cUser.setPeriodId(domain.getPeriodId()!=null?domain.getPeriodId():"");
		cUser.setNickName(domain.getNickName()!=null?domain.getNickName():"");
		cUser.setAddr(domain.getAddr()!=null?domain.getAddr():"");
		cUser.setTrueName(domain.getTrueName()!=null?domain.getTrueName():"");
		cUser.setSex(domain.getSex()!=null?domain.getSex():"");
		cUser.setImageUrl(domain.getImageUrl()!=null?domain.getImageUrl():"");
		try {
//			Map<String,Object> fileInfo = doUpload(request);
//			if (fileInfo != null&&fileInfo.get("url")!=null) {
//				cUser.setImageId(String.valueOf(fileInfo.get("id")));
//				cUser.setImageUrl(String.valueOf(fileInfo.get("url")));
//			}
			userService.save(cUser);
			return ResultUtil.success();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(""+e);
			resultMap.put("CODE", ReturnCode.ERROR.value());
			resultMap.put("MSG", "修改信息失败");
			return ResultUtil.error(ReturnCode.UNSUCCESS);
		}
	}
	
	/**
	 * 星级评价
	 * @param domain
	 * @return
	 */
	@RequestMapping(value="star",method = RequestMethod.POST)
	public Result star(CEvaluationDomain domain){
		if (checkUserToken(domain.getUserToken())) {
			return ResultUtil.error(ReturnCode.TOKEN_NOT_VALID);
		}
		if (checkResourse(domain.getResCode())) {
			return ResultUtil.error(ReturnCode.COURCES_NOT_FOUND);
		}
			CEvaluation cEvaluation = new CEvaluation();
			cEvaluation.setResCode(domain.getResCode());
			cEvaluation.setUserId(domain.getUserId());
			cEvaluation.setScore(String.valueOf(domain.getStar()));
			CEvaluation evaluation = CEvaluationService.getByResCodeAndUid(cEvaluation);
			if(evaluation!=null){
				CEvaluationService.delete(evaluation);
				CEvaluationService.save(cEvaluation);
				return ResultUtil.success(cEvaluation);
			}else{
				CEvaluationService.save(cEvaluation);
				return ResultUtil.success(cEvaluation);
			}
			
	}
	
	/**
	 * 
	*<pre>
	*<b>说明:</b> 记录视频浏览记录
	*<b>日期:</b> 2016年6月3日 下午1:22:05
	*userToken 必填 courseId 必填
	 */
	@RequestMapping(value="videoRecord",method = RequestMethod.POST)
	@ResponseBody
	public Result videoRecord(CUserRecordDomain domain){
		if (checkUserToken(domain.getUserToken())) {
			return ResultUtil.error(ReturnCode.TOKEN_NOT_VALID);
		}
		if (checkResourse(domain.getCourseId())) {
			return ResultUtil.error(ReturnCode.COURCES_NOT_FOUND);
		}
		CUserRecord userRecord = new CUserRecord();
		userRecord.setUserId(domain.getUserId());
		userRecord.setCourseId(domain.getCourseId());
		
		try {
			List<String> recordList = userRecordService.checkTodayRecord(userRecord);
			if(recordList==null||recordList.size()<1){
				userRecordService.save(userRecord);
			}else {
				userRecord.setId(recordList.get(0));
				userRecordService.save(userRecord);
			}
			return ResultUtil.success();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(""+e);
			return ResultUtil.error(ReturnCode.UNSUCCESS);
		}
	}
	/**
	 * 
	*<pre>
	*<b>说明:</b> 我的学习记录
	*userToken  
	*<b>日期:</b> 2016年6月3日 下午2:30:39
	 */
	@RequestMapping(value="userRecordList",method = RequestMethod.POST)
	@ResponseBody
	public Result userRecordList(UserRecordDomain userRecordDomain){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (checkUserToken(userRecordDomain.getUserToken())) {
			resultMap.put("CODE", ReturnCode.PARAM_NOT_VALID.value());
			resultMap.put("MSG", "用户Token不合法");
			return ResultUtil.error(ReturnCode.TOKEN_NOT_VALID);
		}
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<Course> recordList;
		try {
			CUserRecord userRecord = new CUserRecord();
			userRecord.setLimit(userRecordDomain.getLimit());
			userRecord.setOffset((userRecordDomain.getOffset() - 1) * userRecordDomain.getLimit());
			userRecord.setUserId(userRecordDomain.getUserId());
			Date date = sdFormat.parse(sdFormat.format(userRecordDomain.getQueryTime()));
			userRecord.setSearchDate(date);
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        userRecord.setStart(cal.getTimeInMillis() / 1000);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			userRecord.setEnd(cal.getTimeInMillis() / 1000);
			recordList = userRecordService.getRecordList(userRecord);
			Map<String, Object> result = new LinkedHashMap<String, Object>();
			int totalSize =  userRecordService.getRecordListCount(userRecord);
			int pageSize = userRecordDomain.getLimit();
			int totalPage = ToolUtil.getPageSize(totalSize, pageSize);
			result.put("results", recordList);
			result.put("count", totalSize);
			result.put("totalPage", totalPage);
			
			return ResultUtil.success(result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(""+e);
			return ResultUtil.error(ReturnCode.UNSUCCESS);
		}
	}
	public static void main(String[] args) throws ParseException {
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");  
        Long time= Long.parseLong("1516377600000");
        String d = format.format(time);  
        Date date = format.parse(d);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, 1);
        System.out.println(cal.getTime().getTime());
        System.out.println("Format To String(Date):"+d);  
        System.out.println("Format To Date:"+date);  
        System.out.println("Format To Date:"+date.getTime());  
	}
	/**
	 * 
	*<pre>
	*<b>说明:</b> 清空学习记录
	*<b>日期:</b> 2016年7月7日 上午11:08:01
	 */
	@RequestMapping(value="deleteAllRecord", method = RequestMethod.POST)
	@ResponseBody
	public Result deleteAllRecord(UserRecordDomain userRecordDomain){
		if (checkUserToken(userRecordDomain.getUserToken())) {
			return ResultUtil.error(ReturnCode.TOKEN_NOT_VALID);
		}
		CUserRecord userRecord = new CUserRecord();
		userRecord.setUserId(userRecordDomain.getUserId());
		try {
			userRecordService.deleteAllRecord(userRecord);
			return ResultUtil.success();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(""+e);
			return ResultUtil.error(ReturnCode.UNSUCCESS);
		}
	}
	/**
	 * 获取资源中心资源
	 * @param userid
	 * @param userToken
	 * @param resCode
	 * @return
	 */
	@RequestMapping(value="getSingelRes",method = RequestMethod.POST)
	public Result getSingelRes(HttpServletRequest request,@RequestParam(value="userId", required=false) String userId,
			@RequestParam(value="userToken", required=false) String userToken,
			@RequestParam(value="id", required=true) String id,
			@RequestParam(value="classId", required=true) String classId){
		Map<String,Object> resultMap = new LinkedHashMap<String, Object>();
		Object isClear = request.getAttribute("CLEAR_USER_PARAMES");
		if(isClear != null){
			userId = null;
			userToken = null;
			resultMap.put("exception", (Result)isClear);
		}
		
		if(userToken!= null && !"".equals(userToken)){
			if (checkUserToken(userToken)) {
				return ResultUtil.error(ReturnCode.TOKEN_NOT_VALID);
			}
		}
		String userid = userId!= null && !"".equals(userId) ? userId : null;
		List<ResourceItem> resourceList = resourceItemService.getResourceByAny(userid,id,null,null,null,null,null,null);
		ResourceItem resourceItem = null;
		if(resourceList != null && resourceList.size() > 0){
			resourceItem = resourceList.get(0);
		} 
		resultMap.put("results", resourceItem);
		return ResultUtil.success(resultMap);
	}
	/**
	 * 获取资源收藏评星数据
	 * @param userid
	 * @param userToken
	 * @param resCode
	 * @return
	 */
	@RequestMapping(value="getSingelCourse",method = RequestMethod.POST)
	public Result getSingelCourse(HttpServletRequest request,@RequestParam(value="userId", required=false) String userId,
			@RequestParam(value="userToken", required=false) String userToken,
			@RequestParam(value="id", required=true) String id,
			@RequestParam(value="classId", required=true) String classId,
			@RequestParam(value="sort", required=true) int sort){
		
		Map<String,Object> resultMap = new LinkedHashMap<String, Object>();
		Object isClear = request.getAttribute("CLEAR_USER_PARAMES");
		if(isClear != null){
			userId = null;
			userToken = null;
			resultMap.put("exception", (Result)isClear);
		}
		
		if(userToken!= null && !"".equals(userToken)){
			if (checkUserToken(userToken)) {
				return ResultUtil.error(ReturnCode.TOKEN_NOT_VALID);
			}
		}
		String userid = userId!= null && !"".equals(userId) ? userId : null;
		Course singleRes = courseService.getSingleRes(id, userId, classId);
		List<Course> course = courseService.getCourseListByclassidAndMonthBySinglDay(classId,sort,userid);//每日
		resultMap.put("result", singleRes);
		resultMap.put("course", course);
		return ResultUtil.success(resultMap);
	}
	
	@RequestMapping(value="getRecentHot",method = RequestMethod.POST)
	public Result getRecentHot(HttpServletRequest request,@RequestParam(value="userId", required=false) String userId,@RequestParam(value="userToken", required=false) String userToken){
		Map<String,Object> resultMap = new LinkedHashMap<String, Object>();
		Object isClear = request.getAttribute("CLEAR_USER_PARAMES");
		if(isClear != null){
			userId = null;
			userToken = null;
			resultMap.put("exception", (Result)isClear);
		}
		
		if(userToken!= null && !"".equals(userToken)){
			if (checkUserToken(userToken)) {
				return ResultUtil.error(ReturnCode.TOKEN_NOT_VALID);
			}
		}
		List<SysResource> list = sysResourceService.getRecentHot(userId);
		resultMap.put("results", list);
		return ResultUtil.success(resultMap);
	}
	
	/**
	 * 收藏作品
	 * @param domain
	 * @return
	 */
	@RequestMapping(value="collection",method = RequestMethod.POST)
	public Result collection(CollectionDomain domain) {
		if (checkUserToken(domain.getUserToken())) {
			return ResultUtil.error(ReturnCode.TOKEN_NOT_VALID);
		}
		if (checkResourse(domain.getCourseId())) {
			return  ResultUtil.error(ReturnCode.COURCES_NOT_FOUND);
		}
		try {
			domain.setUserId(domain.getUserId());
			int result = userService.collection(domain);
			if (result == 1) {
				logger.info("已经收藏该{},请求参数：{}",("课程"),domain);
				return ResultUtil.error(ReturnCode.COURCES_IS_COLLECT);
			}
			return ResultUtil.success();
		} catch (Exception e) {
			logger.error(""+e);
			return ResultUtil.error(ReturnCode.UNSUCCESS);
		}
	}
	/**
	 * 
	*<pre>
	*<b>说明:</b> 批量取消收藏
	*<b>日期:</b> 2016年6月12日 下午4:51:56
	*user  id 
	 */
	@RequestMapping(value="cancelCollection",method = RequestMethod.POST)
	@ResponseBody
	public Result cancelCollection(CollectionDomain domain){
		CUserCollection cUserCollection = new CUserCollection();
		if (checkUserToken(domain.getUserToken())) {
			return ResultUtil.error(ReturnCode.TOKEN_NOT_VALID);
		}
		List<String>courseList = domain.getCourseList();
		if(courseList==null||courseList.size()<1){
			return ResultUtil.error(ReturnCode.COURCESPARM_NOT_VALID);
		}
		int ret=0;
		try {
			for(int i=0;i<courseList.size();i++){
				cUserCollection.setUserId(domain.getUserId());
				cUserCollection.setCourseId(courseList.get(i));
				ret+=userCollectionService.deleteReturn(cUserCollection);
			}
			if(ret>0){
				return ResultUtil.success();
			}else {
				return ResultUtil.error(ReturnCode.COURCES_NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(""+e);
			return ResultUtil.error(ReturnCode.ERROR);
		}
		
	}
	/**
	 * 
	*<pre>
	*<b>说明:</b> 批量删除收藏
	*<b>日期:</b> 2016年6月12日 下午4:51:56
	*user  id 
	 */
	@RequestMapping(value="deleteCollection",method = RequestMethod.POST)
	@ResponseBody
	public Result deleteCollection(CollectionDomain domain){
		CUserCollection cUserCollection = new CUserCollection();
		if (checkUserToken(domain.getUserToken())) {
			return ResultUtil.error(ReturnCode.TOKEN_NOT_VALID);
		}
		List<String>courseList = domain.getCourseList();
		if(courseList==null||courseList.size()<1){
			return ResultUtil.error(ReturnCode.COURCESPARM_NOT_VALID);
		}
		int ret=0;
		try {
			for(int i=0;i<courseList.size();i++){
				cUserCollection.setUserId(domain.getUserId());
				cUserCollection.setCourseId(courseList.get(i));
				ret+=userCollectionService.deleteReturn(cUserCollection);
			}
			if(ret>0){
				return ResultUtil.success();
			}else {
				return ResultUtil.error(ReturnCode.COURCES_NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(""+e);
			return ResultUtil.error(ReturnCode.ERROR);
		}
	}
	/**
	 * 收藏列表
	 * @param domain
	 * @return
	 */
	@RequestMapping(value="collectionList",method = RequestMethod.POST)
	@ResponseBody
	public Result collectionList(CollectionDomain domain) {
		if (checkUserToken(domain.getUserToken())) {
			return ResultUtil.error(ReturnCode.TOKEN_NOT_VALID);
		}
		try {
			Map<String, Object> listMap = new LinkedHashMap<String, Object>();
			CUserCollection collectionBean = new CUserCollection();
			collectionBean.setUserId(domain.getUserId());
			collectionBean.setLimit(domain.getLimit());
			collectionBean.setOffset((domain.getOffset() - 1) * domain.getLimit());
			List<Course> dataList = userCollectionService.getUserCollectionList(collectionBean);
			listMap.put("results", dataList);
			int totalSize = userCollectionService.getCollectionCount(domain.getUserId());
			int totalPage = ToolUtil.getPageSize(totalSize, domain.getLimit());
			listMap.put("count", totalSize);
			listMap.put("totalPage", totalPage);
			return ResultUtil.success(listMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(""+e);
			return ResultUtil.error(ReturnCode.UNSUCCESS);
		}
	}

	/**
	 * 获取商品
	 * @return
	 */
	@RequestMapping(value="getProducts",method = RequestMethod.POST)
	public Result getProducts(@RequestParam(value="userId", required=true) String userid,
			@RequestParam(value="userToken", required=true) String userToken,
			@RequestParam(value="psncode", required=false) String psncode
			){
		if (checkUserToken(userToken)) {
			return ResultUtil.error(ReturnCode.TOKEN_NOT_VALID);
		}
		List products = productsService.findList(new Products());
		return ResultUtil.success(products);
	}
	

	/**
	 * 资源浏览数
	 * @param courseId
	 * @return
	 */
	@RequestMapping(value="addClick",method = RequestMethod.POST)
	public Result addClick(CUserRecordDomain domain){
		//增加视频点击数
		sysResourceService.addClicksNum(domain.getCourseId());
		return ResultUtil.success();
	}
	
	/**
	 * 家家秀上传审核结果
	 * @param userid
	 * @param userToken
	 * @param resCode
	 * @return
	 */
	@RequestMapping(value="auditList",method = RequestMethod.POST)
	public Result auditList(SearchDomain domain){
		if (checkUserToken(domain.getUserToken())) {
			return ResultUtil.error(ReturnCode.TOKEN_NOT_VALID);
		}
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		
		List<FmShow> auditList = fmShowService.getAuditList(domain);
		
		int totalSize =  fmShowService.getAuditListCount(domain);
		int pageSize = domain.getLimit();
		int totalPage = ToolUtil.getPageSize(totalSize, pageSize);
		result.put("results", auditList);
		result.put("count", totalSize);
		result.put("totalPage", totalPage);
		return ResultUtil.success(result);
	}
	
	/**
	 * 搜索列表
	 * @param userid
	 * @param userToken
	 * @param resCode
	 * @return
	 */
	@RequestMapping(value="searchList",method = RequestMethod.POST)
	public Result searchList(HttpServletRequest request,SearchDomain domain){
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		Object isClear = request.getAttribute("CLEAR_USER_PARAMES");
		if(isClear != null){
			domain.setUserId(null);
			domain.setUserToken(null);
			result.put("exception", (Result)isClear);
		}
		
		if(domain.getUserToken() != null && !"".equals(domain.getUserToken())){
			if (checkUserToken(domain.getUserToken())) {
				return ResultUtil.error(ReturnCode.TOKEN_NOT_VALID);
			}	
		}
		String userId = domain.getUserId() != null  && !"".equals(domain.getUserId()) ? domain.getUserId() : null ;
		Integer pageNo = (domain.getOffset() - 1) * domain.getLimit();
		List<ResourceItem> searchList = resourceItemService.getResourceByAny(userId,null,null,null,null,domain.getTitle(),pageNo,domain.getLimit());
		int totalSize =  resourceItemService.getSearchListCount(domain.getTitle());
		int pageSize = domain.getLimit();
		int totalPage = ToolUtil.getPageSize(totalSize, pageSize);
		result.put("results", searchList);
		result.put("count", totalSize);
		result.put("totalPage", totalPage);
		return ResultUtil.success(result);
	}
	
	/**
	 * 家家秀首页
	 * @param domain
	 * @return
	 */
	@RequestMapping(value="homeShowIndex",method = RequestMethod.POST)
	public Result homeShowIndex() {
		try {
			List<FmShow> list = fmShowService.getHomeShowIndexList();
			return ResultUtil.success(list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(""+e);
			return ResultUtil.error(ReturnCode.UNSUCCESS);
		}
	}
	
	
	/**
	 * 家家秀子目录数据
	 * @param domain
	 * @return
	 */
	@RequestMapping(value="homeShowList",method = RequestMethod.POST)
	public Result homeShowList(CollectionDomain domain,@RequestParam(value="resCode", required=true)String resCode) {
		try {
			Integer pageNo = (domain.getOffset() - 1) * domain.getLimit();
			List<FmShow> list = fmShowService.getHomeShowListByResCode(resCode, pageNo, domain.getLimit());
			Map<String, Object> result = new LinkedHashMap<String, Object>();
			int totalSize =  fmShowService.getHomeShowCountByResCode(resCode);
			int pageSize = domain.getLimit();
			int totalPage = ToolUtil.getPageSize(totalSize, pageSize);
			result.put("results", list);
			result.put("count", totalSize);
			result.put("totalPage", totalPage);
			MetaItemTree mt = metaItemTreeService.getByCode(resCode);
			if(mt != null){
				result.put("parentName", mt.getName());
				result.put("showText", mt.getRemarks());
			}
			return ResultUtil.success(result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(""+e);
			return ResultUtil.error(ReturnCode.UNSUCCESS);
		}
	}
	
	/**
	 * 家家秀内页资源详情
	 * @param domain
	 * @return
	 */
	@RequestMapping(value="homeShowResourceInfo",method = RequestMethod.POST)
	public Result homeShowResourceInfo(CollectionDomain domain,@RequestParam(value="id", required=true)String id) {
		String userId = domain.getUserId() != null && !"".equals(domain.getUserId()) ? domain.getUserId() : null;
		try {
			FmShow fmShow = fmShowService.get(id,userId);
			Map<String, Object> result = new LinkedHashMap<String, Object>();
			result.put("result", fmShow);
			result.put("parentName", metaItemTreeService.getByCode(fmShow.getResCode()).getName());
			return ResultUtil.success(result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(""+e);
			return ResultUtil.error(ReturnCode.UNSUCCESS);
		}
	}
	
	/**
	 * 家家秀大家都在看
	 * @param userId
	 * @param userToken
	 * @return
	 */
	@RequestMapping(value="getHomeShowRecentHot",method = RequestMethod.POST)
	public Result getHomeShowRecentHot(){
		List<FmShow> list = fmShowService.getRecentHot();
		return ResultUtil.success(list);
	}
	
	/**
	 * 家家秀增加点击次数
	 * @param userId
	 * @param userToken
	 * @return
	 */
	@RequestMapping(value="homeShowAddClick",method = RequestMethod.POST)
	public Result homeShowAddClick(@RequestParam(value="id", required=false)String id){
		fmShowService.addClick(id);
		return ResultUtil.success();
	}
	
	/**
	 * 家家秀马上上传
	 * @param domain
	 * @return
	 */
	@RequestMapping(value="uploadNow",method = RequestMethod.POST)
	public Result uploadNow(CollectionDomain domain) {
		try {
			List<MetaItemTree> list = metaItemTreeService.findByParentId(metaItemTreeService.getByCode("002003").getId());
			Map<String, Object> result = new LinkedHashMap<String, Object>();
			result.put("result", list);
			result.put("maxSize", HOME_SHOW_FILE_MAX_SIZE);
			result.put("fileType", HOME_SHOW_FILE_TYPE);
			return ResultUtil.success(result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(""+e);
			return ResultUtil.error(ReturnCode.UNSUCCESS);
		}
	}
	
	/**
	 * 家家秀上传资源
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="homeShowUpload",method=RequestMethod.POST) 
	public Result homeShowUpload(CollectionDomain domain,HttpServletRequest request,@RequestParam(value="resCode", required=true)String resCode) throws Exception {
		if (checkUserToken(domain.getUserToken())) {
			return ResultUtil.error(ReturnCode.TOKEN_NOT_VALID);
		}
		List<String> fileList = Arrays.asList(HOME_SHOW_FILE_TYPE.split(","));
		boolean un_success_num = false;
		
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		InputStream input = null;
		SimpleDateFormat times = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String code = times.format(new Date());
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					// 取得当前上传文件的文件名称
					String originName = file.getOriginalFilename();// 原始文件名

					// 如果名称不为"",说明该文件存在，否则说明该文件不存在
					if (originName.trim() != "") {
						if(fileList.contains(originName.substring(originName.lastIndexOf(".") + 1).toLowerCase())){
							try {
								input = file.getInputStream();
								JFTSClient jftsClient = new JFTSClient();
								ResourceBundle.getBundle("jfts-client");
								String lid = IdGen.uuid();
								String remoteFileName = originName.substring(originName.lastIndexOf(".") + 1);
								boolean isSucc = jftsClient.uploadFile1(input, lid + "." + remoteFileName, domain.getUserId() + "/" + code);
								if (isSucc) {
									FmShow fs = new FmShow();
									fs.setName(originName.substring(0,originName.lastIndexOf(".")));
									fs.setResCode(resCode);
									fs.setFileUrl(domain.getUserId() + "/" + code + "/" + lid + "." + remoteFileName);
									if(remoteFileName.toLowerCase().equals("mp4")){
										fs.setIconUrl(domain.getUserId() + "/" + code + "/" + lid + ".png");
										fs.setThumbUrl(domain.getUserId() + "/" + code + "/" + lid + "_thumb.jpg");
									}
									fs.setUid(domain.getUserId());
									fs.setAuditStatus("0");
									fmShowService.save(fs);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}else{
							un_success_num = true;
						}
					}
				}
			}
			if (input != null) {
				input.close();
			}
			return un_success_num ? ResultUtil.error("文件不含不支持的类型") : ResultUtil.success();
		} else {
			ResultUtil.error(ReturnCode.FILE_IS_NULL);
		}
		return ResultUtil.error(ReturnCode.UNSUCCESS);
	}
	
	/**
	 * 家家秀上传资源
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="homeShowUpload2",method=RequestMethod.POST) 
	public Result homeShowUpload2(CollectionDomain domain,@RequestParam(value="files", required=true)MultipartFile[] files,@RequestParam(value="resCode", required=true)String resCode) throws Exception {
		if (checkUserToken(domain.getUserToken())) {
			return ResultUtil.error(ReturnCode.TOKEN_NOT_VALID);
		}
		
		InputStream input = null;
		SimpleDateFormat times = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String code = times.format(new Date());
		
		if(files != null && files.length > 0){
			for(MultipartFile file : files){
				if (file != null) {
					// 取得当前上传文件的文件名称
					String originName = file.getOriginalFilename();// 原始文件名

					// 如果名称不为"",说明该文件存在，否则说明该文件不存在
					if (originName.trim() != "") {
						try {
							input = file.getInputStream();
							JFTSClient jftsClient = new JFTSClient();
							ResourceBundle.getBundle("jfts-client");
							String lid = IdGen.uuid();
							String remoteFileName = originName.substring(originName.lastIndexOf(".") + 1);
							boolean isSucc = jftsClient.uploadFile1(input, lid + "." + remoteFileName, domain.getUserId() + "/" + code);
							if (isSucc) {
								FmShow fs = new FmShow();
								fs.setName(originName.substring(0,originName.lastIndexOf(".")));
								fs.setResCode(resCode);
								fs.setFileUrl("/" + domain.getUserId() + "/" + code + "/" + lid + "/" + lid + "." + remoteFileName);
								if(remoteFileName.toLowerCase().equals("mp4")){
									fs.setIconUrl("/" + domain.getUserId() + "/" + code + "/" + lid + "/" + lid + ".jpg");
									fs.setThumbUrl("/" + domain.getUserId() + "/" + code + "/" + lid + "/" + lid + "_thumb.jpg");
								}else if(remoteFileName.toLowerCase().equals("png") || remoteFileName.toLowerCase().equals("jpg")||remoteFileName.toLowerCase().equals("gif")||remoteFileName.toLowerCase().equals("jpeg")){
									fs.setIconUrl("/" + domain.getUserId() + "/" + code + "/" + lid + "/" + lid + "." + remoteFileName);
								}
								fs.setUid(domain.getUserId());
								fs.setAuditStatus("1");
								fs.setClickNum(0);
								fmShowService.save(fs);
							}else{
								return ResultUtil.error("服务器异常，请稍后尝试");
							}
						} catch (Exception e) {
							e.printStackTrace();
							return ResultUtil.error("服务器异常，请稍后尝试");
						}finally{
							if (input != null) {
								input.close();
							}
						}
					}
				}
			}
			return ResultUtil.success();
			
		} else {
			ResultUtil.error(ReturnCode.FILE_IS_NULL);
		}
		return ResultUtil.error(ReturnCode.UNSUCCESS);
	}
	
	
	
	/**
	*<pre>
	*<b>说明:</b> 帮助与反馈
	*<b>日期:</b> 2016年6月3日 下午3:50:47
	*userToken content email name  phone
	 */
	@RequestMapping(value="feedBack",method = RequestMethod.POST)
	@ResponseBody
	public Result feedBack(FeedbackDomain domain){
		if (checkUserToken(domain.getUserToken())) {
			return ResultUtil.error(ReturnCode.TOKEN_NOT_VALID);
		}
		if (checkMobile(domain.getPhone())) {
			return ResultUtil.error(ReturnCode.PARAM_NOT_VALID);
		}
		Feedback feedback = new Feedback();
		feedback.setUserId(domain.getUserId());
		feedback.setContent(domain.getContent());
		feedback.setEmail(domain.getEmail());
		feedback.setName(domain.getName());
		feedback.setPhone(domain.getPhone());
		feedback.setStatus("0");
		try {
			feedbackService.save(feedback);
			return ResultUtil.success();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultUtil.error(ReturnCode.ERROR);
		}
		
	}
	/**
	 * 
	*<pre>
	*<b>说明:</b> 检查版本
	*<b>日期:</b> 2016年6月4日 上午9:57:23
	 */
	@RequestMapping(value="version")
	@ResponseBody
	public Result version(Version version){
		 
		 try {
			Map<String, String> versionInfo =versionService.getVertion(version);
			if (versionInfo==null||versionInfo.size()==0) {
				return ResultUtil.error(ReturnCode.VERSION_NOT_FOUND);
			}
			return ResultUtil.success(versionInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultUtil.error(ReturnCode.ERROR);
		}
		
		
	}
	private boolean checkUserToken(String userToken) {
		if (StringUtils.isBlank(userToken)) {
			return true;
		}
		CUser userBean = new CUser();
		userBean.setUserToken(userToken);
		user = userService.get(userBean);
		return user == null;
	}
	private void addClick(String userid,String userToken,String resCode){
		SysResource resource = sysResourceService.getByResCode(resCode);
		resource.setIsNewRecord(false);
		sysResourceService.updateClickNum(resource);
	}
//	private boolean checkCourse(String courseId) {
//		if (StringUtils.isEmpty(courseId)) {
//			return true;
//		}
//		Course course = courseService.getByCourseCode(courseId);
//		return course == null;
//	}
	private boolean checkResourse(String rescode) {
		if (StringUtils.isEmpty(rescode)) {
			return true;
		}
		SysResource course = sysResourceService.getByResCode(rescode);
		FmShow fmShow = fmShowService.get(rescode);
		return course == null && fmShow == null;
	}
	@RequestMapping(value="uploadFile",method=RequestMethod.POST) 
	@ResponseBody  
	public Map<String,Object> uploadFile(@RequestParam("pic")CommonsMultipartFile pic,HttpServletRequest req,HttpServletResponse response) throws IOException{  
		User user = UserUtils.getUser();
		String message="fail";
		Map<String, Object> map = new HashMap<String,Object>();
		if(user.getId() != null && !"".equals(user.getId())){
			String fileName = pic.getOriginalFilename();  
			//String name=fileName.substring(0, fileName.lastIndexOf("."));
			String Suffix=fileName.substring(fileName.lastIndexOf(".")+1,fileName.length()).toLowerCase();
			InputStream input=null;
			InputStream pptorpdfinput=null;
			String path="";
			String returnSuffix="";//转换后后缀
			SimpleDateFormat times=new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String code =times.format(new Date());
			String exitfilename="";
			String exitpath="";
			int pageSize=0;
			String originalSuffix="";
			String savefilename=code;
	        try{
	         	String filepath_name=user.getOffice().getId();
				if(Suffix.equals("doc") || Suffix.equals("docx")){
					returnSuffix="swf";
					originalSuffix="swf";
					path="/"+filepath_name+"/"+code+"/"+code+"."+returnSuffix;
					//name=fileName;
				}else{
					returnSuffix=Suffix;
					originalSuffix=Suffix;
					path="/"+filepath_name+"/"+code+"/"+code+"."+returnSuffix;
				}
				exitfilename=savefilename+"."+returnSuffix;
				exitpath=filepath_name+"/"+code;
				input=pic.getInputStream();
				JFTSClient jftsClient=new JFTSClient();
				ResourceBundle.getBundle("jfts-client");
				
				System.out.println("--------------"+input.available());
				System.out.println(code+"."+Suffix+"--------------"+filepath_name);
				boolean isSucc=jftsClient.uploadFile1(input,code+"."+Suffix, "/"+filepath_name);
				
				if(isSucc){
					message="succ";
				}
	         }catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(pptorpdfinput!=null){
					pptorpdfinput.close();
				}
				if(input!=null){
					input.close();
				}
			}
	        map.put("filename", fileName);
	        map.put("path", path);
	        map.put("exitfilename", exitfilename);
	        map.put("exitpath", exitpath);
	        /*map.put("suffix", returnSuffix);*/
	        map.put("suffix", originalSuffix);
	        
	        map.put("code", code);
		}else {
			message="nologin";
		}   
		map.put("message", message);
 		//为了避免文件名重复，在文件名前加UUID  
		/*String uuid = UUID.randomUUID().toString().replace("-","");  
		String uuidFileName = uuid + fileName;  */
		//将文件保存到服务器  
		//FileUtil.upFile(pic.getInputStream(), uuidFileName, filePath);  
		/*File file = new File(filePath);  
		if(!file.exists()){  
		file.mkdirs();  
		}  
	    File f = new File(filePath+"/"+uuidFileName);  
	    pic.transferTo(f);*/
		return map;  
		}
	/**
	 *  检测文件
	 * @param request
	 * @return
	 */
	@RequestMapping(value="existHttpPath",method=RequestMethod.POST) 
	@ResponseBody  
	public  Map<String,Object> existHttpPath(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String,Object>();
		
		String message="fail";
		String exitfilename=request.getParameter("exitfilename");
		String exitpath=request.getParameter("exitpath");
		System.out.println(exitfilename+"----------"+exitpath+"-----------");
		JFTSClient jftsClient=new JFTSClient();
		ResourceBundle.getBundle("jfts-client");
		try{
			boolean isExit=jftsClient.fileExist(exitfilename, exitpath);
			if(isExit){
				message="succ";
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(exitfilename+"^^^^&&&&&&&&"+exitpath+"&&&&&&^^^^^^"+message);
		map.put("message", message);
		return map;
	}
	private Map<String, Object> doUpload(HttpServletRequest request) throws IllegalStateException, IOException {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		InputStream input=null;
		InputStream pptorpdfinput=null;
		Map<String,Object> fileMap = new HashMap<String,Object>();
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					// 取得当前上传文件的文件名称
					String originName = file.getOriginalFilename();
					input=file.getInputStream();
					String paramName = file.getName();
					// 如果名称不为"",说明该文件存在，否则说明该文件不存在
					if (originName.trim() != "") {
						
						// 开始上传原文件
						String fileName = IdGen.uuid() + originName.substring(originName.lastIndexOf("."));
						String userImagePath = "upload" + File.separatorChar + "user";
						File localFile = new File(Global.getConfig("docBase") + File.separatorChar + userImagePath + File.separatorChar + fileName);
						File parentFile = localFile.getParentFile();
						if(!parentFile.exists()){
							parentFile.mkdir();
						}
						file.transferTo(localFile);
						SysResource resource = new SysResource();
						resource.setFileAuffix(originName.substring(originName.lastIndexOf(".")));
//						resource.setOrgName(originName);
						resource.setFileName(fileName);
//						resource.setFilePath(localFile.getAbsolutePath());
						resource.setFileUrl(Global.getConfig("docPath")+"/upload/user/"+fileName);
						resource.setIsNewRecord(true);
						String id = IdGen.uuid();
						resource.setId(id);
						sysResourceService.save(resource);
						fileMap.put("id", id);
						fileMap.put("url", resource.getFileUrl());
						fileMap.put("pname", paramName);
					}
				}
			}
		} else {
			return null;
		}
		return fileMap;
	}
	/**
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="upload",method=RequestMethod.POST) 
	public Result upload(HttpServletRequest request, @RequestParam(value="username", required=false) String username) throws Exception {
		if(username==null||username==""||username.equals("")){
			username=String.valueOf((char)('a'+Math.random()*('z'-'a'+1)));
		}
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		
		
		InputStream input = null;
		SimpleDateFormat times = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String code = times.format(new Date());
//		Map<String, Object> fileMap = new HashMap<String, Object>();
		List l = new ArrayList();
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					// 取得当前上传文件的文件名称
					String originName = file.getOriginalFilename();// 原始文件名
					String paramName = file.getName();

					// 如果名称不为"",说明该文件存在，否则说明该文件不存在
					if (originName.trim() != "") {
						try {
							input = file.getInputStream();
							JFTSClient jftsClient = new JFTSClient();
							ResourceBundle.getBundle("jfts-client");
							String lid = IdGen.uuid();
							String remoteFileName = originName.substring(originName.lastIndexOf(".") + 1);
							boolean isSucc = jftsClient.uploadFile1(input, lid + "." + remoteFileName, username + "/" + code);
							if (isSucc) {
								Map<String, Object> fileMap = new HashMap<String, Object>();
								SysResource resource = new SysResource();
								resource.setFileAuffix(originName.substring(originName.lastIndexOf(".") + 1));
//								resource.setOrgName(originName);
								resource.setFileName(IdGen.uuid() + originName.substring(originName.lastIndexOf(".") + 1));
//								resource.setPath("");
								resource.setFileUrl(username + "/" + code + "/" + lid + "." + remoteFileName);
								resource.setIsNewRecord(true);
								String id = IdGen.uuid();
//								resource.setId(id);
								resource.setUuid(id);
								sysResourceService.save(resource);
								// fileMap.put("id", id);
								fileMap.put("url", resource.getFileUrl());
								fileMap.put("localName", paramName);
								l.add(fileMap);
							}
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							
						}
					}
				}
			}
			if (input != null) {
				input.close();
			}
			return ResultUtil.success(l);
		} else {
			ResultUtil.error(ReturnCode.FILE_IS_NULL);
		}
		return ResultUtil.error(ReturnCode.UNSUCCESS);
	}
	private boolean checkMobile(String mobile) {
		if (StringUtils.isBlank(mobile) || mobile.length() != 11 || TELNUM.indexOf(mobile.substring(0, 3)) == -1) {
			return true;
		}
		return false;
	}
	
	/** 
     * 缩放图像（按高度和宽度缩放） 
     * @param srcImageFile 源图像文件地址 
     * @param result 缩放后的图像地址 
     * @param height 缩放后的高度 
     * @param width 缩放后的宽度 
     * @param bb 比例不对时是否需要补白：true为补白; false为不补白; 
     */  
    public void scale(String srcImageFile, String result, int height, int width, boolean bb) {  
        try {  
            double ratio = 0.0; // 缩放比例  
            File f = new File(srcImageFile);  
            BufferedImage bi = ImageIO.read(f);  
            Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);  
            // 计算比例  
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {  
                if (bi.getHeight() > bi.getWidth()) {  
                    ratio = (new Integer(height)).doubleValue()  
                            / bi.getHeight();  
                } else {  
                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();  
                }  
                AffineTransformOp op = new AffineTransformOp(AffineTransform  
                        .getScaleInstance(ratio, ratio), null);  
                itemp = op.filter(bi, null);  
            }  
            if (bb) {//补白  
                BufferedImage image = new BufferedImage(width, height,  
                        BufferedImage.TYPE_INT_RGB);  
                Graphics2D g = image.createGraphics();  
                g.setColor(Color.white);  
                g.fillRect(0, 0, width, height);  
                if (width == itemp.getWidth(null))  
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,  
                            itemp.getWidth(null), itemp.getHeight(null),  
                            Color.white, null);  
                else  
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,  
                            itemp.getWidth(null), itemp.getHeight(null),  
                            Color.white, null);  
                g.dispose();  
                itemp = image;  
            }  
            ImageIO.write((BufferedImage) itemp, f.getName().substring(f.getName().lastIndexOf(".")+1), new File(result));  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
}