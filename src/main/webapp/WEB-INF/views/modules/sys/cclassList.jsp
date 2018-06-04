<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>班级表管理</title>
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
		<li class="active"><a href="${ctx}/sys/cclass/">班级列表</a></li>
		<shiro:hasPermission name="sys:class:edit"><li><a href="${ctx}/sys/cclass/form">班级添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="CClass" action="${ctx}/sys/cclass/" method="post" class="breadcrumb form-search">
		<%-- <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/> --%>
		<label>名称：</label><input id="name" name="name" type="text" maxlength="50" style="width:180px;" value="${cclass.name}"/>
		<label>入学年份：</label><input id="year" name="year" type="text" maxlength="50" style="width:180px;" value="${cclass.year}"/><br/><br/>
		<label>学段：</label>
			<form:select id="period" path="period" class="input-medium"><form:option value="" label=""/><form:options items="${fns:getDictList('sys_period_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/></form:select>
		<label>负责老师：</label>
			<sys:treeselect id="teacherId" name="teacherId" value="${cclass.teacherId}" labelName="cclass.teacherId" labelValue="${cclass.teacherName}"
					title="教师" url="/sys/user/treeDataForClass" allowClear="true" notAllowSelectParent="true" isAll="1"/>
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>班级名称</th>
				<th>学段</th>
				<th>负责老师</th>
				<th>入学年份</th>
				<th>创建时间</th>
				<th>备注</th>
				<shiro:hasPermission name="sys:class:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="cclass">
			<tr>
				<td><a href="${ctx}/sys/cclass/form?id=${cclass.id}">
					${cclass.name}
				</a></td>
				<td>
					${fns:getDictLabel(cclass.period,'sys_period_type','')}
				</td>
				<td>
					${cclass.teacherName}
				</td>
				<td>
					${cclass.year}
				</td>
				<td>
					<fmt:formatDate value="${cclass.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${cclass.remarks}
				</td>
				<shiro:hasPermission name="sys:class:edit"><td>
    				<a href="${ctx}/sys/cclass/form?id=${cclass.id}">修改</a>
					<a href="${ctx}/sys/cclass/delete?id=${cclass.id}" onclick="return confirmx('确认要删除该班级吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<%-- <div class="pagination">${page}</div> --%>
</body>
</html>