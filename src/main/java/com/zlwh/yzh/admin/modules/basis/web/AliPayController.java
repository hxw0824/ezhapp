package com.zlwh.yzh.admin.modules.basis.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.thinkgem.jeesite.common.enums.OrderEnum;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.order.entity.Order;
import com.thinkgem.jeesite.modules.order.service.OrderService;
import com.thinkgem.jeesite.modules.products.entity.Products;
import com.thinkgem.jeesite.modules.products.service.ProductsService;
import com.zlwh.yzh.admin.modules.basis.entity.AlipayConfig;
import com.zlwh.yzh.admin.modules.basis.entity.Card;
import com.zlwh.yzh.admin.modules.basis.service.CardService;
import com.zlwh.yzh.admin.modules.user.entity.CUser;
import com.zlwh.yzh.admin.modules.user.service.CUserService;
import com.zlwh.yzh.api.common.ResultUtil;
import com.zlwh.yzh.api.common.ReturnCode;
import com.zlwh.yzh.api.domain.VipInfo;
import com.zlwh.yzh.api.domain.base.Result;
/**
 * 
 * @author hcq
 *@date 2018年1月17日
 * @version 1.0
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/v1.0/alipay")
public class AliPayController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductsService productsService;
	@Autowired
	CUserService userService;
	private CUser user = null;
	//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
	/**
	 * 发起支付请求
	 * @param userid 下单人
	 * @param WIDout_trade_no 商户订单号
	 * @param WIDtotal_amount 付款金额
	 * @param WIDsubject 订单名称
	 * @param WIDbody 商品描述
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="pay",method = RequestMethod.POST)
	public ModelAndView pay(
			@RequestParam(value="userId", required=true) String userid,
						@RequestParam(value="userToken", required=true) String userToken,
						@RequestParam(value="classId", required=true) String classId,
						@RequestParam(value="WIDout_trade_no", required=true) String out_trade_no,
						@RequestParam(value="WIDtotal_amount", required=true) String total_amount,
						@RequestParam(value="psncode", required=true) String psncode,
						@RequestParam(value="WIDsubject", required=true) String subject,
						@RequestParam(value="WIDbody", required=true) String body,
						HttpServletRequest request, HttpServletResponse response) throws IOException{
		ModelAndView mv = new ModelAndView("/modules/basis/alipayTradePay");
		String result = null;
		if (checkUserToken(userToken)) {
			result = "用户token不合法";
		}
		//生成订单，并设置订单状态
//		Products products = productsService.getProductsBySncode(psncode);
		Order order = new Order();
		order.setUserid(userid);
		order.setPsncode(psncode);
		order.setPeriodId(classId);
		order.setIsPay("0");
		order.setOrderNumber(out_trade_no);
		order.setSalePrice(total_amount);
		order.setStatus("1");
		order.setOrderTitle(subject);
		order.setCreateDate(new Date());
		order.setTimeoutexpress("90");
		order.setPayType("ALIPAY");
		orderService.save(order);
		//设置请求参数
				AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
				alipayRequest.setReturnUrl(AlipayConfig.return_url);
				alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
				+ "\"total_amount\":\""+ total_amount +"\"," 
				+ "\"subject\":\""+ subject +"\"," 
				+ "\"body\":\""+ body +"\"," 
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		try {
			result = alipayClient.pageExecute(alipayRequest).getBody();
	        mv.addObject("data", result);
	        return mv;
		} catch (AlipayApiException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	/**
	 * 交易查询
	 * @param out_trade_no 商户订单号
	 * @param trade_no 支付宝交易号
	 * @return
	 */
	@RequestMapping(value="query",method = RequestMethod.POST)
	public Result query(@RequestParam(value="WIDout_trade_no", required=false) String out_trade_no,
			@RequestParam(value="WIDTQtrade_no", required=false) String trade_no){
		//设置请求参数
		AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","+"\"trade_no\":\""+ trade_no +"\"}");
		
		//请求
		try {
			String result = alipayClient.execute(alipayRequest).getBody();
			return ResultUtil.success(result);
//			ModelAndView mv = new ModelAndView("/modules/basis/alipayTradeQuery");
//	        mv.addObject("data", result);
//	        return mv;
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultUtil.error(e);
//			return null;
		}
	}
	/**
	 * 退款
	 * @param out_trade_no 商户订单号，商户网站订单系统中唯一订单号
	 * @param trade_no 支付宝交易号
	 * @param refund_amount 需要退款的金额，该金额不能大于订单金额，必填
	 * @param refund_reason 退款的原因说明
	 * @param out_request_no 标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
	 * @return
	 */
	@RequestMapping(value="refund",method = RequestMethod.POST)
	public ModelAndView refund(@RequestParam(value="WIDTRout_trade_no", required=true) String out_trade_no,
			@RequestParam(value="WIDTRtrade_no", required=false) String trade_no,
			@RequestParam(value="WIDTRrefund_amount", required=true) String refund_amount,
			@RequestParam(value="WIDTRrefund_reason", required=false) String refund_reason,
			@RequestParam(value="WIDTRout_request_no", required=false) String out_request_no
			
			){
		//设置请求参数
		AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
				+ "\"trade_no\":\""+ trade_no +"\"," 
				+ "\"refund_amount\":\""+ refund_amount +"\"," 
				+ "\"refund_reason\":\""+ refund_reason +"\"," 
				+ "\"out_request_no\":\""+ out_request_no +"\"}");
		
		//请求
		try {
			String result = alipayClient.execute(alipayRequest).getBody();
			ModelAndView mv = new ModelAndView("/modules/basis/alipayTradeRefund");
	        mv.addObject("data", result);
	        return mv;
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 退款查询
	 * @param out_trade_no 商户订单号，商户网站订单系统中唯一订单号
	 * @param trade_no 支付宝交易号
	 * @param out_request_no 请二选一设置,请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部交易号，必填
	 * @return
	 */
	@RequestMapping(value="refundQuery",method = RequestMethod.POST)
	public ModelAndView refundQuery(@RequestParam(value="WIDRQout_trade_no", required=true) String out_trade_no,
				@RequestParam(value="WIDRQtrade_no", required=false) String trade_no,
				@RequestParam(value="WIDRQout_request_no", required=false) String out_request_no
				
				){
			//设置请求参数
			//设置请求参数
			AlipayTradeFastpayRefundQueryRequest alipayRequest = new AlipayTradeFastpayRefundQueryRequest();
			alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
					+"\"trade_no\":\""+ trade_no +"\","
					+"\"out_request_no\":\""+ out_request_no +"\"}");
			
			//请求
			try {
				String result = alipayClient.execute(alipayRequest).getBody();
				ModelAndView mv = new ModelAndView("/modules/basis/alipayTradeFastpayRefundQuery");
		        mv.addObject("data", result);
//				return ResultUtil.success(result);
		        return mv;
			} catch (AlipayApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
	}
	/**
	 * 关闭交易
	 * @param out_trade_no 商户订单号，商户网站订单系统中唯一订单号
	 * @param trade_no 支付宝交易号
	 * @return
	 */
	@RequestMapping(value="close",method=RequestMethod.POST)
	public ModelAndView close(@RequestParam(value="WIDTCout_trade_no", required=true) String out_trade_no,
			@RequestParam(value="WIDTCtrade_no", required=false) String trade_no
			
			){
		//设置请求参数
		//设置请求参数
		AlipayTradeCloseRequest alipayRequest = new AlipayTradeCloseRequest();
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," +"\"trade_no\":\""+ trade_no +"\"}");
		
		//请求
		try {
			String result = alipayClient.execute(alipayRequest).getBody();
			ModelAndView mv = new ModelAndView("/modules/basis/alipayTradeClose");
	        mv.addObject("data", result);
//			return ResultUtil.success(result);
	        return mv;
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 异步通知支付宝
	 * @param requestParams
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="notify",method = RequestMethod.POST)
	public String notify(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		Map<String,String[]> requestParams = request.getParameterMap();
		   Map<String,String> params = new HashMap<String,String>();
		   for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用
//				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
//				if (valueStr != null && !"".equals(valueStr)){
//					if (!(java.nio.charset.Charset.forName("GBK").newEncoder().canEncode(valueStr))){
//						try{
//							valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
//						}catch (Exception e){
//							e.printStackTrace();
//						}
//					}
//				}
				System.out.println(name+":"+valueStr);
				params.put(name, valueStr);
			}
			
			boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
//			ModelAndView mv = new ModelAndView("/modules/basis/notify");
			//——请在这里编写您的程序（以下代码仅作参考）——
			
			/* 实际验证过程建议商户务必添加以下校验：
			1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
			2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
			3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
			4、验证app_id是否为该商户本身。
			*/
			if(signVerified) {//验证成功
				//商户订单号
				String out_trade_no = new String(params.get("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
				String app_id = new String(params.get("app_id").getBytes("ISO-8859-1"),"UTF-8");
				
				String total_amount = new String(params.get("total_amount").getBytes("ISO-8859-1"),"UTF-8");
				//实收金额
				String receipt_amount = new String(params.get("receipt_amount").getBytes("ISO-8859-1"),"UTF-8");
				//买家实际支付金额
				String buyer_pay_amount = new String(params.get("buyer_pay_amount").getBytes("ISO-8859-1"),"UTF-8");
				//卖家支付宝用户号
				String seller_id = new String(params.get("seller_id").getBytes("ISO-8859-1"),"UTF-8");
				//支付宝交易号
				String trade_no = new String(params.get("trade_no").getBytes("ISO-8859-1"),"UTF-8");
				//交易状态
				String trade_status = new String(params.get("trade_status").getBytes("ISO-8859-1"),"UTF-8");
				System.out.println();
				/**
				 * 	状态TRADE_SUCCESS的通知触发条件是商户签约的产品支持退款功能的前提下，买家付款成功；
				 *	交易状态TRADE_FINISHED的通知触发条件是商户签约的产品不支持退款功能的前提下，买家付款成功；
				 *	或者，商户签约的产品支持退款功能的前提下，交易已经成功并且已经超过可退款期限
				 */
				if(trade_status.equals("TRADE_FINISHED")){//支付流程彻底走完,提示交易关闭
					//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
					System.out.println("TRADE_FINISHED：检查订单");
					
						Order order = orderService.getByOrderNo(out_trade_no);
						System.out.println("订单存在");
						System.out.println(order!=null);
						System.out.println(seller_id.equals(AlipayConfig.seller_id));
						System.out.println(app_id.equals(AlipayConfig.app_id));
						System.out.println(total_amount.equals(order.getSalePrice()));
						if(order!=null&&seller_id.equals(AlipayConfig.seller_id)&&app_id.equals(AlipayConfig.app_id)&&total_amount.equals(order.getSalePrice())){
							System.out.println("检验");
						order.setStatus("0");//设置订单状态不可更改
						order.setOrderTitle(order.getOrderTitle());
						order.setReceiptamount(receipt_amount);
						order.setBuyerpayamount(buyer_pay_amount);
						order.setSellerid(seller_id);
						order.setTradeno(order.getTradeno());
						order.setTradeno(trade_no);
						order.setPeriodId(order.getPeriodId());
						order.setIsNewRecord(false);
						orderService.save(order);
					}
					
					//注意：
					//退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
				}else if (trade_status.equals("TRADE_SUCCESS")){//支付完成，流程还没走完，提示交易完成
					//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
					System.out.println("TRADE_SUCCESS：检查订单");
						Order order = orderService.getByOrderNo(out_trade_no);
						System.out.println("订单存在");
						long days = Long.valueOf(productsService.getProductsBySncode(order.getPsncode()).getDays());
//						return ResultUtil.error(ReturnCode.PARAM_NOT_VALID);
						if(order!=null&&seller_id.equals(AlipayConfig.seller_id)&&app_id.equals(AlipayConfig.app_id)&&total_amount.equals(order.getSalePrice())){
							System.out.println("检验");
							System.out.println(order!=null);
							System.out.println(seller_id.equals(AlipayConfig.seller_id));
							System.out.println(app_id.equals(AlipayConfig.app_id));
							System.out.println(total_amount.equals(order.getSalePrice()));
							order.setIsPay("1");//置为已支付
							order.setOrderTitle(order.getOrderTitle());
							order.setReceiptamount(receipt_amount);
							order.setBuyerpayamount(buyer_pay_amount);
							order.setSellerid(seller_id);
							order.setTradeno(trade_no);
							Date d  = new Date();
							order.setPayTime(DateUtils.formatDateTime(d));
							long t = d.getTime()+days*1000 * 60 * 60 * 24;
							order.setExpireTime(DateUtils.formatDateTime(DateUtils.addDate(new Date(),days)));
							order.setPeriodId(order.getPeriodId());
							orderService.save(order);
						}
				}
					//注意：
					//付款完成后，支付宝系统发送该交易状态通知
//				 mv.addObject("data","success");
				System.out.println("noyify");
				return "success";
				
			}else {//验证失败
//				mv.addObject("data","fail");
				System.out.println("noyifyfail");
				String sWord = AlipaySignature.getSignCheckContentV1(params);
				AlipayConfig.logResult(sWord);
				return "fail";
			
				//调试用，写文本函数记录程序运行情况是否正常
				
			}
	   } 
	public String transferLongToDate(String dateFormat, Long millSec) {
		         SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		         Date date = new Date(millSec);
		         return sdf.format(date);
		     }
	@RequestMapping(value="sucsess",method=RequestMethod.GET)
	public ModelAndView sucsess(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String,String[]> requestParams = request.getParameterMap();
		Map<String,String> params = new HashMap<String,String>();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
//			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			System.out.println(name+"::"+valueStr);
			params.put(name, valueStr);
//			out.println(requestParams);
		}
		
		boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
//		ModelAndView mv = new ModelAndView("/modules/basis/alipayTradeReturn");
		ModelAndView mv = new ModelAndView(new RedirectView("f.ezhihe.cn/return_url.jsp"));
		//——请在这里编写您的程序（以下代码仅作参考）——
		if(signVerified) {
			//商户订单号
			String out_trade_no = new String(params.get("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		
			//支付宝交易号
			String trade_no = new String(params.get("trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//支付宝交易号
//			String out_trade_user = new String(params.get("out_trade_user").getBytes("ISO-8859-1"),"UTF-8");
		
			//付款金额
			String total_amount = new String(params.get("total_amount").getBytes("ISO-8859-1"),"UTF-8");
			System.out.println("检验");
			
			
	       
//			return "支付宝交易号:"+trade_no+"<br/>订单号:"+out_trade_no+"<br/>交易金额:"+total_amount;
			 mv.addObject("data", "支付宝交易号:"+trade_no+"<br/>订单号:"+out_trade_no+"<br/>交易金额:"+total_amount);
			 System.out.println("return");
		     return mv;
		}else {
			mv.addObject("data","验签失败");
			return mv;
		}
		//——请在这里编写您的程序（以上代码仅作参考）——
	}
	private boolean checkOuttradeno(String out_trade_no) {
		if (StringUtils.isBlank(out_trade_no)) {
			return true;
		}
		Order order = new Order();
		order.setOrderNumber(out_trade_no);
		order = orderService.get(order);
		return order == null;
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
	@RequestMapping(value="getVipInfo",method=RequestMethod.POST)
	public Result getVipInfo(@RequestParam(value="userId", required=true) String userid,
			@RequestParam(value="userToken", required=true) String userToken
			){
		if (checkUserToken(userToken)) {
			return ResultUtil.error(ReturnCode.TOKEN_NOT_VALID);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Order order = new Order();
		order.setUserid(userid);
		List <Order>infiList = orderService.findList(order);
		VipInfo vipInfo = new VipInfo();
		List <Order>info = new ArrayList();
		if(infiList.size()>0){
			for (Order order1: infiList) {
				
				vipInfo.setLevel(order1.getCuser().getLevel()!=null?order1.getCuser().getLevel():"6");
				vipInfo.setNickName(order1.getCuser().getNickName());
				vipInfo.setUserid(order1.getCuser().getId());
				vipInfo.setUserToken(order1.getCuser().getUserToken());
				vipInfo.setCurTime(sdf.format(new Date()));
				if(order1.getIsPay().equals("1")){
					Order orders = new Order();
					orders.setOrderTitle(order1.getOrderTitle());
					orders.setOrderNumber(order1.getOrderNumber());
					orders.setPayTime(order1.getPayTime());
					orders.setExpireTime(order1.getExpireTime());
					info.add(orders);
				}
				vipInfo.setVipInfo(info);
			}
			return ResultUtil.success(vipInfo);
		}else{
			return ResultUtil.error("您还没有购买vip");
		}
	}
	
	/**
	 * 根据订单号查询订单详情
	 * @param userid
	 * @param userToken
	 * @param orderNo
	 * @return
	 */
	@RequestMapping(value="getOrderInfo",method=RequestMethod.POST)
	public Result getOrderInfo(
			@RequestParam(value="orderNo", required=true) String orderNo
			){
		Order order = orderService.getByOrderNo(orderNo);
		if(order != null){
			return ResultUtil.success(order);
		}else{
			return ResultUtil.error(ReturnCode.ORDER_IS_NOT_EXISTS,null);
		}
	}
	
	
}
