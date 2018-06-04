<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>授权卡管理</title>
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

        function toAdd() {
            window.location = "${ctx}/basis/card/form";
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/basis/card/">授权卡列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="card" action="${ctx}/basis/card/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">

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
			<li class="btns"><input id="addCard" class="btn btn-primary"  type="button" value="生成授权卡" onclick="toAdd()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>授权卡编号</th>
				<th>授权卡密码</th>
				<th>授权卡对应学段</th>
				<th>生成日期</th>
				<th>已使用次数</th>
				<shiro:hasPermission name="basis:card:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="card">
			<tr>
				<td>
					${card.cardNo}
				</td>
				<td>
					${card.pwd}
				</td>
				<td>
					<c:if test="${card.periodId =='0'}">小班</c:if>
					<c:if test="${card.periodId =='1'}">中班</c:if>
					<c:if test="${card.periodId =='2'}">大班</c:if>
					
				</td>
				<td>
					<fmt:formatDate value="${card.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${card.useNum} 
				</td>
				<shiro:hasPermission name="basis:card:edit"><td>
					<a href="${ctx}/basis/card/delete?id=${card.id}" onclick="return confirmx('确认要删除该授权卡吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>