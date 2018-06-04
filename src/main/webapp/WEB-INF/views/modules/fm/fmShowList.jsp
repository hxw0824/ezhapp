<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>家家秀管理</title>
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
		
		//未通过审核提示
		function unSuccessAudit(id){
			var html = '<div style="padding:10px;"><textarea id="auditReason" name="auditReason" cols="3" rows="3"></textarea></div>';  
			$.jBox(html, {
				width:240,
				height:180,
				title: '审核未通过原因',
				submit : function(v,h,f){
					if(v == 'ok'){
						window.location.href = '${ctx}/fm/fmShow/changeAudit?id='+id+'&auditStatus=2&auditReason='+f.auditReason;
					}
				},
				loaded: function (h) {
			       h.children(0).find('textarea').focus();
			    }
			});
		}
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="fmShow" action="${ctx}/fm/fmShow/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<label>资源名称：</label><form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			<label>上传用户：</label><form:select id="uid" path="uid" class="input-medium"><form:option value="" label=""/><form:options items="${allUserList}" itemValue="uid" itemLabel="cUser.nickName" htmlEscape="false"/></form:select>
			<label>所属栏目：</label><form:select id="resCode" path="resCode" class="input-medium"><form:option value="" label=""/><form:options items="${allItems}" itemValue="resCode" itemLabel="name" htmlEscape="false"/></form:select>
			
			<br/><br/>
			<label>日期范围：&nbsp;</label><input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
				value="<fmt:formatDate value="${fmShow.beginDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			<label>&nbsp;--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
				value="<fmt:formatDate value="${fmShow.endDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginDate\')}',dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>资源名称</th>
				<th>所属栏目</th>
				<th>上传用户</th>
				<th>资源</th>
				<th>上传时间</th>
				<shiro:hasPermission name="fm:fmShow:edit"><th>审核</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="fmShow">
			<tr>
				<td>
					${fmShow.name}
				</td>
				<td>
					<c:forEach items="${allItems}" var="item">
						<c:if test="${fmShow.resCode eq item.resCode}">${item.name}</c:if>
					</c:forEach>
				</td>
				<td>
					<c:forEach items="${allUserList}" var="user">
						<c:if test="${user.uid eq fmShow.uid}">${user.cUser.nickName}</c:if>
					</c:forEach>
				</td>
				<td>
					<a href="${fns:getDictValue('家家秀上传路径','homeshow_upload_path','')}${fmShow.fileUrl}" target="_blank">审查资源</a>
					
				</td>
				<td>
					<fmt:formatDate value="${fmShow.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="fm:fmShow:edit">
					<td>
	    				<a href="${ctx}/fm/fmShow/changeAudit?id=${fmShow.id}&auditStatus=0">通过审核</a> |
	    				<a href="javascript:void(0);" onclick="unSuccessAudit('${fmShow.id}');">未通过审核</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>