<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="renderer" content="webkit" />

<title>一纸鹤互动家园下载</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/umeditor.css" type="text/css" rel="stylesheet">
</head>
<body>
	<div class="clear"></div>
	<div>
		<h3 id="focush2"></h3>
	</div>
	<script type="text/javascript">
		window.onload = function() {
			var u = navigator.userAgent;
			if (/(iPhone|iPad|iPod|iOS)/i.test(u)) {
					//来源微信，将访问提示使用safari浏览器打开下载
					if (isWeixin()) {
						window.location.href = "${pageContext.request.contextPath}/api/index/1.0/downapp?";
					} else {
						window.location.href = 'https://itunes.apple.com/cn/app/hu-dong-jia-yuan/id1135353234?l=en&mt=8';
					}
				
			} else if (/(Android)/i.test(u)) {
				window.location.href = 'http://a.app.qq.com/o/simple.jsp?pkgname=com.yizhihe.jyhd';

			} else {
				alert("未识别的设备！");
			}
		}
		
		 function isWeixin() {
	        var ua = navigator.userAgent.toLowerCase();
	        if (ua.match(/MicroMessenger/i) == "micromessenger") {
	            return true;
	        } else {
	            return false;
	        }
	   }
	</script>
</body>
</html>