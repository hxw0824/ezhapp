package com.zlwh.yzh.admin.modules.basis.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.UserTokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import sun.misc.BASE64Encoder;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.weixin.sdk.api.PaymentApi;
import com.jfinal.weixin.sdk.api.PaymentApi.TradeType;
import com.jfinal.weixin.sdk.kit.PaymentKit;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.order.entity.Order;
import com.thinkgem.jeesite.modules.order.service.OrderService;
import com.thinkgem.jeesite.modules.products.service.ProductsService;
import com.zlwh.yzh.admin.modules.user.entity.CUser;
import com.zlwh.yzh.admin.modules.user.service.CUserService;
import com.zlwh.yzh.admin.modules.weixin.PayAttach;
import com.zlwh.yzh.admin.modules.weixin.ZxingKit;
import com.zlwh.yzh.api.common.ResultUtil;
import com.zlwh.yzh.api.common.ReturnCode;
import com.zlwh.yzh.api.domain.base.Result;

/**
 * @author Javen
 * 2016年3月19日
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/v1.0/wxpay")
public class WeixinPayController{
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductsService productsService;
	
	@Autowired
	CUserService userService;
	private CUser user = null;
	
	//商户相关资料
	private static final String APP_ID = "wxd6c2cb0811b070aa";
	private static final String MCH_ID = "1497401252";
	private static final String API_KEY = "kRJcpsUjriLuQd919458qpd7TPJWk8TR";
	private static final String NOTIFY_URL = "http://openapi.ezhihe.cn/v1.0/wxpay/wxnotify";
	
	/**
	 * 扫码支付模式二
	 */
	@RequestMapping(value="refresh",method = RequestMethod.POST)
	public Result refresh(HttpServletRequest request,
			@RequestParam(value="userId", required=true) String userId,
			@RequestParam(value="userToken", required=true) String userToken,
			@RequestParam(value="total_fee", required=true) String total_fee,
			@RequestParam(value="body", required=true) String body,
			@RequestParam(value="classId", required=true) String classId,
			@RequestParam(value="psncode", required=true) String psncode,
			@RequestParam(value="out_trade_no", required=true) String out_trade_no
			) {
		if (checkUserToken(userToken)) {
			return ResultUtil.error(ReturnCode.PARAM_NOT_VALID);
		}
		
		Map<String, Object> returns = new LinkedHashMap<String, Object>();
		
		if (StrKit.isBlank(total_fee)) {
			total_fee="0.01";
		}
		
		// 统一下单文档地址：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
		
		Map<String, String> params = new HashMap<String, String>();
		int price=((int)(Float.valueOf(total_fee)*100));
		
		String ip = "127.0.0.1";
		if (StrKit.isBlank(ip)) {
			ip = request.getRemoteAddr();
		}
		
		params.put("appid", APP_ID);
		params.put("mch_id", MCH_ID);
		params.put("nonce_str", System.currentTimeMillis() / 1000 + "");
		params.put("body", body);
		params.put("out_trade_no", out_trade_no);
		params.put("total_fee", price+"");
		params.put("spbill_create_ip", ip);
		params.put("notify_url", NOTIFY_URL);
		params.put("trade_type", TradeType.NATIVE.name());

		String sign = PaymentKit.createSign(params, API_KEY);
		params.put("sign", sign);
		
		String xmlResult = PaymentApi.pushOrder(params);
		
		System.out.println(xmlResult);
		Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
		
		String return_code = result.get("return_code");
		String result_code = result.get("result_code");
		String return_msg = result.get("return_msg");
		
		if (StrKit.isBlank(return_code) || !"SUCCESS".equals(return_code) || StrKit.isBlank(result_code) || !"SUCCESS".equals(result_code)) {
			System.out.println(return_msg);
			return ResultUtil.error(return_msg);
		}
		
		//生成预付订单success
		Order order = new Order();
		order.setUserid(userId);
		order.setPsncode(psncode);
		order.setPeriodId(classId);
		order.setIsPay("0");
		order.setOrderNumber(out_trade_no);
		order.setSalePrice(total_fee);
		order.setStatus("1");
		order.setOrderTitle(body);
		order.setCreateDate(new Date());
		order.setTimeoutexpress("90");
		order.setPayType("WXPAY");
		orderService.save(order);
		
		String qrCodeUrl = result.get("code_url");
		String name = "payQRCode"+System.currentTimeMillis()+".png";
		FileUtils.deleteDirectory(PathKit.getWebRootPath()+File.separator+"static"+File.separator+"wxpay"+File.separator);
		FileUtils.createDirectory(PathKit.getWebRootPath()+File.separator+"static"+File.separator+"wxpay"+File.separator);
		Boolean encode = ZxingKit.encode(qrCodeUrl, BarcodeFormat.QR_CODE, 0, ErrorCorrectionLevel.H, "png", 200, 200,
				PathKit.getWebRootPath()+File.separator+"static"+File.separator+"wxpay"+File.separator+name );
		if (encode) {
			String base64Str = getImageStr(PathKit.getWebRootPath()+File.separator+"static"+File.separator+"wxpay"+File.separator+name);
			returns.put("base64Str", base64Str);
			returns.put("out_trade_no", out_trade_no);
		}
		return ResultUtil.success(returns);
	}
	
	/**
	 * 查询订单
	 */
	@RequestMapping(value="checkOrder",method = RequestMethod.POST)
	public Result checkOrder(HttpServletRequest request,String out_trade_no) {
		Map<String, Object> returns = new LinkedHashMap<String, Object>();
		
		Map<String, String> payResult = PaymentApi.queryByOutTradeNo(APP_ID, MCH_ID, API_KEY, out_trade_no);
		String return_code = payResult.get("return_code");
		String result_code = payResult.get("result_code");
		String trade_state = payResult.get("trade_state");
		
		int isPay = 0;
		int isOrder = 0;
		if (StrKit.notBlank(return_code) && StrKit.notBlank(result_code) && return_code.equalsIgnoreCase("SUCCESS")&&result_code.equalsIgnoreCase("SUCCESS")) {
			isOrder = 1;
			if(trade_state.equalsIgnoreCase("SUCCESS")){
				isPay = 1;
		  	}else if(trade_state.equalsIgnoreCase("CLOSED")){
		  		isPay = 3;
		  	}
		}
		returns.put("isPay", isPay);
		returns.put("isOrder", isOrder);
		return ResultUtil.success(returns);
	}
	
	/**
	 * 回调
	 */
	@RequestMapping(value="wxnotify",method = RequestMethod.GET)
	public ModelAndView wxnotify(HttpServletRequest request,
			@RequestParam(value="out_trade_no", required=true) String out_trade_no			
			) throws Exception {
//		if (checkUserToken(userToken)) {
//			return ResultUtil.error(ReturnCode.PARAM_NOT_VALID);
//		}
		ModelAndView mav = new ModelAndView("/modules/basis/return_url");
		Map<String, String> map = PaymentApi.queryByOutTradeNo(APP_ID, MCH_ID, API_KEY, out_trade_no);
		for (String key : map.keySet()) {
			   System.out.println(key + "\t" + map.get(key));
		}
		
		String return_code = map.get("return_code");
		String result_code = map.get("result_code");
		
		if (StrKit.notBlank(return_code) && StrKit.notBlank(result_code) && return_code.equalsIgnoreCase("SUCCESS")&&result_code.equalsIgnoreCase("SUCCESS")) {
			String transaction_id = map.get("transaction_id");
			Order order = orderService.getByOrderNo(out_trade_no);
			
			order.setIsPay("1");//置为已支付
			order.setOrderTitle(order.getOrderTitle());
			order.setTradeno(transaction_id);
			
			long days = Long.valueOf(productsService.getProductsBySncode(order.getPsncode()).getDays());
			Date d  = new Date();
			order.setPayTime(DateUtils.formatDateTime(d));
			order.setExpireTime(DateUtils.formatDateTime(DateUtils.addDate(new Date(),days)));
			order.setPeriodId(order.getPeriodId());
			orderService.save(order);
		}
		mav.addObject("out_trade_no", out_trade_no);
		return mav;
	}
	
	/**
	 * @Description: 根据图片地址转换为base64编码字符串
	 * @Author: 
	 * @CreateTime: 
	 * @return
	 */
	public static String getImageStr(String imgFile) {
	    InputStream inputStream = null;
	    byte[] data = null;
	    try {
	        inputStream = new FileInputStream(imgFile);
	        data = new byte[inputStream.available()];
	        inputStream.read(data);
	        inputStream.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    // 加密
	    BASE64Encoder encoder = new BASE64Encoder();
	    return "data:image/png;base64,"+encoder.encode(data).replaceAll("\r\n", "");
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
}
