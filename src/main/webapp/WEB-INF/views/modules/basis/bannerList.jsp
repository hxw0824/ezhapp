<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>焦点图管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/basis/banner/">焦点图列表</a></li>
		<shiro:hasPermission name="basis:banner:edit"><li><a href="${ctx}/basis/banner/form">焦点图添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="banner" action="${ctx}/basis/banner/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>标题</th>
				<th>图片</th>
				<th>链接地址</th>
				<th>排序</th>
				<shiro:hasPermission name="basis:banner:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="banner">
			<tr>
				<td><a href="${ctx}/basis/banner/form?id=${banner.id}">
					${banner.title}
				</a></td>
				<td>
					<img id="image" style="max-width:300px;" alt="" src="${banner.imageUrl}">
				</td>
				<td>
					${banner.link}
				</td>
				<td>
					${banner.sort}
				</td>
				<shiro:hasPermission name="basis:banner:edit"><td>
    				<a href="${ctx}/basis/banner/form?id=${banner.id}">修改</a>
					<a href="${ctx}/basis/banner/delete?id=${banner.id}" onclick="return confirmx('确认要删除该焦点图吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>