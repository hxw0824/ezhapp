<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>授权卡使用情况管理</title>
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
		<li class="active"><a href="${ctx}/basis/cUserCard/">授权卡使用情况列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="cUserCard" action="${ctx}/basis/cUserCard/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户手机号：</label>
				<form:input path="loginName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
	
			<li><label>授权卡密码：</label>
				<form:input path="pwd" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>对应学段：</label>
				<form:select path="periodId">
				<form:option value="">全部</form:option>
					<form:option value="0">小班</form:option>
					<form:option value="1">中班</form:option>
					<form:option value="2">大班</form:option>
					
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>手机号/ID</th>
				<th>用户昵称</th>
				<th>授权卡密码</th>
				<th>授权卡对应学段</th>
				<th>绑定日期</th>
				<shiro:hasPermission name="basis:cUserCard:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userCard">
			<tr>
				<td>
					${userCard.loginName}
				</td>
				<td>
					${userCard.nickName}
				</td>
				<td>
					${userCard.pwd}
				</td>
				<td>
					<c:if test="${userCard.periodId =='0'}">小班</c:if>
					<c:if test="${userCard.periodId =='1'}">中班</c:if>
					<c:if test="${userCard.periodId =='2'}">大班</c:if>
					
				</td>
				<td>
					<fmt:formatDate value="${userCard.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="basis:cUserCard:edit"><td>
					<a href="${ctx}/basis/cUserCard/delete?id=${userCard.id}" onclick="return confirmx('确认要删除该授权卡吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>