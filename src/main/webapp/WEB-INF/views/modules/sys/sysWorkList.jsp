<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>打卡机表管理</title>
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
		<li class="active"><a href="${ctx}/sys/sysWork/">打卡机列表</a></li>
		<shiro:hasPermission name="sys:sysWork:edit"><li><a href="${ctx}/sys/sysWork/form">打卡机添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="sysWork" action="${ctx}/sys/sysWork/" method="post" class="breadcrumb form-search">
		<label>设备编号：</label><form:input path="deviceId" htmlEscape="false" maxlength="100" class="input-medium"/>
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>设备编号</th>
				<th>广告轮播图</th>
				<th>广告语</th>
				<th>位置信息</th>
				<th>创建时间</th>
				<th>备注</th>
				<shiro:hasPermission name="sys:sysWork:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="sysWork">
			<tr>
				<td><a href="${ctx}/sys/sysWork/form?id=${sysWork.id}">
					${sysWork.deviceId}
				</a></td>
				<td>
					${fns:abbr(sysWork.images,50)}
				</td>
				<td>
					${sysWork.language}
				</td>
				<td>
					${sysWork.position}
				</td>
				<td>
					<fmt:formatDate value="${sysWork.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${sysWork.remarks}
				</td>
				<shiro:hasPermission name="sys:sysWork:edit"><td>
    				<a href="${ctx}/sys/sysWork/form?id=${sysWork.id}">修改</a>
					<a href="${ctx}/sys/sysWork/delete?id=${sysWork.id}" onclick="return confirmx('确认要删除该打卡机吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>