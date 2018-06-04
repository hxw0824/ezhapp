<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport"
	content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no" />
<meta name="apple-mobile-web-app-title" content="标题" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="screen-orientation" content="portrait" />
<meta name="x5-orientation" content="portrait" />
<title>iosSafari</title>
<style type="text/css">
.cover{
 position:absolute;
 top:0;
 left:0;
 width:100%;
 height:100%;
 text-align:center;
 background:url(../images/bg_06.png) center 5px no-repeat,
 url(../images/bg1_06.png);
}
.cover p{
 font-size:18px;
 line-height:24px;
 padding:60px 40px;
 padding-top:100px;
 color:#ecbf1e;
 text-shadow:1px 1px 1px rgba(255,255,255,0.5);
 background:url(../images/arrow.png) right 5px no-repeat;
}
</style>
<script type="text/javascript" src="${ctxStatic}/js/jquery-1.11.1.min.js"></script>
</head>
<body>
	<div class="cover">
		<p>点击右上角的按钮，选择在"Safari中打开",就可以下载一纸鹤互动家园。</p>
	</div>
</body>
<script type="text/javascript">
//跳转到苹果商城下载
  window.onload = function() {
	 window.location.href = 'https://itunes.apple.com/cn/app/hu-dong-jia-yuan/id1135353234?l=en&mt=8'; 	                                       
  }
</script>
</html>