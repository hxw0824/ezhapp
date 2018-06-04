<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>反馈管理</title>
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
		<li class="active"><a href="${ctx}/basis/feedback/">反馈列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="feedback" action="${ctx}/basis/feedback/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>处理状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部"/>
					<form:option value="0" label="未处理"/>
					<form:option value="1" label="已处理"/>
				</form:select>
			</li>
			<li><label>邮箱：</label>
				<form:input path="email" htmlEscape="false" maxlength="500" class="input-medium"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>手机号：</label>
				<form:input path="phone" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>处理人：</label>
				<form:input path="approveUser.name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<th>手机号</th>
				<th>邮箱</th>
				<th>处理状态</th>
				<th>处理人</th>
				<th>创建时间</th>
				<shiro:hasPermission name="basis:feedback:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="feedback">
			<tr>
				<td><a href="${ctx}/basis/feedback/form?id=${feedback.id}">
					${feedback.name}
				</a></td>
				<td>
					${feedback.phone}
				</td>
				<td>
					${feedback.email}
				</td>
				<td>
					<c:if test="${feedback.status == '0'}">未处理</c:if>
					<c:if test="${feedback.status == '1'}">已处理</c:if>
				</td>
				<td>
					${feedback.approveUser.name}
				</td>
				<td>
					<fmt:formatDate value="${feedback.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="basis:feedback:edit"><td>
    				<a href="${ctx}/basis/feedback/form?id=${feedback.id}">修改</a>
					<a href="${ctx}/basis/feedback/delete?id=${feedback.id}" onclick="return confirmx('确认要删除该反馈吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>