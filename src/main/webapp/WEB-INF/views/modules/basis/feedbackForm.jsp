<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>反馈管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/basis/feedback/">反馈列表</a></li>
		<li class="active"><a href="${ctx}/basis/feedback/form?id=${feedback.id}">反馈<shiro:hasPermission name="basis:feedback:edit">${not empty feedback.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="basis:feedback:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="feedback" action="${ctx}/basis/feedback/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				${feedback.name}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号：</label>
			<div class="controls">
				${feedback.phone}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱：</label>
			<div class="controls">
				${feedback.email}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">处理状态：</label>
			<div class="controls">
				<c:if test="${feedback.status == '0'}">未处理</c:if>
				<c:if test="${feedback.status == '1'}">已处理</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge " readOnly="true"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="basis:feedback:edit">
				<c:if test="${feedback.status == '0'}"><input id="btnSubmit" class="btn btn-primary" type="submit" value="处理完毕"/></c:if>
				<c:if test="${feedback.status == '1'}"><input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/></c:if>
			</shiro:hasPermission>
			<shiro:lacksPermission name="basis:feedback:edit">
				<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
			</shiro:lacksPermission>
		</div>
	</form:form>
</body>
</html>