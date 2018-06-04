/**   
* @Title: WeiXinController.java    
* @Package com.zlwh.yzh.api    
* @Description: TODO(用一句话描述该文件做什么)    
* @author 刘斐   
* @date 2016年7月14日 下午1:46:49    
* @version V1.0   
*/
package com.zlwh.yzh.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.thinkgem.jeesite.common.web.BaseController;
import com.zlwh.yzh.admin.modules.basis.entity.Card;
import com.zlwh.yzh.admin.modules.basis.service.CardService;
import com.zlwh.yzh.admin.modules.user.entity.CUser;
import com.zlwh.yzh.admin.modules.user.entity.CUserCard;
import com.zlwh.yzh.admin.modules.user.service.CUserCardService;
import com.zlwh.yzh.admin.modules.user.service.CUserService;
import com.zlwh.yzh.admin.modules.weixin.WeixinOauth2Token;
import com.zlwh.yzh.api.common.HttpRequestTool;
import com.zlwh.yzh.api.common.ReturnCode;

import net.sf.json.JSONObject;

/**   
* @ClassName: WeiXinController    
* @Description: TODO(这里用一句话描述这个类的作用)    
* @author 刘斐   
* @date 2016年7月14日 下午1:46:49    
*         
*/
@Controller
@RequestMapping("/weixin")
public class WeiXinController  extends BaseController{

	@Autowired
	CUserService cUserService;
	@Autowired
	CardService cardService;
	@Autowired
	CUserCardService cUserCardService;
	/**
	 * 进入授权卡绑定页面
	 * 
	 * @author zhaoxin
	 * @throws Exception
	 * @return view
	 **/
	@RequestMapping("/forUserCardBinding")
	public ModelAndView forUserCardBinding(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		Map<String, Object> resultMap = returnOpenId(request);
//		String openId=(String) resultMap.get("openId");
		ModelAndView view = new ModelAndView("mobile/wx/authCodeCard");
		Map<String, Object> openIdMap = new HashMap<String, Object>();
		// 模拟openId
		String openId="o6dmlwDk";
		openIdMap.put("openId", openId);
		//根据opendID获取userId
		view.addObject("openIdCode",200);
		view.addObject("openId",openId);
		return view;
	}
	/**
	 * 功能：微信端 授权码授权
	 * 
	 * @param parameter
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@RequestMapping(value = "/authorizationCodeByWx")
	@ResponseBody
	public Map<String, Object> authorizationCodeByWx(HttpServletRequest request) throws ClientProtocolException, IOException {
		//返回结果到页面
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String pwd = request.getParameter("pwd");
		//获取用户信息
		String mobile = request.getParameter("mobile");
		String password=request.getParameter("password");
		CUser userBean = new CUser();
		userBean.setLoginName(mobile);
		userBean.setPwd(new Md5Hash(password).toHex());
		List<CUser> userList = cUserService.findList(userBean);
		int result=0;
		if (userList == null || userList.size() == 0) {
			result=561;
			resultMap.put("result", result);
			return resultMap;
		}
		CUser user = userList.get(0);
		if (pwd == null) {
			result=501;
			resultMap.put("result", result);
			return resultMap;
		}
		//根据授权卡密码查询授权卡信息
		Card card = cardService.get(pwd);
		if (card==null) {
			result=502;
			resultMap.put("result", result);
			return resultMap;
		}
		//查询授权卡使用次数
		int count = cUserCardService.getCardCount(card.getId());
		if(count>1){
			result=505;
			resultMap.put("result", result);
			return resultMap;
		}
		//进行权限控制查看用户是否已购买图书
		CUserCard cUserCard = new CUserCard();
		cUserCard.setUserId(user.getId());
		cUserCard.setPeriodId(card.getPeriodId());
		int cardCount = cUserCardService.selectUserCard(cUserCard);
		if(cardCount>0){
			result=560;
			resultMap.put("result", result);
			return resultMap;
		}
		//检查授权卡学段与用户学段是否相符
		if(!card.getPeriodId().equals(user.getPeriodId())){
			result = 506;
			resultMap.put("result", result);
			return resultMap;
		}
		//进行授权卡绑定
		cUserCard.setCardId(card.getId());
		try {
			int ret = cUserCardService.saveReturn(cUserCard);
			//修改授权卡使用次数
			card.setUseNum(card.getUseNum()+1);
			ret = cardService.saveReturn(card);
			if (ret<1) {
				result=504;
				resultMap.put("result", result);
				return resultMap;
			}
			if (ret<1) {
				result=504;
				resultMap.put("result", result);
				return resultMap;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(""+e);
			result=504;
			resultMap.put("result", result);
			return resultMap;
		}
		
		return resultMap;
	}
	
	/**
	 * 返回OpenID
	 * @param request
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	private Map<String, Object> returnOpenId(HttpServletRequest request) throws ClientProtocolException, IOException {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//获取到的code
		String returnCode = request.getParameter("code");
		String state = request.getParameter("state");
		String appId="wx2dfce7acd4c03fc6";
		String appSecret="03f9531dea55e7a9778f37cc7fe73631";
		String param="?appid="+appId+"&secret="+appSecret+"&code="+returnCode+"&grant_type=authorization_code";
		
		//根据code获取的cacesstoken的url
		String url="https://api.weixin.qq.com/sns/oauth2/access_token";
		//获取刷新后accesstoken的url
		String urlf="https://api.weixin.qq.com/sns/oauth2/refresh_token";
		String jsonResult=HttpRequestTool.sendSSLPost(url+param);
		//openid
		String result = pareJsonObectReturnOpenId(jsonResult);
		JSONObject jsonObject = null;
		 jsonObject = JSONObject.fromObject(jsonResult);
		 String refreshToken=jsonObject.getString("refresh_token");
		 String paramf="?appid="+appId+"&grant_type=refresh_token&refresh_token="+refreshToken;
		//获取刷新后的token
		 String finalStr=HttpRequestTool.sendSSLPost(urlf+paramf);
		 JSONObject jsonObjectf = null;
		 jsonObjectf = JSONObject.fromObject(jsonResult);
		 String accessToken=jsonObject.getString("access_token");
		 
		 //获取user的url
		 String urlUser="https://api.weixin.qq.com/sns/userinfo";
		 String paramUser="?access_token="+accessToken+"&openid="+result+"&lang=zh_CN";
		 String userInfoStr=HttpRequestTool.sendSSLPost(urlUser+paramUser);
		 JSONObject jsonObjectUserInfo = null;
		 jsonObjectUserInfo= JSONObject.fromObject(userInfoStr);
		 String nickName=jsonObjectUserInfo.getString("nickname");
		 resultMap.put("openId", result);
		 resultMap.put("nickName", nickName);
		return resultMap;
	}
	/**
	 *  微信accesstoken解析类
	 * @param jsonResult
	 * @return
	 */
	private String pareJsonObectReturnOpenId(String jsonResult) {
		JSONObject jsonObject = null;
		 jsonObject = JSONObject.fromObject(jsonResult);
         WeixinOauth2Token wo2t=new WeixinOauth2Token();
         wo2t.setAccessToken(jsonObject.getString("access_token"));
         wo2t.setExpiresIn(jsonObject.getString("expires_in"));
         wo2t.setOpenId(jsonObject.getString("openid"));
         wo2t.setRefreshToken(jsonObject.getString("refresh_token"));
         wo2t.setScope(jsonObject.getString("scope"));
         String openId=wo2t.getOpenId();
		return openId;
	}
}
