<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>内容详情</title>
<link type="text/css" rel="stylesheet" href="${ctxStatic}/css/content-detail-share-style.css" />
</head>

<body>
<div class="section">
    <div class="video">
        <c:if test="${courseDetail.isFree != 'Y'}">
            <video src="" controls poster="${courseDetail.imageUrl}"></video> 
          	<p class="limit">您没有权限访问该视频,请注册并下载App播放</p>
        </c:if>
        <c:if test="${courseDetail.isFree == 'Y'}">
            <video src="${courseDetail.absVideoPath}" controls autoplay="autoplay" poster="${courseDetail.imageUrl}"></video> 
        </c:if>
    </div>
    <div class="book">
    	<img src="${book.imageUrl}"/>
        <span>${book.name}</span>
    </div>
     <h3>视频介绍</h3>
     <div class="videoContent">
	     <h4>${courseDetail.courseName}</h4>
	     <p>${courseDetail.content}</p>
     </div>
     <div class="download">
          <img src="${ctxStatic}/images/ic_launcher.png"/>
          <a class="more" href="http://a.app.qq.com/o/simple.jsp?pkgname=com.yizhihe.jyhd">更多精彩内容，请下载互动家园客户端</a>
          <div class="clear"></div>
     </div>
</div>
</body>
</html>
