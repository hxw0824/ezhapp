<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>通知表现管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/doubleChatMessage/list");
			$("#searchForm").submit();
	    	return false;
        }
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="doubleChatMessage" action="${ctx}/sys/doubleChatMessage/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<label>用户：</label>
			<sys:treeselect id="from" name="from" value="${doubleChatMessage.from}" labelName="doubleChatMessage.from" labelValue="${doubleChatMessage.userName}"
					title="用户" url="/sys/user/treeDataForNoTeacher" allowClear="true"/>
		<label>类型：</label>
			<form:select id="type" path="type" class="input-medium">
				<form:option value="" label=""/><form:options items="${fns:getDictList('sys_notice_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th class="sort-column a.user_id">发起人账号</th>
				<th>文本内容</th>
				<th>语音内容</th>
				<th>图文内容</th>
				<th class="sort-column a.type">类型</th>
				<th class="sort-column a.create_time">创建时间</th>
				<shiro:hasPermission name="sys:doubleChatMessage:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="doubleChatMessage">
			<tr>
				<td>${doubleChatMessage.userName}</td>
				<td>${fns:abbr(doubleChatMessage.text,60)}</td>
				<td>${doubleChatMessage.audio}</td>
				<td>${doubleChatMessage.image}</td>
				<td>${fns:getDictLabel(doubleChatMessage.type,'sys_notice_type','')}</td>
				<td><fmt:formatDate value="${doubleChatMessage.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<shiro:hasPermission name="sys:doubleChatMessage:edit"><td>
    				<%-- <a href="${ctx}/sys/doubleChatMessage/form?id=${doubleChatMessage.id}">修改</a> --%>
					<a href="${ctx}/sys/doubleChatMessage/delete?id=${doubleChatMessage.id}" onclick="return confirmx('确认要删除该通知（表现）吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>