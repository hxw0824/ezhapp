<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function () {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/user/cUser/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
	    });
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/user/cUser/list");
			$("#searchForm").submit();
	    	return false;
        }
		
		function initPwd(id){
			$.post("initPwd", { 'id' : id }, function(data){
				alertx("密码已重置为：123456");
			} );
		}
	</script>
</head>
<body>
	<div id="importBox" class="hide">
	    <form id="importForm" action="${ctx}/user/cUser/import" method="post" enctype="multipart/form-data"
	          class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
	        <input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
	        <input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
	        <a href="${ctx}/user/cUser/import/template">下载模板</a>
	    </form>
	</div>
	
	<c:if test="${not empty cclass}">
		您所管理的班级名称：<h6 style="display:inline-block;">${cclass.year}.${cclass.name}</h6>
		<ul class="nav nav-tabs">
			<li class="active"><a href="${ctx}/user/cUser/">家长列表</a></li>
			<li><a href="${ctx}/user/cUser/form?id=${cUser.id}">添加家长</a></li>
		</ul>
		<form:form id="searchForm" modelAttribute="cUser" action="${ctx}/user/cUser/" method="post" class="breadcrumb form-search">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
			<ul class="ul-form">
				<li>
					<label>登录名：</label>
					<form:input path="loginName" htmlEscape="false" maxlength="11" class="input-medium"/>
					<label>手机号：</label>
					<form:input path="tel" htmlEscape="false" maxlength="11" class="input-medium"/>
				</li>
	
				<li>
					<label>冻结状态：</label>
					<form:select path="status" class="input-medium">
						<form:option value="">--全部--</form:option>
						<form:option value="0">未冻结</form:option>
						<form:option value="1">已冻结</form:option>
					</form:select>
				</li>
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
					<input id="btnImport" class="btn btn-primary" type="button" value="导入"/>
				</li>
				<li class="clearfix"></li>
			</ul>
		</form:form>
		<sys:message content="${message}"/>
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th class="sort-column loginName">登录名</th>
					<th>昵称</th>
					<th class="sort-column tel">手机号</th>
					<th class="sort-column status">冻结状态</th>
					<th>注册日期</th>
					<shiro:hasPermission name="user:cUser:edit"><th>操作</th></shiro:hasPermission>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${page.list}" var="cUser">
				<tr>
					<td>
						<a href="${ctx}/user/cUser/form?id=${cUser.id}">
							${cUser.loginName }
						</a>
					</td>
					<td>
						${cUser.nickName}
					</td>
					<td>
						${cUser.tel}
					</td>
					<td>
						<c:if test="${cUser.status == '0'}">未冻结</c:if>
						<c:if test="${cUser.status == '1'}">已冻结</c:if>
					</td>
					<td>
						<fmt:formatDate value="${cUser.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
	
					<shiro:hasPermission name="user:cUser:edit"><td>
						<c:if test="${cUser.status == '0'}"><a href="${ctx}/user/cUser/lock?id=${cUser.id}">冻结</a></c:if>
						<c:if test="${cUser.status == '1'}"><a href="${ctx}/user/cUser/unlock?id=${cUser.id}">解冻</a></c:if>
						<a href="javascript:initPwd('${cUser.id}')">重置密码</a>
	    				<a href="${ctx}/user/cUser/form?id=${cUser.id}">修改</a>
						<a href="${ctx}/user/cUser/delete?id=${cUser.id}" onclick="return confirmx('确认要删除该单表吗？', this.href)">删除</a>
					</td></shiro:hasPermission>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<div class="pagination">${page}</div>
	</c:if>
	<c:if test="${empty cclass}">
		<h2>您还没有管理班级</h2>
		<h2>请联系园长开通管理权限</h2>
	</c:if>
</body>
</html>