<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>宝贝圈管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/cBaby/list");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="cBaby" action="${ctx}/sys/cBaby/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<label>家长：</label>
			<sys:treeselect id="userId" name="userId" value="${cBaby.userId}" labelName="cBaby.userId" labelValue="${cBaby.userName}"
					title="家长" url="/sys/user/treeDataForTeacher" allowClear="true"/>
		<label>班级：</label>
			<sys:treeselect id="classId" name="classId" value="${cBaby.classId}" labelName="cBaby.classId" labelValue="${cBaby.className}"
					title="班级" url="/sys/cclass/treeData" allowClear="true" isAll="1"/>
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>归属学校</th>
				<th class="sort-column a.class_id">归属班级</th>
				<th class="sort-column a.user_id">发布人</th>
				<th class="sort-column a.create_date">发布时间</th>
				<shiro:hasPermission name="sys:cBaby:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cBaby">
			<tr>
				<td>${cBaby.officeName}</td>
				<td>${cBaby.className}</td>
				<td>${cBaby.userName}</td>
				<td><fmt:formatDate value="${cBaby.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<shiro:hasPermission name="sys:cBaby:edit"><td>
    				<%-- <a href="${ctx}/sys/cBaby/form?id=${cBaby.id}">修改</a> --%>
					<a href="${ctx}/sys/cBaby/delete?id=${cBaby.id}" onclick="return confirmx('确认要删除该宝贝圈吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>