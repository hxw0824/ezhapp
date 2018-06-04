<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户修改</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#loginName").focus();
			$("#inputForm").validate({
				rules: {
					loginName: {remote: "${ctx}/user/cUser/checkLoginName?oldLoginName=" + encodeURIComponent('${cUser.loginName}')},
					tel : {remote: "${ctx}/user/cUser/checkTel?oldTel=" + encodeURIComponent('${cUser.tel}')}
				},
				messages: {
					loginName: {remote: "家长登录名已存在"},
					tel: {remote: "家长手机号已存在"},
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
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

		function clearImage(id){
			$.post("clearImage", { 'id' : id, 'imageId': '', 'imageUrl': '' }, function(data){
				$("#image").attr("src","");
				alertx("清除成功");
			} );
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/user/cUser/">家长列表</a></li>
		<li class="active"><a href="${ctx}/user/cUser/form?id=${cUser.id}">家长${not empty cUser.id?'修改':'添加'}</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cUser" action="${ctx}/user/cUser/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">登录名：</label>
			<div class="controls">
				<input id="oldLoginName" name="oldLoginName" type="hidden" value="${cUser.loginName}">
				<form:input path="loginName" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<c:if test="${not empty cUser.id}">
			<div class="control-group">
				<label class="control-label">昵称：</label>
				<div class="controls">
					<form:input path="nickName" htmlEscape="false" maxlength="50"/>
				</div>
			</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">手机号：</label>
			<div class="controls">
				<input id="oldTel" name="oldTel" type="hidden" value="${cUser.tel}">
				<form:input path="tel" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">密码:</label>
			<div class="controls">
				<input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3" class="${empty cUser.id?'required':''}"/>
				<c:if test="${empty cUser.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
				<c:if test="${not empty cUser.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="3" equalTo="#newPassword"/>
				<c:if test="${empty cUser.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
			</div>
		</div>
		<c:if test="${not empty cUser.id}">
			<div class="control-group">
				<label class="control-label">状态：</label>
				<div class="controls">
					<input type="radio" name="status" <c:if test="${cUser.status == '0' }">checked="checked"</c:if> value="0"/>启用
					<input type="radio" name="status" <c:if test="${cUser.status == '1' }">checked="checked"</c:if> value="1"/>冻结
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">注册时间：</label>
				<div class="controls">
					<fmt:formatDate value='${cUser.createDate}' pattern='yyyy-MM-dd HH:mm:ss'/>
				</div>
			</div>
		</c:if>

		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>