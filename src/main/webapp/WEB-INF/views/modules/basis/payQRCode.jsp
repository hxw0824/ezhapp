<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<title>微信扫码支付-模式二</title>
<style>
.refresh{
	position:absolute;
	top:0;
	left:0;
	width:200px;
	height:200px;
	background:rgba(7,17,27,0.8) url('${ctxStatic}/images/refresh.png') no-repeat center 40px;
	cursor:pointer;
}
.refresh p{
	color:#fff;
	font-size:16px;
	text-align:center;
	margin-top:130px;
    letter-spacing: 2px;
}
</style>
</head>
<body>
	<h2>欢迎使用 微信支付</h2>
	<h4>商户订单号：<span id="out_trade_no_id"></span></h4>
	<div style="position:relative">
		<img alt="二维码" src="${ctxIndex}/static/wxpay/${payQRCode}">
		<div class="refresh">
			<p>二维码失效<br/>点击刷新</p>
		</div>
	</div>
		
	<h4>${return_msg}</h4>
</body>
<script type="text/javascript" src="${ctxStatic}/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
var time;
$(function(){
	refresh();
	if("${out_trade_no}" != ''){
		time = setInterval('checkOrder(${out_trade_no})',2000);
	}
});

function refresh(){
	$.ajax({
    	url : "${ctxIndex}/v1.0/wxpay/refresh",
    	type : "GET",
    	dataType : "text",
    	success : function(data){
    		console.log(data);
    	}
	});
}

//定时器，每隔1s查询订单支付状态，订单状态改变，清除页面定时器，页面跳转
function checkOrder(orderId) {
    $.ajax({
    	url : "${ctxIndex}/v1.0/wxpay/checkOrder",
    	dataType : "text",
    	data : {out_trade_no : orderId},
    	success : function(data){
    		data = eval("("+data+")").data;
    		console.log(data);
    		var isorder = data.result_code;
    		var paystatus = data.trade_state;
    		console.log(isorder);
    		console.log(paystatus);
    		if(isorder=="SUCCESS"){
    		  	if(paystatus=="SUCCESS"){
    			  	//支付成功跳转
    			 	alert(data.transaction_id);
    			  	clearInterval(time);
    		  	}else if(paystatus=="NOTPAY"){
    			  	//未支付
    			  	console.log("订单未支付");
    		  	}else if(paystatus=="CLOSED"){
    			  	//支付失败
    			  	console.log("已关单");
    			  	clearInterval(time);
    		  	}
   			}
    	}
    });
}
</script>
</html>