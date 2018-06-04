<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>体温管理</title>
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
	<c:choose>
		<c:when test="${not empty cclass or curUser.userType eq fns:getDictValue('kindergarten','sys_user_type','')}">
			<form:form id="searchForm" modelAttribute="cTemperature" action="${ctx}/sys/cTemperature/" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<label>家长：</label>
					<sys:treeselect id="userId" name="userId" value="${cTemperature.userId}" labelName="cTemperature.userId" labelValue="${cTemperature.userName}"
							title="家长" url="/sys/user/treeDataForTeacher" allowClear="true"/>
				<c:if test="${curUser.userType eq fns:getDictValue('kindergarten','sys_user_type','')}">
					<label>班级：</label>
						<sys:treeselect id="classId" name="classId" value="${cTemperature.classId}" labelName="cTemperature.classId" labelValue="${cTemperature.className}"
								title="班级" url="/sys/cclass/treeData" allowClear="true" isAll="1"/>
				</c:if>
				<br/><br/>
				<label>日期范围：&nbsp;</label><input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
					value="<fmt:formatDate value="${cTemperature.beginDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<label>&nbsp;--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
					value="<fmt:formatDate value="${cTemperature.endDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginDate\')}',dateFmt:'yyyy-MM-dd',isShowClear:false});"/>&nbsp;&nbsp;
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>家长名称</th>
						<th>班级</th>
						<th>体温 / ℃</th>
						<th>测量时间</th>
						<th>备注</th>
						<%-- <shiro:hasPermission name="sys:cTemperature:edit"><th>操作</th></shiro:hasPermission> --%>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="cTemperature">
					<tr>
						<td>${cTemperature.userName}</td>
						<td>${cTemperature.className}</td>
						<td>${cTemperature.temperVal}</td>
						<td><fmt:formatDate value="${cTemperature.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${cTemperature.remarks}</td>
						<%-- <shiro:hasPermission name="sys:cTemperature:edit"><td>
		    				<a href="${ctx}/sys/cTemperature/form?id=${cTemperature.id}">修改</a>
							<a href="${ctx}/sys/cTemperature/delete?id=${cTemperature.id}" onclick="return confirmx('确认要删除该体温吗？', this.href)">删除</a>
						</td></shiro:hasPermission> --%>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="pagination">${page}</div>
		</c:when>
		<c:otherwise>
			<h2>您还没有管理班级</h2>
			<h2>请联系园长开通管理权限</h2>
		</c:otherwise>
	</c:choose>
</body>
</html>