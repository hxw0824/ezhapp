<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>监控管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/sysMonitor/list");
			$("#searchForm").submit();
	    	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/sysMonitor/">监控列表</a></li>
		<shiro:hasPermission name="sys:sysMonitor:edit"><li><a href="${ctx}/sys/sysMonitor/form">监控添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="sysMonitor" action="${ctx}/sys/sysMonitor/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<label>名称：</label><input id="name" name="name" type="text" maxlength="50" value="${sysMonitor.name}"/>
		<label>设备编号：</label><input id="deviceId" name="deviceId" type="text" maxlength="50" value="${sysMonitor.deviceId}"/><br/><br/>
		<label>班级：</label>
			<sys:treeselect id="classId" name="classId" value="${sysMonitor.classId}" labelName="sysMonitor.classId" labelValue="${sysMonitor.className}"
					title="班级" url="/sys/cclass/treeData" allowClear="true" isAll="1"/>
		<label>设备状态：</label>
			<form:select id="status" path="status" class="input-medium">
				<form:option value="" label=""/><form:options items="${fns:getDictList('sys_status_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th class="sort-column deviceId">设备编号</th>
				<th>归属班级</th>
				<th>IP地址</th>
				<th class="sort-column status">设备状态</th>
				<th>开启时间</th>
				<th>修改时间</th>
				<th>备注</th>
				<shiro:hasPermission name="sys:sysMonitor:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysMonitor">
			<tr>
				<td><a href="${ctx}/sys/sysMonitor/form?id=${sysMonitor.id}">${sysMonitor.name}</a></td>
				<td>${sysMonitor.deviceId}</td>
				<td>${sysMonitor.className}</td>
				<td>${fns:abbr(sysMonitor.ip,80)}</td>
				<td>${fns:getDictLabel(sysMonitor.status,'sys_status_type','')}</td>
				<td>
					${sysMonitor.beginTime}:00 -- ${sysMonitor.endTime}:00
				</td>
				<td>
					<fmt:formatDate value="${sysMonitor.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>${sysMonitor.remarks}</td>
				<shiro:hasPermission name="sys:sysMonitor:edit"><td>
					<a href="${ctx}/sys/sysMonitor/save?id=${sysMonitor.id}&status=${sysMonitor.status}&changeStatus=1">${sysMonitor.status eq 0 ? '关闭' : '开启'}</a>
    				<a href="${ctx}/sys/sysMonitor/form?id=${sysMonitor.id}">修改</a>
					<a href="${ctx}/sys/sysMonitor/delete?id=${sysMonitor.id}" onclick="return confirmx('确认要删除该监控吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>