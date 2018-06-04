<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>系统通知</title>
<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<style type="text/css">
body {
	font-family:'微软雅黑';
	font-size:15;
	color:#333;
}
</style>
</head>
<body>
	<section>
		<p align="center" style="color: #999;padding-bottom:10px">
			<fmt:formatDate value="${message.publishTime}" pattern="yyyy-MM-dd" />
		</p>
		<h3 align="center" style="padding-top:19px;padding-bottom:10px;">${message.title}</h3>
		<div style="color: #999">${message.content}</div>
	</section>
</body>
</html>