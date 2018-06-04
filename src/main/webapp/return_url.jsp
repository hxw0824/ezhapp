<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>电脑网站支付return_url</title>
	<style type="text/css">
		*{
		margin:0;
		padding:0;
	}
	body{
		width:100%;
		height: 100%;
		background:#f2f2f2;
	}
.wrap{
	width: 774px;
	height: 433px;
	background:#fff;
	margin:154px auto 0;
	border:1px solid #ccc;
}
.wrap .wrapTop{
	width: 630px;
	height: 103px;
	margin: 0 auto;

	position: relative;
}
.wrap img{
	display: inline-block;
	width: 100px;
	height: 86px;
	position: absolute;
	left: 50%;
	margin-left: -50px;
}
.wrapTop span{
    display: inline-block;
    width: 120px;
    height: 51px;
    line-height: 50px;
    text-align: center;
    background: #fff;
    position: absolute;
    left: 50%;
    top: 78px;
    margin-left: -60px;
    color:#7cb772;
    font-size: 20px;
}
.wrapCon{
	width: 630px;
	border-top:5px solid #7cb772;
	margin: 0 auto;
	padding-top: 42px;
}
.wrapCon .conItem{
	margin: 10px 0 10px 40px;
	line-height: 30px;
	color:#b6b6b6;
	font-size: 18px;
}
.conItem span{
	display: inline-block;
	width: 120px;
	text-align: right;
	margin-right: 10px;
}
.conItem p{
	display: inline-block;
}

.goBack{
	display: inline-block;
	width: 200px;
	height: 50px;
	line-height: 50px;
	text-align: center;
	color: #fff;
	font-size: 20px;
	background:#7cb772;
	border-radius: 20px;
	text-decoration: none;
    margin: 35px 0 0 284px;

}


	</style>
</head>
<body>
<div class="wrap">
<div class="wrapTop">
		<img src="images/dui.png">
		<span>订阅成功</span>
</div>
<div class="wrapCon">
	<div class="conItem" >
		<span>订单号:</span>
		<p id="orderNumber"></p>
	</div>
		<div class="conItem" >
		<span>订单课程:</span>
		<p id="orderTitle"></p>
	</div>
		<div class="conItem" >
		<span>交易金额:</span>
		<p id="salePrice" style="color:orange"></p>
	</div>
</div>
<a href="index.html" class="goBack">返回首页</a>

</div>
<script type="text/javascript" src="js/jqueryCommon.min.js"></script>
<script src="js/jquery-cookie.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
	$(function(){
		getCookie();
		var num = '${out_trade_no}'
		var Data;
		var orderNumber;//商品号
		var orderTitle;//订单课程
		var salePrice;//订单金额
		$.ajax({
			url:address+'v1.0/alipay/getOrderInfo',
			type:'post',
			dataType:'json',
			data:{
				  "orderNo": num
			},
			success:function(res){
				if(res.code == '0'){
					Data = res.data;
					orderNumber= Data.orderNumber;
					orderTitle= Data.orderTitle;
					salePrice= Data.salePrice;
					$("#orderNumber").text(orderNumber);
					$("#orderTitle").text(orderTitle);
					$("#salePrice").text(salePrice+'元');
				}
			}
		})
	})
</script>
</body>
</html>